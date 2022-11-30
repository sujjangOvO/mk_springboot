package com.example.moonkey.repository;

import com.example.moonkey.domain.Review;
import com.example.moonkey.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAll();

    Optional<Review> findByReviewId(long reviewId);

    Optional<Review> findByStoreId(Store storeId);
}
