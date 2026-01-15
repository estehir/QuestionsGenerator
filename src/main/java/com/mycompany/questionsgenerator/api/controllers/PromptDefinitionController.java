package com.mycompany.questionsgenerator.api.controllers;

import com.mycompany.questionsgenerator.api.dtos.PromptDefinitionRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.PromptDefinitionResponseDTO;
import com.mycompany.questionsgenerator.business.interfaces.IPromptDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/prompts/define")
@RequiredArgsConstructor
public class PromptDefinitionController {

    private final IPromptDefinitionService promptDefinitionService;

    @GetMapping("/active")
    public ResponseEntity<PromptDefinitionResponseDTO> getActive() {
        return ResponseEntity.ok(promptDefinitionService.getActive());
    }

    @GetMapping
    public ResponseEntity<List<PromptDefinitionResponseDTO>> getAll() {
        return ResponseEntity.ok(promptDefinitionService.getAll());
    }
    @PostMapping
    public ResponseEntity<PromptDefinitionResponseDTO> create(@RequestBody PromptDefinitionRequestDTO request) {
        return ResponseEntity.ok(promptDefinitionService.create(request));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<PromptDefinitionResponseDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok(promptDefinitionService.activate(id));
    }

}

