package com.mycompany.questionsgenerator.business.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"template_id", "variable_order"}) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateVariable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variable_id", nullable = false)
    private Variable variable;

    @Column(nullable = false)
    private Boolean required = true;

}

