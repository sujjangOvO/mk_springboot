package com.example.moonkey.dto;

import com.example.moonkey.domain.Store;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StoreDisplayDto {

    @NotNull
    private long storeId;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @Column(nullable = true)
    private String description;

    @NotNull
    private String category;

    @NotNull
    private String contact;

    public static StoreDisplayDto from(Store store) {
        if (store == null) return null;
        return StoreDisplayDto.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .address(store.getAddress())
                .description(store.getDescription())
                .category(store.getCategoryName().getCategoryName())
                .contact(store.getContact())
                .build();
    }

}
