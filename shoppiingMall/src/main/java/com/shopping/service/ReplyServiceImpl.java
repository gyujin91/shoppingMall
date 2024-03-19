package com.shopping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dao.ReplyDAO;
import com.shopping.dto.ReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	ReplyDAO replyDao;
	
	@Override
	public List<ReplyDTO> selectReply(int review_no) throws Exception {
		return replyDao.selectReply(review_no);
	}

	
}