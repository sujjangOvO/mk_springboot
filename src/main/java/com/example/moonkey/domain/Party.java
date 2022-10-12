package com.example.moonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Party {

    @Id
    private int party_number;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store_id; // FK로 등록

    private String nickname;

}
