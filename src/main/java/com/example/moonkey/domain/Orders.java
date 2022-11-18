package com.example.moonkey.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private long orderId;

    @NotNull
    private int number;

    @NotNull
    private Timestamp orderDate;

    @ManyToOne
    @JoinColumn(name="menuId")
    private Menu menuId;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store storeId;

    @ManyToOne
    @JoinColumn(name = "accountUid")
    private Account uid;
}
