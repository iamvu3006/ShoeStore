package com.team4.shoestore.repository;

import com.team4.shoestore.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    
}
