package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Review;
import com.example.moonkey.domain.Store;
import com.example.moonkey.dto.ReviewDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.ReviewRepository;
import com.example.moonkey.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AccountRepository accountRepository;

    public ReviewService(ReviewRepository reviewRepository, AccountRepository accountRepository){
        this.reviewRepository = reviewRepository;
        this.accountRepository = accountRepository;
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

    @Transactional
    public ReviewDto reviewRegister(ReviewDto reviewDto, Store store){
        Account account = SecurityUtil.getCurrentUsername()
                .flatMap(accountRepository::findOneWithAuthoritiesById)
                .orElseThrow(()->new NotFoundMemberException("Member not found"));
        Review review = Review.builder()
                .account(account)
                .content(reviewDto.getContent())
                .storeId(store)
                .stars(reviewDto.getStars())
                .build();

        return ReviewDto.from(reviewRepository.save(review));
    }

}
