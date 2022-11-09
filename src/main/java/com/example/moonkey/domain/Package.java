package com.example.moonkey.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private int packageId;

    @OneToOne
    @JoinColumn(name="orderId")
    private Orders orderId; // FK

    @OneToOne
    @JoinColumn(name="partyId")
    private Party partyId; // FK

    @NotNull
    private String product;

    @NotNull
    private String address;

    @NotNull
    private int amount;
}
