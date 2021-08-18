<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	href="<%=request.getContextPath()%>/front_end/locationroom/css/LocSellerProfile.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/locationroom/css/newCart.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/locationroom/css/friendchat.css">
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
<script src="<%=request.getContextPath()%>/front_end/locationroom/js/newCartJSP.js"></script>

<title>廳房中心｜MarryHappiness</title>
</head>
<style>
	ul{
		list-style: none;
		padding-left: 0px;
	}
</style>
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
            <img class="imgdata" src="" alt="">
            <div class="user-name s-user-name"></div>
            <div class="user-hr"><i class="fas fa-heart"></i></div>
            <ul class="menu">
                <li><a href="../front_end/member/LocSellerProfile.html?action=profile" id="profile"><i class="fas fa-user"></i>個人資料</a></li>
                <li><a href="../front_end/member/LocSellerProfile.html?action=setting" id="setting"><i class="fas fa-file-invoice"></i>帳號設定</a></li>
                <li><a href="../front_end/member/LocSellerProfile.html?action=sellerProfile"><i class="fas fa-shopping-cart"></i>店家資料</a></li>
                <li><a href="../front_end/locationprogram/Locmanagement.html"><i class="fas fa-church"></i>場地管理</a></li>
                <li><a href="javascript:void(0)" id="locRManage"><i class="fas fa-home"></i>廳房管理</a></li>
                <ul class="menu-locR">
                    <li><a href="../front_end/locationorder/locationOrderGetAll.html"><i class="fas fa-user"></i>訂單資料</a>
                    </li>
                    <li><a href="../locationroom/locationRoomServlet?action=locationRoomGetAll"><i
                                class="fas fa-file-invoice"></i>查詢廳房</a></li>

                    <li><a href="../front_end/locationroom/locationRoomGetOnee2.jsp"><i
                            class="fas fa-church"></i>新增廳房</a></li>
                    <li><a href="../locationroom/locationRoomServlet?action=locationRoomDelete"><i
                            class="fas fa-camera-retro"></i>刪除廳房</a></li>
                </ul>
                <li><a href="javascript:void(0)" id="locPManage"><i class="fas fa-indent"></i>方案管理</a></li>
                <ul class="menu-locP">
                    <li><a href="../front_end/locationprogram/listLocpBySmemId.html"><i class="fas fa-tasks"></i>查詢方案</a></li>
                    <li><a href="../front_end/locationprogram/addLocpimage.html"><i class="fas fa-folder-plus"></i></i>新增方案</a></li>
                    <li><a href="../front_end/locationprogram/updateLocpimage.html"><i class="fas fa-pencil-alt"></i>修改方案</a></li>
                    <li><a href="../front_end/locationprogram/delLocp.html"><i class="fas fa-trash-alt"></i>刪除方案</a></li>
                </ul>
                <li><a href="../front_end/locationprogram/loccalendar.html"><i class="far fa-calendar-alt"></i>行程管理</a></li>
            </ul>
        </div>
		<div class="content">
			<table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">廳房名稱</th>
                        <th scope="col">最大總桌數</th>
                        <th scope="col">最小總桌數</th>
						<th scope="col">主桌人數</th>
						<th scope="col">客桌人數</th>
						<th scope="col">廳房樓層</th>
						<th scope="col">廳房狀態</th>
						<th scope="col">編輯廳房</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="LocrVO" items="${list}">
                    <tr>
                        <td>${LocrVO.LOCR_NAME}</td>
                        <td>${LocrVO.LOCR_MAX_TABLE}</td>
                        <td>${LocrVO.LOCR_MIN_TABLE}</td>
                        <td>${LocrVO.LOCR_MAIN_TABLE}</td>
                        <td>${LocrVO.LOCR_GUEST_TABLE}</td>
                        <td>${LocrVO.LOCR_FLOOR}</td>
						<td>
							<c:if test="${LocrVO.LOCR_STATUS==1}">上架</c:if>
							<c:if test="${LocrVO.LOCR_STATUS==0}">下架</c:if>
						</td>
						<td><button type="button" class="btn btn-primary" onclick="editRoom(${LocrVO.LOCR_ID})">編輯</button></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
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
		function editRoom(id) {
			window.location.href="<%=request.getContextPath()%>/locationroom/locationRoomServlet?action=locationRoomUpdate&locr_id="+id;
		}

        //profile跟資料庫請求個人資料
        Ajaxprofile();
        //跟資料庫請求個人頭像
        Ajaxheadshot();

        function Ajaxprofile() {
            $.ajax({
                type: "get",
                url: "../member/buyProfileServlet",
                dataType: 'json',
                success: function(result) {
                    if (result == "0") {
                        window.location.href = "../front_end/index/index.jsp";
                    } else {
                        resultData = result;
                        $('.user-name').html(result.name != null ? result.name : "尚未填寫");
                        $('#phone').html(result.phone != null ? result.phone : "尚未填寫");
                        $('#city').html(result.city != null ? result.city : "尚未填寫");
                        $('#cityarea').html(result.cityarea != null ? result.cityarea : "尚未填寫");
                        $('#street').html(result.street != null ? result.street : "尚未填寫");
                    }
                }
            });
        };

        function Ajaxheadshot() {
            $.ajax({
                type: "post",
                url: "../member/headshotBuyServlet",
                data: {
                    "headshot": "headshot"
                },
                xhrFields: {
                    // 將回傳結果以Blob保持原本二進位的格式回傳
                    //jquery的dataType無法設定返回格式為blob需要手動修改
                    responseType: "blob"
                },
                success: function(result) {
                    let img = document.getElementsByClassName('imgdata');
                    if (result.size != "0") {
                        let url = URL.createObjectURL(result);
                        for (let i = 0; i < img.length; i++) {
                            img[i].src = url;
                        }
                    } else {
                        for (let i = 0; i < img.length; i++) {
                            img[i].src = "../front_end/locationroom/images/music_castanet_girl.png";
                        }
                    }
                }
            });
        }

        $('#locRManage').on('click', function() {
            $('.menu-locR').slideToggle();
        });
        $('#locPManage').on('click', function() {
            $('.menu-locP').slideToggle();
        });
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/front_end/locationroom/js/friendchat.js"></script>
</body>
</html>