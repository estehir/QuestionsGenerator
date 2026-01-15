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
     * Create or update a template version (backoffice).
     * The template name is the business key.
     */
    @PostMapping
    public ResponseEntity<TemplateVersionResponseDTO> create(
            @RequestBody TemplateRequestDTO request) {

        TemplateVersionResponseDTO response =
                templateService.createTemplateVersion(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Get template by template ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponseDTO> getTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.getTemplateById(id));
    }

    /**
     * Get latest version of a template by template ID.
     */
    @GetMapping("/{id}/versions/latest")
    public ResponseEntity<TemplateVersionResponseDTO> getLatestVersion(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.getLatestTemplateVersion(id));
    }

    /**
     * Get a specific version of a template by template ID and version ID.
     */
    @GetMapping("/{id}/versions/{version}")
    public ResponseEntity<TemplateVersionResponseDTO> getVersion(
            @PathVariable Long id, @PathVariable Integer version) {
        return ResponseEntity.ok(
                templateService.getTemplateVersion(id, version)
        );
    }

}
