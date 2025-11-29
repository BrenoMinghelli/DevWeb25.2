package com.devweb.controller;

import com.devweb.model.Professor;
import com.devweb.repository.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/professores")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfessorController {
    
    @Autowired
    private ProfessorRepository professorRepository;
    
    @GetMapping
    public List<Professor> getAllProfessores() {
        return professorRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        return professor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createProfessor(@Valid @RequestBody Professor professor) {
        if (professorRepository.existsByEmail(professor.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        Professor savedProfessor = professorRepository.save(professor);
        return ResponseEntity.ok(savedProfessor);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfessor(@PathVariable Long id, @Valid @RequestBody Professor professorDetails) {
        Optional<Professor> optionalProfessor = professorRepository.findById(id);
        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();
            
            // Verifica se o email já existe em outro professor
            if (professorRepository.existsByEmailAndIdNot(professorDetails.getEmail(), id)) {
                return ResponseEntity.badRequest().body("Email já cadastrado em outro professor");
            }
            
            professor.setNome(professorDetails.getNome());
            professor.setEmail(professorDetails.getEmail());
            return ResponseEntity.ok(professorRepository.save(professor));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable Long id) {
        if (professorRepository.existsById(id)) {
            professorRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}