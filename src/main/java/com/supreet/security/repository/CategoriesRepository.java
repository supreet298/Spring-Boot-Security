package com.supreet.security.repository;

import com.supreet.security.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    Optional<Categories> findByUuid(String uuid);
}
