package com.shopping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dao.ProductDAO;
import com.shopping.dto.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductDAO productDao;
	
	@Override
	public List<ProductDTO> productList(String cate_no) throws Exception {
		return productDao.productList(cate_no);
	}

	@Override
	public ProductDTO productDetail(int prod_no) throws Exception {
		return productDao.productDetail(prod_no);
	}

	@Override
	public List<ProductDTO> itemList() throws Exception {
		return productDao.itemList();
	}

	@Override
	public List<ProductDTO> itemListByCategory(String cate_no) throws Exception {
		return productDao.itemListByCategory(cate_no);
	}

	
	
	
	
}
