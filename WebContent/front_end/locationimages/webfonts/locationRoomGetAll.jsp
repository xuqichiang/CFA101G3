<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.locationroom.model.*"%>

<%
	LocrService LocrSvc = new LocrService();
	List<LocrVO> list = LocrSvc.getAll();
	pageContext.setAttribute("list", list);
%>


<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/all.css">
<link rel="stylesheet" href="css/cssreset.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/memberBuyProfile.css">

<style>
#table thead, #table tr {
	border-top-width: 1px;
	border-top-style: solid;
	border-top-color: rgb(230, 189, 189);
}

#table {
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: rgb(230, 189, 189);
}

#table td, #table th {
	padding: 5px 10px;
	font-size: 12px;
	font-family: Verdana;
	color: rgb(177, 106, 104);
}

#table tr:nth-child(even) {
	background: rgb(238, 211, 210)
}

#table tr:nth-child(odd) {
	background: #FFF
}
<style>
  table {
	width: 800px;
	background-color: red;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid rgb(238, 211, 210);
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</style>
<title>廳房中心｜MarryHappiness</title>
</head>
<body>
	<div class="header">
		<div class="top-container">
			<a href="#" class="logo"><img src="images/MHlogo_04.svg" alt=""></a>
			<div class="user">
				<div class="headshot">
					<img src="images/bird_couple.png" alt="">
				</div>
				<div class="user-name"></div>
			</div>
		</div>
		<ul class="nav">
			<li><a href="#">拍婚紗</a></li>
			<li><a href="#">婚禮場地</a></li>
			<li><a href="#">婚禮週邊</a></li>
			<li><a href="#">找靈感</a></li>
		</ul>
	</div>
	<div class="wrap">
		<div class="side-bar">
			<img src="images/shop_tenin_houseki.png" alt="">
			<div class="user-name s-user-name"></div>
			<div class="user-hr">
				<i class="fas fa-heart"></i>
			</div>
			<ul class="menu">
				<li><a href="locationRoomSelect.jsp"><i class="fas fa-user"></i>店家資料</a></li>
				<li><a href="locationRoomGetAll.jsp"><i
						class="fas fa-file-invoice"></i>查詢廳房</a></li>
				<li><a href="/CFA101G3/ProductINSERT.html"><i
						class="fas fa-shopping-cart"></i>新增廳房</a></li>
				<li><a href="/CFA101G3/ProductUPDATE.html"><i
						class="fas fa-church"></i>修改廳房</a></li>
				<li><a href="/CFA101G3/ProductDELETE.html"><i
						class="fas fa-camera-retro"></i>刪除廳房</a></li>
			</ul>
		</div>
		<div class="content">
			<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
			<table>
				<tr>
					<th>廳房編號</th>
					<th>店家編號</th>
					<th>廳房名稱</th>
					<th>廳房最大總桌數</th>
					<th>廳房最小總桌數</th>		
					<th>主桌人數</th>
					<th>客桌人數</th>							
					<th>廳房樓層</th>
<!-- 					<th>簡介</th> -->
					<th>廳房狀態</th>
					
				</tr>
<%-- 				<%@ include file="page1.file"%> --%>
				<c:forEach var="LocrVO" items="${list}" >

					<tr>
						<td>${LocrVO.LOCR_ID}</td>
						<td>${LocrVO.LOCR_SMEM_ID}</td>
						<td>${LocrVO.LOCR_NAME}</td>
						<td>${LocrVO.LOCR_MAX_TABLE}</td>
						<td>${LocrVO.LOCR_MIN_TABLE}</td>
						<td>${LocrVO.LOCR_MAIN_TABLE}</td>
						<td>${LocrVO.LOCR_GUEST_TABLE}</td>
						<td>${LocrVO.LOCR_FLOOR}F</td>
<%-- 						<td>${LocrVO.LOCR_CONTENT}</td> --%>
						<td>${LocrVO.LOCR_STATUS}</td>
						
					</tr>
				</c:forEach>
			</table>
<%-- 			<%@ include file="page2.file" %> --%>
		</div>
	</div>


</body>
</html>