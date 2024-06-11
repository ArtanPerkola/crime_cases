package com.zhaw.crime_cases.controller;

import com.zhaw.crime_cases.entity.Multiple;
import com.zhaw.crime_cases.repository.MultipleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/multiples")
public class MultipleController {

    @Autowired
    private MultipleRepository multipleRepository;

    @GetMapping
    public List<Multiple> getAllMultiples() {
        return multipleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Multiple> getMultipleById(@PathVariable Long id) {
        return multipleRepository.findById(id)
                .map(multiple -> ResponseEntity.ok().body(multiple))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Multiple createMultiple(@RequestBody Multiple multiple) {
        return multipleRepository.save(multiple);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Multiple> updateMultiple(@PathVariable Long id, @RequestBody Multiple multipleDetails) {
        return multipleRepository.findById(id)
                .map(multiple -> {
                    multiple.setSeverity(multipleDetails.getSeverity());
                    multiple.setNumberOfIncidents(multipleDetails.getNumberOfIncidents());
                    multiple.setCrimeDate(multipleDetails.getCrimeDate());
                    Multiple updatedMultiple = multipleRepository.save(multiple);
                    return ResponseEntity.ok().body(updatedMultiple);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMultiple(@PathVariable Long id) {
        return multipleRepository.findById(id)
                .map(multiple -> {
                    multipleRepository.delete(multiple);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
