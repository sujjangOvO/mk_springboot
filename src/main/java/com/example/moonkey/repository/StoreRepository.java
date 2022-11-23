package com.example.moonkey.repository;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {

	Optional<Store> findOneByName(String name);
	Store findOneByStoreId(long id);

	Optional<Store> findStoreByOwnerId(Account ownerId);

	Optional<Store> findStoreByStoreId(long storeId);

	List<Store> findAll();
}
