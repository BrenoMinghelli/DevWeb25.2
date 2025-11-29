package com.devweb.controller;

import com.devweb.model.Aluno;
import com.devweb.repository.AlunoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "http://localhost:3000")
public class AlunoController {
    
    @Autowired
    private AlunoRepository alunoRepository;
    
    @GetMapping
    public List<Aluno> getAllAlunos() {
        return alunoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        return aluno.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nao-inscritos/{turmaId}")
    public List<Aluno> getAlunosNaoInscritos(@PathVariable Long turmaId) {
        return alunoRepository.findAlunosNaoInscritosNaTurma(turmaId);
    }
    
    @PostMapping
    public Aluno createAluno(@Valid @RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> updateAluno(@PathVariable Long id, @Valid @RequestBody Aluno alunoDetails) {
        Optional<Aluno> optionalAluno = alunoRepository.findById(id);
        if (optionalAluno.isPresent()) {
            Aluno aluno = optionalAluno.get();
            aluno.setNome(alunoDetails.getNome());
            aluno.setEmail(alunoDetails.getEmail());
            return ResponseEntity.ok(alunoRepository.save(aluno));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAluno(@PathVariable Long id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}