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

    public List<Variant> getAllVariants() {
        return variantRepository.findAll();
    }

    public Variant getVariantById(Integer id) {
        return variantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy biến thể có ID: " + id));
    }

    public Variant saveVariant(Variant variant) {
        return variantRepository.save(variant);
    }

    public void deleteVariant(Integer id) {
        variantRepository.deleteById(id);
    }

    public List<Variant> findVariantsByShoeId(Integer shoeId) {
        return variantRepository.findByShoe_ShoeId(shoeId);
    }

    public List<Variant> findVariantsBySize(String size) {
        return variantRepository.findBySize(size);
    }

    public List<Variant> findVariantsByColor(String color) {
        return variantRepository.findByColor(color);
    }

    public Variant getDefaultVariantForShoe(Integer shoeId) {
        List<Variant> variants = findVariantsByShoeId(shoeId);
        if (variants != null && !variants.isEmpty()) {
            return variants.get(0); // Return the first variant
        }
        return null;
    }
}
