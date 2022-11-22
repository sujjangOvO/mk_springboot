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
    private long deliveryId;

    @OneToOne
    @JoinColumn(name = "uid")
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

    @NotNull
    private boolean callCheck;

    @Column(nullable = true)
    private String requests; // 요청사항

    @NotNull
    private int pay; // 배달료
}
