package com.mycompany.questionsgenerator.api.controllers;

import com.mycompany.questionsgenerator.api.dtos.TemplateRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.TemplateResponseDTO;
import com.mycompany.questionsgenerator.api.dtos.TemplateVersionResponseDTO;
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
     * Get template by template ID.
     */
    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateResponseDTO> getTemplateById(@PathVariable Long templateId) {
        return ResponseEntity.ok(templateService.getTemplateById(templateId));
    }

    /**
     * Get a specific versionId of a template by template ID and versionId ID.
     */
    @GetMapping("/{templateId}/versions/{versionId}")
    public ResponseEntity<TemplateVersionResponseDTO> getVersion(
            @PathVariable Long templateId, @PathVariable Integer versionId) {
        return ResponseEntity.ok(
                templateService.getTemplateVersion(templateId, versionId)
        );
    }

    /**
     * Get latest version of a template by template ID.
     */
    @GetMapping("/{templateId}/versions/latest")
    public ResponseEntity<TemplateVersionResponseDTO> getLatestVersion(@PathVariable Long templateId) {
        return ResponseEntity.ok(templateService.getLatestTemplateVersion(templateId));
    }

    /**
     * Create a new template version (backoffice).
     */
    @PostMapping
    public ResponseEntity<TemplateVersionResponseDTO> createVersion(@RequestBody TemplateRequestDTO request) {
        TemplateVersionResponseDTO response =
                templateService.createTemplateVersion(request);

        return ResponseEntity.ok(response);
    }

}
