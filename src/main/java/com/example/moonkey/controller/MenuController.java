package com.example.moonkey.controller;

import com.example.moonkey.dto.MenuDto;
import com.example.moonkey.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/app/store")

public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("{storeId}/menu/reg")
    public ResponseEntity<MenuDto> register(
            @PathVariable("storeId") Long storeId, @Valid @RequestBody MenuDto menuDto
    ) {

        return ResponseEntity.ok(
                menuService.register(storeId, menuDto));
    }

    @PostMapping("{storeId}/menu/unreg/{menuId}")
    public ResponseEntity<String> unregister(
            @PathVariable("storeId") Long storeId, @PathVariable("menuId") Long menuId
    ) {
        return ResponseEntity.ok(menuService.unregister(menuId));
    }

    @GetMapping("/{storeId}/menu/list")
    public ResponseEntity<List<MenuDto>> search(
            @PathVariable("storeId") String storeId
    ) {

        return ResponseEntity.ok(
                menuService.getMenu(Long.parseLong(storeId)));
    }

}
