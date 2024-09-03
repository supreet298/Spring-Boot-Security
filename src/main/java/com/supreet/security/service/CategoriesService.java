package com.supreet.security.service;

import com.supreet.security.dto.CategoriesDTO;
import com.supreet.security.model.Categories;
import java.util.List;
import java.util.Optional;

public interface CategoriesService {

    List<Categories> getAll();

    Categories createCategory(Categories category);

    Optional<CategoriesDTO> getCategoryByUuid(String uuid);

    Optional<Categories> updateCategory(String uuid, Categories categoryDetails);

    boolean deleteCategory(String uuid);

    List<CategoriesDTO> getAllCategories();
}

