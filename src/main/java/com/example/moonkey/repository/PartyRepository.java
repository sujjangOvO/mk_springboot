package com.example.moonkey.repository;

import com.example.moonkey.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party,Long> {
    Optional<Party> findOneByPartyTitle(String partyTitle);
    // Optional<Party> findOneByPartyId(int partyId);

    Party findOneByPartyId(long partyId);
    List<Party> findAll();
}
