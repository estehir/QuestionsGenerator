package com.mycompany.questionsgenerator.api.controllers;

import com.mycompany.questionsgenerator.api.dtos.VariableRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.VariableResponseDTO;
import com.mycompany.questionsgenerator.business.interfaces.IVariableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variables")
@RequiredArgsConstructor
public class VariableController {

    private final IVariableService variableService;

    /**
     * Create a new variable.
     */
    @PostMapping
    public ResponseEntity<VariableResponseDTO> create(
            @RequestBody VariableRequestDTO request) {

        return ResponseEntity.ok(
                variableService.createVariable(request)
        );
    }

    /**
     * Update an existing variable.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VariableResponseDTO> update(
            @PathVariable Long id,
            @RequestBody VariableRequestDTO request) {

        return ResponseEntity.ok(
                variableService.updateVariable(id, request)
        );
    }

    /**
     * Get variable by code.
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<VariableResponseDTO> getByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                variableService.getByCode(code)
        );
    }

    /**
     * List all variables.
     */
    @GetMapping
    public ResponseEntity<List<VariableResponseDTO>> listAll() {

        return ResponseEntity.ok(
                variableService.listAll()
        );
    }
}
