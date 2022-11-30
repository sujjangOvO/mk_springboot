package com.example.moonkey.dto;

import com.example.moonkey.domain.Review;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    @NotNull
    private long reviewId;

    @NotNull
    @Column(length = 1000)
    private String content;

    @NotNull
    private short stars;

    @NotNull
    private long account;

    private String nickname;

    @NotNull
    private long storeId;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder().
                reviewId(review.getReviewId()).
                content(review.getContent()).
                stars(review.getStars()).
                account(review.getAccount().getUid()).
                nickname(review.getAccount().getNickname()).
                storeId(review.getStoreId().getStoreId()).
                build();
    }
}
