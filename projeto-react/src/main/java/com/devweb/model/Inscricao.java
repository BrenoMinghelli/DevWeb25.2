package com.devweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscricao", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"aluno_id", "turma_id"}))
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime datahora = LocalDateTime.now();
    
    @NotNull(message = "Aluno é obrigatório")
    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;
    
    @NotNull(message = "Turma é obrigatória")
    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;
    
    // Construtores
    public Inscricao() {}
    
    public Inscricao(Aluno aluno, Turma turma) {
        this.aluno = aluno;
        this.turma = turma;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getDatahora() { return datahora; }
    public void setDatahora(LocalDateTime datahora) { this.datahora = datahora; }
    
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    
    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}