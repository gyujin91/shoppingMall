package com.shopping.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/payment/*")
public class PaymentController {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
//	@RequestMapping("agreement.do")
//	public String agreement() throws Exception {
//		return "member/agreement";
//	}
	
	
}
