package com.example.moonkey.domain;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE package SET deleted = true WHERE package_id = ?")
@Entity
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageId")
    private long packageId;

    @OneToOne
    @JoinColumn(name = "storeId")
    private Store storeId;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Orders> orderId = new ArrayList<>(Collections.emptyList()); // FK

    @OneToOne
    @JoinColumn(name = "partyId")
    private Party partyId; // FK

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "package_product")
    @Builder.Default
    private List<String> product = new ArrayList<>(Collections.emptyList());

    @NotNull
    private String address;

    @NotNull
    private int amount;
    @Builder.Default
    private boolean activated = Boolean.TRUE;
    @Builder.Default
    private boolean deleted = Boolean.FALSE;

    public List<Long> getOrderIds() {
        List<Long> ordersList = new ArrayList<>(orderId.size());
        for (Orders orders : orderId) {
            ordersList.add(orders.getOrderId());
        }
        return ordersList;
    }

    public void setPackageActivatedFalse(Package aPackage) {
        this.activated = false;
    }
}
