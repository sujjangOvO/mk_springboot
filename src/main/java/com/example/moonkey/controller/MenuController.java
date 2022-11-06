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

		@PostMapping("/menu/reg")
		public ResponseEntity<MenuDto> register (
				@Valid @RequestBody long storeUid,MenuDto menuDto
		){
			return ResponseEntity.ok(
					menuService.register(storeUid, menuDto));
		}

		public ResponseEntity<List<MenuDto>> search(
				@Valid @RequestBody long storeUid
		){
			return ResponseEntity.ok(
					menuService.getMenu(storeUid));
		}

}
