package com.example.moonkey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE store SET deleted = true WHERE store_id = ?")
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId")
    private long storeId;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "accountUid")
    private Account ownerId; // FK

    @NotNull
    private String address;

    @Column(nullable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "categoryName")
    private Category categoryName;

    @NotNull
    private String contact;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;

}
