package com.shopping.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shopping.dto.MemberDTO;

@Repository
public class AdminDAOImpl implements AdminDAO{
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<MemberDTO> memberList() throws Exception {
		return sqlSession.selectList("admin.memberList");
	}

	@Override
	public List<MemberDTO> allMemberList() throws Exception {
		return sqlSession.selectList("admin.allMemberList");
	}
	
	@Override
	public MemberDTO memberRead(String mem_id) throws Exception {
		return sqlSession.selectOne("admin.memberRead", mem_id);
	}

	@Override
	public void memberUpdate(MemberDTO dto) throws Exception {
		sqlSession.update("admin.memberUpdate", dto);
	}

	@Override
	public void memberDelete(String mem_id) throws Exception {
		sqlSession.update("admin.memberDelte", mem_id);
	}
	
	
}
