package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.api.dtos.GeneratePromptRequestDTO;
import com.mycompany.questionsgenerator.business.interfaces.IPromptGenerationService;
import com.mycompany.questionsgenerator.business.models.*;
import com.mycompany.questionsgenerator.business.models.enums.VariableValueType;
import com.mycompany.questionsgenerator.business.vo.PromptGenerationVO;
import com.mycompany.questionsgenerator.persistence.interfaces.IPromptDefinitionRepository;
import com.mycompany.questionsgenerator.persistence.interfaces.ITemplateVersionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PromptGenerationService implements IPromptGenerationService {

    private final ITemplateVersionRepository templateVersionRepository;
    private final IPromptDefinitionRepository promptDefinitionRepository;

    @Override
    @Transactional(readOnly = true)
    public PromptGenerationVO generatePrompt(GeneratePromptRequestDTO request) {

        // 1️⃣ Buscar TemplateVersion com variáveis
        TemplateVersion templateVersion = templateVersionRepository
            .findByTemplateId(
                    request.getTemplateId(),
                    PageRequest.of(
                            0,
                            1,
                            Sort.by(Sort.Direction.DESC, "version")))
            .stream()
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Template not found"));

        Map<String, String> userValues = request.getVariables();
        Map<String, String> resolvedValues = new HashMap<>();

        // 2️⃣ Validar e resolver variáveis
        for (TemplateVersionVariable tv : templateVersion.getVariables()) {

            Variable variable = tv.getVariable();
            String code = variable.getCode();

            String userValue = userValues.get(code);

            if (tv.getRequired() && (userValue == null || userValue.isBlank())) {
                throw new IllegalArgumentException(
                        "Missing required variable: " + code
                );
            }

            if (userValue != null) {
                validateValue(variable, userValue);
                resolvedValues.put(
                        code,
                        resolveValue(variable, userValue)
                );
            }
        }

        // 3️⃣ Substituir placeholders
        StringSubstitutor substitutor =
                new StringSubstitutor(resolvedValues, "{{", "}}");

        // 4️⃣ Buscar prompt base ativo
        PromptDefinition promptBase =
                promptDefinitionRepository.findByActiveTrue()
                        .orElseThrow(() ->
                                new IllegalStateException("No active prompt")
                        );

        // 5️⃣ Montar prompt final
        String finalPrompt = promptBase.getText()
                + "\n\n---\n\n"
                + substitutor.replace(templateVersion.getTemplateText());

        return PromptGenerationVO.builder()
                .templateId(request.getTemplateId())
                .templateVersionId(templateVersion.getId())
                .promptDefinitionId(promptBase.getId())
                .variablesSnapshot(request.getVariables())
                .finalPrompt(finalPrompt)
                .build();
    }

    // 🔹 helpers

    private void validateValue(Variable variable, String value) {

        VariableValueType type = variable.getValueType();

        switch (type) {
            case TEXT -> {
                // nada a validar
            }
            case NUMBER -> {
                try {
                    Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "Invalid numeric value for variable " + variable.getCode()
                    );
                }
            }
            case ENUM -> {
                boolean exists = variable.getOptions().stream()
                        .map(VariableOption::getCode)
                        .anyMatch(code -> code.equals(value));

                if (!exists) {
                    throw new IllegalArgumentException(
                            "Invalid option for variable " + variable.getCode()
                    );
                }
            }
        }
    }

    private String resolveValue(Variable variable, String value) {

        if (variable.getValueType() == VariableValueType.ENUM) {
            return variable.getOptions().stream()
                    .filter(opt -> opt.getCode().equals(value))
                    .findFirst()
                    .map(VariableOption::getLabel)
                    .orElse(value);
        }

        return value;
    }
}

