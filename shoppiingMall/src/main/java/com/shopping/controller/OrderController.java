package com.shopping.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.service.OrderService;


@Controller
@RequestMapping("/order/*")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@RequestMapping("orderInsert.do")
	public String orderInsert(HttpSession session, Model model, Map<String, Object> params) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
		
		// 로그인 체크
		if(loginMap != null) {
			String mem_id = (String) loginMap.get("MEM_ID");
			
			orderService.orderInsert(params);
		}
		return "";
	}
	
	
}
