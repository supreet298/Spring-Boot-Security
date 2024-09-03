package com.supreet.security.repository;

import com.supreet.security.model.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {

}