package com.example.moonkey.controller;

import com.example.moonkey.dto.StoreDto;
import com.example.moonkey.dto.StoreDisplayDto;
import com.example.moonkey.service.StoreService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/app")
public class StoreController {

	private final StoreService storeService;

	public StoreController(StoreService storeService){
		this.storeService = storeService;
	}

	@PostMapping("/store/reg")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<StoreDto> register(
			@Valid @RequestBody StoreDto storeDto
	){
		return ResponseEntity.ok(storeService.register(storeDto));
	}

	@PatchMapping("/store/leave")
	public ResponseEntity <String> unregister(
			@Valid @RequestBody long store_id
	){
		return ResponseEntity.ok(storeService.unregister(store_id));
	}

	@GetMapping("/store/{name}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<StoreDto> getStoreInfo(@PathVariable String name){
		return ResponseEntity.ok(storeService.getStore(name));
	}

	@GetMapping("/store/list")
	public ResponseEntity<List<StoreDisplayDto>> getStores(HttpServletRequest request){
		return ResponseEntity.ok(storeService.getStores());
	}

//	@GetMapping("/store")
//	@PreAuthorize("hasAnyRole('USER','ADMIN')")
//	public ResponseEntity<List<StoreDto>> getStoresInfo(HttpServletRequest request){
//
//
//		return ResponseEntity.ok();
//	}


}
