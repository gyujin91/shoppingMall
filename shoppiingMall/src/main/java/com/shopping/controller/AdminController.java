package com.shopping.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shopping.dto.MemberDTO;
import com.shopping.dto.NoticeDTO;
import com.shopping.dto.OrderDTO;
import com.shopping.dto.ProductDTO;
import com.shopping.dto.ReviewDTO;
import com.shopping.service.AdminService;
import com.shopping.service.MemberService;
import com.shopping.service.NoticeService;
import com.shopping.service.OrderService;
import com.shopping.service.ProductService;
import com.shopping.service.ReviewService;


@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	// 관리자 대쉬보드  정보 조회
	@RequestMapping("admin.do")
	public String memberList(Model model, HttpSession session) throws Exception {
		try {
			// 세션 만료 체크 후 로그인 페이지로 리다이렉트
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/admin";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				List<MemberDTO> memberList = adminService.memberList();
				List<OrderDTO> orderList = adminService.orderList();
				List<Map<String, Object>> userTotalPrice = adminService.userTotalPrice();
				int pTotalCnt = productService.productTotalCnt();	// 총 상품 건 수
				int oTotalCnt = orderService.orderTotalCnt();	// 총 주문 건 수
				int rTotalCnt = reviewService.reviewTotalCnt();	// 총 리뷰 건 수
				int totalSales = adminService.totalSales();	// 현재 년도 총 매출액
				// List<Map<String, Object>> cumulativeSales = adminService.cumulativeSales();	// 누적 매출액
				
				
				model.addAttribute("memberList", memberList);
				model.addAttribute("orderList", orderList);
				model.addAttribute("userTotalPrice", userTotalPrice);
				model.addAttribute("pTotalCnt", pTotalCnt);
				model.addAttribute("oTotalCnt", oTotalCnt);
				model.addAttribute("rTotalCnt", rTotalCnt);
				model.addAttribute("totalSales", totalSales);
				// model.addAttribute("cumulativeSales", cumulativeSales);
				
				return "admin/admin";
			} else {
				System.out.println("관리자 로그인 후 이용 가능");
				model.addAttribute("loginChk", "fail");
				return "admin/admin";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/admin";
		}
	}
	
	/* -------------------------------------------------------- 유저 -------------------------------------------------------- */
	@RequestMapping("allMemberList.do")
	public String allMemberList(Model model, HttpSession session) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/allMemberList";
			}	
				
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				List<MemberDTO> allMemberList = adminService.allMemberList();
				model.addAttribute("allMemberList", allMemberList);
				
				return "admin/allMemberList";
				
			} else {
				System.out.println("관리자 로그인 후 이용 가능");
				model.addAttribute("loginChk", "fail");
				return "admin/allMemberList";
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/allMemberList";
		}
		
		
	}
	
	// 회원 정보 상세 보기
	@RequestMapping(value = "memberInfo.do", method = RequestMethod.GET)
	public String memberInfo(Model model, @RequestParam String mem_id) throws Exception {
		MemberDTO memberDto = adminService.memberInfo(mem_id);
		
		String phoneNumber = memberDto.getPhone();		
		// 전화번호 분리
		String mobile1 = phoneNumber.substring(0, 3);
		String mobile2 = phoneNumber.substring(3, 7);
		String mobile3 = phoneNumber.substring(7);
		
		model.addAttribute("mobile1", mobile1);
		model.addAttribute("mobile2", mobile2);
		model.addAttribute("mobile3", mobile3);		
		model.addAttribute("memberDto", memberDto);
		return "admin/memberInfo";
	}
	
	// 회원 정보 수정
	@RequestMapping(value = "adminUserUpdate.do", method = RequestMethod.POST)
	public String adminUserUpdate(Model model, MemberDTO dto, @RequestParam String mem_pw, @RequestParam String mobile1, @RequestParam String mobile2,
			@RequestParam String mobile3) throws Exception {
		// 분리된 전화번호를 다시 하나로 합쳐서 phoneNumber 변수에 저장
		String phoneNumber = mobile1 + mobile2 + mobile3;
				
		// 비밀번호가 넘어온 경우 암호화
		if(mem_pw != null && !mem_pw.isEmpty()) {
			String encodeedPassword = pwdEncoder.encode(mem_pw);
			dto.setMem_pw(encodeedPassword);
		}

		// 3개로 분리된 전화번호가 하나 라도 비어 있다면 errorMsg 출력 
		if(mobile1 != null && mobile2 != null & mobile3 != null) {
			dto.setPhone(phoneNumber);
			adminService.adminUserUpdate(dto);
			return "redirect:admin/admin.do";
		} else {
			model.addAttribute("errorMsg", "error");
			return "admin/memberInfo";
		}		
	}
	
	// 회원 탈퇴
	@RequestMapping(value = "adminUserDelete.do", method = RequestMethod.GET)
	public String memberDelete(@RequestParam String mem_id) throws Exception {
		adminService.adminUserDelete(mem_id);
		return "redirect:admin/admin.do";
	}	
	/* -------------------------------------------------------- 유저 -------------------------------------------------------- */
	
	/* -------------------------------------------------------- 상품 -------------------------------------------------------- */
	// 관리자 화면 모든 상품 노출
	@RequestMapping("productList.do")
	public String allProductList(Model model, HttpSession session) throws Exception {		
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/productList";
			}
					
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				List<ProductDTO> allList = productService.allProductList();
				int totalCnt = productService.productTotalCnt();
				
				model.addAttribute("allList", allList);
				model.addAttribute("totalCnt", totalCnt);
				return "admin/productList";
			} else {
				System.out.println("관리자 로그인 후 이용 가능");
				model.addAttribute("loginChk", "fail");
				return "admin/productList";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/productList";
		}
	
	}
	
	// 상품 등록 페이지 이동
	@RequestMapping("fileUpload.do")
	public String productFile() throws Exception {
		return "admin/fileUpload";
	}
	
	// 파일 업로드
	@RequestMapping(value = "fileUpload.do", method = RequestMethod.POST)
	public String fileUpload(HttpSession session, @RequestParam("file") MultipartFile file, ProductDTO pdto,
			Model model, @RequestParam String prod_kind, HttpServletRequest request) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/productInfo";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 로그인 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				try {
					// 파일 업로드 처리
					long size = file.getSize();
					String uploadPath = "D:\\gyujin\\sideP\\shoppiingMall\\src\\main\\webapp\\resources\\img";	// 업로드 파일 저장 경로
					
					String fileRealName = file.getOriginalFilename();	// 원본 파일명
					UUID uuid = UUID.randomUUID();	// 파일명을 현재 시간과 랜덤 UUID를 조합하여 생성
					String fileName = uuid + "_" + fileRealName;		
					// String savePath = session.getServletContext().getRealPath(uploadPath);	// 서버에서 실제 파일이 저장될 경로
					String filePath = uploadPath + File.separator + fileName;	// 파일이 저장될 최종 경로 지정
					file.transferTo(new File(filePath));	// 해당 객체 생성 후 해당 경로에 파일 저장
					
					// 데이터베이스에 저장할 파일 경로 설정
					// String dbFilePath = "/resources/img/" + fileName;
					String dbFilePath = "/resources/img/" + fileName + "?timestamp=" + System.currentTimeMillis();  // 이미지 URL에 쿼리 매개변수 추가
					
					System.out.println("파일용량(byte) ::" + size);
					System.out.println("원본 파일명 ::" + fileName);
					System.out.println("파일이 저장될 최종 경로 ::" + filePath);
					System.out.println("파일 객체 ::" + file);
					
					pdto.setProd_image(dbFilePath);
					
					// 상품 정보 세팅
					if(prod_kind.equals("bottom")) {
						pdto.setProd_kind("bottom");
						pdto.setCate_no("10");
					} else if(prod_kind.equals("coat")) {
						pdto.setProd_kind("coat");
						pdto.setCate_no("20");
					} else if(prod_kind.equals("zipup")) {
						pdto.setProd_kind("zipup");
						pdto.setCate_no("30");
					} else if(prod_kind.equals("hood")) {
						pdto.setProd_kind("hood");
						pdto.setCate_no("40");
					} else if(prod_kind.equals("mantoman")) {
						pdto.setProd_kind("mantoman");
						pdto.setCate_no("50");
					} else if(prod_kind.equals("outer")) {
						pdto.setProd_kind("outer");
						pdto.setCate_no("60");
					}
					
					productService.insertProduct(pdto);	
					System.out.println("파일 업로드 성공");
					return "redirect:/admin/productList.do";
				} catch(Exception e) {
					e.printStackTrace(); // 파일 업로드 실패
					System.out.println("파일 업로드 실패");
					model.addAttribute("upload", "fail");
					return "admin/fileUpload";
				}
			} else {
				System.out.println("관리자 로그인 후 이용 가능");
				model.addAttribute("loginChk", "fail");
				return "admin/fileUpload";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/fileUpload";
		}
		
		
	}

	// 상품 상세보기
	@RequestMapping("productInfo.do")
	public String productInfo(Model model, @RequestParam int prod_no, HttpSession session) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/productInfo";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 로그인 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				ProductDTO pdto = productService.productDetail(prod_no);
				
				model.addAttribute("pdto", pdto);
				return "admin/productInfo";
			} else {
				System.out.println("관리자 로그인 후 이용 가능");
				model.addAttribute("loginChk", "fail");
				return "admin/productInfo";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/productInfo";
		}
	}
	
	// 상품 수정
	@RequestMapping("updateProduct.do")
	public String updateProduct(Model model, HttpSession session, ProductDTO pdto,
			@RequestParam String prod_name, @RequestParam String prod_kind, @RequestParam int price, 
			@RequestParam String prod_content ,@RequestParam String cate_no) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				pdto.setProd_name(prod_name);
				pdto.setProd_kind(prod_kind);
				pdto.setPrice(price);
				pdto.setProd_content(prod_content);
				pdto.setCate_no(cate_no);
				productService.updateProduct(pdto);
				return "redirect:/admin/productList.do";
			} else {
				System.out.println("관리자 로그인 후 이용 가능");
				model.addAttribute("loginChk", "fail");
				return "admin/productInfo";
			}			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/productInfo";
		}
	}
	
	// 상품 삭제
	@RequestMapping("deleteProduct.do")
	public String deleteProduct(Model model, HttpSession session, ProductDTO pdto) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				productService.deleteProduct(pdto);
				return "redirect:/admin/productList.do";
			} else {
				System.out.println("관리자 로그인 후 이용 가능");
				model.addAttribute("loginChk", "fail");
				return "admin/productInfo";
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/productInfo";
		}
		
	}
	/* -------------------------------------------------------- 상품 -------------------------------------------------------- */
	
	/* -------------------------------------------------------- 주문 -------------------------------------------------------- */
	@RequestMapping("orderList.do")
	public String orderList(Model model, HttpSession session) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/orderList";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				List<OrderDTO> allOrderList = adminService.allOrderList();
				int oTotalCnt = orderService.orderTotalCnt();
				
				model.addAttribute("allOrderList", allOrderList);
				model.addAttribute("oTotalCnt", oTotalCnt);
				return "admin/orderList";
			} else {
				System.out.println("관리자 로그인 후 이용 가능");
				model.addAttribute("loginChk", "fail");
				return "admin/orderList";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/orderList";
		}
	}
	
	// 사용자 주문 목록 조회
	@RequestMapping("orderInfo.do")
	public String orderInfo(Model model, HttpSession session, @RequestParam String mem_id, @RequestParam int order_no) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/orderInfo";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				
				MemberDTO memberDTO = memberService.myPage(mem_id);		// 회원 정보	
