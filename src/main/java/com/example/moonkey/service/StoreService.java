package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Menu;
import com.example.moonkey.dto.MenuDto;
import com.example.moonkey.dto.StoreDisplayDto;
import com.example.moonkey.dto.StoreDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.StoreRepository;
import com.example.moonkey.util.SecurityUtil;
import com.example.moonkey.domain.Store;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@Service
public class StoreService {
	private final StoreRepository storeRepository;
	private AccountRepository accountRepository;


	public StoreService(StoreRepository storeRepository, AccountRepository accountRepository){
		this.storeRepository = storeRepository;
		this.accountRepository = accountRepository;
	}

	@Transactional
	public StoreDto register(StoreDto storeDto){
		Account account =
				SecurityUtil.getCurrentUsername()
				.flatMap(accountRepository::findOneWithAuthoritiesById)
				.orElseThrow(()->new NotFoundMemberException("Member not found"));
		Store store = Store.builder()
				.storeId(storeDto.getStoreId())
				.name(storeDto.getName())
				.address(storeDto.getAddress())
				.description(storeDto.getDescription())
				.ownerId(account)
				.build();

		return storeDto.from(storeRepository.save(store));
	}

	@Transactional
	public String unregister(long store_id){
		Store store = storeRepository.findOneByStoreId(store_id);
		if (store != null) {
			String name = store.getName();
			storeRepository.delete(store);
			return "Success to delete" + name;
		}
		return "Store not found";
	}

	@Transactional
	public StoreDto getStore(String name){
		return StoreDto.from(
				storeRepository.findOneByName(name)
				.orElseThrow(()->new NotFoundStoreException("Store not found")));
	}

	@Transactional
	public List<StoreDisplayDto> getStores(){

		List<Store> storeList = storeRepository.findAll();
		Iterator<Store> iter = storeList.iterator();


		List<StoreDisplayDto> storeDisplayDtos = new ArrayList<>(Collections.emptyList());

		while(iter.hasNext())
		{
			Store store = iter.next();
			StoreDisplayDto storeDisplayDto = StoreDisplayDto.builder()
							.storeId(store.getStoreId())
							.address(store.getAddress())
							.name(store.getName())
							.description(store.getDescription())
							.category(store.getCategory())
							.build();
			storeDisplayDtos.add(storeDisplayDto);
		}
		return storeDisplayDtos;

	}
}
