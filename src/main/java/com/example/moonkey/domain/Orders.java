package com.example.moonkey.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_number;

    private int total_price;

    @OneToMany(mappedBy = "order_id")
    private List<Menu> menus = new ArrayList<>(); // FK

    @OneToOne(mappedBy = "order_number")
    private Rider rider_id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store_id; // FK

    @ManyToOne
    @JoinColumn(name = "customer_customer_id")
    private Customer customer_id; // FK
}
