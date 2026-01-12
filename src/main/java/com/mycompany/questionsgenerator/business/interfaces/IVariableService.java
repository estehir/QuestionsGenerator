package com.mycompany.questionsgenerator.business.interfaces;

import com.mycompany.questionsgenerator.api.dtos.VariableRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.VariableResponseDTO;

import java.util.List;

public interface IVariableService {

    VariableResponseDTO createVariable(VariableRequestDTO request);

    VariableResponseDTO updateVariable(Long id, VariableRequestDTO request);

    VariableResponseDTO getByCode(String code);

    List<VariableResponseDTO> listAll();
}

