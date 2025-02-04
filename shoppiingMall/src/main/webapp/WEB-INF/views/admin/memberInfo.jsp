<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원정보수정</title>
    <link rel="stylesheet" href="${path }/resources/css/normalize.css">
    <link rel="stylesheet" href="${path }/resources/css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
    
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <style>
        .mypage
        {
            margin: 100px;
        }
        .mypage h2
        {
            font-size: 1.3em;
            margin: 30px 0 30px 0;
            font-weight: bold;
        }
        .mypage .grade 
        {
            display: flex;
            align-items: center;
            background: #ececec;
            border: 1px solid #ececec;
            border-radius: 25px;
            height: 120px;
        }
        .mypage .grade div:first-child 
        {
            margin: 30px;
            border-right: 1px solid #404040;
            padding-right: 20px;
        }

        .mypage .grade div:last-child 
        {
            margin-top: 20px;
            font-size: 12px;
        }
        .mypage .grade div:last-child p strong
        {
            font-weight: bold;
        }
        .mypage .userInfo 
        {
            margin: 50px 0 50px 0;
        }
        .mypage .userInfo .title
        {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .mypage .userInfo .title h3
        {
            font-size: 1.2em;
            font-weight: bold;
        }
        .mypage .userInfo .title p
        {
            font-size: 12px;
            font-weight: bold;
            position: relative;
        }
        .mypage .userInfo .title p::before
        {
            content: "*";
            position: absolute;
            color: red;
            top: 70%;
            right: 105%;
            transform: translateY(-50%); 
            
        }
        .mypage .userInfo .table1
        {
            width: 100%;
            border-collapse: collapse;
        }
        .mypage .userInfo .table1 th
        {
            border-top: 1px solid #dfdfdf;      
            padding: 8px 0 8px 0;
            font-size: 13px;
        }
        .mypage .userInfo .table1 tr:nth-child(1) th
        {
            position: relative;
        }
        .mypage .userInfo .table1 tr:nth-child(1) th::after
        {
            content: "*"; 
            position: absolute;          
            color: red;   
            top: 35%;
            left: 20%;         
        }
        .mypage .userInfo .table1 tr:nth-child(2) th
        {
            position: relative;
        }
        .mypage .userInfo .table1 tr:nth-child(2) th::after
        {
            content: "*"; 
            position: absolute;          
            color: red;   
            top: 35%;
            left: 20%;         
        }
        .mypage .userInfo .table1 tr:nth-child(3) th
        {
            position: relative;
        }
        .mypage .userInfo .table1 tr:nth-child(3) th::after
        {
            content: "*"; 
            position: absolute;          
            color: red;   
            top: 35%;
            left: 25%;         
        }
        .mypage .userInfo .table1 tr:nth-child(4) th
        {
            position: relative;
        }
        .mypage .userInfo .table1 tr:nth-child(4) th::after
        {
            content: "*"; 
            position: absolute;          
            color: red;   
            top: 35%;
            left: 38%;         
        }
        .mypage .userInfo .table1 tr:nth-child(5) th
        {
            position: relative;
        }
        .mypage .userInfo .table1 tr:nth-child(5) th::after
        {
            content: "*"; 
            position: absolute;          
            color: red;   
            top: 35%;
            left: 15%;         
        }
        .mypage .userInfo .table1 tr:nth-child(6) th
        {
            position: relative;
        }
        .mypage .userInfo .table1 tr:nth-child(6) th::after
        {
            content: "*"; 
            position: absolute;          
            color: red;   
            top: 45%;
            left: 16%;         
        }
        .mypage .userInfo .table1 tr:nth-child(7) th
        {
            position: relative;
        }
        .mypage .userInfo .table1 tr:nth-child(7) th::after
        {
            content: "*"; 
            position: absolute;          
            color: red;   
            top: 35%;
            left: 25%;         
        }
        .mypage .userInfo .table1 td
        {
            border-top: 1px solid #dfdfdf;
            padding: 8px 0 8px 0;
        }
        .mypage .userInfo .table1 tr:nth-child(3) td::after
        {
            content: "(비밀번호는 최소 8자 이상이어야 하며, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.)";
            font-size: 12px;
            margin-left: 10px;
        }
        .mypage .userInfo .table1 td input
        {
            border: 1px solid #d5d5d5;     
            border-radius: 8px;
            padding: 5px;    
            font-size:13px; 	
        }
        .mypage .userInfo .table1 tr:nth-child(6) td
        {
            display: flex;
            flex-direction: column;
        }
        .mypage .userInfo .table1 tr:nth-child(6) input:nth-child(1)
        {
            width: 150px;
        }
        .mypage .userInfo .table1 tr:nth-child(6) input
        {
            width: 350px;
            margin: 3px 0;           
        }
        .mypage .userInfo .table1 tr:nth-child(6) a
        {
            border: 1px solid black;  
            width: 80px;
            height: 30px;  
            text-align: center;
            padding: 5px;  
            background-color: #dfdfdf; 
            font-size: 13px;
            border-radius: 5px;
            margin: 3px 0 3px 0;
        }
        .mypage .userInfo .table1 tr:nth-child(6) a:hover
        {
            color: black;
        }
        .mypage .userInfo .table1 tr:nth-child(6) input::placeholder
        {
            padding: 8px;
            font-size: 13px;
        }
        .mypage .userInfo .table1 tr:nth-child(7) th,tr:nth-child(7) td
        {
            border-bottom: 1px solid #dfdfdf;
        }
        .mypage .userInfo .table1 tr:nth-child(7) select
        {
            width: 70px;
            height: 27.2px;
            border-radius: 8px;
            border: 1px solid #d5d5d5; 
        }
        .mypage .userInfo .table1 tr:nth-child(7) input
        {
            width: 100px;
        }
        .mypage .userInfo .table1 td input[readonly]
        {
        	background-color: #efefef;
        }
        .mypage .userInfo .formBtn
        {
            text-align: center;
            margin: 50px 0 50px 0;
        }
        .mypage .userInfo .formBtn .update
        {
            height: 50px;
            width: 120px;
            border: none;
            border-radius: 15px;
            background-color: #2a2b2c;
            color: #ffffff;
        }
        .mypage .userInfo .formBtn .cancel
        {
            height: 50px;
            width: 120px;
            border: none;
            border-radius: 15px;
            margin-left: 10px;
            background-color: #84868b;
            color: #ffffff;
        }
        .mypage .userInfo .formBtn .delete
        {
            float: left;
            height: 35px;
            width: 100px;
            border: none;
            border-radius: 8px;   
            background-color: #dfdfdf;   
            color: black;
        }
    </style>
</head>
<body>
    <%@ include file="../include/header.jsp" %>
    <div class="mypage">
        <h2>회원 정보 수정</h2>   
        <div class="grade">
            <div><img src="https://img.echosting.cafe24.com/skin/base_ko_KR/member/img_member_default.gif" alt="이미지"></div>
            <div><p>안녕하세요 <strong>${loginMap.MEM_NAME }님</strong></p></div>
        </div>
        <div class="userInfo">
            <div class="title">
                <h3>기본정보</h3>
                <p>필수 입력 사항</p>   
            </div>  
            <form id="updateFrm" onsubmit="return validateAndSubmitForm()">
                <table class="table1">
                    <tr>
                        <th>아이디</th>
                        <td><input type="text" name="mem_id" id="mem_id" value="${memberDto.mem_id }" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td><input type="email" name="email" id="email" value="${memberDto.email }" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <th>비밀번호</th>
                        <td><input type="password" name="mem_pw" id="mem_pw" value="${memberDto.mem_pw }" ></td>                   
                    </tr>
                    <tr>
                        <th>비밀번호 확인</th>
                        <td><input type="password" name="mem_pw2" id="mem_pw2" value="${memberDto.mem_pw }" ></td>
                    </tr>
                    <tr>
                        <th>이름</th>
                        <td><input type="text" name="mem_name" id="mem_name" value="${memberDto.mem_name }" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <th>주소</th>
                        <td>
                            <input type="text" name="post" id="post" value="${memberDto.post }" placeholder="우편번호">
                            <a href="">주소검색</a>
                            <input type="text" name="addr1" value="${memberDto.addr1 }" placeholder="기본 주소">
                            <input type="text" name="addr2" value="${memberDto.addr2 }" placeholder="나머지 주소">
                        </td>
                        
                    </tr>
                    <tr>
                        <th>휴대전화</th>
                        <td>
                            <select name="mobile1" id="mobile1">                                                        
                                <option value="${mobile1 }">${mobile1 }</option>                             
                            </select>
                            
                            <label for="mobile2">-</label>
                            <input type="text" id="mobile2" name="mobile2" value="${mobile2 }" style="text-align: center;" required>
                            <label for="mobile3">-</label>
                            <input type="text" id="mobile3" name="mobile3" value="${mobile3 }" style="text-align: center;" required>
                        </td>
                    </tr>
                </table>
                <div class="formBtn">
                	<button class="delete" id="delete" onclick="memberDelete()">회원탈퇴</button>
                    <button class="update" id="update">회원정보수정</button>
                    <button class="cancel" id="cancel" onclick="goHome()">취소</button>                    
                </div>
            </form>
        </div>
    </div>
    

    <%@ include file="../include/footer.jsp" %>
    <script>
    /* 로그인 체크 */
    var loginChk = '${loginChk}';
    
    if (loginChk == 'fail') {
        alert("로그인 후 이용 가능합니다.");
        window.location.href = '${path}/member/loginForm.do'; // 리다이렉트
    }
    
    /* 회원정보수정 유효성검사 */
    $(function() {				
		$("#update").click(function() {
            var mem_pw = $("#mem_pw").val();
            var mem_pw2 = $("#mem_pw2").val();
            var mobile1 = $("#mobile1").val();
            var mobile2 = $("#mobile2").val();
            var mobile3 = $("#mobile3").val();
			var errorMsg = '${errorMsg}';
         
            
            // 비밀번호 유효성 검사 패턴
            var pwPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
            // 비밀번호 최소 길이
            var minPwLength = 8;

            if(mem_pw == "") {
				alert("비밀번호를 입력 하세요.");
				$("#mem_pw").focus();
			} else if(!pwPattern.test(mem_pw)) {
                alert("비밀번호는 최소 8자 이상이어야 하며, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.");
                $("#mem_pw").focus();
            }  else if(mem_pw2 == "") {
				alert("비밀번호 확인을 입력 하세요.");
				$("#mem_pw2").focus();
            } else if(mobile2 == "") {
				alert("전화번호를 입력하세요.");
				$("#mobile2").focus();
            } else if(mobile3 == "") {
            	alert("전화번호를 입력하세요.");
            	$("#mobile3").focus();
            } else if(mem_pw != mem_pw2) {
                alert("비밀번호가 일치 하지 않습니다.");
                $("#mem_pw2").focus();
            } else if(errorMsg == 'error') {
            	alert("회원 정보를 수정 하던 중 에러가 발생 했습니다.");         	
			} else {
				alert("회원 정보를 수정 하였습니다.");
				$("#updateFrm").attr("action", "adminUserUpdate.do").attr("method", "post").submit();
			}
		});
	});
    
    /* 회원탈퇴 */
    var errorMessage = '${errorMessage}';
   	var errorMsg = '${errorMsg}';
   	
    function memberDelete() {
    	if(errorMessage) {
    		alert(errorMessage);
    	} else if(errorMsg) {
    		alert(errorMsg);
    	} else {
    		alert("정상적으로 회원 탈퇴를 진행 했습니다.");
    		$("#updateFrm").attr("action", "adminUserDelete.do").attr("method", "post")	
    	}    	
    }
    
 	// 취소 버튼 클릭 시 메인 화면으로 이동
    function goHome() {
        window.location.href = '${path}/admin/admin'; // 메인 화면으로 이동
    }
 
    
    
    
    </script>
</body>
</html>