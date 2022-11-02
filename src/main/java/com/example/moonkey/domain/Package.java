package com.example.moonkey.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int packageId;

    @OneToOne
    @JoinColumn(name="orders_orderId")
    private Orders orderId; // FK

    @NotNull
    private String product;

    @NotNull
    private String address;

    @NotNull
    private int amount;
}
