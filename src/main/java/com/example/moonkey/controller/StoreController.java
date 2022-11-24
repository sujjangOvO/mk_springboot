package com.example.moonkey.controller;

import com.example.moonkey.domain.Store;
import com.example.moonkey.dto.StoreDto;
import com.example.moonkey.dto.StoreDisplayDto;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.StoreRepository;
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
	private final StoreRepository storeRepository;

	public StoreController(StoreService storeService, StoreRepository storeRepository){
		this.storeService = storeService;
		this.storeRepository = storeRepository;
	}

	@PostMapping("/store/reg")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<StoreDto> register(
			@Valid @RequestBody StoreDto storeDto
	){
		return ResponseEntity.ok(storeService.register(storeDto));
	}

	@PostMapping("/store/unreg/{storeId}")
	public ResponseEntity <String> unregister(
			@PathVariable("storeId") Long storeId
	){
		return ResponseEntity.ok(storeService.unregister(storeId));
	}

	@GetMapping("/store/{name}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<StoreDto> getStoreInfo(@PathVariable("name") String name){
		return ResponseEntity.ok(storeService.getStore(name));
	}

	@GetMapping("/store/{storeId}/get")
	public ResponseEntity<StoreDto> getStoreByStoreId(@PathVariable("storeId") long storeId){
		Store store = storeRepository.findStoreByStoreId(storeId).orElseThrow(()->new NotFoundStoreException("Store not found"));
		return ResponseEntity.ok(storeService.getStoreByStoreId(storeId));
	}

	@GetMapping("/store/get/{ownerId}")
	public ResponseEntity<StoreDto> getStoreInfoByUid(@PathVariable("ownerId") long ownerId){
		return ResponseEntity.ok(storeService.getStore(ownerId));
	}

	@GetMapping("/store/list")
	public ResponseEntity<List<StoreDisplayDto>> getStores(HttpServletRequest request){
		return ResponseEntity.ok(storeService.getStores());
	}

	@GetMapping("store/myList")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<StoreDto>> getMyStores(HttpServletRequest request){
		return ResponseEntity.ok(storeService.getMyStores());
	}

//	@GetMapping("/store")
//	@PreAuthorize("hasAnyRole('USER','ADMIN')")
//	public ResponseEntity<List<StoreDto>> getStoresInfo(HttpServletRequest request){
//
//
//		return ResponseEntity.ok();
//	}


}
