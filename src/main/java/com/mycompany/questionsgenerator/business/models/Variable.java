package com.mycompany.questionsgenerator.business.models;

import com.mycompany.questionsgenerator.business.models.enums.VariableValueType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Variable {

    @Builder
    private Variable(
            String code,
            String description,
            String placeholder,
            VariableValueType valueType
    ) {
        this.code = code;
        this.description = description;
        this.placeholder = placeholder;
        this.valueType = valueType;
    }

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
    private List<VariableOption> options = new ArrayList<>();

    public void addOption(VariableOption option) {
        options.add(option);
        option.setVariable(this);
    }

    public void clearOptions() {
        options.clear();
    }

}
