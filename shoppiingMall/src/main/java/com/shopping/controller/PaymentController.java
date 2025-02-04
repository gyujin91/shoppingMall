package com.shopping.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping.dto.OrderDTO;
import com.shopping.dto.PaymentDTO;
import com.shopping.service.OrderService;
import com.shopping.service.PaymentService;


@Controller
@RequestMapping("/payment/*")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	OrderService orderService;
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@RequestMapping("insertPayment.do")
	public String insertPayment(@RequestParam int order_no ,HttpSession session, Model model) throws Exception {
	    
		@SuppressWarnings("unchecked")
	    Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
	    
	    if(loginMap != null) {
	        String mem_id = (String) loginMap.get("MEM_ID");	        
        	System.out.println("======================= | 로그인 ID :: " + mem_id + "|=======================");
        	
	        List<OrderDTO> orderList = orderService.orderList(mem_id);
	        System.out.println("orderList ::" + orderList);
	        // PaymentDTO duplicateCheck = paymentService.duplicateCheck(order_no);
	        PaymentDTO payment = new PaymentDTO();
	        for(OrderDTO order : orderList) {	        	
	        		payment.setOrder_no(order.getOrder_no());
	            	payment.setMem_id(order.getMem_id());
	            	payment.setMem_name(order.getMem_name());
	            	payment.setPayment_price(order.getPrice());
	            	payment.setPayment_date(order.getOrder_date());
	            	payment.setDeposit_bank("기업 123456789 주식 대부");
	            	payment.setPayment_method("무통장 입금");	        	
	        }
	        paymentService.insertPayment(payment);	
        	System.out.println("결제 정보가 성공적으로 등록 되었습니다.");
        	
	        return "redirect:/order/orderList.do";
	    } else {
	        System.out.println("로그인 하지 않은 상태");
	        return "redirect:/member/loginForm.do";
	    }
	}
	
	
}
