package com.zhaw.crime_cases.repository;

import com.zhaw.crime_cases.entity.Crime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrimeRepository extends JpaRepository<Crime, Long> {
}
