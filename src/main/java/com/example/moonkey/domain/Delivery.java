package com.example.moonkey.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int delivery_number;

    private int order_number; // FK

    private String store_id; // FK

    private String producer_id; // FK

    private String rider_id; // FK

    private int distance;
    private String address;
}
