package com.shopping.controller;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.dto.MemberDTO;
import com.shopping.service.MemberService;
//import com.shopping.util.PasswordUtils;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
//	private PasswordEncoder passwordEncoder;
	BCryptPasswordEncoder pwdEncoder;
	
//	@Autowired
//	PasswordUtils generateRandomPassword;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	// 회원가입 폼 이동
	@RequestMapping("joinForm.do")
	public String joinForm() throws Exception {
		return "member/joinForm";
	}
	
	@RequestMapping(value = "/secuTest", method = RequestMethod.GET)
    public void secuTest() {
        
        String rawPassword = "Drw15963!!";  //인코딩 전 메서드
        String encdoePassword1;      // 인코딩된 메서드
        String encdoePassword2;      // 똑같은 비밀번호 데이터를 encdoe()메서드를 사용했을 때 동일한 인코딩된 값이 나오는지 확인하기 위해 추가
        
        encdoePassword1 = pwdEncoder.encode(rawPassword);
        encdoePassword2 = pwdEncoder.encode(rawPassword);
        
        // 인코딩된 패스워드 출력
        System.out.println("encdoePassword1 : " +encdoePassword1);
        System.out.println(" encdoePassword2 : " + encdoePassword2);
        
        String truePassowrd = "Drw15963!!";
        String falsePassword = "abcd1234";
        
        System.out.println("truePassword verify : " + pwdEncoder.matches(truePassowrd, encdoePassword1));
        System.out.println("falsePassword verify : " + pwdEncoder.matches(falsePassword, encdoePassword1));    
    
	}
	
	// 회원가입
	@RequestMapping(value = "join.do")
	public String join(MemberDTO dto, @RequestParam String mem_id, Model model) throws Exception {
		String mem_pw = dto.getMem_pw();
		// 패스워드 암호화
		String pwd = pwdEncoder.encode(mem_pw);
		dto.setMem_pw(pwd);
		memberService.join(dto);
		return "redirect:/";
	}
	
	// 로그인 폼 이동
	@RequestMapping("loginForm.do")
	public String loginForm() throws Exception {
		return "member/loginForm";
	}
	
