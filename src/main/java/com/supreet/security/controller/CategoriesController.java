package com.supreet.security.controller;

import com.supreet.security.dto.CategoriesDTO;
import com.supreet.security.model.Categories;
import com.supreet.security.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
    @GetMapping("/allCategories")
    public List<Categories> getAll() {
        return categoriesService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
    @GetMapping("/{uuid}")
    public Optional<CategoriesDTO> getCategoryByUuid(@PathVariable String uuid) {
        return categoriesService.getCategoryByUuid(uuid);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public Categories createCategory(@RequestBody Categories category) {
        return categoriesService.createCategory(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{uuid}")
    public Categories updateCategory(@PathVariable String uuid, @RequestBody Categories categoryDetails) {
        return categoriesService.updateCategory(uuid, categoryDetails).orElse(null);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{uuid}")
    public boolean deleteCategory(@PathVariable String uuid) {
        return categoriesService.deleteCategory(uuid);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','USER')")
    @GetMapping
    public ResponseEntity<List<CategoriesDTO>> getAllCategories() {
        List<CategoriesDTO> users = categoriesService.getAllCategories();
        return ResponseEntity.ok(users);
    }
}
