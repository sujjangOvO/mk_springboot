package com.example.moonkey.repository;

import com.example.moonkey.domain.Package;
import com.example.moonkey.domain.Party;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findAll();

    Optional<Package> findOneByPackageId(long id);

    @Where(clause = "activated=true")
    Package findOneByPartyId(Party party);

}
