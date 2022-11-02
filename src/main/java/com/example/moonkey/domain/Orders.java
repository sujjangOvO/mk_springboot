package com.example.moonkey.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @NotNull
    private int total_price;

    /*
    @OneToMany(mappedBy = "orders")
    private List<Menu> menus = new ArrayList<>(); 오류*/

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store_id; // FK

    @ManyToOne
    @JoinColumn(name = "account_uid")
    private Account uid; // FK
}
