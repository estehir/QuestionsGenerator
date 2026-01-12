package com.mycompany.questionsgenerator.api.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatbotConfigurationResponseDTO {

    private String provider;
    private Boolean active;

}
