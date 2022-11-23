package com.example.moonkey.controller;

import com.example.moonkey.domain.Store;
import com.example.moonkey.dto.PartyDisplayDto;
import com.example.moonkey.dto.ReviewDto;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.ReviewRepository;
import com.example.moonkey.repository.StoreRepository;
import com.example.moonkey.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, StoreRepository storeRepository){
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
        this.storeRepository = storeRepository;
    }

    @GetMapping("/review/{storeId}")
    public ResponseEntity<List<ReviewDto>> storeReviewList( // 가게의 리뷰 리스트 반환
            @PathVariable("storeId") @Valid long storeId){
        Store store = storeRepository.findStoreByStoreId(storeId).orElseThrow(()->new NotFoundStoreException("Store not found"));

        return ResponseEntity.ok(reviewService.storeReviewList(storeId));
    }
}
