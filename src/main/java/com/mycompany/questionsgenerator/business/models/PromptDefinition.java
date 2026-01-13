package com.mycompany.questionsgenerator.business.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PromptDefinition {

    @Builder
    public PromptDefinition(Long id, String version, String description,
                            String text, Boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.version = version;
        this.description = description;
        this.text = text;
        this.active = active;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String version;

    @Column()
    private String description;

    @Column(nullable = false, columnDefinition = "text")
    private String text;

    @Column(nullable = false)
    private Boolean active = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}

