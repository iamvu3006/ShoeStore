package com.team4.shoestore.service;

import com.team4.shoestore.model.Brand;
import com.team4.shoestore.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand getBrandById(Integer id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with ID: " + id));
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand updateBrand(Integer id, Brand brand) {
        Brand existing = getBrandById(id);
        existing.setName(brand.getName());
        existing.setCountry(brand.getCountry());
        return brandRepository.save(existing);
    }

    public void deleteBrand(Integer id) {
        Brand existing = getBrandById(id);
        brandRepository.delete(existing);
    }
}
