package com.team4.shoestore.service;

import com.team4.shoestore.model.Variant;
import com.team4.shoestore.repository.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantService {

    @Autowired
    private VariantRepository variantRepository;

    public Variant createVariant(Variant variant) {
        return variantRepository.save(variant);
    }

    public Variant getVariantById(Integer id) {
        return variantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variant not found with ID: " + id));
    }

    public List<Variant> getAllVariants() {
        return variantRepository.findAll();
    }

    public Variant updateVariant(Integer id, Variant variant) {
        Variant existing = getVariantById(id);
        existing.setColor(variant.getColor());
        existing.setSize(variant.getSize());
        existing.setStockQuantity(variant.getStockQuantity());
        existing.setShoe(variant.getShoe());
        return variantRepository.save(existing);
    }

    public void deleteVariant(Integer id) {
        Variant existing = getVariantById(id);
        variantRepository.delete(existing);
    }
}
