//package com.shopping.controller;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.shopping.dto.PaymentDTO;
//import com.shopping.service.PaymentService;
//
//
//@Controller
//@RequestMapping("/payment/*")
//public class PaymentController2 {
//	
//	@Autowired
//	PaymentService paymentService;
//	
//	private static final Logger logger = LoggerFactory.getLogger(PaymentController2.class);
//	
//	@RequestMapping("insertPayment.do")
//	public String insertPayment(PaymentDTO dto, HttpSession session, Model model, @RequestParam String mem_id, 
//			@RequestParam("selectedOption") String selectedOption, @RequestParam("labelOption") String labelOption) throws Exception {
//		@SuppressWarnings("unchecked")
//		Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
//		
//		if(loginMap != null) {
//			
//			if("bank".equals(dto.getPayment_method())) {
//				// 무통장 입금인 경우
//				dto.setDeposit_bank("기업 123456789 주식 대부");
//				dto.setPayment_method("무통장 입금");
//			} else if("creditCard".equals(dto.getPayment_method())) {
//				// 카드 결제 인 경우
//				// 카드 결제 로직 추가 예정
//				dto.setPayment_method("카드 결제");
//			}
//			paymentService.insertPayment(dto);
//			model.addAttribute("selectedOption", selectedOption);
//			model.addAttribute("labelOption", labelOption);
//			return "redirect:/order/orderDetail.do";
//		} else {
//			System.out.println("로그인 하지 않은 상태");
//			return "redirect:/member/loginForm.do";
//		}
//		
//	}
//	
////	@RequestMapping("paymentDetail.do")
////	public String paymentDetail(Model model) throws Exception {
////		return "";
////	}
//	
//	
//}