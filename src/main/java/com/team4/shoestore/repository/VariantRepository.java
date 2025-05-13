package com.team4.shoestore.repository;

import com.team4.shoestore.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant, Integer> {
}