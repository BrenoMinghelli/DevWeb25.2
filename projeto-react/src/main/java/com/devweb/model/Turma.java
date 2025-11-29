package com.devweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "turma")
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome da turma é obrigatório")
    @Column(nullable = false)
    private String nome;
    
    @Min(value = 2000, message = "Ano deve ser válido")
    @Column(nullable = false)
    private Integer ano;
    
    @NotBlank(message = "Período é obrigatório")
    @Column(nullable = false)
    private String periodo;
    
    @NotNull(message = "Disciplina é obrigatória")
    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;
    
    @NotNull(message = "Professor é obrigatório")
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;
    
    // Construtores
    public Turma() {}
    
    public Turma(String nome, Integer ano, String periodo, Disciplina disciplina, Professor professor) {
        this.nome = nome;
        this.ano = ano;
        this.periodo = periodo;
        this.disciplina = disciplina;
        this.professor = professor;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
    
    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }
}