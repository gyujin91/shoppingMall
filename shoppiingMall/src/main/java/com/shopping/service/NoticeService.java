package com.shopping.service;

import java.util.List;

import com.shopping.dto.NoticeDTO;

public interface NoticeService {
	
	// 공지글 조회
	public List<NoticeDTO> noticeList() throws Exception;
	
	// 공지글 등록
	public void insertNotice(NoticeDTO dto) throws Exception;
	
	// 현재 최대 공지 번호 +1
	public int nextNum() throws Exception;
	
	// 공지 상세 보기
	public NoticeDTO noticeRead(int num) throws Exception;
	
	// 조회수 증가
	public int increaseViewCount(int num) throws Exception;
	
	// 조회수 중복 방지
	
	// 공지 글 수정
	public void updateNotice(NoticeDTO dto) throws Exception;
	
	// 공지 글 삭제
	public void deleteNotice(NoticeDTO dto) throws Exception;
	
	// 총 공지 건수
	public int noticeTotalCnt() throws Exception;
}
