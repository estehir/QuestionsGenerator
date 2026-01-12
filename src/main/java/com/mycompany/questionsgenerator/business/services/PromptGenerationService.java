package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.api.dtos.GeneratePromptRequestDTO;
import com.mycompany.questionsgenerator.business.interfaces.IPromptGenerationService;
import com.mycompany.questionsgenerator.business.models.Template;
import com.mycompany.questionsgenerator.business.models.TemplateVariable;
import com.mycompany.questionsgenerator.business.models.Variable;
import com.mycompany.questionsgenerator.business.models.VariableOption;
import com.mycompany.questionsgenerator.business.models.enums.VariableValueType;
import com.mycompany.questionsgenerator.persistence.interfaces.ITemplateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PromptGenerationService implements IPromptGenerationService {

    private final ITemplateRepository templateRepository;

    @Override
    @Transactional
    public String generatePrompt(GeneratePromptRequestDTO request) {

        Template template = templateRepository.findByIdWithVariables(request.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));

        Map<String, String> userValues = request.getVariables();

        Map<String, String> resolvedValues = new HashMap<>();

        for (TemplateVariable tv : template.getVariables()) {

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
                resolvedValues.put(code, resolveValue(variable, userValue));
            }
        }

        // Substituição usando Apache Commons Text
        StringSubstitutor substitutor =
                new StringSubstitutor(resolvedValues, "{{", "}}");

        return substitutor.replace(template.getTemplateText());
    }

    private void validateValue(Variable variable, String value) {

        VariableValueType type = variable.getValueType();

        switch (type) {
            case TEXT -> {
                // nada a validar por enquanto
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
