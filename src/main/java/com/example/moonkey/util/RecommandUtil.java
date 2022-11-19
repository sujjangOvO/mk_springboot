package com.example.moonkey.util;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.example.moonkey.util.SecurityUtil;

import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.StoreRepository;
import com.example.moonkey.repository.OrderRepository;
import com.example.moonkey.repository.CategoryRepository;


import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Orders;
import com.example.moonkey.domain.Store;
import com.example.moonkey.domain.Category;


public class RecommandUtil {
/* TO-DO
	회원별 주문 조회
	회원간 주문 기록 비교
	가장 비슷한 회원 연관
	연관 규칙 분석 (카테고리별 지지도, 신뢰도 기반 분석) -> matrix representation(2D-array)
	회원별로 연관도 기반 가중치 분석
	출력값을 바탕으로 수치 정렬
	정렬 기준
 */
	AccountRepository accountRepository;
	StoreRepository storeRepository;
	OrderRepository orderRepository;
	CategoryRepository categoryRepository;

	public RecommandUtil(AccountRepository accountRepository, StoreRepository storeRepository,
						 OrderRepository orderRepository, CategoryRepository categoryRepository){
		this.accountRepository = accountRepository;
		this.storeRepository = storeRepository;
		this.orderRepository = orderRepository;
		this.categoryRepository = categoryRepository;
	}

/* TO-DO
	JWT 토큰을 기반으로 회원정보 받아올 것
	회원 정보 확인 후 처리
 */

}
