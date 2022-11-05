package com.example.moonkey.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private long menuId;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store storeId; // FK

    @NotNull
    private int price;

    @NotNull
    private String menuName;

    @Column(nullable = true)
    private String options;

    @Column(nullable = true)
    private String description;
}
