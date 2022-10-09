package com.example.moonkey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Menu {

    @Id
    private long menu_number;

    private String store_id; // FK

    private int price;
    private String menu_name;

    @Column(nullable = true)
    private String option;
}
