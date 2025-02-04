package com.shopping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dao.PaymentDAO;
import com.shopping.dto.PaymentDTO;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDAO paymentDao;

	@Override
	public void insertPayment(PaymentDTO dto) throws Exception {
		paymentDao.insertPayment(dto);
	}

	@Override
	public PaymentDTO duplicateCheck(int order_no) throws Exception {
		return paymentDao.duplicateCheck(order_no);
	}
	
	@Override
	public List<PaymentDTO> paymentList(String mem_id) throws Exception {
		return paymentDao.paymentList(mem_id);
	}


	
}
