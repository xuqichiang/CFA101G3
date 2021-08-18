<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script> -->
<style>
div img {
	border: 1px solid black;
	padding: 10px;
	width: 500px;
	height: 300px;
	vertical-align: middle;
}
</style>
</head>

<body>
	<a
		href="<%=request.getContextPath()%>/LociServlet?LOCI_LOCR_ID=1&action=jsp">1號廳</a>
	<a
		href="<%=request.getContextPath()%>/LociServlet?LOCI_LOCR_ID=2&action=jsp">2號廳</a>
	<a
		href="<%=request.getContextPath()%>/LociServlet?LOCI_LOCR_ID=3&action=jsp">3號廳</a>
	<a
		href="<%=request.getContextPath()%>/LociServlet?LOCI_LOCR_ID=4&action=jsp">4號廳</a>
	<a
		href="<%=request.getContextPath()%>/LociServlet?LOCI_LOCR_ID=5&action=jsp">5號廳</a>
	<a
		href="<%=request.getContextPath()%>/LociServlet?LOCI_LOCR_ID=6&action=jsp">6號廳</a>

	<ul>

		<jsp:useBean id="LocrSvc" scope="page"
			class="com.locationroom.model.LocrService" />
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/LociServlet">
				<b>選擇廳房編號:</b> <select size="1" name="LOCI_LOCR_ID">
<%-- 					<c:forEach var="deptVO" items="${deptSvc.all}"> --%>
<%-- 						<option value="${deptVO.deptno}" --%>
<%-- 							${(empVO.deptno==deptVO.deptno)? 'selected':'' }>${deptVO.dname} --%>
<%-- 					</c:forEach> --%>
					<c:forEach var="LocrVO" items="${LocrSvc.all}">
						<option value="${LocrVO.LOCR_ID}"${(LociVO.LOCI_LOCR_ID==LocrVO.LOCR_ID)?'selected':'' }>${LocrVO.LOCR_NAME}
					</c:forEach>
				</select><br> <input type="hidden" name="action" value="jsp"> <input
					type="submit" value="送出">
			</FORM>
		</li>
	</ul>
	<div id="img">
		<c:forEach var="LociVO" items="${list}">
			<img
				src="<%=request.getContextPath()%>/ImgLociServlet?LOCI_ID=${LociVO.LOCI_ID}"
				alt="">
		</c:forEach>
	</div>
	    <!--尾巴-->
    <div class="footer">
        <div class="foote-top">
            <ul class="foote-content">
                <div>婚攝服務</div>
                <li><a href="../workphoto/browseHome.html">拍婚紗</a></li>
            </ul>
            <ul class="foote-content">
                <div>婚宴服務</div>
                <li><a href="../locationprogram/LocIndex.html">婚宴場地</a></li>
            </ul>
            <ul class="foote-content">
                <div>婚禮週邊</div>
                <li><a href="../product/ProductSubGoods.html">婚禮小物</a></li>
                <li><a href="../product/ProductSubShoes.html">婚鞋</a></li>
                <li><a href="../product/ProductSubRing.html">婚戒</a></li>
            </ul>
            <ul class="foote-content">
                <div>專欄討論</div>
                <li><a href="../post/forumindex.html">幸福專欄</a></li>
            </ul>
        </div>
        <div class="foote-between">
            <img src="images/MHlogo_01.svg" alt="">
            <p class="f-logo">2021 MarryHappiness 嫁給幸福</p>
        </div>
    </div>
</body>
</html>