package com.mycompany.questionsgenerator.api.controllers;

import com.mycompany.questionsgenerator.api.dtos.GeneratePromptRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.GenerateQuestionResponseDTO;
import com.mycompany.questionsgenerator.business.interfaces.IQuestionGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/questions")
@RequiredArgsConstructor
public class QuestionGenerationController {

    private final IQuestionGenerationService questionGenerationService;

    @PostMapping("/generate")
    public ResponseEntity<GenerateQuestionResponseDTO> generate(@RequestBody GeneratePromptRequestDTO request) {
        return ResponseEntity.ok(
                questionGenerationService.generateQuestion(request)
        );
    }
}
