package com.example.moonkey.repository;

import com.example.moonkey.domain.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,String> {

	List<Category> findAll();
	Category findOneByCategoryName(String name);

}
