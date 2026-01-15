package com.mycompany.questionsgenerator.business.models;

import com.mycompany.questionsgenerator.business.models.enums.VariableValueType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Variable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column()
    private String placeholder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VariableValueType valueType;

    @OneToMany(
            mappedBy = "variable",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    @OrderBy("orderIndex ASC")
    private Set<VariableOption> options = new HashSet<>();

    public void addOption(VariableOption option) {
        options.add(option);
        option.setVariable(this);
    }

    public void clearOptions() {
        options.clear();
    }

}
