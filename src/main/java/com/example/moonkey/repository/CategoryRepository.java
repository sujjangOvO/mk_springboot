package com.example.moonkey.repository;

import com.example.moonkey.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findAll();

    Category findOneByCategoryName(String name);

}
