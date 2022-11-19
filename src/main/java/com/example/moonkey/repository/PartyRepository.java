package com.example.moonkey.repository;

import com.example.moonkey.domain.Party;
import com.example.moonkey.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party,Long> {
    Optional<Party> findOneByPartyTitle(String partyTitle);
    Optional<Party> findOneByPartyId(long partyId);

    //Party findOneByPartyId(long partyId);
    List<Party> findAll();

    List<Party> findAllByStoreId(Store store);
}
