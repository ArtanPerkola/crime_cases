package com.zhaw.crime_cases.repository;

import com.zhaw.crime_cases.entity.Indictment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndictmentRepository extends JpaRepository<Indictment, Long> {
}
