package com.example.moonkey.service;

import com.example.moonkey.domain.*;
import com.example.moonkey.dto.StoreDisplayDto;
import com.example.moonkey.dto.StoreDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.exception.PartyRunningException;
import com.example.moonkey.repository.*;
import com.example.moonkey.util.SecurityUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class StoreService {
	private final StoreRepository storeRepository;
	private final AccountRepository accountRepository;
	private final CategoryRepository categoryRepository;
	private final MenuRepository menuRepository;
	private final PartyRepository partyRepository;
	private final PackageRepository packageRepository;



	public StoreService(StoreRepository storeRepository, AccountRepository accountRepository, CategoryRepository categoryRepository, MenuRepository menuRepository, PartyRepository partyRepository, PackageRepository packageRepository){
		this.storeRepository = storeRepository;
		this.accountRepository = accountRepository;
		this.categoryRepository = categoryRepository;
		this.menuRepository = menuRepository;
		this.partyRepository = partyRepository;
		this.packageRepository = packageRepository;
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

		Store resultStore = storeRepository.save(store);

		// account 접근해서 account의 store 변경
		Set<Store> stores = account.getStores();
		stores.add(store);
		account.setStores(stores);
		accountRepository.save(account);

		return storeDto.from(resultStore); // occurs errors Field "category" dosen't have a default value
	}

	@Transactional
	public StoreDto unregister(long store_id){
		Store store = storeRepository.findOneByStoreId(store_id)
				.orElseThrow(()->new NotFoundStoreException("Store not found"));
		//TODO 현재 진행중인 파티 or 배달에 대해 확인할 것
		List<Party> partyList = partyRepository.findAllByStoreId(store);
		Iterator<Party> partyIterator = partyList.iterator();
		while(partyIterator.hasNext()){
			Party party = partyIterator.next();
			if(party.isActivated()){
				throw new PartyRunningException("There is running parties on the store");
			}
		}

		StoreDto storeDto = StoreDto.from(store);
		String name = store.getName();
		List<Menu> menuList = menuRepository.findAllByStoreId(store);
		Iterator<Menu> menuIter = menuList.iterator();

		menuRepository.deleteAll(menuList);
		storeRepository.delete(store);

		return  storeDto;
	}

	@Transactional
	public StoreDto getStore(String name){
		return StoreDto.from(
				storeRepository.findOneByName(name)
				.orElseThrow(()->new NotFoundStoreException("Store not found")));
	}

	public StoreDto getStoreByStoreId(long storeId){
		return StoreDto.from(
				storeRepository.findStoreByStoreId(storeId)
						.orElseThrow(()->new NotFoundStoreException("Store not found")));
	}

	@Transactional
	public StoreDto getStore(long ownerId){
		Account account = accountRepository.findAccountByUid(ownerId)
				.orElseThrow(()->new NotFoundMemberException("Member not found"));

		Store store = storeRepository.findStoreByOwnerId(account)
				.orElseThrow(()->new NotFoundStoreException("Store not found"));

		return StoreDto.from(store);
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

	@Transactional
	public List<StoreDto> getMyStores(){
		Account user =  SecurityUtil.getCurrentUsername()
				.flatMap(accountRepository::findOneWithAuthoritiesById)
				.orElseThrow(()->new NotFoundMemberException("Member not found"));
		List<Store> storeList = storeRepository.findAllByOwnerId(user);
		List<StoreDto> storeDtoList = new ArrayList<>(Collections.emptyList());

		Iterator<Store> iter = storeList.iterator();

		while(iter.hasNext()){
			Store store = iter.next();
			storeDtoList.add(StoreDto.from(store));
		}
		return storeDtoList;
	}
}
