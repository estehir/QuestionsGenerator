package com.mycompany.questionsgenerator.business.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TemplateVersionVariable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @OrderBy("version ASC")
    private TemplateVersion templateVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @OrderBy("id ASC")
    private Variable variable;

    @Column(nullable = false)
    private Boolean required;

}

