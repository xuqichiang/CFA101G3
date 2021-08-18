<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.sql.DataSource"%>
<%@ page import="org.springframework.dao.DataAccessException"%>
<%@ page import="org.springframework.jdbc.core.BeanPropertyRowMapper"%>
<%@ page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.product_images.model.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.workphoto.model.*"%>
<%@ page import="com.weddingphoto.model.*"%>
<%@ page import="com.locationroom.model.*"%>
<%@ page import="com.locationimages.model.*"%>
<%@ page import="com.post.model.*"%>
<%@ page import="com.category.model.*"%>
<%@ page import="com.message.model.*"%>
<%@ page import="com.member.model.*"%>

<%
DataSource ds = null;
try {
	Context ctx = new InitialContext();
	ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
} catch (NamingException e) {
	e.printStackTrace();
}
JdbcTemplate template = new JdbcTemplate(ds);
String prdSql = "select * from PRODUCT order by pro_id desc limit 0,6";//獲取最新商品
String worSql = "select * from WORK_PHOTO order by wor_id desc limit 0,6";//獲取最新婚紗作品
String locSql = "select * from LOCATION_ROOM order by locr_id desc limit 0,6";//獲取最新婚禮場地
String locMemSql = "select * from `MEMBER` where mem_role = 30 order by mem_id desc limit 0,6";//獲取場地賣家
String postSql = "select * from POST order by post_id desc limit 0,6";//獲取最新文章
String mesSql = "select * from MESSAGE";//取得所有留言

List<ProVO> proList = template.query(prdSql, new BeanPropertyRowMapper(ProVO.class));
List<WorVO> worList = template.query(worSql, new BeanPropertyRowMapper(WorVO.class));
List<LocrVO> locrList = template.query(locSql, new BeanPropertyRowMapper(LocrVO.class));
List<MemVO> locMemList = template.query(locMemSql, new BeanPropertyRowMapper(MemVO.class));
List<PostVO> postList = template.query(postSql, new BeanPropertyRowMapper(PostVO.class));


pageContext.setAttribute("proList", proList);//將商品list存入scope
pageContext.setAttribute("worList", worList);//將婚紗作品list存入scope
pageContext.setAttribute("locrList", locrList);//將場地廳房list存入scope
pageContext.setAttribute("locMemList", locMemList);//將場地賣家list存入scope
pageContext.setAttribute("postList",postList);//將文章list存入scope


//將商品各取一張照片存入list
ProImgService proImgSvc = new ProImgService();
List<ProImgVO> proImgList = new ArrayList<ProImgVO>();
for (ProVO proVO : proList) {
	 List<ProImgVO> proImgListByFk = proImgSvc.findByForeignKey(proVO.getPro_id());
		if(proImgListByFk.size() != 0){
			ProImgVO proImgVO = proImgListByFk.get(0);
			proImgList.add(proImgVO);
		}
}
pageContext.setAttribute("proImgList", proImgList);//將商品照片存入scope

//將婚紗作品集各取一張照片存入list
WedService wedSvc = new WedService();
List<WedVO> wedImgList = new ArrayList<WedVO>();
for (WorVO worVO : worList) {
	List<WedVO> wedImgListByFK = wedSvc.findByForeignKey(worVO.getWor_id());
	if(wedImgListByFK.size() != 0){
		WedVO wedVO = wedImgListByFK.get(0);
		wedImgList.add(wedVO);
	}
}
pageContext.setAttribute("wedImgList", wedImgList);//將婚紗照片存入scope

//將場地廳房各取一張照片存入list
LociService lociSvc = new LociService();
List<LociVO> lociImgList = new ArrayList<LociVO>();
for (LocrVO locrVO : locrList) {
	List<LociVO> lociImgListByFK = lociSvc.findByForeignKey(locrVO.getLOCR_ID());
	if(lociImgListByFK.size() != 0){
		LociVO lociVO = lociImgListByFK.get(0);
		lociImgList.add(lociVO);
	}
}
pageContext.setAttribute("lociImgList", lociImgList);//將廳房照片存入scope

//取得文章分類list
CategoryService catSvc = new CategoryService();
List<CategoryVO> catList = catSvc.getAll();
pageContext.setAttribute("catList",catList);

//計算文章留言數
MessageService mesSvc = new MessageService();
HashMap<Integer,Integer> mesCountMap = new HashMap<Integer,Integer>();
for(PostVO postVO : postList){
	Integer count = mesSvc.getBy_mes_post_id(postVO.getPost_id()).size();
	Integer post_id = postVO.getPost_id();
	mesCountMap.put(post_id,count);
}
pageContext.setAttribute("mesCountMap",mesCountMap);
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="images/logo-icon01.ico" type="image/x-icon" />
<!-- boostrap -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<!-- JQ -->
<script src="https://code.jquery.com/jquery-3.6.0.js"
	integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	crossorigin="anonymous"></script>
<!-- SWEET -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<!-- 購物車 -->
<script src="../product/js/newCart.js"></script>
<!-- SWiper -->
<link rel="stylesheet"
	href="https://unpkg.com/swiper/swiper-bundle.min.css">
