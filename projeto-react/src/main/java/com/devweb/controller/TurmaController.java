package com.devweb.controller;

import com.devweb.model.Turma;
import com.devweb.repository.TurmaRepository;
import com.devweb.repository.ProfessorRepository;
import com.devweb.repository.DisciplinaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/turmas")
@CrossOrigin(origins = "http://localhost:3000")
public class TurmaController {
    
    @Autowired
    private TurmaRepository turmaRepository;
    
    @Autowired
    private ProfessorRepository professorRepository;
    
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    
    @GetMapping
    public List<Turma> getAllTurmas() {
        return turmaRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Turma> getTurmaById(@PathVariable Long id) {
        Optional<Turma> turma = turmaRepository.findById(id);
        return turma.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/professor/{professorId}")
    public List<Turma> getTurmasByProfessor(@PathVariable Long professorId) {
        return turmaRepository.findByProfessorId(professorId);
    }
    
    @GetMapping("/disciplina/{disciplinaId}")
    public List<Turma> getTurmasByDisciplina(@PathVariable Long disciplinaId) {
        return turmaRepository.findByDisciplinaId(disciplinaId);
    }
    
    @PostMapping
    public ResponseEntity<?> createTurma(@Valid @RequestBody Turma turma) {
        // Verifica se professor existe
        if (!professorRepository.existsById(turma.getProfessor().getId())) {
            return ResponseEntity.badRequest().body("Professor não encontrado");
        }
        
        // Verifica se disciplina existe
        if (!disciplinaRepository.existsById(turma.getDisciplina().getId())) {
            return ResponseEntity.badRequest().body("Disciplina não encontrada");
        }
        
        // Verifica se já existe turma com mesmo nome, ano e período
        if (turmaRepository.existsByNomeAndAnoAndPeriodo(turma.getNome(), turma.getAno(), turma.getPeriodo())) {
            return ResponseEntity.badRequest().body("Já existe uma turma com este nome, ano e período");
        }
        
        Turma savedTurma = turmaRepository.save(turma);
        return ResponseEntity.ok(savedTurma);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTurma(@PathVariable Long id, @Valid @RequestBody Turma turmaDetails) {
        Optional<Turma> optionalTurma = turmaRepository.findById(id);
        if (optionalTurma.isPresent()) {
            Turma turma = optionalTurma.get();
            
            // Verifica se professor existe
            if (!professorRepository.existsById(turmaDetails.getProfessor().getId())) {
                return ResponseEntity.badRequest().body("Professor não encontrado");
            }
            
            // Verifica se disciplina existe
            if (!disciplinaRepository.existsById(turmaDetails.getDisciplina().getId())) {
                return ResponseEntity.badRequest().body("Disciplina não encontrada");
            }
            
            turma.setNome(turmaDetails.getNome());
            turma.setAno(turmaDetails.getAno());
            turma.setPeriodo(turmaDetails.getPeriodo());
            turma.setProfessor(turmaDetails.getProfessor());
            turma.setDisciplina(turmaDetails.getDisciplina());
            
            return ResponseEntity.ok(turmaRepository.save(turma));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTurma(@PathVariable Long id) {
        if (turmaRepository.existsById(id)) {
            turmaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}