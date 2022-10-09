package com.example.moonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Party {

    @Id
    private int party_number;

    private String store_id; // FK로 등록

    private String nickname;

}
