package com.example.moonkey.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private long packageId;

    @OneToMany
    @JoinColumn(name="orderId")
    private List<Orders> orderId; // FK

    @OneToOne
    @JoinColumn(name="partyId")
    private Party partyId; // FK

    @NotNull
    private String product;

    @NotNull
    private String address;

    @NotNull
    private int amount;

    public List<Long> getOrderIds(){
        List<Long> ordersList = new ArrayList<>(orderId.size());
        Iterator<Orders> iter = orderId.iterator();
        while(iter.hasNext()){
            ordersList.add(iter.next().getOrderId());
        }
        return ordersList;
    }

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}
