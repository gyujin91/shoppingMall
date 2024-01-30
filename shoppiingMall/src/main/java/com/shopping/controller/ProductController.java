package com.shopping.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping.dto.ProductDTO;
import com.shopping.service.ProductService;


@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	// 객체 주입
	@Autowired
	ProductService productService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	// items 페이지 이동
	@RequestMapping("items.do")
	public String items() throws Exception {
		return "product/items";
	}
	
	// item 리스트
	@RequestMapping("itemList.do")
	public String itemList(Model model) throws Exception {
		List<ProductDTO> itemList = productService.itemList();
		model.addAttribute("itemList", itemList);
		return "product/items";
	}
	 	
	// 제품 상세보기 페이지 이동
	@RequestMapping("productDetail.do")
	public String productDetail() throws Exception {
		return "product/productDetail";
	}
	
	// 제품 상세보기
	@RequestMapping(value="productDetail.do", method = RequestMethod.GET)
	public String productDetailForm(Model model, @RequestParam int prod_no) throws Exception {
		ProductDTO dto = productService.productDetail(prod_no);
		dto.setBrand("자체 브랜드");
		dto.setDeliveryFee("무료");
		model.addAttribute("dto", dto);
		return "product/productDetail";
	}
	
	
	
	
	
	
	
}
