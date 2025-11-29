package com.devweb.controller;

import com.devweb.model.Disciplina;
import com.devweb.repository.DisciplinaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disciplinas")
@CrossOrigin(origins = "http://localhost:3000")
public class DisciplinaController {
    
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    
    @GetMapping
    public List<Disciplina> getAllDisciplinas() {
        return disciplinaRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> getDisciplinaById(@PathVariable Long id) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        return disciplina.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createDisciplina(@Valid @RequestBody Disciplina disciplina) {
        if (disciplinaRepository.existsByNome(disciplina.getNome())) {
            return ResponseEntity.badRequest().body("Já existe uma disciplina com este nome");
        }
        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);
        return ResponseEntity.ok(savedDisciplina);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDisciplina(@PathVariable Long id, @Valid @RequestBody Disciplina disciplinaDetails) {
        Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(id);
        if (optionalDisciplina.isPresent()) {
            Disciplina disciplina = optionalDisciplina.get();
            
            // Verifica se o nome já existe em outra disciplina
            if (disciplinaRepository.existsByNomeAndIdNot(disciplinaDetails.getNome(), id)) {
                return ResponseEntity.badRequest().body("Já existe uma disciplina com este nome");
            }
            
            disciplina.setNome(disciplinaDetails.getNome());
            disciplina.setCargaHoraria(disciplinaDetails.getCargaHoraria());
            return ResponseEntity.ok(disciplinaRepository.save(disciplina));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDisciplina(@PathVariable Long id) {
        if (disciplinaRepository.existsById(id)) {
            disciplinaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}