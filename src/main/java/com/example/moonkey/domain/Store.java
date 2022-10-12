package com.example.moonkey.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "store_id")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store_id")
    private List<Party> parties = new ArrayList<>();

    @OneToMany(mappedBy = "store_id")
    private List<Orders> orderList = new ArrayList<>();

    @OneToOne(mappedBy = "store_id")
    private Rider rider_id;

    private String store_name;

    @ManyToOne
    @JoinColumn(name = "producer_id")
    private Producer producer_id; // FK


    private String address;

}