//	// 로그인
//	@RequestMapping("login.do")
//	public String login(Model model, @RequestParam String mem_id, @RequestParam String mem_pw, HttpSession session)
//			throws Exception {
//		Map<String, Object> loginMap = memberService.login(mem_id);
//		String url = "";
//
//		if (loginMap == null) {
//			model.addAttribute("loginCheck", "idFail"); // 아이디가 없는 경우
//			url = "member/loginForm";
//		} else {
//			String encodePw = loginMap.get("MEM_PW").toString();
//			// pwdEncoder를 이용해 비밀번호 확인
//			if (!pwdEncoder.matches(mem_pw, encodePw)) {
//				model.addAttribute("loginCheck", "pwFail"); // 비밀번호가 틀렸을 경우
//				url = "member/loginForm";
//			} else {
//				String code = loginMap.get("CODE").toString();
//				String useyn = loginMap.get("USEYN").toString();
//
//				if ("1".equals(code) && "Y".equals(useyn)) {
//					System.out.println("================== 관리자로 로그인 ==================");
//					session.setAttribute("loginMap", loginMap);
//					url = "redirect:/admin/admin.do"; // 관리자일 경우 관리자 페이지로 리다이렉트
//				} else if ("2".equals(code) && "Y".equals(useyn)) {
//					System.out.println("================== 일반회원 으로 로그인 ==================");
//					session.setAttribute("loginMap", loginMap);
//					url = "redirect:/"; // 일반회원일 경우 메인 홈으로 리다이렉트
//				} else if ("3".equals(code) && "N".equals(useyn)) {
//					System.out.println("================== 정지된 회원 ==================");
//					model.addAttribute("loginCheck", "suspended");
//					url = "member/loginForm";
//				}
//			}
//		}
//		return url;
//	}
	
	// 로그인
	@RequestMapping("login.do")
	public String login(Model model, @RequestParam String mem_id, @RequestParam String mem_pw, HttpSession session)
	        throws Exception {
	    Map<String, Object> loginMap = memberService.login(mem_id);
	    String url = "";

	    if (loginMap == null) {
	        model.addAttribute("loginCheck", "idFail"); // 아이디가 없는 경우
	        url = "member/loginForm";
	    } else {
	        String encodePw = loginMap.get("MEM_PW").toString();
	        // 아이디가 "test" 또는 "admin"이고 비밀번호가 "1111"인 경우에는 비밀번호를 인코딩하지 않음
	        if (("test".equals(mem_id) || "admin".equals(mem_id)) && "1111".equals(mem_pw)) {
	        } else if (!pwdEncoder.matches(mem_pw, encodePw)) {
	            model.addAttribute("loginCheck", "pwFail"); // 비밀번호가 틀렸을 경우
	            url = "member/loginForm";
	            return url;
	        }

	        String code = loginMap.get("CODE").toString();
	        String useyn = loginMap.get("USEYN").toString();

	        if ("1".equals(code) && "Y".equals(useyn)) {
	            System.out.println("================== 관리자로 로그인 ==================");
	            session.setAttribute("loginMap", loginMap);
	            url = "redirect:/admin/admin.do"; // 관리자일 경우 관리자 페이지로 리다이렉트
	        } else if ("2".equals(code) && "Y".equals(useyn)) {
	            System.out.println("================== 일반회원 으로 로그인 ==================");
	            session.setAttribute("loginMap", loginMap);
	            url = "redirect:/"; // 일반회원일 경우 메인 홈으로 리다이렉트
	        } else if ("3".equals(code) && "N".equals(useyn)) {
	            System.out.println("================== 정지된 회원 ==================");
	            model.addAttribute("loginCheck", "suspended");
	            url = "member/loginForm";
	        }
	    }
	    return url;
	}

	
	// 로그아웃
	@RequestMapping("logOut.do")
	 public String logOut(HttpSession session) throws Exception {
		session.invalidate();
		return "redirect:/";
	}
	
	// 마이페이지
	@RequestMapping(value= "myPage.do", method = RequestMethod.GET)
	 public String myPage(Model model, HttpSession session) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
		
		// 로그인 체크
		if(loginMap != null) {
			String mem_id = (String) loginMap.get("MEM_ID");
			MemberDTO member = memberService.myPage(mem_id);
			
			String phoneNumber = member.getPhone();		
			// 전화번호 분리
			String mobile1 = phoneNumber.substring(0, 3);
			String mobile2 = phoneNumber.substring(3, 7);
			String mobile3 = phoneNumber.substring(7);
			
			model.addAttribute("mobile1", mobile1);
			model.addAttribute("mobile2", mobile2);
			model.addAttribute("mobile3", mobile3);
			model.addAttribute("member", member);
			
			return "member/myPage";
		} else {
			// 로그인이 되어 있지 않다면 리다이렉트
			model.addAttribute("loginChk", "fail");
			
			return "member/myPage";
		}				
	}
	
	// 회원 탈퇴
	@RequestMapping(value= "memberDelete.do")
	public String memberDelete(Model model, @RequestParam("mem_id") String mem_id, @RequestParam("mem_pw") String mem_pw, HttpSession session) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> loginMap = (Map<String, Object>) session.getAttribute("loginMap");
			if(loginMap == null) {
				// 세션에 로그인 정보가 없는 경우 로그인 페이지로 리다이렉트
				return "redirect:/member/loginForm.do";
			}
			
			String actualPw = (String) loginMap.get("MEM_PW");
			
			// 사용자에게 입력 받은 비밀번호와 실제 비밀번호가 일치 하는지 확인
			if(mem_pw.equals(actualPw)) {
				// 비밀번호 일치 시 회원 삭제 진행			
				memberService.memberDelete(mem_id);
				session.invalidate();
				return "redirect:/";
			} else {
				// 비밀번호가 일치 하지 않을 경우 
				model.addAttribute("errorMessage", "비밀번호가 일치 하지 않습니다.");
				return "member/myPage";
			}
		} catch(Exception e) {
			// 예외 발생 시 로깅
	        logger.error("회원 탈퇴 중 오류 발생: " + e.getMessage(), e);
	        // 예외 페이지로 리다이렉트 또는 오류 메시지를 화면에 표시
	        model.addAttribute("errorMsg", "회원 탈퇴 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
	        return "member/myPage";
		}	
	}
	
	// 회원 정보 수정
	@RequestMapping(value= "memberUpdate.do")
	public String memberUpdate(Model model, MemberDTO dto, @RequestParam String mobile1, @RequestParam String mobile2, 
			@RequestParam String mobile3, @RequestParam String mem_pw) throws Exception {
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
			memberService.memberUpdate(dto);
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "error");
			return "member/myPage";
		}		
	}
	
	// 아이디 중복 체크
	@RequestMapping(value = "idCheck.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> idCheck(@RequestParam String mem_id) throws Exception {
	
			int idCheck = memberService.idCheck(mem_id);
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(idCheck == 1) {
				System.out.println("이미 사용중인 아이디 입니다.");
				map.put("result", "fail");
			} else {	// idCheck == 0
				System.out.println("사용 가능한 아이디 입니다.");
				map.put("result", "success");
			}			
			return map;
			
	}
	
	
}
