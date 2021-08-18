<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/member/css/all.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/member/css/sellerMember.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/member/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/member/css/footer.css">

<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<title>賣家會員帳號管理系統</title>
</head>
<style>
li{ 
	list-style: none;
}
</style>
<body>
    <!--大頭-->
    <header>
        <div class="top">
            <ul>
                <li><a href="<%=request.getContextPath()%>/front_end/index/index.jsp">首頁</a></li>
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
                <a href="<%=request.getContextPath()%>/front_end/index/index.jsp" class="logo"><img src="<%=request.getContextPath()%>/back_end/member/images/MHlogo_01.svg"></a>
            </li>
            <li><a href="<%=request.getContextPath()%>/front_end/product/ProductMain.html">婚禮週邊<br>Product
				</a></li>
            <li><a href="<%=request.getContextPath()%>/front_end/post/forumindex.html">專欄討論<br>Post
				</a></li>
        </ul>
    </header>
	<div class="wrap">
		<div class="side-bar">
			<ul class="menu">
				<li><a href="<%=request.getContextPath() %>/member/memberServlet?action=buyMember&whichPage=1&rowsPerPage=5"><i class="fas fa-user"></i>買家帳號管理</a></li>
				<li><a href="<%=request.getContextPath() %>/member/memberServlet?action=sellerMember&whichPage=1&rowsPerPage=5"><i class="fas fa-user"></i>賣家帳號管理</a></li>
			</ul>
		</div>
		<div class="content" id="content">
			<%-- 賣家帳號展示 --%>
			<h5>賣家帳號管理</h5>
			<div class="find-form">
				<form action="<%=request.getContextPath() %>/member/memberServlet?action=sellerMember&whichPage=1&rowsPerPage=5" method="post">
					<label for="">帳號</label>
					<input type="text" name="find_username" value="${condition.find_username[0]}">
					<label for="">會員名稱</label>
					<input type="text" name="find_name" value="${condition.find_name[0]}">
					<br>
					<label for="">會員狀態</label>
					<select name="find_status">
						<option value="">請選擇</option>
						<option value="0" id="find_status0">Email未驗證</option>
						<option value="1" id="find_status1">Email已驗證</option>
						<option value="2" id="find_status2">停權</option>
					</select>
					<label for="">店家狀態</label>
					<select name="find_shop_status">
						<option value="">請選擇</option>
						<option value="0" id="find_shop_status0">未開通店家身份</option>
						<option value="1" id="find_shop_status1">已開通店家身份</option>
						<%-- <option value="2" id="find_shop_status2">申請開店中</option>
						<option value="3" id="find_shop_status3">商店上架</option>
						<option value="4" id="find_shop_status4">商店下架</option> --%>
					</select>
					<label for="">店家身份</label>
					<select name="mem_role">
						<option value="">請選擇</option>
						<option value="20" id="find_mem_role20">週邊店家</option>
						<option value="30" id="find_mem_role30">場地店家</option>
						<option value="40" id="find_mem_role40">婚攝店家</option>
					</select>
					<button type="submit" class="btn btn-primary">送出查詢</button>
				</form>
			</div>
			<div class="account">
				<table class="table table-striped" id="accountTable">
					<thead>
						<tr>
							<th scope="col">會員ID</th>
							<th scope="col">會員帳號</th>
							<th scope="col">會員名稱</th>
							<th scope="col">會員手機</th>
							<th scope="col">會員狀態</th>
							<th scope="col">店家狀態</th>
							<th scope="col">會員身份</th>
							<th scope="col">會員修改</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="memVO" items="${pageVO.pageDatas}">
							<tr>
								<th scope="row">${memVO.mem_id}</th>
								<td>${memVO.mem_username}</td>
								<td>${memVO.mem_name}</td>
								<td>${memVO.mem_phone}</td>
								<c:if test="${memVO.mem_status==0}">
									<td class="text-danger">Email未驗證</td>
								</c:if>
								<c:if test="${memVO.mem_status==1}">
									<td class="text-success">Email已驗證</td>
								</c:if>
								<c:if test="${memVO.mem_status==2}">
									<td class="text-dark">停權</td>
								</c:if>
								<c:if test="${memVO.mem_shop_status==0}">
									<td class="text-dark">未開通店家身份</td>
								</c:if>
								<c:if test="${memVO.mem_shop_status==1}">
									<td class="text-dark">已開通店家身份</td>
								</c:if>
								<%-- <c:if test="${memVO.mem_shop_status==2}">
									<td class="text-dark">申請開店中</td>
								</c:if>
								<c:if test="${memVO.mem_shop_status==3}">
									<td class="text-dark">商店上架</td>
								</c:if>
								<c:if test="${memVO.mem_shop_status==4}">
									<td class="text-dark">商店下架</td>
								</c:if> --%>
								<c:if test="${memVO.mem_role==20}">
									<td class="text-dark">週邊店家</td>
								</c:if>
								<c:if test="${memVO.mem_role==30}">
									<td class="text-dark">場地店家</td>
								</c:if>
								<c:if test="${memVO.mem_role==40}">
									<td class="text-dark">婚攝店家</td>
								</c:if>
								<td><button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#staticBackdrop" data-memid="${memVO.mem_id}">編輯</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<%-- 分頁 --%>
			<ul class="pagination">
				<li
					class="page-item <c:if test="${pageVO.whichPage==1}">disabled</c:if>">
					<a class="page-link"
					href="<%=request.getContextPath() %>/member/memberServlet?action=sellerMember&whichPage=${pageVO.whichPage-1}&rowsPerPage=5&find_username=${condition.find_username[0]}&
					find_name=${condition.find_name[0]}&find_status=${condition.find_status[0]}&find_shop_status=${condition.find_shop_status[0]}&mem_role=${condition.mem_role[0]}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a>
				</li>
				<c:forEach var="i" begin="1" end="${pageVO.pageNumber}">
					<li
						class="page-item <c:if test="${pageVO.whichPage==i}">active</c:if>">
						<a class="page-link"
						href="<%=request.getContextPath() %>/member/memberServlet?action=sellerMember&whichPage=${i}&rowsPerPage=5&find_username=${condition.find_username[0]}&
						find_name=${condition.find_name[0]}&find_status=${condition.find_status[0]}&find_shop_status=${condition.find_shop_status[0]}&mem_role=${condition.mem_role[0]}">${i}</a>
					</li>
				</c:forEach>
				<li
					class="page-item <c:if test="${pageVO.whichPage>=pageVO.pageNumber}">disabled</c:if>">
					<a class="page-link"
					href="<%=request.getContextPath() %>/member/memberServlet?action=sellerMember&whichPage=${pageVO.whichPage+1}&rowsPerPage=5&find_username=${condition.find_username[0]}&
					find_name=${condition.find_name[0]}&find_status=${condition.find_status[0]}&find_shop_status=${condition.find_shop_status[0]}&mem_role=${condition.mem_role[0]}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a>
				</li>
			</ul>
			<span>總共${pageVO.rowNumber}筆，</span> <span>共${pageVO.pageNumber}頁</span>


			<!-- 會員資料修改Modal -->
			<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static"
				data-bs-keyboard="false" tabindex="-1"
				aria-labelledby="staticBackdropLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="staticBackdropLabel">會員資料修改</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<form action="" id="editForm" class="editForm">
							<div class="modal-body">
								<label for="">會員ID</label><br>
								<input type="text" id="mem_id" name="mem_id" class="form-control" disabled>
								<label for="">會員帳號</label><br>
								<input type="text" id="mem_username" name="mem_username" class="form-control" disabled>
								<label for="">會員名稱</label><br> 
								<input type="text" id="mem_name" name="mem_name" class="form-control">
								<label for="">會員手機</label><br>
								<input type="text" id="mem_phone" name="mem_phone" class="form-control">
								<label for="">會員狀態</label><br>
								<label for="status0">Email未驗證</label>
								<input type="radio" name="mem_status" value="0" id="status0">
								<label for="status1">Email已驗證</label>
								<input type="radio" name="mem_status" value="1" id="status1">
								<label for="status2">停權</label>
								<input type="radio" name="mem_status" value="2" id="status2"><br>
								<label for="" style="margin-top: 5px">店家狀態</label><br>
								<label for="shop_status0">未開通店家身份</label>
								<input type="radio" name="mem_shop_status" value="0" id="shop_status0">
								<label for="shop_status1">已開通店家身份</label>
								<input type="radio" name="mem_shop_status" value="1" id="shop_status1">
								<%-- <label for="shop_status2">申請開店中</label>
								<input type="radio" name="mem_shop_status" value="2" id="shop_status2">
								<label for="shop_status3">商店上架</label>
								<input type="radio" name="mem_shop_status" value="3" id="shop_status3">
								<label for="shop_status4">商店下架</label>
								<input type="radio" name="mem_shop_status" value="4" id="shop_status4"> --%>
								<br>
								<label for="" style="margin-top: 5px">會員身份</label><br>
								<label for="mem_role20">週邊店家</label>
								<input type="radio" name="mem_role" value="20" id="mem_role20">
								<label for="mem_role30">場地店家</label>
								<input type="radio" name="mem_role" value="30" id="mem_role30">
								<label for="mem_role40">婚攝店家</label>
								<input type="radio" name="mem_role" value="40" id="mem_role40">


							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
								<button type="button" class="btn btn-primary" id="formSubmit">送出修改</button>
							</div>
						</form>
					</div>
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
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
	<script>
		//取得會員個人資料
		$('#accountTable').on('click','button',function(e){
			let memdId = e.target.dataset.memid;
			$('input[name=mem_status]:checked').prop('checked',false);
            $.ajax({
                type: "post",
                url: "<%=request.getContextPath()%>/member/memberServlet",
                data: {
					"action":"buyMemberGetOne",
					"mem_id":memdId
					},
                dataType: "json",
                success: function (response) {
					console.log(response);
					$('#mem_id').val(response.mem_id);
					$('#mem_username').val(response.mem_username);
					$('#mem_name').val(response.mem_name);
					$('#mem_phone').val(response.mem_phone);
					let mem_status = response.mem_status;
					let mem_shop_status = response.mem_shop_status;
					let mem_role = response.mem_role;
					$('input[name=mem_status]').eq(mem_status).prop('checked',true);
					$('input[name=mem_shop_status]').eq(mem_shop_status).prop('checked',true);
					$('input[name=mem_role]').eq((mem_role/10)-2).prop('checked',true);
                }
            });
		});
		
		// 送出編輯會員個人資料表單
		$('#formSubmit').on('click',function(e){
			let mem_id = $('#mem_id').val();
			let mem_username = $('#mem_username').val();
			let mem_name = $('#mem_name').val();
			let mem_phone = $('#mem_phone').val();
			let mem_status = $('input[name=mem_status]:checked').val();
			let mem_shop_status = $('input[name=mem_shop_status]:checked').val();
			let mem_role = $('input[name=mem_role]:checked').val();

			
			$.ajax({
				type: "post",
				url:  "<%=request.getContextPath()%>/member/memberServlet",
				data: {
					"action":"sellerMemberUpdate",
					"mem_id":mem_id,
					"mem_username":mem_username,
					"mem_name":mem_name,
					"mem_phone":mem_phone,
					"mem_status":mem_status,
					"mem_shop_status":mem_shop_status,
					"mem_role":mem_role
				},
				success: function (response) {
					 swal("恭喜您!", "已修改成功", "success").then((result) => {
                        window.location.reload();
                    });
				},
				 error:function(xhr){
					alert(xhr.responseText);
				}
			});
			e.preventDefault();
		})

		$('#find_status${condition.find_status[0]}').prop('selected',true)
		$('#find_shop_status${condition.find_shop_status[0]}').prop('selected',true)
		$('#find_mem_role${condition.mem_role[0]}').prop('selected',true)

	</script>
</body>
</html>