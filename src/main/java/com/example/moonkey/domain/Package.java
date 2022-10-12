package com.example.moonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Package {
    @Id
    private int package_number;

    @OneToOne
    @JoinColumn(name="party_party_number")
    private Party party_number; // FK

    private String customer_id;

    private String package_name;
    private String package_menu;
    private String address;
    private int join_amount;
}
