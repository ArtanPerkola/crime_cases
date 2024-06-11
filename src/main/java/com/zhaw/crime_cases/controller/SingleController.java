package com.zhaw.crime_cases.controller;

import com.zhaw.crime_cases.entity.Single;
import com.zhaw.crime_cases.repository.SingleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/singles")
public class SingleController {

    @Autowired
    private SingleRepository singleRepository;

    @GetMapping
    public List<Single> getAllSingles() {
        return singleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Single> getSingleById(@PathVariable Long id) {
        return singleRepository.findById(id)
                .map(single -> ResponseEntity.ok().body(single))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Single createSingle(@RequestBody Single single) {
        return singleRepository.save(single);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Single> updateSingle(@PathVariable Long id, @RequestBody Single singleDetails) {
        return singleRepository.findById(id)
                .map(single -> {
                    single.setSeverity(singleDetails.getSeverity());
                    single.setLocation(singleDetails.getLocation());
                    single.setCrimeDate(singleDetails.getCrimeDate());
                    Single updatedSingle = singleRepository.save(single);
                    return ResponseEntity.ok().body(updatedSingle);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSingle(@PathVariable Long id) {
        return singleRepository.findById(id)
                .map(single -> {
                    singleRepository.delete(single);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
