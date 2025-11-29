package com.devweb.repository;

import com.devweb.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByAno(Integer ano);
    List<Turma> findByPeriodo(String periodo);
    
    @Query("SELECT t FROM Turma t WHERE t.professor.id = :professorId")
    List<Turma> findByProfessorId(@Param("professorId") Long professorId);
    
    @Query("SELECT t FROM Turma t WHERE t.disciplina.id = :disciplinaId")
    List<Turma> findByDisciplinaId(@Param("disciplinaId") Long disciplinaId);
    
    boolean existsByNomeAndAnoAndPeriodo(String nome, Integer ano, String periodo);
}