package com.mycompany.questionsgenerator.api.controllers;

import com.mycompany.questionsgenerator.api.dtos.GeneratePromptRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.GeneratePromptResponseDTO;
import com.mycompany.questionsgenerator.business.interfaces.IPromptGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/prompts/generate")
@RequiredArgsConstructor
public class PromptGenerationController {

    private final IPromptGenerationService promptGenerationService;

    @PostMapping
    public ResponseEntity<GeneratePromptResponseDTO> generate(
            @RequestBody GeneratePromptRequestDTO request
    ) {
        String prompt = promptGenerationService.generatePrompt(request);

        return ResponseEntity.ok(
                GeneratePromptResponseDTO.builder()
                        .prompt(prompt)
                        .build()
        );
    }
}
