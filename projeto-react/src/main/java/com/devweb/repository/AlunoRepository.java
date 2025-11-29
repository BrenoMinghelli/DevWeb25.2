package com.devweb.repository;

import com.devweb.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean existsByEmail(String email);
    
    @Query("SELECT a FROM Aluno a WHERE a.id NOT IN " +
           "(SELECT i.aluno.id FROM Inscricao i WHERE i.turma.id = :turmaId)")
    List<Aluno> findAlunosNaoInscritosNaTurma(@Param("turmaId") Long turmaId);
}