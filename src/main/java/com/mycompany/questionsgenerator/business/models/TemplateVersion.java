package com.mycompany.questionsgenerator.business.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TemplateVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Template template;

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false, columnDefinition = "text")
    private String templateText;

    @OneToMany(
            mappedBy = "templateVersion",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    @OrderBy("id ASC")
    private Set<TemplateVersionVariable> variables = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}



