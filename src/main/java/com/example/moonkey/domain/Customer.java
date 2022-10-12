package com.example.moonkey.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    private String customer_id;

    @OneToMany(mappedBy = "customer_id")
    private List<Orders> orderList = new ArrayList<>();

    private String address;
    private String nickname;
}
