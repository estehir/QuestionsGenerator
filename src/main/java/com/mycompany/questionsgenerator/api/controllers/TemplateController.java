package com.mycompany.questionsgenerator.api.controllers;

import com.mycompany.questionsgenerator.api.dtos.TemplateRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.TemplateResponseDTO;
import com.mycompany.questionsgenerator.business.interfaces.ITemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final ITemplateService templateService;

    /**
     * Create or update a template (backoffice).
     * The template name is the business key.
     */
    @PostMapping
    public ResponseEntity<TemplateResponseDTO> createOrUpdate(
            @RequestBody TemplateRequestDTO request) {

        TemplateResponseDTO response =
                templateService.createOrUpdateTemplate(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Get template by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponseDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                templateService.getTemplateById(id)
        );
    }
}
