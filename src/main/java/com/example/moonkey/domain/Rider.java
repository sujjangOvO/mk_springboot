package com.example.moonkey.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rider {

    @Id
    private String rider_id;

    private String nickname;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store_id; // FK

    @OneToMany(mappedBy = "rider_id")
    private List<RiderHistory> riderHistories = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "orders_order_number")
    private Orders order_number; // FK

    /*
    @OneToMany(mappedBy = "rider")
    private List<Customer> customers = new ArrayList<>(); // FK

    손님이 Rider 정보를 알 필요가 있나? */
}
