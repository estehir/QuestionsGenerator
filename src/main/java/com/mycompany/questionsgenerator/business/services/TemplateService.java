package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.api.dtos.*;
import com.mycompany.questionsgenerator.business.interfaces.ITemplateService;
import com.mycompany.questionsgenerator.business.models.*;
import com.mycompany.questionsgenerator.persistence.interfaces.ITemplateRepository;
import com.mycompany.questionsgenerator.persistence.interfaces.IVariableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateService implements ITemplateService {

    private static final Pattern PLACEHOLDER_PATTERN =
            Pattern.compile("\\{\\{(\\w+)}}");

    private final ITemplateRepository templateRepository;
    private final IVariableRepository variableRepository;

    @Override
    @Transactional
    public TemplateResponseDTO createOrUpdateTemplate(TemplateRequestDTO request) {

        Template template = templateRepository
                .findByName(request.getName())
                .orElseGet(() -> Template.builder().name(request.getName()).build());

        template.setTemplateText(request.getTemplateText());

        // 1️⃣ Extrair variáveis do texto
        Set<String> codesInText = extractCodesFromText(request.getTemplateText());

        // 2️⃣ Buscar variáveis existentes
        List<Variable> variables = variableRepository.findByCodeIn(codesInText);

        if (variables.size() != codesInText.size()) {
            throw new IllegalArgumentException("Template contains unknown variables");
        }

        Map<String, Variable> variableMap = variables.stream()
                .collect(Collectors.toMap(Variable::getCode, v -> v));

        // 3️⃣ Validar configuração enviada
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

        // 4️⃣ Sincronizar TemplateVariable
        template.clearVariables();

        templateRepository.flush();

        for (String code : codesInText) {
            TemplateVariableConfigDTO cfg = configMap.get(code);

            TemplateVariable tv = TemplateVariable.builder()
                    .template(template)
                    .variable(variableMap.get(code))
                    .required(cfg.getRequired())
                    .build();

            template.addVariable(tv);
        }

        Template saved = templateRepository.save(template);

        return toResponse(saved);
    }

    @Override
    public TemplateResponseDTO getTemplateById(Long id) {
        return templateRepository.findByIdWithVariables(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Template not found"));
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

    private TemplateResponseDTO toResponse(Template template) {
        return TemplateResponseDTO.builder()
                .id(template.getId())
                .name(template.getName())
                .templateText(template.getTemplateText())
                .createdAt(template.getCreatedAt())
                .updatedAt(template.getUpdatedAt())
                .variables(
                        template.getVariables().stream()
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
