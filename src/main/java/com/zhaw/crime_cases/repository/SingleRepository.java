package com.zhaw.crime_cases.repository;

import com.zhaw.crime_cases.entity.Single;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleRepository extends JpaRepository<Single, Long> {
}
