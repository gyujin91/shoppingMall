package com.shopping.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dao.OrderDAO;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDAO orderDao;

	@Override
	public void orderInsert(Map<String, Object> params) throws Exception {
		orderDao.orderInsert(params);		
	}
	
	
}
