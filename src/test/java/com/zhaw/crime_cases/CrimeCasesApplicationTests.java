package com.zhaw.crime_cases;

import com.zhaw.crime_cases.entity.Crime;
import com.zhaw.crime_cases.repository.CrimeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CrimeCasesApplicationTests {

    @Autowired
    private CrimeRepository crimeRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testDatabaseConnection() {
        long count = crimeRepository.count();
        assertThat(count).isNotNull();
    }

    @Test
    void testCrimeCRUDOperations() {
        // Create
        Crime crime = new Crime();
        crime.setCrimeType("Theft");
        crime.setCrimeDate(LocalDate.now());
        crime = crimeRepository.save(crime);
        assertThat(crime.getId()).isNotNull();

        // Read
        Crime fetchedCrime = crimeRepository.findById(crime.getId()).orElse(null);
        assertThat(fetchedCrime).isNotNull();
        assertThat(fetchedCrime.getCrimeType()).isEqualTo("Theft");

        // Update
        fetchedCrime.setCrimeType("Robbery");
        crimeRepository.save(fetchedCrime);
        Crime updatedCrime = crimeRepository.findById(fetchedCrime.getId()).orElse(null);
        assertThat(updatedCrime).isNotNull();
        assertThat(updatedCrime.getCrimeType()).isEqualTo("Robbery");

        // Delete
        crimeRepository.delete(updatedCrime);
        Crime deletedCrime = crimeRepository.findById(fetchedCrime.getId()).orElse(null);
        assertThat(deletedCrime).isNull();
    }
}
