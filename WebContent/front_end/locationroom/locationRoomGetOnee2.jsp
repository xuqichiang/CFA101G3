<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.locationroom.model.*"%>
<%
	LocrVO LocrVO = (LocrVO) request.getAttribute("LocrVO");
%>
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
<script
	src="<%=request.getContextPath()%>/front_end/locationroom/js/newCart.js"></script>
<title>廳房中心｜MarryHappiness</title>
</head>
<style>
div.contant div.inner {
	/* 	position:sticky; */
	
}

div.content {
	/* 		width: :50%; */
	
}

div #errorMsg {
	color: red;
}

div.md-4 {
	width: 40%;
}

div.col-sm-6col-md-3 img {
	width: 720px;
}

ul {
	list-style: none;
	padding-left: 0px;
}
.form-control {
 
    width: 70%;
    
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
                <li><a href="../member/LocSellerProfile.html?action=profile" id="profile"><i class="fas fa-user"></i>個人資料</a></li>
                <li><a href="../member/LocSellerProfile.html?action=setting" id="setting"><i class="fas fa-file-invoice"></i>帳號設定</a></li>
                <li><a href="../member/LocSellerProfile.html?action=sellerProfile"><i class="fas fa-shopping-cart"></i>店家資料</a></li>
                <li><a href="../locationprogram/Locmanagement.html"><i class="fas fa-church"></i>場地管理</a></li>
                <li><a href="javascript:void(0)" id="locRManage"><i class="fas fa-home"></i>廳房管理</a></li>
                <ul class="menu-locR">
                    <li><a href="../locationorder/locationOrderGetAll.html"><i class="fas fa-user"></i>訂單資料</a>
                    </li>
                    <li><a href="../../locationroom/locationRoomServlet?action=locationRoomGetAll"><i
                                class="fas fa-file-invoice"></i>查詢廳房</a></li>

                    <li><a href="../locationroom/locationRoomGetOnee2.jsp"><i
                            class="fas fa-church"></i>新增廳房</a></li>
                    <li><a href="../../locationroom/locationRoomServlet?action=locationRoomDelete"><i
                            class="fas fa-camera-retro"></i>刪除廳房</a></li>
                </ul>
                <li><a href="javascript:void(0)" id="locPManage"><i class="fas fa-indent"></i>方案管理</a></li>
                <ul class="menu-locP">
                    <li><a href="../locationprogram/listLocpBySmemId.html"><i class="fas fa-tasks"></i>查詢方案</a></li>
                    <li><a href="../locationprogram/addLocpimage.html"><i class="fas fa-folder-plus"></i></i>新增方案</a></li>
                    <li><a href="../locationprogram/updateLocpimage.html"><i class="fas fa-pencil-alt"></i>修改方案</a></li>
                    <li><a href="../locationprogram/delLocp.html"><i class="fas fa-trash-alt"></i>刪除方案</a></li>
                </ul>
                <li><a href="../locationprogram/loccalendar.html"><i class="far fa-calendar-alt"></i>行程管理</a></li>
            </ul>
        </div>

		<div class="content">
			<form action="" enctype="multipart/form-data" method="POST"
				id="imgform">
				<div class="imgblock">
					<div class="form-horizontal">
						<div class="form-group">
							<label for="file1">選取圖片:</label> <input type="file" id="file1"
								name="file1" accept="image/*"> <input type="hidden"
								id="uplocpid" name="locp_id">
						</div>
						<br> <img id="demoimg" width="450" />
					</div>
				</div>

				<div class="mb-3">
					<label for="LOCR_SMEM_ID" class="form-label">店家代號</label> <input
						type="text/UTF-8" class="form-control" id="LOCR_SMEM_ID"
						name="LOCR_SMEM_ID" value="" disabled>
				</div>
				<div class="mb-3">
					<label for="LOCR_NAME" class="form-label">廳房名稱</label> <input
						type="text" class="form-control" id="LOCR_NAME" name="LOCR_NAME"
						value="<%=(LocrVO == null) ? "2今天明天後天" : LocrVO.getLOCR_NAME()%>">
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
				<input type="submit" value="新增方案" id="button">
			</form>
			<div id="errorMsg"></div>
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
		//找店家名
		$.ajax({
			type : "post",
			url : "../../locationroom/locrServletxx",
			data : {
				"action" : "getname"
			},
			dataType:"json",
			success : function(result) {
				console.log(result);

				$("#LOCR_SMEM_ID").val(result.Mem_shop_name);

			}

		})
		//預覽圖片
		$('#file1').on('change', function() {
			var file = $('#file1')[0].files[0];
			var reader = new FileReader;
			reader.onload = function(e) {
				$('#demoimg').attr('src', e.target.result);
			};
			reader.readAsDataURL(file);
		})

		$('#imgform').on('submit', function(e) {
			$.ajax({
				url : "../../locationroom/locrServletAdd2",
				type : 'POST',
				data : new FormData(this),
				dataType : "JSON",
				processData : false,
				contentType : false,
				success : function(data) {
					// 					console.log(data);

					alert("上傳成功");

				},

				error : function(data) {
					$("#errorMsg").html("");
					console.log(data.responseText);
					// let errorMsg = data.responseText.split(",");
					let errorMsg = JSON.parse(data.responseText);
					for (let i = 0; i < errorMsg.length; i++) {
						$("#errorMsg").append(errorMsg[i] + "<br>");
						// 						console.log(errorMsg[i]);

					}
					alert("上傳失敗");
				}
			});
			//取消form表單的跳轉畫面
			e.preventDefault();
		});


        //profile跟資料庫請求個人資料
        Ajaxprofile();
        //跟資料庫請求個人頭像
        Ajaxheadshot();
        function Ajaxprofile() {
            $.ajax({
                type: "get",
                url: "../../member/buyProfileServlet",
                dataType: 'json',
                success: function(result) {
                    if (result == "0") {
                        window.location.href = "../../front_end/index/index.jsp";
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
                url: "../../member/headshotBuyServlet",
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
                            img[i].src = "images/music_castanet_girl.png";
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
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/front_end/locationroom/js/friendchat.js"></script>
</body>
</html>