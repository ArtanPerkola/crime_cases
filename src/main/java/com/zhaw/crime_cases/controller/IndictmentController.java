package com.zhaw.crime_cases.controller;

import com.zhaw.crime_cases.entity.Indictment;
import com.zhaw.crime_cases.repository.IndictmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/indictments")
public class IndictmentController {

    @Autowired
    private IndictmentRepository indictmentRepository;

    @GetMapping
    public List<Indictment> getAllIndictments() {
        return indictmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Indictment> getIndictmentById(@PathVariable Long id) {
        return indictmentRepository.findById(id)
                .map(indictment -> ResponseEntity.ok().body(indictment))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Indictment createIndictment(@RequestBody Indictment indictment) {
        return indictmentRepository.save(indictment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Indictment> updateIndictment(@PathVariable Long id, @RequestBody Indictment indictmentDetails) {
        return indictmentRepository.findById(id)
                .map(indictment -> {
                    indictment.setIndictmentDate(indictmentDetails.getIndictmentDate());
                    indictment.setDefendantId(indictmentDetails.getDefendantId());
                    indictment.setCrimeId(indictmentDetails.getCrimeId());
                    Indictment updatedIndictment = indictmentRepository.save(indictment);
                    return ResponseEntity.ok().body(updatedIndictment);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteIndictment(@PathVariable Long id) {
        return indictmentRepository.findById(id)
                .map(indictment -> {
                    indictmentRepository.delete(indictment);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
