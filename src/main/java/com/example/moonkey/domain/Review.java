package com.example.moonkey.domain;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql="UPDATE review SET deleted = true WHERE review_id = ?")
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private long reviewId; // 리뷰 Id

    @NotNull
    @Column(length = 1000)
    private String content; // 리뷰 내용 (~1000자 제한)

    @NotNull
    private short stars; // 별점 (1~5)

    @OneToOne
    @JoinColumn(name = "uid")
    private Account account; // 작성자 Account

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store storeId; // 리뷰 of 가게

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}
