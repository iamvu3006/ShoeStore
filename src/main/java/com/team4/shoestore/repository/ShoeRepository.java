package com.team4.shoestore.repository;

import com.team4.shoestore.model.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoeRepository extends JpaRepository<Shoe, Integer> {
    List<Shoe> findByNameContainingIgnoreCase(String name);
    List<Shoe> findByCategoryContainingIgnoreCase(String category);
    List<Shoe> findByBrand_BrandId(Integer brandId);
}