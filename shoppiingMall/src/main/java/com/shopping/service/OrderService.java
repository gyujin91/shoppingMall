package com.shopping.service;

import java.util.Map;

public interface OrderService {
	
	// 주문 목록 담기
	public void orderInsert(Map<String, Object> params) throws Exception;
}