//				List<OrderDTO> orderList = orderService.orderList(mem_id);	// 주문 목록
				OrderDTO orderList = adminService.orderInfo(order_no);
				Integer totalPrice = orderService.totalPrice(mem_id);	// 합계(가격 * 수량)
				Date firstOrderDate = orderService.getFirstOrderDate(mem_id);	// 주문날짜 중 첫번쨰 날짜만 조회
				
				// 분할 된 주소 합치기
				StringBuilder addressBuilder = new StringBuilder();
				addressBuilder.append(memberDTO.getPost()).append(" ");
				addressBuilder.append(memberDTO.getAddr1()).append(" ");
				addressBuilder.append(memberDTO.getAddr2());
				String address = addressBuilder.toString();
				
				model.addAttribute("memberDTO", memberDTO);
				model.addAttribute("orderList", orderList);
				model.addAttribute("totalPrice", totalPrice);
				model.addAttribute("firstOrderDate", firstOrderDate);
				model.addAttribute("address", address);
				
				// 전화 번호 마스킹
				if (memberDTO != null) {
				    String phoneNumber = memberDTO.getPhone();
				    if (phoneNumber != null && !phoneNumber.isEmpty()) {
				        StringBuilder maskedPhoneNumber = new StringBuilder();
				        if (phoneNumber.length() == 11) {
				            // 전화번호가 11자리인 경우 (010-1234-5678)
				            maskedPhoneNumber.append(phoneNumber.substring(0, 3)); // 앞부분 (010)
				            maskedPhoneNumber.append("-****-"); // 중간 4자리 마스킹 처리
				            maskedPhoneNumber.append(phoneNumber.substring(7)); // 뒷부분 (5678)
				        } else if (phoneNumber.length() == 10) {
				            // 전화번호가 10자리인 경우 (010-123-4567)
				            maskedPhoneNumber.append(phoneNumber.substring(0, 3)); // 앞부분 (010)
				            maskedPhoneNumber.append("-***-"); // 중간 3자리 마스킹 처리
				            maskedPhoneNumber.append(phoneNumber.substring(6)); // 뒷부분 (4567)
				        }
				        model.addAttribute("phoneNumber", maskedPhoneNumber.toString());
				    }
				} 
				
				// 이름 마스킹
				String mem_name = memberDTO.getMem_name();
				
				if (mem_name.length() > 2) {
				    // 이름이 세 글자 이상일 때는 첫 번째 글자와 마지막 글자를 제외하고 마스킹 처리
				    StringBuilder maskedName = new StringBuilder();
				    maskedName.append(mem_name.charAt(0)); // 첫 번째 글자는 그대로 유지
				    for (int i = 1; i < mem_name.length() - 1; i++) {
				        maskedName.append("*");
				    }
				    maskedName.append(mem_name.charAt(mem_name.length() - 1)); // 마지막 글자는 그대로 유지
				    System.out.println(maskedName);
				    model.addAttribute("maskedName", maskedName);
				} else if (mem_name.length() == 2) {
				    // 이름이 두 글자일 때는 두 번째 글자만 마스킹 처리
				    StringBuilder maskedName = new StringBuilder();
				    maskedName.append(mem_name.charAt(0)); // 첫 번째 글자는 그대로 유지
				    maskedName.append("*"); // 두 번째 글자를 마스킹 처리
				    System.out.println(maskedName);
				    model.addAttribute("maskedName", maskedName);
				} else {
				    System.out.println("이름은 한 글자가 될 수 없습니다.");
				}
				
				return "admin/orderInfo";
			} else {
				// 로그인을 하지 않았을 경우
				model.addAttribute("loginChk", "fail");
				return "admin/orderInfo";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/orderInfo";
		}	 		
	}
	/* -------------------------------------------------------- 주문 -------------------------------------------------------- */
	/* -------------------------------------------------------- 공지 -------------------------------------------------------- */
	// 골지 글 조회
	@RequestMapping("allNoticeList.do")
	public String noticeList(Model model, HttpSession session) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/noticeList";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				List<NoticeDTO> noticeList = adminService.allNoticeList();
				int noticeTotalCnt = noticeService.noticeTotalCnt();
				
				model.addAttribute("noticeList", noticeList);
				model.addAttribute("noticeTotalCnt", noticeTotalCnt);
				return "admin/noticeList";
			} else {
				// 로그인을 하지 않았을 경우
				model.addAttribute("loginChk", "fail");
				return "admin/noticeList";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/noticeList";
		}
	}
	
	// 공지 글 등록
	@RequestMapping("insertNotice.do")
	public String insertNotice(Model model, HttpSession session, NoticeDTO dto, @RequestParam String title,
			@RequestParam String content) throws Exception {
		try {
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				if(title == null || title.isEmpty() || content == null || content.isEmpty()) {
					model.addAttribute("nullMsg", "제목과 내용을 입력 해주세요.");
					return "admin/insertNoticeForm";
				} else {
					dto.setTitle(title);
					dto.setContent(content);
					noticeService.insertNotice(dto);
					return "redirect:/admin/allNoticeList.do";
				}
			} else {
				// 로그인을 하지 않았을 경우
				model.addAttribute("loginChk", "fail");
				return "admin/insertNoticeForm";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/insertNoticeForm";
		}
	}
	
	// 공지 사항 등록 폼 
	@RequestMapping("insertNoticeForm.do")
	public String insertNoticeForm(Model model, HttpSession session, Map<String, Object> map) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/insertNoticeForm";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {			
				int nextNum = noticeService.nextNum();
				map.put("nextNum", String.valueOf(nextNum));
				
				model.addAttribute("nextNum", nextNum);
				return "admin/insertNoticeForm";
			} else {
				// 로그인을 하지 않았을 경우
				model.addAttribute("loginChk", "fail");
				return "admin/insertNoticeForm";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/insertNoticeForm";
		}
	}
	
	// 공지사항 상세보기
	@RequestMapping("noticeRead.do")
	public String noticeRead(Model model, HttpSession session, @RequestParam int num) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/noticeRead";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {			
				NoticeDTO noticeDto = noticeService.noticeRead(num);				
				noticeService.increaseViewCount(num);	// 조회수 증가
				
				model.addAttribute("noticeDto", noticeDto);
				return "admin/noticeRead";
			} else {
				// 로그인을 하지 않았을 경우
				model.addAttribute("loginChk", "fail");
				return "admin/noticeRead";
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/noticeRead";
		}
	}
	
	// 공지 글 수정
	@RequestMapping("updateNotice.do")
	public String updateNotice(Model model, HttpSession session, NoticeDTO dto, @RequestParam String title, 
			@RequestParam String content, @RequestParam int num) throws Exception {
		try {
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {			
				dto.setTitle(title);
				dto.setContent(content);
				noticeService.updateNotice(dto);
				
				return "redirect:/admin/allNoticeList.do";
			} else {
				// 로그인을 하지 않았을 경우
				model.addAttribute("loginChk", "fail");
				return "admin/noticeRead";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/noticeRead";
		}
	}
	
	// 공지 글 삭제
	@RequestMapping("deleteNotice.do")
	public String deleteNotice(Model model, HttpSession session, NoticeDTO dto) throws Exception {
		try {
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {			
				
				noticeService.deleteNotice(dto);
				return "redirect:/admin/allNoticeList.do";
			} else {
				// 로그인을 하지 않았을 경우
				model.addAttribute("loginChk", "fail");
				return "admin/noticeRead";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/noticeRead";
		}
	}
	
	/* -------------------------------------------------------- 공지 -------------------------------------------------------- */
	/* -------------------------------------------------------- 리뷰 -------------------------------------------------------- */
	// 모든 리뷰 조회
	@RequestMapping("selectReviewList.do")
	public String selectReviewList(Model model, HttpSession session) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/noticeRead";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {		
				
				List<ReviewDTO> selectReviewList = adminService.selectReviewList();	// 모든 리뷰 목록
				List<String> maskedNames = new ArrayList<String>();
				int rTotalCnt = reviewService.reviewTotalCnt();	// 리뷰 총 건 수
				
				for(ReviewDTO rDTO : selectReviewList) {
					
					String mem_name = rDTO.getMem_name();
				    StringBuilder maskedName = new StringBuilder();
					
					// 이름 마스킹
					if (mem_name.length() > 2) {
					    // 이름이 세 글자 이상일 때는 첫 번째 글자와 마지막 글자를 제외하고 마스킹 처리
					    maskedName.append(mem_name.charAt(0)); // 첫 번째 글자는 그대로 유지
					    for (int i = 1; i < mem_name.length() - 1; i++) {
					        maskedName.append("*");
					    }
					    maskedName.append(mem_name.charAt(mem_name.length() - 1)); // 마지막 글자는 그대로 유지
					    System.out.println(maskedName);
					} else if (mem_name.length() == 2) {
					    // 이름이 두 글자일 때는 두 번째 글자만 마스킹 처리
					    maskedName.append(mem_name.charAt(0)); // 첫 번째 글자는 그대로 유지
					    maskedName.append("*"); // 두 번째 글자를 마스킹 처리
					    System.out.println(maskedName);
					} else {
					    System.out.println("이름은 한 글자가 될 수 없습니다.");
					}
					maskedNames.add(maskedName.toString());
				}
				
				model.addAttribute("selectReviewList", selectReviewList);
				model.addAttribute("maskedNames", maskedNames);
				model.addAttribute("rTotalCnt", rTotalCnt);
				return "admin/allReviewList";
			} else {
				// 로그인을 하지 않았을 경우
				model.addAttribute("loginChk", "fail");
				return "admin/allReviewList";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("내부 서버 에러 발생");
			model.addAttribute("errorMessage", "error");
			return "admin/allReviewList";
		}
	}
	
	// 관리자 댓글 
	@RequestMapping("insertReply.do")
	public String insertReply(Model model, HttpSession session, @RequestParam int rno, ReviewDTO dto) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				// 회원들의 리뷰 목록
				List<ReviewDTO> reviewList = reviewService.reviewList();
				// 회원들의 리뷰 목록이 비어 있지 않다면 관리자 댓글 달기
				if(reviewList != null && !reviewList.isEmpty()) {
					// 댓글을 달 리뷰 선택
					ReviewDTO selectedReview = reviewService.getReviewByRno(rno);
					
					Date currentTime = new Date();
					ReviewDTO replyDTO = new ReviewDTO();
					replyDTO.setRno(selectedReview.getRno());	// 리뷰 번호
					replyDTO.setReply(dto.getReply());	// 관리자 댓글
					replyDTO.setReply_id((String) loginMap.get("MEM_ID"));	// 관리자 아이디
					replyDTO.setReply_name((String) loginMap.get("MEM_NAME"));	// 관리자 이름
					replyDTO.setReply_regdate(currentTime);
					
					adminService.insertReply(replyDTO);	// 댓글 업데이트					
				}
				return "redirect:/review/reviewList.do";
			} else {
				System.out.println("관리자 로그인 후 이용 가능 합니다.");
				model.addAttribute("adminChk", "fail");
				return "review/reviewList"; 
			}
		} catch(Exception e) {
			logger.error("에러 내용 : ", e);
			model.addAttribute("msg", "관리자 댓글을 작성 중 에러가 발생 했습니다.");
			return "review/reviewList";
		}
	
	}
	/* -------------------------------------------------------- 리뷰 -------------------------------------------------------- */
	/* -------------------------------------------------------- 매출 -------------------------------------------------------- */
	// 월 별 매출액, 판매량
	@RequestMapping("sales.do")
	public String sales(Model model, HttpSession session, Map<String, Object> map) throws Exception {
		try {
			if(session.getAttribute("loginMap") == null) {
				System.out.println("세션 만료");
				model.addAttribute("session", "exp");
				return "admin/insertNoticeForm";
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			
			String code = loginMap.get("CODE").toString();	// 회원 코드
			String useyn = loginMap.get("USEYN").toString();	// 사용 여부
			
			// 세션이 있고 코드가 관리자 코드("1")이고 사용여부가 "Y" 일 경우
			if(loginMap != null && "1".equals(code) && "Y".equals(useyn)) {
				// 월 별 매출액
				List<Map<String, Object>> monthAmount = adminService.monthAmount(map);
				// 월 별 판매량
				List<Map<String, Object>> monthSales = adminService.monthSales(map);
				
				Gson gson = new Gson();
				String monthAmountJson = gson.toJson(monthAmount);
				String monthSalesJson = gson.toJson(monthSales);
//				
				model.addAttribute("monthAmountJson", monthAmountJson);
				model.addAttribute("monthSalesJson", monthSalesJson);

				// Gson 라이브러리를 사용하여 JSON 문자열을 JSON 객체로 변환
				JsonArray monthAmountArray = JsonParser.parseString(monthAmountJson).getAsJsonArray();
				JsonArray monthSalesArray = JsonParser.parseString(monthSalesJson).getAsJsonArray();
				
				List<JsonObject> monthAmountList = new ArrayList<>();
				List<JsonObject> monthSalesList = new ArrayList<>();

				for (JsonElement element : monthAmountArray) {
				    monthAmountList.add(element.getAsJsonObject());
				}

				for (JsonElement element : monthSalesArray) {
				    monthSalesList.add(element.getAsJsonObject());
				}

				model.addAttribute("monthAmountArray", monthAmountArray);
				model.addAttribute("monthSalesArray", monthSalesArray);

				return "admin/sales";
			} else {
				// 로그인을 하지 않았을 경우
				System.out.println("관리자 로그인 후 이용 가능");
				model.addAttribute("loginChk", "fail");
				return "admin/sales";
			}
		} catch(Exception e) {
			logger.error("에러 내용 : ", e);
			model.addAttribute("msg", "error");
			return "admin/sales";
		}
	}
	/* -------------------------------------------------------- 매출 -------------------------------------------------------- */
	/* -------------------------------------------------------- 조회수 -------------------------------------------------------- */
	// 조회수
	@RequestMapping("views.do")
	public String views() throws Exception {
		return "admin/views";
	}
	/* -------------------------------------------------------- 조회수 -------------------------------------------------------- */
	
}
