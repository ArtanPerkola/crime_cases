package com.zhaw.crime_cases.controller;

import com.zhaw.crime_cases.entity.Crime;
import com.zhaw.crime_cases.repository.CrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crimes")
public class CrimeController {

    @Autowired
    private CrimeRepository crimeRepository;

    @GetMapping
    public List<Crime> getAllCrimes() {
        return crimeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crime> getCrimeById(@PathVariable Long id) {
        return crimeRepository.findById(id)
                .map(crime -> ResponseEntity.ok().body(crime))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Crime createCrime(@RequestBody Crime crime) {
        return crimeRepository.save(crime);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crime> updateCrime(@PathVariable Long id, @RequestBody Crime crimeDetails) {
        return crimeRepository.findById(id)
                .map(crime -> {
                    crime.setCrimeType(crimeDetails.getCrimeType());
                    crime.setCrimeDate(crimeDetails.getCrimeDate());
                    Crime updatedCrime = crimeRepository.save(crime);
                    return ResponseEntity.ok().body(updatedCrime);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCrime(@PathVariable Long id) {
        return crimeRepository.findById(id)
                .map(crime -> {
                    crimeRepository.delete(crime);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
