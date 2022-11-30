package com.example.moonkey.service;

import com.example.moonkey.domain.*;
import com.example.moonkey.dto.StoreDisplayDto;
import com.example.moonkey.dto.StoreDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.exception.PartyRunningException;
import com.example.moonkey.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final PartyRepository partyRepository;
    private final AccountService accountService;

    @Transactional
    public StoreDto register(StoreDto storeDto) {
        Account account = accountService.getAccount();
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

        return StoreDto.from(resultStore); // occurs errors Field "category" dosen't have a default value
    }

    @Transactional
    public StoreDto unregister(long store_id) {
        Store store = storeRepository.findOneByStoreId(store_id)
                .orElseThrow(() -> new NotFoundStoreException("Store not found"));
        //TODO 현재 진행중인 파티 or 배달에 대해 확인할 것
        List<Party> partyList = partyRepository.findAllByStoreId(store);
        for (Party party : partyList) {
            if (party.isActivated()) {
                throw new PartyRunningException("There is running parties on the store");
            }
        }

        StoreDto storeDto = StoreDto.from(store);
        List<Menu> menuList = menuRepository.findAllByStoreId(store);

        menuRepository.deleteAll(menuList);
        storeRepository.delete(store);

        return storeDto;
    }

    @Transactional(readOnly = true)
    public StoreDto getStore(String name) {
        return StoreDto.from(
                storeRepository.findOneByName(name)
                        .orElseThrow(() -> new NotFoundStoreException("Store not found")));
    }

    @Transactional(readOnly = true)
    public StoreDto getStoreByStoreId(long storeId) {
        return StoreDto.from(
                storeRepository.findStoreByStoreId(storeId)
                        .orElseThrow(() -> new NotFoundStoreException("Store not found")));
    }

    @Transactional(readOnly = true)
    public StoreDto getStore(long ownerId) {
        Account account = accountRepository.findAccountByUid(ownerId)
                .orElseThrow(() -> new NotFoundMemberException("Member not found"));

        Store store = storeRepository.findStoreByOwnerId(account)
                .orElseThrow(() -> new NotFoundStoreException("Store not found"));

        return StoreDto.from(store);
    }

    @Transactional(readOnly = true)
    public List<StoreDisplayDto> getStores() {
        List<Store> storeList = storeRepository.findAll();
        return storeList.stream()
                .map(StoreDisplayDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StoreDto> getMyStores() {
        Account user = accountService.getAccount();
        List<Store> storeList = storeRepository.findAllByOwnerId(user);

        return storeList.stream()
                .map(StoreDto::from)
                .toList();
    }
}
