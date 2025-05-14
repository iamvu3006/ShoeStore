package com.team4.shoestore.repository;

import com.team4.shoestore.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariantRepository extends JpaRepository<Variant, Integer> {
    List<Variant> findByShoe_ShoeId(Integer shoeId);
    List<Variant> findBySize(String size);
    List<Variant> findByColor(String color);
}