package com.mycompany.questionsgenerator.api.controllers;

import com.mycompany.questionsgenerator.api.dtos.ActivateChatbotRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.ChatbotConfigurationResponseDTO;
import com.mycompany.questionsgenerator.business.interfaces.IChatbotConfigurationService;
import com.mycompany.questionsgenerator.business.models.enums.ChatbotProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/chatbot")
@RequiredArgsConstructor
public class ChatbotConfigurationController {

    private final IChatbotConfigurationService chatbotConfigurationService;

    @PostMapping("/activate")
    public ResponseEntity<Void> activate(@RequestBody ActivateChatbotRequestDTO request) {
        chatbotConfigurationService.activateProvider(
                ChatbotProvider.valueOf(request.getProvider())
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/active")
    public ResponseEntity<ChatbotConfigurationResponseDTO> getActive() {

        ChatbotProvider provider = chatbotConfigurationService.getActiveProvider();

        return ResponseEntity.ok(
                ChatbotConfigurationResponseDTO.builder()
                        .provider(provider.name())
                        .active(true)
                        .build()
        );
    }
}
