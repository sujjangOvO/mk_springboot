package com.example.moonkey.domain;

import javax.persistence.*;

@Entity(name = "store")
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String store_name;

    private String producer_id; // FK

    private String address;

}
