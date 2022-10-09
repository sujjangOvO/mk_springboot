package com.example.moonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Package {
    @Id
    private int package_number;

    private int party_number; // FK

    private String customer_id;

    private String package_name;
    private String package_menu;
    private String address;
    private int join_amount;
}
