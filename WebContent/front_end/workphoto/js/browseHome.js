//作品集TOP
$.ajax({
    type: "get",
    url: "../../workphoto/worServlet",
    dataType: "json",//接收到回應->json格式
    success: function (result) {
        console.log(result);
        for (let i = 0; i < 3; i++) {
            //判斷攝影師狀態
            if (result[i].phog_status != 0) {
                let element =
                    `<a href="browsePhoto.html?wor_id=${result[i].wor_id}" class="browse-wed-photo" id="browse-wed-photo-id">
                    <ul class="wor-name">
                        <li class="wed-images"><img src="../../workphoto/worGetOneServlet?wor_id=${result[i].wor_id}"></li>
                        <li class="phog-name">${result[i].phog_name}｜${result[i].wor_name}</li>
                        <li class="mem-shop-name">${result[i].mem_shop_name}</li>
                    </ul>
                </a>`;
                $("#web-content-id").append(element);
            }
        }
    }
});
//方案TOP
$.ajax({
    type: "GET",
    url: "../../photoprogram/phopGetImagesServlet",
    dataType: "json",
    success: function (result) {
        console.log(result);
        for (let i = 0; i < 3; i++) {
            //下架狀態不顯示
            if (result[i].PHOP_STATUS != 0) {
                let element =
                `<ul class="program">
                    <li class="program-images"><a href="../photoprogram/programContent.html?phop_id=${result[i].PHOP_ID}"><img src="../../photoprogramimages/phopImgServlet?phopi_id=${getImg(result[i].PHOP_ID)}"></a></li>
                    <li class="program-content">
                        <a href="../photoprogram/programContent.html?phop_id=${result[i].PHOP_ID}">
                            <p class="phop-name">${result[i].PHOP_NAME}</p>
                            <p class="phop-content">${result[i].PHOP_CONTENT}</p>
                            <p class="phop-price"><span>NT</span>${result[i].PHOP_PRICE}</p>
                        </a>
                    </li>
                    <li class="program-news">
                        <p class="phop-logo"><a href=""><img src="data:image/*;base64,${result[i].MEM_SHOP_LOGO}"></a></p>
                        <p class="phop-smem">
                            <a href="">${result[i].MEM_SHOP_NAME}</a>
                            <span><i class="fas fa-map-marker-alt"></i>${result[i].MEM_CITY}</span>
                        </p>
                    </li>
                </ul>`;
                $("#phop-content-id").append(element);
            }
        }
    }
});
//獲取圖片方法
function getImg(PHOP_ID) {//方案ID
    let phopi_id = 0;
    $.ajax({
        type: "post",
        url: "../../photoprogramimages/phopFKservlet",//取得方案圖片FK
        data: { "phop_id": PHOP_ID },
        dataType: "json",
        async: false,
        success: function (phopImg) {
            phopi_id = phopImg[0].phopi_id;//取得FK ID第一張片作為封面圖
        }
    });
    return phopi_id;
}
$(document).ready(function () {
    $('#carouselExampleIndicators').carousel({
        interval:5000//每隔秒自動輪播
    });
});