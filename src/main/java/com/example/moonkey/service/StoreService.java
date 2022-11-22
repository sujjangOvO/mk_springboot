package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Category;
import com.example.moonkey.dto.StoreDisplayDto;
import com.example.moonkey.dto.StoreDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.CategoryRepository;
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
	private final AccountRepository accountRepository;

	private final CategoryRepository categoryRepository;


	public StoreService(StoreRepository storeRepository, AccountRepository accountRepository, CategoryRepository categoryRepository){
		this.storeRepository = storeRepository;
		this.accountRepository = accountRepository;
		this.categoryRepository = categoryRepository;
	}

	@Transactional
	public StoreDto register(StoreDto storeDto){
		Account account =
				SecurityUtil.getCurrentUsername()
				.flatMap(accountRepository::findOneWithAuthoritiesById)
				.orElseThrow(()->new NotFoundMemberException("Member not found"));

		Category category = categoryRepository.findOneByCategoryName(storeDto.getCategory());

		Store store = Store.builder()
				.name(storeDto.getName())
				.address(storeDto.getAddress())
				.description(storeDto.getDescription())
				.categoryName(category)
				.ownerId(account)
				.contact(storeDto.getContact())
				.build();


		return storeDto.from(storeRepository.save(store)); // occurs errors Field "category" dosen't have a default value
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
							.category(store.getCategoryName().getCategoryName())
							.contact(store.getContact())
							.build();
			storeDisplayDtos.add(storeDisplayDto);
		}
		return storeDisplayDtos;

	}
}
