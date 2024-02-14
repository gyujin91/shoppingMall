package com.shopping.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shopping.dto.ProductDTO;

@Repository
public class ProductDAOImpl implements ProductDAO{
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<ProductDTO> productList(String cate_no) throws Exception {
		return sqlSession.selectList("product.productList", cate_no);
	}

	@Override
	public ProductDTO productDetail(int prod_no) throws Exception {
		return sqlSession.selectOne("product.productDetail", prod_no);
	}

	@Override
	public List<ProductDTO> itemList() throws Exception {
		return sqlSession.selectList("product.itemList");
	}

	@Override
	public List<ProductDTO> itemListByCategory(String cate_no) throws Exception {
		return sqlSession.selectList("product.itemListByCategory", cate_no);
	}

	
	
	
	
}
