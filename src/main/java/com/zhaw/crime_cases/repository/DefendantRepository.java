package com.zhaw.crime_cases.repository;

import com.zhaw.crime_cases.entity.Defendant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefendantRepository extends JpaRepository<Defendant, Long> {
}
