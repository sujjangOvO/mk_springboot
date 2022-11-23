package com.example.moonkey.service;

import com.example.moonkey.domain.Review;
import com.example.moonkey.dto.ReviewDto;
import com.example.moonkey.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public List<ReviewDto> storeReviewList(long storeId){
        List<Review> reviewList = reviewRepository.findAll();
        Iterator<Review> iter = reviewList.iterator();

        List<ReviewDto> reviewDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Review review = iter.next();

            long reviewsStoreId = review.getStoreId().getStoreId();
            System.out.println("reviewsStoreId: "+reviewsStoreId);

            if(reviewsStoreId == storeId) {
                ReviewDto reviewDto = ReviewDto.from(review);
                reviewDtos.add(reviewDto);
            }
        }

        return reviewDtos;
    }

}
