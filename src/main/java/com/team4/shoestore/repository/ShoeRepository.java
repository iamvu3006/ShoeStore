package com.team4.shoestore.repository;

import com.team4.shoestore.model.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoeRepository extends JpaRepository<Shoe, Integer> {
}