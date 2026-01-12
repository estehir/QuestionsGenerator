package com.mycompany.questionsgenerator.business.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariableOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Variable variable;

    @Column(nullable = false, length = 50)
    private String code;        // EASY, MEDIUM, HARD

    @Column(nullable = false, length = 100)
    private String label;       // Fácil, Média, Difícil

    @Column(name = "numeric_value")
    private Integer numericValue; // opcional (1, 2, 3)

    @Column(nullable = false)
    private Integer orderIndex;

    @Column(nullable = false)
    private Boolean active = true;

}
