package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.dto.StoreDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.StoreRepository;
import com.example.moonkey.util.SecurityUtil;
import com.example.moonkey.domain.Store;
import com.example.moonkey.dto.AccountUidDto;
import com.example.moonkey.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


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
	public StoreDto getStore(String name){
		return StoreDto.from(
				storeRepository.findOneByName(name)
				.orElseThrow(()->new NotFoundStoreException("Store not found")));
	}
}
