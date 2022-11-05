package com.example.moonkey.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int orderId;

    @NotNull
    private int number;

    @ManyToOne
    @JoinColumn(name="menuId")
    private Menu menuId;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store storeId; // FK

    @ManyToOne
    @JoinColumn(name = "accountUid")
    private Account accountUid; // FK

    @NotNull
    private Timestamp orderDate;
}
