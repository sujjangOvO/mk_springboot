package com.example.moonkey.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
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
    private boolean callCheck; // 콜 승인 여부

    @NotNull
    private boolean deliveryCheck; // 배달 완로 여부

    @Column(nullable = true)
    private String requests; // 요청사항

    @NotNull
    private int pay; // 배달료

    @Column(nullable = true)
    private long totalPay; // 배달비 총 정산금

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}
