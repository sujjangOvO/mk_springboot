package com.example.moonkey.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private long packageId;

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
