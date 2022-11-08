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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Service
public class MenuService {

	private final MenuRepository menuRepository;
	private StoreRepository storeRepository;


	public MenuService(StoreRepository storeRepository, MenuRepository menuRepository){
		this.menuRepository = menuRepository;
		this.storeRepository = storeRepository;
	}

	@Transactional
	public MenuDto register(MenuDto menuDto){
		long storeId = menuDto.getStoreId();
		Store store = Store.builder().build();

		Menu menu = Menu.builder()
				.menuName(menuDto.getMenuName())
				.storeId(store)
				.description(menuDto.getDescription())
				.options(menuDto.getOptions())
				.price(menuDto.getPrice())
				.options(menuDto.getOptions())
				.build();

		return new MenuDto();
	}

	public List<MenuDto> getMenu(long storeid){
		List<Menu> menuList = menuRepository.findAllbyStore_storeId(storeid);
		Iterator<Menu> iter = menuList.iterator();



		List<MenuDto> menuDtos = new List<MenuDto>;
		// TO-DO:Converting Menu list -> MenuDto list
		while(iter.hasNext())
		{
			Menu menu = iter.next();
			MenuDto menuDto;
			menuDtos.add(menuDto);
		}

		return	menuDtos;
	}

}
