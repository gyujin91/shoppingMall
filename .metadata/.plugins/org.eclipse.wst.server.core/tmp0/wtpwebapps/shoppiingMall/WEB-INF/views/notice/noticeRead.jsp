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
    <title>공지사항 상세보기</title>
    <link rel="stylesheet" href="${path }/resources/css/normalize.css">
    <link rel="stylesheet" href="${path }/resources/css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
    
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    
    <style>
        .noticeInfo
        {
            width: 900px;
            margin: auto;    
            background-color: #ececec;  
            border-radius: 8px;
            height: 550px;
        }
        .noticeInfo h2
        {
            text-align: center;
            font-weight: bold;
            font-size: 1.6em;
            padding-top: 20px;
        }
        .noticeInfo .notice 
        {   
            margin: 15px;
            background-color: #ffffff;
            border-radius: 8px;
        }
        .noticeInfo .notice .tableBox
        {            
            padding: 30px;
        }
        .noticeInfo .notice .tableBox table
        {
            width: 100%;
            border-collapse: collapse;
        }
        .noticeInfo .notice .tableBox table input
        {
        	border: none;
        }
        .noticeInfo .notice .tableBox table tr:nth-child(1)
        {
            border-top: 2px solid black;
        }
        .noticeInfo .notice .tableBox table tr:nth-child(1) th, tr:nth-child(2) th
        {
            width: 15%;
            padding: 10px;
            border: 1px solid rgba(0,0,0,.09);
            font-size: 0.8em;
            background-color: rgba(0,0,0,.09);
        }
        .noticeInfo .notice .tableBox table tr:nth-child(1) td, tr:nth-child(2) td
        {
            width: 35%;
            padding: 10px;
            border: 1px solid rgba(0,0,0,.09);    
            font-size: 0.8em;       
        }
        .noticeInfo .notice .tableBox table textarea
        {
            width: 100%;
            border: 1px solid rgba(0,0,0,.09); 
            margin-top: 10px;
            padding: 10px;
            font-size: 0.8em;
        }
        .noticeInfo .notice .tableBox table textarea:focus {
            outline: none;
            border-color: rgba(0, 0, 0, 0.09);
        }
        .noticeInfo .notice .tableBox .btn_group
        {
            text-align: center;
            margin-top: 15px;
        }
        .noticeInfo .notice .tableBox .btn_group button
        {
            margin: 3px;
            padding: 3px;
            border-color: rgba(0, 0, 0, 0.09);
            background-color: #ececec;
            border-radius: 8px;
            font-size: 0.8em;
        }
    </style>
</head>
<body>
    <%@ include file="../include/header.jsp" %>
    <div class="noticeInfo">
        <h2>공지사항</h2>
        <div class="notice">
            <div class="tableBox">
                <form action="">
                	<input type="hidden" name="num" id="num" value="${noticeDto.num }">
                	<table>
                    <tr>
                        <th>제목</th>
                        <td>${noticeDto.title }</td>
                        <th>등록일</th>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${noticeDto.regdate }"/></td>
                    </tr>
                    <tr>
                        <th>작성자</th>
                        <td>${noticeDto.author }</td>
                        <th>조회수</th>
                        <td>${noticeDto.view_cnt }</td>
                    </tr>
                    <tr>
                        <td colspan="4"><textarea id="content" name="content" cols="30" rows="10" required="required">${noticeDto.content }</textarea></td>
                    </tr>
                </table>
                <div class="btn_group">
                    <button type="button" onclick="location.href='${path}/notice/noticeList.do'">목록</button>
                </div>
                </form>
            </div>
            
        </div>
    </div>
    <%@ include file="../include/footer.jsp" %>
    
</body>
</html>
