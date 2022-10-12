package com.example.moonkey.domain;

import javax.persistence.*;

@Entity
public class Menu {

    @Id
    private long menu_number;

    @ManyToOne
    @JoinColumn(name = "orders_order_number")
    private Orders order_id;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store_id; // FK

    private int price;
    private String menu_name;

    @Column(nullable = true)
    private String option;
}
