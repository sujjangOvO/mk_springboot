package com.example.moonkey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql="UPDATE menu SET deleted = true WHERE menu_id = ?")
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

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}
