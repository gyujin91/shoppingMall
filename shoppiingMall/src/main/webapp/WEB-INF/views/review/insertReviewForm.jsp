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
    <title>리뷰 작성</title>
    <link rel="stylesheet" href="${path }/resources/css/style.css">
    <link rel="stylesheet" href="${path }/resources/css/normalize.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
   
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    
    <style>
        .insertReview
        {
            width: 900px;
            margin: auto;
            background-color: #ececec;
            padding: 20px;
        }
        .insertReview h2
        {
            text-align: center;
            font-weight: bold;
            font-size: 1.6em;
        }
        .insertReview .tableBox
        {
            background-color: #ffffff;
            padding: 20px;
            border: 1px solid #ececec;
            border-radius: 10px;
            box-shadow: 2px 2px 2px 2px rgba(33, 33, 33, 0.15);
        }
        .insertReview .tableBox table th, td
        {
            padding: 20px;
            font-size: 0.8em;
        }
        .insertReview .tableBox table input
        {
            border: 1px solid rgba(0, 0, 0, 0.29);
            padding-left: 5px;
        }
        .insertReview .tableBox table #prod_image
        {
            border: none;
        }
        .insertReview .tableBox table textarea
        {
            border: 1px solid rgba(0, 0, 0, 0.29);
            padding-left: 5px;
        }
        .insertReview .tableBox table #prod_name, #review_title
        {
            width: 350px;
        }
        .insertReview .tableBox .btn_group
        {
            text-align: center;
            margin: 15px;
        }
        .insertReview .tableBox button
        {
            width: 80px;
            margin-right: 10px;
            border-radius: 8px;
            border: 1px solid rgba(0, 0, 0, 0.09);
            background: #c8c8c8;
        }     
    </style>
    
</head>
<body>
    <%@ include file="../include/header.jsp" %>
    <div class="insertReview">
        <h2>리뷰 작성</h2>
        <div class="tableBox">
            <form action="">
                <table>
                    <tr>
                        <th>상품명</th>
                        <td><input type="text" id="prod_name" name="prod_name"></td>
                    </tr>
                    <tr>
                        <th>제목</th>
                        <td><input type="text" id="review_title" name="review_title"></td>
                    </tr>
                    <tr>
                        <th>작성자</th>
                        <td><input type="text" id="mem_id" name="mem_id"></td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td><textarea id="review_content" name="review_content" rows="4" cols="100"></textarea></td>
                    </tr>
                    <tr>
                        <th>이미지</th>
                        <td><input type="file" id="prod_image" name="prod_image" accept="image/*" required></td>
                    </tr>
                </table>
            </form>
            <div class="btn_group">
                <button type="button" id="insert">등록</button>
                <button type="button" id="cancel" onclick="location.href='${path}/review/reviewList.do'">취소</button>
            </div>
        </div>
    </div>
    <%@ include file="../include/footer.jsp" %>
</body>
</html>