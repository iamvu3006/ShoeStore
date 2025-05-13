package com.team4.shoestore.service;

import com.team4.shoestore.model.Shoe;
import com.team4.shoestore.repository.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoeService {

    @Autowired
    private ShoeRepository shoeRepository;

    public Shoe createShoe(Shoe shoe) {
        return shoeRepository.save(shoe);
    }

    public Shoe getShoeById(Integer id) {
        return shoeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shoe not found with ID: " + id));
    }

    public List<Shoe> getAllShoes() {
        return shoeRepository.findAll();
    }

    public Shoe updateShoe(Integer id, Shoe shoe) {
        Shoe existing = getShoeById(id);
        existing.setName(shoe.getName());
        existing.setDescription(shoe.getDescription());
        existing.setPrice(shoe.getPrice());
        existing.setCategory(shoe.getCategory());
        existing.setImageUrl(shoe.getImageUrl());
        existing.setStatus(shoe.isStatus());
        existing.setBrand(shoe.getBrand());
        return shoeRepository.save(existing);
    }

    public void deleteShoe(Integer id) {
        Shoe existing = getShoeById(id);
        shoeRepository.delete(existing);
    }
}
