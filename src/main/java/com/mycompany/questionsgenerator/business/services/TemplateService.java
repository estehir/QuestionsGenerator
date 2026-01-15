package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.api.dtos.*;
import com.mycompany.questionsgenerator.business.interfaces.ITemplateService;
import com.mycompany.questionsgenerator.business.models.Template;
import com.mycompany.questionsgenerator.business.models.TemplateVersion;
import com.mycompany.questionsgenerator.business.models.TemplateVersionVariable;
import com.mycompany.questionsgenerator.business.models.Variable;
import com.mycompany.questionsgenerator.persistence.interfaces.ITemplateRepository;
import com.mycompany.questionsgenerator.persistence.interfaces.ITemplateVersionRepository;
import com.mycompany.questionsgenerator.persistence.interfaces.IVariableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateService implements ITemplateService {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(\\w+)}}");

    private final ITemplateRepository templateRepository;
    private final ITemplateVersionRepository templateVersionRepository;
    private final IVariableRepository variableRepository;

    @Override
    @Transactional(readOnly = true)
    public TemplateResponseDTO getTemplateById(Long templateId) {

        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new NoSuchElementException("Template not found"));

        int latestVersion = template.getVersions().stream()
                .mapToInt(TemplateVersion::getVersion)
                .max()
                .orElse(0);

        return TemplateResponseDTO.builder()
                .id(template.getId())
                .name(template.getName())
                .latestVersion(latestVersion)
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "latestTemplateVersion", allEntries = true)
    public TemplateVersionResponseDTO createTemplateVersion(TemplateRequestDTO request) {

        // 1️⃣ Buscar ou criar o container Template
        Template template = templateRepository
                .findByName(request.getName())
                .orElseGet(() -> {
                    Template t = new Template();
                    t.setName(request.getName());
                    return t;
                });

        // 2️⃣ Calcular próxima versão
        int nextVersion = template.getVersions().isEmpty()
                ? 1
                : template.getVersions().stream()
                .mapToInt(TemplateVersion::getVersion)
                .max()
                .getAsInt() + 1;

        // 3️⃣ Criar nova versão
        TemplateVersion version = TemplateVersion.builder()
                .template(template)
                .version(nextVersion)
                .templateText(request.getTemplateText())
                .build();

        // 4️⃣ Extrair variáveis do texto
        Set<String> codesInText =
                extractCodesFromText(request.getTemplateText());

        // 5️⃣ Buscar variáveis existentes
        List<Variable> variables =
                variableRepository.findByCodeIn(codesInText);

        if (variables.size() != codesInText.size()) {
            throw new IllegalArgumentException(
                    "Template contains unknown variables"
            );
        }

        Map<String, Variable> variableMap = variables.stream()
                .collect(Collectors.toMap(
                        Variable::getCode,
                        v -> v
                ));

        // 6️⃣ Validar configuração enviada
        Map<String, TemplateVariableConfigDTO> configMap =
                request.getVariables().stream()
                        .collect(Collectors.toMap(
                                TemplateVariableConfigDTO::getCode,
                                v -> v
                        ));

        if (!configMap.keySet().equals(codesInText)) {
            throw new IllegalArgumentException(
                    "Mismatch between variables in text and variables configuration"
            );
        }

        // 7️⃣ Criar vínculos TemplateVersionVariable
        for (String code : codesInText) {
            TemplateVariableConfigDTO cfg = configMap.get(code);
            TemplateVersionVariable tv = TemplateVersionVariable.builder()
                    .templateVersion(version)
                    .variable(variableMap.get(code))
                    .required(cfg.getRequired())
                    .build();
            version.getVariables().add(tv);
        }

        // 8️⃣ Associar versão ao template
        template.getVersions().add(version);

        // 9️⃣ Persistir (cascade resolve tudo)
        Template saved = templateRepository.save(template);

        TemplateVersion createdVersion = saved.getVersions().stream()
                .max(Comparator.comparingInt(TemplateVersion::getVersion))
                .orElseThrow();

        return toResponse(createdVersion);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "latestTemplateVersion", key = "#templateId")
    public TemplateVersionResponseDTO getLatestTemplateVersion(Long templateId) {
        TemplateVersion latestVersion = templateVersionRepository
                .findByTemplateId(
                        templateId,
                        PageRequest.of(
                                0,
                                1,
                                Sort.by(Sort.Direction.DESC, "version")))
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Template not found"));

        return toResponse(latestVersion);
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateVersionResponseDTO getTemplateVersion(Long templateId, Integer versionId) {
        TemplateVersion templateVersion =
                templateVersionRepository
                        .findByTemplateIdAndVersion(templateId, versionId)
                        .orElseThrow(() ->
                                new NoSuchElementException("Template version not found")
                        );

        return toResponse(templateVersion);
    }

    // 🔹 helpers

    private Set<String> extractCodesFromText(String text) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
        Set<String> codes = new HashSet<>();
        while (matcher.find()) {
            codes.add(matcher.group(1));
        }
        return codes;
    }

    private TemplateVersionResponseDTO toResponse(TemplateVersion version) {
        Template template = version.getTemplate();

        return TemplateVersionResponseDTO.builder()
            .templateId(template.getId())
            .templateName(template.getName())
            .versionId(version.getId())
            .version(version.getVersion())
            .templateText(version.getTemplateText())
            .createdAt(version.getCreatedAt())
            .variables(
                    version.getVariables().stream()
                            .map(tv -> TemplateVariableResponseDTO.builder()
                                    .code(tv.getVariable().getCode())
                                    .description(tv.getVariable().getDescription())
                                    .required(tv.getRequired())
                                    .valueType(tv.getVariable().getValueType().name())
                                    .build()
                            )
                            .toList()
            )
            .build();
    }

}
