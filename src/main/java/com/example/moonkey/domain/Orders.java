package com.example.moonkey.domain;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql="UPDATE orders SET deleted = true WHERE order_id = ?")
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
    @JoinColumn(name = "uid")
    private Account uid;

    @ManyToOne
    @JoinColumn(name="packageId")
    private Package packageId;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}
