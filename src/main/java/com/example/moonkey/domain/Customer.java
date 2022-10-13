package com.example.moonkey.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customer_id;

    @Column(unique = true)
    private String id;

    @OneToMany(mappedBy = "customer_id")
    private List<Orders> orderList = new ArrayList<>();

    private String address;
    private String nickname;
}
