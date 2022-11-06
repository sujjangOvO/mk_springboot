package com.example.moonkey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuId")
    private long menuId;

    @ManyToOne
    @JoinColumn(name="storeId")
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
