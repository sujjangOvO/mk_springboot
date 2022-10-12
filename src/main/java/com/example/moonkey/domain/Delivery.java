package com.example.moonkey.domain;

import javax.persistence.*;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int delivery_number;

    @OneToOne
    @JoinColumn(name = "orders_order_number")
    private Orders order_number; // FK

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store_id; // FK

    @OneToOne
    @JoinColumn(name = "rider_rider_id")
    private Rider rider_id; // FK

    private int distance;
    private String address;
}
