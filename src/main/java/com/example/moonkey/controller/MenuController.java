package com.example.moonkey.controller;

import com.example.moonkey.domain.Menu;
import com.example.moonkey.dto.MenuDto;
import com.example.moonkey.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/app/store")

public class MenuController {

		private final MenuService menuService;

		public MenuController(MenuService menuService){
			this.menuService = menuService;
		}

		@PostMapping("{storeId}/menu/reg")
		public ResponseEntity<MenuDto> register (
				@PathVariable("storeId") String storeId , @Valid @RequestBody MenuDto menuDto
		){
			return ResponseEntity.ok(
					menuService.register(Long.parseLong(storeId),menuDto));
		}

		@PatchMapping("{storeId}/menu/unreg/{menuId}")
		public ResponseEntity<String> unregister(
				@PathVariable("storeId") String storeId, @PathVariable("menuId") String menuId
		){

			return ResponseEntity.ok(menuService.unregister(Long.getLong(menuId)));
		}

		@GetMapping("/{storeId}/menu/list")
		public ResponseEntity<List<MenuDto>> search(
				@PathVariable("storeId") String storeId
		){

			return ResponseEntity.ok(
					menuService.getMenu(Long.parseLong(storeId)));
		}

}
