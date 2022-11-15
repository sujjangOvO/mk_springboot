package com.example.moonkey.repository;

import com.example.moonkey.domain.Menu;
import com.example.moonkey.domain.Store;
import com.example.moonkey.domain.Package;
import com.example.moonkey.domain.Orders;
import com.example.moonkey.domain.Party;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PackageRepository extends JpaRepository<Package,Long>{
    List<Package> findAll();
    Package findOneByPackageId(long id);

}
