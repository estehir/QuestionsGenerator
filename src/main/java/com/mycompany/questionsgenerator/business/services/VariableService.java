package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.api.dtos.*;
import com.mycompany.questionsgenerator.business.interfaces.IVariableService;
import com.mycompany.questionsgenerator.business.models.Variable;
import com.mycompany.questionsgenerator.business.models.VariableOption;
import com.mycompany.questionsgenerator.business.models.enums.VariableValueType;
import com.mycompany.questionsgenerator.persistence.interfaces.IVariableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariableService implements IVariableService {

    private final IVariableRepository variableRepository;

    @Override
    @Transactional
    public VariableResponseDTO createVariable(VariableRequestDTO request) {

        validateRequest(request);

        if (variableRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Variable code already exists");
        }

        Variable variable = buildVariable(request);

        Variable saved = variableRepository.save(variable);

        return toResponse(saved);
    }

    @Override
    @Transactional
    public VariableResponseDTO updateVariable(Long id, VariableRequestDTO request) {

        validateRequest(request);

        Variable variable = variableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Variable not found"));

        variable.setDescription(request.getDescription());
        variable.setPlaceholder(request.getPlaceholder());
        variable.setValueType(
                VariableValueType.valueOf(request.getValueType())
        );

        variable.clearOptions();

        variableRepository.flush();

        if (variable.getValueType() == VariableValueType.ENUM) {
            for (VariableOption option : buildOptions(variable, request.getOptions())) {
                variable.addOption(option);
            }
        }

        return toResponse(variable);
    }

    @Override
    public VariableResponseDTO getByCode(String code) {
        return variableRepository.findByCodeWithOptions(code)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Variable not found"));
    }

    @Override
    public List<VariableResponseDTO> listAll() {
        return variableRepository.findAllWithOptions()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // -----------------------
    // Validation & Mapping
    // -----------------------

    private void validateRequest(VariableRequestDTO request) {

        VariableValueType type =
                VariableValueType.valueOf(request.getValueType());

        if (type == VariableValueType.ENUM) {
            if (request.getOptions() == null || request.getOptions().isEmpty()) {
                throw new IllegalArgumentException(
                        "ENUM variable must have options"
                );
            }
        } else {
            if (request.getOptions() != null && !request.getOptions().isEmpty()) {
                throw new IllegalArgumentException(
                        "Only ENUM variables can have options"
                );
            }
        }
    }

    private Variable buildVariable(VariableRequestDTO request) {

        Variable variable = Variable.builder()
                .code(request.getCode())
                .description(request.getDescription())
                .placeholder(request.getPlaceholder())
                .valueType(VariableValueType.valueOf(request.getValueType()))
                .build();

        if (variable.getValueType() == VariableValueType.ENUM) {
            for (VariableOption option : buildOptions(variable, request.getOptions())) {
                variable.addOption(option);
            }
        }

        return variable;
    }

    private List<VariableOption> buildOptions(
            Variable variable,
            List<VariableOptionRequestDTO> dtos) {

        return dtos.stream()
                .map(dto -> VariableOption.builder()
                        .variable(variable)
                        .code(dto.getCode())
                        .label(dto.getLabel())
                        .numericValue(dto.getNumericValue())
                        .orderIndex(dto.getOrderIndex())
                        .active(true)
                        .build()
                )
                .toList();
    }

    private VariableResponseDTO toResponse(Variable variable) {

        return VariableResponseDTO.builder()
                .id(variable.getId())
                .code(variable.getCode())
                .description(variable.getDescription())
                .placeholder(variable.getPlaceholder())
                .valueType(variable.getValueType().name())
                .options(
                        variable.getOptions() == null
                                ? List.of()
                                : variable.getOptions().stream()
                                .map(opt -> VariableOptionResponseDTO.builder()
                                        .code(opt.getCode())
                                        .label(opt.getLabel())
                                        .numericValue(opt.getNumericValue())
                                        .orderIndex(opt.getOrderIndex())
                                        .active(opt.getActive())
                                        .build()
                                )
                                .toList()
                )
                .build();
    }
}
