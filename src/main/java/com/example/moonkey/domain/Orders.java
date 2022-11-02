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
    private int orderId;

    @NotNull
    private int number;

    @ManyToOne
    @JoinColumn(name="menu_menuId")
    private Menu menuId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store_id; // FK

    @ManyToOne
    @JoinColumn(name = "account_uid")
    private Account account_uid; // FK

    @NotNull
    private Timestamp orderDate;
}
