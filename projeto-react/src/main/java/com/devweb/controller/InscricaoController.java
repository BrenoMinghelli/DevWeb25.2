package com.devweb.controller;

import com.devweb.model.Inscricao;
import com.devweb.repository.InscricaoRepository;
import com.devweb.repository.AlunoRepository;
import com.devweb.repository.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscricoes")
@CrossOrigin(origins = "http://localhost:3000")
public class InscricaoController {
    
    @Autowired
    private InscricaoRepository inscricaoRepository;
    
    @Autowired
    private AlunoRepository alunoRepository;
    
    @Autowired
    private TurmaRepository turmaRepository;
    
    @GetMapping
    public List<Inscricao> getAllInscricoes() {
        return inscricaoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> getInscricaoById(@PathVariable Long id) {
        Optional<Inscricao> inscricao = inscricaoRepository.findById(id);
        return inscricao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createInscricao(@Valid @RequestBody Inscricao inscricao) {
        // Verifica se aluno existe
        if (!alunoRepository.existsById(inscricao.getAluno().getId())) {
            return ResponseEntity.badRequest().body("Aluno não encontrado");
        }
        
        // Verifica se turma existe
        if (!turmaRepository.existsById(inscricao.getTurma().getId())) {
            return ResponseEntity.badRequest().body("Turma não encontrada");
        }
        
        // Verifica se aluno já está inscrito na turma
        if (inscricaoRepository.existsByAlunoIdAndTurmaId(
            inscricao.getAluno().getId(), 
            inscricao.getTurma().getId())) {
            return ResponseEntity.badRequest().body("Aluno já está inscrito nesta turma");
        }
        
        Inscricao savedInscricao = inscricaoRepository.save(inscricao);
        return ResponseEntity.ok(savedInscricao);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInscricao(@PathVariable Long id) {
        if (inscricaoRepository.existsById(id)) {
            inscricaoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/aluno/{alunoId}")
    public List<Inscricao> getInscricoesByAluno(@PathVariable Long alunoId) {
        return inscricaoRepository.findByAlunoId(alunoId);
    }
    
    @GetMapping("/turma/{turmaId}")
    public List<Inscricao> getInscricoesByTurma(@PathVariable Long turmaId) {
        return inscricaoRepository.findByTurmaId(turmaId);
    }
}