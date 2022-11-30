package com.example.moonkey.service;

import com.example.moonkey.domain.Menu;
import com.example.moonkey.domain.Store;
import com.example.moonkey.dto.MenuDto;
import com.example.moonkey.exception.NotFoundMenuException;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.MenuRepository;
import com.example.moonkey.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public MenuDto register(Long storeId, MenuDto menuDto) {

        Store store = storeRepository.findOneByStoreId(storeId)
                .orElseThrow(() -> new NotFoundStoreException("Store not found"));

        if (menuDto.getMenuId() != 0) {
            Menu originMenu = menuRepository.findOneByMenuId(menuDto.getMenuId())
                    .orElseThrow(() -> new NotFoundMenuException("Menu not found"));
            Menu menu = Menu.builder()
                    .menuId(originMenu.getMenuId())
                    .menuName(menuDto.getMenuName())
                    .storeId(store)
                    .description(menuDto.getDescription())
                    .options(menuDto.getOptions())
                    .price(menuDto.getPrice())
                    .build();
            return MenuDto.from(menuRepository.save(menu));
        } else {
            Menu menu = Menu.builder()
                    .menuName(menuDto.getMenuName())
                    .storeId(store)
                    .description(menuDto.getDescription())
                    .options(menuDto.getOptions())
                    .price(menuDto.getPrice())
                    .build();
            return MenuDto.from(menuRepository.save(menu));
        }
    }

    @Transactional
    public String unregister(long menuId) {
        Menu menu = menuRepository.findOneByMenuId(menuId)
                .orElseThrow(() -> new NotFoundMenuException("Menu not found"));
        if (menu != null) {
            String name = menu.getMenuName();
            menuRepository.delete(menu);
            return "Success to delete " + name;
        }
        return "Menu not found";
    }

    @Transactional(readOnly = true)
    public List<MenuDto> getMenu(long storeid) {
        Store store = storeRepository.findOneByStoreId(storeid)
                .orElseThrow(() -> new NotFoundStoreException("Store not found"));
        List<Menu> menuList = menuRepository.findAllByStoreId(store);
        return menuList.stream().map(MenuDto::from).toList();
    }
}
