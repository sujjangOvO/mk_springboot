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
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId")
    private int deliveryId;

    @OneToOne
    @JoinColumn(name = "accountUid")
    private Account uid; // 배달원

    @OneToOne
    @JoinColumn(name = "orderId")
    private Orders orderId;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store storeId;

    @NotNull
    private int distance;

    @NotNull
    private String address;
}
