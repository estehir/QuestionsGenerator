package com.mycompany.questionsgenerator.api.controllers;

import com.mycompany.questionsgenerator.api.dtos.TemplateRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.TemplateResponseDTO;
import com.mycompany.questionsgenerator.business.interfaces.ITemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    private final ITemplateService templateService;

    public TemplateController(ITemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    public ResponseEntity<TemplateResponseDTO> create(
            @RequestBody TemplateRequestDTO request) {
        return ResponseEntity.ok(templateService.createTemplate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.getTemplateById(id));
    }

}
