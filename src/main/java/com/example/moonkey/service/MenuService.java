package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Store;
import com.example.moonkey.dto.MenuDto;
import com.example.moonkey.domain.Menu;
import com.example.moonkey.dto.StoreDto;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.MenuRepository;
import com.example.moonkey.repository.StoreRepository;
import com.example.moonkey.util.SecurityUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

	private final MenuRepository menuRepository;
	private StoreRepository storeRepository;


	public MenuService(StoreRepository storeRepository, MenuRepository menuRepository){
		this.menuRepository = menuRepository;
		this.storeRepository = storeRepository;
	}

	@Transactional
	public MenuDto register(long storeUid, MenuDto menuDto){

		return new MenuDto();
	}

	public List<Menu> getMenu(long storeid){

		return menuRepository.findAllbyStoreId(storeid);
	}

}
