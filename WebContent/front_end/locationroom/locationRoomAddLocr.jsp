<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.locationroom.model.*"%>
<%
	LocrVO LocrVO = (LocrVO) request.getAttribute("LocrVO");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="icon"
	href="<%=request.getContextPath()%>/front_end/locationroom/images/logo-icon01.ico"
	type="image/x-icon" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/locationroom/css/all.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/locationroom/css/cssreset.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/locationroom/css/header.css">
	<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/locationroom/css/footer.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/locationroom/css/memberBuyProfile.css">
<link rel="stylesheet"
	type="<%=request.getContextPath()%>/front_end/locationroom/text/css"
	href="<%=request.getContextPath()%>/front_end/locationroom/css/jquery.datetimepicker.css" />
<script src="https://code.jquery.com/jquery-3.6.0.js"
	integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	crossorigin="anonymous"></script>
<script
	src="<%=request.getContextPath()%>/front_end/locationroom/js/jquery.datetimepicker.full.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>

<style>
table {
	width: 85%;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	width: 100px;
	height: 50px;
	padding: 1px;
}

ul {
	list-style: none;
	padding-left: 0px;
}

.form-control {
	width: 70%;
}
</style>
<title>廳房中心｜MarryHappiness</title>
</head>
<body>
    <!--大頭-->
    <header>
        <div class="top">
            <ul>
                <!-- 購物車圖片Icon -->

                <li><a href="<%=request.getContextPath()%>/front_end/index/index.jsp">首頁</a></li>
                <li><a href="<%=request.getContextPath()%>/front_end/member/login.html"><i class="fas fa-sign-out-alt"></i>登入</a></li>
				<li><a href="<%=request.getContextPath()%>/member/loginServlet?action=logout"><i class="fas fa-sign-out-alt"></i>登出</a></li>
                <li><a href="<%=request.getContextPath()%>/member/checkServlet"><i class="fas fa-home"></i> 會員系統</a></li>
                <li class="headcart">
                    <a href="javascript:void(0)" id="cartModal">
                        <i class="fas fa-shopping-cart" id="cartIcon"></i></a>
                </li>

            </ul>
        </div>


        <!--上面增加的小色塊-->
        <div class="nav1"></div>
        <ul class="nav2">
            <li><a href="<%=request.getContextPath()%>/front_end/workphoto/browseHome.html">婚禮攝影<br>Photography
				</a></li>
            <li><a href="<%=request.getContextPath()%>/front_end/locationprogram/LocIndex.html">婚禮場地<br>Location
				</a></li>
            <li>
                <a href="<%=request.getContextPath()%>/front_end/index/index.jsp" class="logo"><img src="<%=request.getContextPath()%>/front_end/index/images/MHlogo_01.svg"></a>
            </li>
            <li><a href="<%=request.getContextPath()%>/front_end/product/ProductMain.html">婚禮週邊<br>Product
				</a></li>
            <li><a href="<%=request.getContextPath()%>/front_end/post/forumindex.html">專欄討論<br>Post
				</a></li>
        </ul>
    </header>
	<div class="wrap">
		<div class="side-bar">
			<img
				src="<%=request.getContextPath()%>/front_end/locationroom/images/shop_tenin_houseki.png"
				alt="">
			<div class="user-name s-user-name"></div>
			<div class="user-hr">
				<i class="fas fa-heart"></i>
			</div>
			<ul class="menu">
				<li><a href="<%=request.getContextPath()%>/front_end/locationorder/locationOrderGetAll.html"><i class="fas fa-user"></i>訂單資料</a>
				</li>
				<li><a href="<%=request.getContextPath()%>/locationroom/locationRoomServlet?action=locationRoomGetAll"><i
							class="fas fa-file-invoice"></i>查詢廳房</a></li>
				
				<li><a
					href="<%=request.getContextPath()%>/front_end/locationroom/locationRoomGetOnee2.jsp"><i
						class="fas fa-church"></i>新增廳房</a></li>
				<li><a
					href="<%=request.getContextPath()%>/locationroom/locationRoomServlet?action=locationRoomDelete"><i
						class="fas fa-camera-retro"></i>刪除廳房</a></li>
	
			</ul>
		</div>
		<div class="content">
			<h2>新增廳房資料:</h2>


			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>

			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/locationroom/locrServletxx"
				name="form1">


				<div class="mb-3">
					<label for="LOCR_SMEM_ID" class="form-label">店家代號</label> <input
						type="text/UTF-8" class="form-control" id="LOCR_SMEM_ID"
						name="LOCR_SMEM_ID" value="" disabled>
				</div>
				<div class="mb-3">
					<label for="LOCR_NAME" class="form-label">廳房名稱</label> <input
						type="text" class="form-control" id="LOCR_NAME" name="LOCR_NAME"
						value="<%=(LocrVO == null) ? "今天明天後天" : LocrVO.getLOCR_NAME()%>">
				</div>
				<div class="mb-3">
					<label for="LOCR_MAX_TABLE" class="form-label">最大總桌數</label> <input
						type="text" class="form-control" id="LOCR_MAX_TABLE"
						name="LOCR_MAX_TABLE"
						value="<%=(LocrVO == null) ? "50" : LocrVO.getLOCR_MAX_TABLE()%>">
				</div>
				<div class="mb-3">
					<label for="LOCR_MIN_TABLE" class="form-label">最小總桌數</label> <input
						type="text" class="form-control" id="LOCR_MIN_TABLE"
						name="LOCR_MIN_TABLE"
						value="<%=(LocrVO == null) ? "10" : LocrVO.getLOCR_MIN_TABLE()%>">
				</div>
				<div class="mb-3">
					<label for="LOCR_MAIN_TABLE" class="form-label">主桌人數</label> <input
						type="text" class="form-control" id="LOCR_MAIN_TABLE"
						name="LOCR_MAIN_TABLE"
						value="<%=(LocrVO == null) ? "12" : LocrVO.getLOCR_MAIN_TABLE()%>">
				</div>
				<div class="mb-3">
					<label for="LOCR_GUEST_TABLE" class="form-label">客桌人數</label> <input
						type="text" class="form-control" id="LOCR_GUEST_TABLE"
						name="LOCR_GUEST_TABLE"
						value="<%=(LocrVO == null) ? "10" : LocrVO.getLOCR_GUEST_TABLE()%>">
				</div>
				<div class="mb-3">
					<label for="LOCR_FLOOR" class="form-label">廳房樓層</label> <input
						type="text" class="form-control" id="LOCR_FLOOR" name="LOCR_FLOOR"
						value="<%=(LocrVO == null) ? "1" : LocrVO.getLOCR_FLOOR()%>">
				</div>
				<div class="mb-3">
					<label for="LOCR_STATUS" class="form-label">廳房狀態</label>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="LOCR_STATUS"
							id="LOCR_STATUS1" value="1"
							<c:if test="${locrVO.LOCR_STATUS==1}">checked</c:if>> <label
							class="form-check-label" for="LOCR_STATUS1">上架</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="LOCR_STATUS"
							id="LOCR_STATUS0" value="0"
							<c:if test="${locrVO.LOCR_STATUS==0}">checked</c:if> checked>
						<label class="form-check-label" for="LOCR_STATUS0">下架</label>
					</div>
				</div>
				<div class="mb-3">
					<label for="LOCR_CONTENT" class="form-label">廳房介紹</label>
					<textarea class="form-control" id="LOCR_CONTENT"
						name="LOCR_CONTENT" rows="3">詳細介紹</textarea>
				</div>


				<br> <input type="hidden" name="action" value="insert">
				<input type="submit" value="送出新增">
			</FORM>

		</div>
	</div>
	  <!-- 購物車頁面 -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">購物車<img src="images/MHlogo_04.svg" alt=""></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">商品資訊</th>
                                <th scope="col">商品名稱</th>
                                <th scope="col">價格</th>
                                <th scope="col">數量</th>
                                <th scope="col">小計</th>
                                <th scope="col">操作</th>
                            </tr>
                        </thead>
                        <tbody id="cart"></tbody>
                    </table>
                    <div id="total-list" class="total-list">
                        <h5 class="site-color">合計金額</h5>
                        <h5 class="site-color">$<span id="total"></span></h5>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="checkout">結帳</button>
                </div>
            </div>
        </div>
    </div>
    <!--尾巴-->
    <div class="footer">
        <div class="foote-top">
            <ul class="foote-content">
                <div>婚攝服務</div>
                <li><a href="<%=request.getContextPath()%>/front_end/workphoto/browseHome.html">拍婚紗</a></li>
            </ul>
            <ul class="foote-content">
                <div>婚宴服務</div>
                <li><a href="<%=request.getContextPath()%>/front_end/locationprogram/LocIndex.html">婚宴場地</a></li>
            </ul>
            <ul class="foote-content">
                <div>婚禮週邊</div>
                <li><a href="<%=request.getContextPath()%>/front_end/product/ProductSubGoods.html">婚禮小物</a></li>
                <li><a href="<%=request.getContextPath()%>/front_end/product/ProductSubShoes.html">婚鞋</a></li>
                <li><a href="<%=request.getContextPath()%>/front_end/product/ProductSubRing.html">婚戒</a></li>
            </ul>
            <ul class="foote-content">
                <div>專欄討論</div>
                <li><a href="<%=request.getContextPath()%>/front_end/post/forumindex.html">幸福專欄</a></li>
            </ul>
        </div>
        <div class="foote-between">
            <img src="<%=request.getContextPath()%>/front_end/index/images/MHlogo_01.svg" alt="">
            <p class="f-logo">2021 MarryHappiness 嫁給幸福</p>
        </div>
    </div>
	<script>
		$.ajax({
			type : "post",
			url : "../../locationroom/locrServletxx",
			data : {
				"action" : "getname"
			},
			dataType : "json",
			success : function(result) {
				console.log(result);

				$("#LOCR_SMEM_ID").val(result.Mem_shop_name);

			}

		})
	</script>

</body>
</html>