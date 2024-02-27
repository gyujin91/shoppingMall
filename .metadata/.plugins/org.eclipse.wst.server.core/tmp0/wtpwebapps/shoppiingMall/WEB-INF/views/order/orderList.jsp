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
    <title>주문 상세 내역</title>
    <link rel="stylesheet" href="${path }/resources/css/style.css">
    <link rel="stylesheet" href="${path }/resources/css/normalize.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
   
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    
    <style>
        .point
        {
            border: 1px solid #ececec;
            border-radius: 10px;
            box-shadow: 2px 2px 2px 2px rgba(33, 33, 33, 0.15);
            height: 200px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .point div:nth-child(1)
        {
            width: 40%;
            border-right: 1px solid #ececec;
            text-align: center;
            height: 80%;
            padding-top: 50px;
            position: relative;
        }
        .point div:nth-child(1) i
        {
            float: left;
            font-size: 5.0em;
            position: absolute;
            top: 30%;
            left: 12%;
            color: #0d6efd;
        }
        .point div:nth-child(1) strong
        {
            color: #0d6efd;
        }
        .point div:nth-child(1) h3
        {
            font-size: 20px;
        }
        .point div:nth-child(1) p
        {
            font-size: 15px;
            color: rgba(33, 33, 33, 0.65);
        }
        .point div:nth-child(2)
        {
            width: 30%;
            border-right: 1px solid #ececec;
            text-align: center;
            height: 80%;
            padding-top: 50px;  
        }
        .point div:nth-child(2) h3
        {
            font-size: 15px;
        }
        .point div:nth-child(2) p
        {
            font-size: 20px;
            font-weight: bold;
        }
        .point div:nth-child(3)
        {
            width: 30%;
            text-align: center;
            height: 80%;
            padding-top: 50px;
        }
        .point div:nth-child(3) h3
        {
            font-size: 15px;
        }
        .point div:nth-child(3) p
        {
            font-size: 20px;
            font-weight: bold;
        }
        .content .orderDetail
        {
            margin: 20px 0 20px 0;
        }
        .content .orderDetail .title
        {
            position: relative;   
        }
        .content .orderDetail .title i
        {
            float: left;
            position: absolute;
            top: 25%;
        }
        .content .orderDetail .title h2
        {
            padding: 5px 0 0 25px;
            font-size: 1.3em;
        }
        .content .orderDetail .orderDate
        {
            margin: 20px 0 10px 0;
            padding: 15px 0 0 25px;
            border: 1px solid #ececec;
            border-radius: 10px;
            box-shadow: 2px 2px 2px 2px rgba(33, 33, 33, 0.15);
        }
        .content .orderDetail .orderDate div:nth-child(1)
        {
            display: inline-flex;
        }
        .content .orderDetail .orderDate div:nth-child(1) p
        {
            margin-right: 3px;
            color: rgba(33, 33, 33, 0.65);
        }
        .content .orderDetail .productInfo
        {
            border: 1px solid #ececec;
            border-radius: 10px;
            box-shadow: 2px 2px 2px 2px rgba(33, 33, 33, 0.15);
            margin-top: 30px;
            padding: 15px;
        }
        .content .orderDetail .productInfo table
        {
            border-collapse: collapse;
            width: 100%;
        }
        .content .orderDetail .productInfo table th
        {
            border-bottom: 2px solid #ececec;
            border-right: 2px solid #ececec;
            padding-bottom: 10px;
            color: rgba(33, 33, 33, 0.65);
        }
        .content .orderDetail .productInfo table th:nth-child(1)
        {
            padding-left: 10px;
        }
        .content .orderDetail .productInfo table th:nth-child(2)
        {
            text-align: center;
        }
        .content .orderDetail .productInfo table th:nth-child(3)
        {
            border-right: none;
            text-align: center;
        }
        .content .orderDetail .productInfo table td:nth-child(1) 
        {
            border-right: 2px solid #ececec;
            border-bottom: 2px solid #ececec;
            border-left: 2px solid #ececec;
            position: relative;
            width: 50%;
        }
        .content .orderDetail .productInfo table td:nth-child(1) strong
        {
            position: absolute;
            top: 67%;
        }
        .content .orderDetail .productInfo table td:nth-child(2)
        {
            text-align: center;
            border-right: 2px solid #ececec;
            border-bottom: 2px solid #ececec;
        }
        .content .orderDetail .productInfo table td:nth-child(3)
        {
            text-align: center;
            border-right: 2px solid #ececec;
            border-bottom: 2px solid #ececec;
        }
        .content .orderDetail .productInfo table td p
        {
            padding-top: 20px;
        }
        .content .orderDetail .productInfo table img
        {
            width: 80px;
            height: 80px;
            float: left;
            margin: 20px 20px 20px 10px;
        }
        .content .orderDetail .userInfo
        {
            border: 2px solid #ececec;
            border-radius: 10px;
            box-shadow: 2px 2px 2px 2px rgba(33, 33, 33, 0.15);
            margin-top: 30px;
            padding: 15px;
        }
        .content .orderDetail .userInfo table
        {
            border-collapse: collapse;
            width: 100%;
        }
        .content .orderDetail .userInfo table th
        {
            color: rgba(33, 33, 33, 0.65);
            padding: 5px;
            width: 113px;
        }
        .content .orderDetail .addrInfo
        {
            border: 2px solid #ececec;
            border-radius: 10px;
            box-shadow: 2px 2px 2px 2px rgba(33, 33, 33, 0.15);
            margin-top: 30px;
            padding: 15px;
        }
        .content .orderDetail .addrInfo table
        {
            border-collapse: collapse;
            width: 100%;
        }
        .content .orderDetail .addrInfo table th
        {
            color: rgba(33, 33, 33, 0.65);
            padding: 5px;
            width: 113px;
        }
        .content .orderDetail .paymentDetail
        {
            border: 2px solid #ececec;
            border-radius: 10px;
            box-shadow: 2px 2px 2px 2px rgba(33, 33, 33, 0.15);
            margin-top: 30px;
            padding: 15px;
        }
        .content .orderDetail .paymentDetail .payment
        {
            padding-bottom: 10px;
            text-align: center;
        }
        .content .orderDetail .paymentDetail .payment strong
        {
            color: #0d6efd;
            margin-left: 100px;
            margin-right: 50px;
            font-size: 1.2em;
        }
        .content .orderDetail .paymentDetail .payment strong:nth-child(1)::after
        {
            content: "-";
            padding-left: 50px;
            color: #222;
        }
        .content .orderDetail .paymentDetail .payment strong:nth-child(2)::after
        {
            content: "=";
            padding-left: 50px;
            color: #222;
        }
        .content .orderDetail .paymentDetail .divGrp
        {
            border-top: 2px solid #ececec;
            display: flex;
        }
        .content .orderDetail .paymentDetail .divGrp .div1
        {
            border-right: 2px solid #ececec;
            width: 33.333%;
            padding: 15px;
        }
        .content .orderDetail .paymentDetail .divGrp .div1 table
        {
            border-collapse: collapse;
            width: 100%;
        }
        .content .orderDetail .paymentDetail .divGrp .div1 table th
        {
            color: rgba(33, 33, 33, 0.65);
            padding: 5px;
        }
        .content .orderDetail .paymentDetail .divGrp .div1 table td{
            float: right;
        }
        .content .orderDetail .paymentDetail .divGrp .div2
        {
            border-right: 2px solid #ececec;
            width: 33.333%;
            padding: 15px;
        }
        .content .orderDetail .paymentDetail .divGrp .div2 table
        {
            border-collapse: collapse;
            width: 100%;
        }
        .content .orderDetail .paymentDetail .divGrp .div2 table th
        {
            color: rgba(33, 33, 33, 0.65);
            padding: 5px;
        }
        .content .orderDetail .paymentDetail .divGrp .div2 table td{
            float: right;
        }
        .content .orderDetail .paymentDetail .divGrp .div3
        {
            width: 33.333%;
            padding: 15px;
        }
        .content .orderDetail .paymentDetail .divGrp .div3 .p1 
        {
            color: rgba(33, 33, 33, 0.65);
            font-size: 13px;
        }
        .content .orderDetail .paymentDetail .divGrp .div3 button
        {
            background: #fff;
            color: #222;
            border: 1px solid rgba(33, 33, 33, 0.65);
            border-radius: 8px;
            margin-bottom: 5px;
            font-weight: bold;
            font-size: 13px;
            padding: 2px;
        }
        .content .orderDetail .paymentDetail .divGrp .div3 .p2
        {
            color: #0d8efd;
            font-size: 15px;
        }
    </style>
</head>
<body>
    <%@ include file ="../include/header.jsp" %>

    <div class="content">
        <div class="point">
            <div>
                <i class="xi-profile"></i>
                <h3><strong>${memberDTO.mem_name }</strong>님 안녕하세요.</h3>
                <p>누적 금액: <fmt:formatNumber pattern="###,###,###" value="${totalPrice}"/>원</p>
            </div>
            <div>
                <h3>POINT</h3>
                <P>0</P>
            </div>
            <div>
                <h3>사용 가능 쿠폰</h3>
                <p>0</p>
            </div>
        </div>
        <div class="orderDetail">
            <div class="title">
                <i class="xi-arrow-left"></i>
                <h2>주문 상세 내역</h2>
            </div>
            <div class="orderDate">
            <div>
            	<p>주문일자</p>
            	<strong><fmt:formatDate pattern="yyyy-MM-dd" value="${firstOrderDate }"/></strong>
	            <p style="margin-left: 20px;">주문번호</p>
            	<c:forEach items="${orderList }" var="list">	                                 
	                    <strong>${list.order_no } &nbsp;</strong>	                
                </c:forEach> 
                </div>    
            </div>
            <div class="productInfo">
                <table>
                    <thead>
                        <tr>
                            <th>상품정보</th>
                            <th>배송비</th>
                            <th>진행상태</th>
                        </tr>
                    </thead>
                    <tbody>                      
                        <c:forEach items="${orderList }" var="list">
	                        <tr>
	                            <td>
	                                <img src="${path }/resources/img/${list.prod_image}" alt="">
	                                <p>${list.prod_name }</p>
	                                <strong>${list.price }원 / 수량${list.quantity }개</strong>
	                            </td>
	                            <td rowspan="1">${list.deliveryFee }</td>
	                            <td>
	                                <strong>${list.order_state }</strong>
	                            </td>
	                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="userInfo">
                <table>               	
                    <tr>
                        <th>주문자</th>
                        <td>${maskedName }<td> <!-- 마스킹 처리-->
                    </tr>
                    <tr>
                        <th>연락처</th>
                        <td>${phoneNumber }</td> <!-- 마스킹 처리-->
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td>${memberDTO.email }</td>
                    </tr>                  
                </table>
            </div>
            <div class="addrInfo">
                <table>
                    <tr>
                        <th>수령인</th>
                        <td>${maskedName }<td> <!-- 마스킹 처리-->
                    </tr>
                    <tr>
                        <th>연락처</th>
                        <td>${phoneNumber }</td> <!-- 마스킹 처리-->
                    </tr>
                    <tr>
                        <th>배송지</th>
                        <td>${address }</td>
                    </tr>
                    <tr>
                        <th>배송메모</th>
                        <td>파손 되지 않게 주의 해주세요~</td>
                    </tr>
                </table>
            </div>
            <div class="paymentDetail">
                <div class="payment">
			                    주문금액<strong><fmt:formatNumber pattern="###,###,###" value="${totalPrice}"/>원</strong>
			                    할인금액<strong>0원</strong>
			                    총 주문금액 <strong><fmt:formatNumber pattern="###,###,###" value="${totalPrice}"/>원</strong>
                </div>
                <div class="divGrp">
                    <div class="div1">
                        <table>
                            <tr>
                                <th>상품금액</th>
                                <td><fmt:formatNumber pattern="###,###,###" value="${totalPrice}"/>원</td>
                            </tr>
                            <tr>
                                <th>배송비</th>
                                <td>????</td>
                            </tr>
                        </table>
                    </div>
                    <div class="div2">
                        <table>
                            <tr>
                                <th>회원 할인금액</th>
                                <td>0원</td>
                            </tr>
                            <tr>
                                <th>쿠폰</th>
                                <td>0원</td>
                            </tr>
                            <tr>
                                <th>포인트</th>
                                <td>0원</td>
                            </tr>
                        </table>
                    </div>
                    <c:choose>
					    <c:when test="${orderList.PAYMENT_METHOD == '무통장 입금'}">
					        <div class="div3">
					            <strong>${orderList.PAYMENT_METHOD }</strong>
					            <p class="p1">입금 금액: <fmt:formatNumber pattern="###,###,###" value="${totalPrice}"/>원</p>
					            <button type="button">영수증 조회</button>
					        </div>
					    </c:when>
					    <c:otherwise>
					        <div class="div3">
					            <strong>${orderList.PAYMENT_METHOD }</strong>
					            <p class="p1">BC카드(****-****-****-1234</p> <!-- 마스킹 처리-->
					            <%-- <p class="p1">${orderList.PAYMENT_METHOD} (${fn:substring(orderList.PAYMENT_STATE, 0, 4)}-****-****-****)</p> --%>
					            <button type="button">영수증 조회</button>
					            <p class="p2">???? 포인트 적립예정</p>
					        </div>
					    </c:otherwise>
					</c:choose>
                </div>
            </div>
        </div>
    </div>

    <%@ include file ="../include/footer.jsp" %>
    
    <script>
	    var loginChk = '${loginChk}';
	
	    if (loginChk == 'fail') {
	        alert("로그인 후 이용 가능합니다.");
	        window.location.href = '${path}/member/loginForm.do'; // 리다이렉트
	    }
    </script>
</body>
</html>