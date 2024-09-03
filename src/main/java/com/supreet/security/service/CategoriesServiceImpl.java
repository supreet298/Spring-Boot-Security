package com.supreet.security.service;

import com.supreet.security.dto.CategoriesDTO;
import com.supreet.security.model.Categories;
import com.supreet.security.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public List<Categories> getAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public Optional<CategoriesDTO> getCategoryByUuid(String uuid) {
        return categoriesRepository.findByUuid(uuid).map(this::convertToDTO);
    }

    @Override
    public Categories createCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    @Override
    public Optional<Categories> updateCategory(String uuid, Categories categoryDetails) {
        return categoriesRepository.findByUuid(uuid).map(existingCategory -> {
            // Update the existing category with values from categoryDetails
            existingCategory.setCourse(categoryDetails.getCourse());
            // Save and return the updated category
            return categoriesRepository.save(existingCategory);
        });
    }

    @Override
    public boolean deleteCategory(String uuid) {
        Optional<Categories> existingCategory = categoriesRepository.findByUuid(uuid);
        if (existingCategory.isPresent()) {
            categoriesRepository.delete(existingCategory.get());
            return true;
        }
        return false;
    }

    public List<CategoriesDTO> getAllCategories() {
        List<Categories> categories = categoriesRepository.findAll();
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private CategoriesDTO convertToDTO(Categories categories) {
        CategoriesDTO dto = new CategoriesDTO();
        dto.setUuid(categories.getUuid());
        dto.setCourse(categories.getCourse());
        return dto;
    }
}