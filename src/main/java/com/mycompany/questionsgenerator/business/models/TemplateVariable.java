package com.mycompany.questionsgenerator.business.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"template_id", "variable_id"})
        }
)
public class TemplateVariable {

    @Builder
    private TemplateVariable(Template template, Variable variable, Boolean required) {
        this.template = template;
        this.variable = variable;
        this.required = required != null ? required : true;

        template.getVariables().add(this);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Variable variable;

    @Column(nullable = false)
    private Boolean required = true;

}
