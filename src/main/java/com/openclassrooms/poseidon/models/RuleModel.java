package com.openclassrooms.poseidon.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rulename")
public class RuleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Rule name is mandatory")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 125)
    @Column(name = "description")
    private String description;

    @Size(max = 125)
    @Column(name = "json")
    @NotBlank(message = "Json is mandatory")
    private String json;

    @Size(max = 512)
    @Column(name = "template")
    @NotBlank(message = "Template is mandatory")
    private String template;

    @Size(max = 125)
    @Column(name = "sql_str")
    @NotBlank(message = "SqlStr is mandatory")
    private String sqlStr;

    @Size(max = 125)
    @Column(name = "sql_part")
    @NotBlank(message = "SqlPart is mandatory")
    private String sqlPart;

}
