package com.example.moonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rider {

    @Id
    private String rider_id;

    private String store_id; // FK

    private String nickname;

    private int order_number; // FK

    private int menu_number; // FK

    private String customer_id; // FK
}
