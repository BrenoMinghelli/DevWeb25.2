package com.devweb.repository;

import com.devweb.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    boolean existsByAlunoIdAndTurmaId(Long alunoId, Long turmaId);
    List<Inscricao> findByTurmaIdOrderByIdDesc(Long turmaId);
    
    @Query("SELECT i FROM Inscricao i WHERE i.aluno.id = :alunoId")
    List<Inscricao> findByAlunoId(@Param("alunoId") Long alunoId);
    
    @Query("SELECT i FROM Inscricao i WHERE i.turma.id = :turmaId")
    List<Inscricao> findByTurmaId(@Param("turmaId") Long turmaId);
    
    @Query("SELECT i FROM Inscricao i WHERE i.aluno.id = :alunoId AND i.turma.id = :turmaId")
    Optional<Inscricao> findByAlunoIdAndTurmaId(@Param("alunoId") Long alunoId, @Param("turmaId") Long turmaId);
    
    void deleteByAlunoId(Long alunoId);
    void deleteByTurmaId(Long turmaId);
}