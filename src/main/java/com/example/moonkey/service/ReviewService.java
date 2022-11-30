package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Review;
import com.example.moonkey.domain.Store;
import com.example.moonkey.dto.ReviewDto;
import com.example.moonkey.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AccountService accountService;

    @Transactional(readOnly = true)
    public List<ReviewDto> myReviewList(long uid) {
        List<Review> reviewList = reviewRepository.findAll();
        return reviewList.stream()
                .filter(review -> review.getAccount().getUid() == uid)
                .map(ReviewDto::from)
                .toList();
    }

    @Transactional
    public List<ReviewDto> storeReviewList(long storeId) {
        List<Review> reviewList = reviewRepository.findAll();
        return reviewList.stream()
                .filter(review -> review.getStoreId().getStoreId() == storeId)
                .map(ReviewDto::from)
                .toList();
    }

    @Transactional
    public ReviewDto reviewRegister(ReviewDto reviewDto, Store store) {
        Account account = accountService.getAccount();
        Review review = Review.builder()
                .account(account)
                .content(reviewDto.getContent())
                .storeId(store)
                .stars(reviewDto.getStars())
                .build();
        return ReviewDto.from(reviewRepository.save(review));
    }
}
