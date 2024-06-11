package com.zhaw.crime_cases.controller;

import com.zhaw.crime_cases.entity.Defendant;
import com.zhaw.crime_cases.repository.DefendantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/defendants")
public class DefendantController {

    @Autowired
    private DefendantRepository defendantRepository;

    @GetMapping
    public List<Defendant> getAllDefendants() {
        return defendantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Defendant> getDefendantById(@PathVariable Long id) {
        return defendantRepository.findById(id)
                .map(defendant -> ResponseEntity.ok().body(defendant))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Defendant createDefendant(@RequestBody Defendant defendant) {
        return defendantRepository.save(defendant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Defendant> updateDefendant(@PathVariable Long id, @RequestBody Defendant defendantDetails) {
        return defendantRepository.findById(id)
                .map(defendant -> {
                    defendant.setName(defendantDetails.getName());
                    defendant.setAge(defendantDetails.getAge());
                    Defendant updatedDefendant = defendantRepository.save(defendant);
                    return ResponseEntity.ok().body(updatedDefendant);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDefendant(@PathVariable Long id) {
        return defendantRepository.findById(id)
                .map(defendant -> {
                    defendantRepository.delete(defendant);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