<!-- CSS -->
<link rel="stylesheet" href="css/all.css">
<link rel="stylesheet" href="css/cssreset.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="../product/css/newCart.css">
<link rel="stylesheet" href="css/index.css">
<link rel="stylesheet" href="css/friendchat.css">
    <link rel="stylesheet" href="css/footer.css">
<title>嫁給幸福｜MarryHappiness</title>
</head>
<style>
.swiper-container {
	width: 100%;
	height: 100%;
	padding: 20px;
	z-index: 0;
}

.swiper-slide {
	text-align: center;
	font-size: 18px;
	display: flex;
	justify-content: center;
	align-items: center;
}

.swiper-container img {
	width: 1050px;
	border-radius: 10px;
	margin-bottom: 5px;
	margin-top: 5px;
	box-shadow: 0 10px 15px -3px rgb(115 82 78/ 20%), 0 4px 6px -2px
		rgb(115 82 78/ 10%);
}

.swiper-pagination-bullet-active {
	background: #fc9086;
}
</style>

<body>
    <!--大頭-->
    <header>
        <div class="top">
            <ul>
                <!-- 購物車圖片Icon -->

                <li><a href="../index/index.jsp">首頁</a></li>
                <li><a href="../member/login.html"><i class="fas fa-sign-out-alt"></i>登入</a></li>
				<li><a href="../../member/loginServlet?action=logout"><i class="fas fa-sign-out-alt"></i>登出</a></li>
                <li><a href="../../member/checkServlet"><i class="fas fa-home"></i> 會員系統</a></li>
                <li class="headcart">
                    <a href="javascript:void(0)" id="cartModal">
                        <i class="fas fa-shopping-cart" id="cartIcon"></i></a>
                </li>

            </ul>
        </div>


        <!--上面增加的小色塊-->
        <div class="nav1"></div>
        <ul class="nav2">
            <li><a href="../workphoto/browseHome.html">婚禮攝影<br>Photography
				</a></li>
            <li><a href="../locationprogram/LocIndex.html">婚禮場地<br>Location
				</a></li>
            <li>
                <a href="../index/index.jsp" class="logo"><img src="images/MHlogo_01.svg"></a>
            </li>
            <li><a href="../product/ProductMain.html">婚禮週邊<br>Product
				</a></li>
            <li><a href="../post/forumindex.html">專欄討論<br>Post
				</a></li>
        </ul>
    </header>

	<!-- Swiper -->
	<div class="swiper-container">
		<div class="swiper-wrapper">
			<div class="swiper-slide">
				<img src="images/sw1.jpeg" alt="">
			</div>
			<div class="swiper-slide">
				<img src="images/sw2.jpeg" alt="">
			</div>
			<div class="swiper-slide">
				<img src="images/sw3.jpeg" alt="">
			</div>
			<div class="swiper-slide">
				<img src="images/sw4.jpeg" alt="">
			</div>
		</div>
		<!-- Add Pagination -->
		<div class="swiper-pagination"></div>
	</div>

	<!-- 主頁面 -->
	<div class="container">
		<div class="row">
			<div class="col-6">
				<div class="post">
					<div class="post-heading">
						<div>
							<h2>專欄討論區</h2>
							<p>新娘聊心事的好地方</p>
						</div>
						<a href="<%=request.getContextPath()%>/front_end/post/forumindex.html">
							<div class="post-more">看更多討論</div>
						</a>
					</div>
					<div class="post-content">
						<c:forEach var="postVO" items="${postList}">
							<div class="post-box d-flex">
								<img src="<%=request.getContextPath()%>/member/memImgServlet?action=headShot&mem_id=${postVO.post_mem_id}" alt=""> 
								<a href="<%=request.getContextPath()%>/front_end/post/show-article-message.html?post_id=${postVO.post_id}">
									<div class="post-article">
										<div class="post-tag-box">
											<div class="post-tag c${postVO.post_cat_id}">
												<c:forEach var="catVO" items="${catList}">
													<c:if test="${catVO.cat_id==postVO.post_cat_id}">${catVO.cat_name}</c:if>
												</c:forEach>
											</div>
										</div>
										<div class="post-chat">
											<p>${postVO.post_title}</p>
											<div class="post-article-reply">
												<i class="far fa-comment-dots"></i> <span>${mesCountMap[postVO.post_id]}個回覆</span>
											</div>
										</div>
									</div>
								</a>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="col-6">
				<div class="product">
					<div class="product-heading">
						<div>
							<h2>婚禮週邊</h2>
							<p>新娘最愛的婚禮小物</p>
						</div>
						<a href="<%=request.getContextPath()%>/front_end/product/ProductMain.html">
							<div class="product-more">看更多小物</div>
						</a>
					</div>
					<div class="product-content">
						<div class="row">
							<c:forEach var="proVO" items="${proList}" begin="0" end="1">
								<div class="pro-box col">
									<a href="<%=request.getContextPath()%>/front_end/product/ProductSingle.html?pro_id=${proVO.pro_id}"> <c:forEach var="proImgVO" items="${proImgList}">
											<c:if test="${proImgVO.proi_pro_id==proVO.pro_id}">
												<img
													src="<%=request.getContextPath()%>/product/ProImgOutServlet?proi_id=${proImgVO.proi_id}">
											</c:if>
										</c:forEach>
										<h3>${proVO.pro_name}</h3>
									</a>
								</div>
							</c:forEach>
						</div>
						<div class="row">
							<c:forEach var="proVO" items="${proList}" begin="2" end="3">
								<div class="pro-box col">
									<a href="<%=request.getContextPath()%>/front_end/product/ProductSingle.html?pro_id=${proVO.pro_id}"> <c:forEach var="proImgVO" items="${proImgList}">
											<c:if test="${proImgVO.proi_pro_id==proVO.pro_id}">
												<img
													src="<%=request.getContextPath()%>/product/ProImgOutServlet?proi_id=${proImgVO.proi_id}">
											</c:if>
										</c:forEach>
										<h3>${proVO.pro_name}</h3>
									</a>
								</div>
							</c:forEach>
						</div>
						<div class="row">
							<c:forEach var="proVO" items="${proList}" begin="4" end="5">
								<div class="pro-box col">
									<a href="<%=request.getContextPath()%>/front_end/product/ProductSingle.html?pro_id=${proVO.pro_id}"> <c:forEach var="proImgVO" items="${proImgList}">
											<c:if test="${proImgVO.proi_pro_id==proVO.pro_id}">
												<img
													src="<%=request.getContextPath()%>/product/ProImgOutServlet?proi_id=${proImgVO.proi_id}">
											</c:if>
										</c:forEach>
										<h3>${proVO.pro_name}</h3>
									</a>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="wedding">
			<div class="wedding-heading">
				<div>
					<h2>拍婚紗</h2>
					<p>新娘最喜歡的婚紗照</p>
				</div>
				<a href="<%=request.getContextPath()%>/front_end/workphoto/browseWedding.html">
					<div class="wedding-more">看更多作品</div>
				</a>
			</div>
			<div class="wedding-content">
				<div class="row">
					<c:forEach var="worVO" items="${worList}" begin="0" end="2">
						<div class="wed-box col">
							<a href="<%=request.getContextPath()%>/front_end/workphoto/browsePhoto.html?wor_id=${worVO.wor_id}"> <c:forEach var="wedVO" items="${wedImgList}">
									<c:if test="${wedVO.wed_wor_id == worVO.wor_id}">
										<img
											src="<%=request.getContextPath()%>/weddingphoto/wedPhotoServlet?wed_id=${wedVO.wed_id}">
									</c:if>
								</c:forEach>
								<h3>${worVO.wor_name}</h3>
							</a>
						</div>
					</c:forEach>
				</div>
				<div class="row">
					<c:forEach var="worVO" items="${worList}" begin="3" end="5">
						<div class="wed-box col">
							<a href="<%=request.getContextPath()%>/front_end/workphoto/browsePhoto.html?wor_id=${worVO.wor_id}"> <c:forEach var="wedVO" items="${wedImgList}">
									<c:if test="${wedVO.wed_wor_id==worVO.wor_id }">
										<img
											src="<%=request.getContextPath()%>/weddingphoto/wedPhotoServlet?wed_id=${wedVO.wed_id}">
									</c:if>
								</c:forEach>
								<h3>${worVO.wor_name}</h3>
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="location">
			<div class="location-heading">
				<div>
					<h2>婚禮場地</h2>
					<p>新娘最想在這辦婚禮</p>
				</div>
				<a href="../locationprogram/LocIndex.html">
					<div class="location-more">看更多場地</div>
				</a>
			</div>
			<div class="location-content">
				<div class="row">
					<c:forEach var="memVO" items="${locMemList}" begin="0" end="2">
						<div class="loc-box col">
							<a href="<%=request.getContextPath()%>/front_end/locationprogram/LocSingle.html?memid=${memVO.mem_id}">
								<img src="<%=request.getContextPath()%>/member/memImgServlet?action=getBanner&mem_id=${memVO.mem_id}">
								<h3>${memVO.mem_shop_name}</h3>
							</a>
						</div>
					</c:forEach>
				</div>
				<div class="row">
					<c:forEach var="memVO" items="${locMemList}" begin="3" end="5">
						<div class="loc-box col">
							<a href="<%=request.getContextPath()%>/front_end/locationprogram/LocSingle.html?memid=${memVO.mem_id}">
								<img src="<%=request.getContextPath()%>/member/memImgServlet?action=getBanner&mem_id=${memVO.mem_id}">
								<h3>${memVO.mem_shop_name}</h3>
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
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
	<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
	<script src="js/friendchat.js"></script>

	<!-- Initialize Swiper -->
	<script>
		let swiper = new Swiper('.swiper-container', {
			spaceBetween : 30,
			pagination : {
				el : '.swiper-pagination',
				clickable : true,
			},
		autoplay: {
		     delay: 5000
		 }
		});
	</script>
</body>

</html>