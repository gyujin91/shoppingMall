package com.shopping.dao;

import java.util.Map;

public interface OrderDAO {
	
	// 주문 목록 담기
	public void orderInsert(Map<String, Object> params) throws Exception;
}
