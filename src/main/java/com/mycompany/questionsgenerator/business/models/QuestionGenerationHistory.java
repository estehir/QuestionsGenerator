package com.mycompany.questionsgenerator.business.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class QuestionGenerationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Template usado
    @Column(nullable = false)
    private Long templateId;

    @Column(nullable = false)
    private Long templateVersionId;

    // 🔹 Prompt base
    @Column(nullable = false)
    private Long promptDefinitionId;

    // 🔹 Chatbot
    @Column(nullable = false, length = 50)
    private String chatbotProviderCode;

    // 🔹 Snapshot das variáveis (JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> variablesSnapshot;

    // 🔹 Conteúdo efetivo
    @Column(nullable = false, columnDefinition = "text")
    private String finalPrompt;

    @Column(nullable = false, columnDefinition = "text")
    private String chatbotResponse;

    // 🔹 Métricas (opcional agora, mas já preparado)
    private Integer promptTokens;
    private Integer completionTokens;
    private Integer totalTokens;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }

}

