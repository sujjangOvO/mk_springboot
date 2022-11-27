package com.example.moonkey.repository;

import com.example.moonkey.domain.Menu;
import com.example.moonkey.domain.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long>{

	List<Menu> findAll();
	List<Menu> findAllByStoreId(Store storeId);
	Optional<Menu> findOneByMenuId(long menuId);
	Optional<Menu> findMenuByMenuId(long menuId);


}
