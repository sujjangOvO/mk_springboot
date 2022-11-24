package com.example.moonkey.controller;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Store;
import com.example.moonkey.dto.ReviewDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.ReviewRepository;
import com.example.moonkey.repository.StoreRepository;
import com.example.moonkey.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final AccountRepository accountRepository;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, StoreRepository storeRepository, AccountRepository accountRepository){
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
        this.storeRepository = storeRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/review/{storeId}")
    public ResponseEntity<List<ReviewDto>> storeReviewList( // 가게의 리뷰 리스트 반환
            @PathVariable("storeId") @Valid long storeId){
        Store store = storeRepository.findStoreByStoreId(storeId).orElseThrow(()->new NotFoundStoreException("Store not found"));

        return ResponseEntity.ok(reviewService.storeReviewList(storeId));
    }

    @GetMapping("/review/list/{uid}")
    public ResponseEntity<List<ReviewDto>> myReviewList( // 사용자가 작성한 리뷰 리스트 반환
             @PathVariable @Valid long uid){
        Account account = accountRepository.findAccountByUid(uid).orElseThrow(()->new NotFoundMemberException("Member not found"));
        return ResponseEntity.ok(reviewService.myReviewList(uid));
    }

    @PostMapping("/review/{storeId}/post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ReviewDto> reviewRegister(@PathVariable("storeId") long storeId
            , @RequestBody ReviewDto reviewDto){

        Store store = storeRepository.findStoreByStoreId(storeId).orElseThrow(()->new NotFoundStoreException("Store not found"));
        return ResponseEntity.ok(reviewService.reviewRegister(reviewDto,store));
    }

}
