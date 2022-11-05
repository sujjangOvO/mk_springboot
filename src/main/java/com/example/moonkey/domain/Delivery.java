package com.example.moonkey.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private int deliveryId;

    @OneToOne
    @JoinColumn(name = "Account_uid")
    private Account uid; // 배달원

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orderId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store storeId;

    @NotNull
    private int distance;

    @NotNull
    private String address;
}
