package com.shopping.service;

import java.util.List;
import java.util.Map;

import com.shopping.dto.CartDTO;

public interface CartService {

	// 장바구니 담기
	public void addToCart(CartDTO dto) throws Exception;
	
	// 장바구니 목록 조회
	public List<CartDTO> cartList(String mem_id) throws Exception;
	
	// 장바구니 상품 총수량
	public Integer totalQuantity(String mem_id) throws Exception;
	
	// 장바구니 합계 가격, 택배비
	public Map<String, Object> totalData(String mem_id) throws Exception;
	
	// 장바구니 상품 삭제
	public void cartDelete(int cart_id) throws Exception;
	
	// 주문 완료 시 해당 아이디의 장바구니 모두 삭제 
	public void allCartDelete(String mem_id) throws Exception;

}
