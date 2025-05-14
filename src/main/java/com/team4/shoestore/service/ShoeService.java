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

    public List<Shoe> getAllShoes() {
        return shoeRepository.findAll();
    }

    public Shoe getShoeById(Integer id) {
        return shoeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giày có ID: " + id));
    }

    public Shoe saveShoe(Shoe shoe) {
        return shoeRepository.save(shoe);
    }

    public void deleteShoe(Integer id) {
        shoeRepository.deleteById(id);
    }

    public List<Shoe> findShoesByName(String name) {
        return shoeRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Shoe> findShoesByCategory(String category) {
        return shoeRepository.findByCategoryContainingIgnoreCase(category);
    }

    public List<Shoe> findShoesByBrandId(Integer brandId) {
        return shoeRepository.findByBrand_BrandId(brandId);
    }
}
