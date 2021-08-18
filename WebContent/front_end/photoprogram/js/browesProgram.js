let urlParams = new URLSearchParams(window.location.search);
let param = urlParams.get("phop_name");
$.ajax({
    type: "GET",
    url: "../../photoprogram/phopSearchServlet",
    dataType: "json",
    data:{
        "action":"phopName",
        "phop_name":param
    },
    success: function (result) {
        // console.log(result);
        let sun = 0;
        for (let i = 0; i <result.length; i++) {
            //下架狀態不顯示
            if(result[i].PHOP_STATUS!=0){
                let element = 
                `<ul class="program">
                    <li class="program-images"><a href="programContent.html?phop_id=${result[i].PHOP_ID}"><img src="../../photoprogramimages/phopImgServlet?phopi_id=${getImg(result[i].PHOP_ID)}"></a></li>
                    <li class="program-content">
                        <a href="programContent.html?phop_id=${result[i].PHOP_ID}">
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
                $("#content-id").append(element);
                sun++;
            }
        }$("#phop-amount-id").append(`<p>共${sun}筆方案</p>`);
    }
});
//搜尋類別
$.ajax({
    type: "get",
    url: "../../photocategory/phocServlet",
    dataType: "json",
    success: function (phoc) {
        // console.log(phoc);
        for(let i=0; i<phoc.length; i++){
            let element = `<option value="${phoc[i].phoc_id}">${phoc[i].phoc_name}</option>`;
            $("#category-id").append(element);
        }
        $("#category-id").on("change",function(e){
            $("#content-id").empty();//清除
            $("#phop-amount-id").empty();
            let category = $(e.target).val();
                $.ajax({
                type: "get",
                url: "../../photoprogram/phopSearchServlet",
                data: {
                    "action":"searchCategory",
                    "phop_phoc_id":category
                },
                dataType: "json",
                success: function (result) {
                    let sun = 0;
                    for (let i = 0; i <result.length; i++) {
                        //下架狀態不顯示
                        if(result[i].PHOP_STATUS!=0){
                            let element = 
                            `<ul class="program">
                                <li class="program-images"><a href="programContent.html?phop_id=${result[i].PHOP_ID}"><img src="../../photoprogramimages/phopImgServlet?phopi_id=${getImg(result[i].PHOP_ID)}"></a></li>
                                <li class="program-content">
                                    <a href="programContent.html?phop_id=${result[i].PHOP_ID}">
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
                            $("#content-id").append(element);
                            sun++;
                        }
                    }$("#phop-amount-id").append(`<p>搜尋到${sun}筆方案</p>`);
                }
            });
        });
    }
});
$("#search-btn").on("click",function(){
    if(checkPhop_price()){
        let city = $(".city").val();
        let phop_price_1 = $("#phop_price_1").val();
        let phop_price_2 = $("#phop_price_2").val();
        let category = $("#category-id").val();
        $.ajax({
            type: "get",
            url: "../../photoprogram/phopSearchServlet",
            data: {
                "action":"searchList",
                "city":city,
                "phop_price_1":phop_price_1,
                "phop_price_2":phop_price_2,
                "phop_phoc_id":category
            },
            dataType: "json",
            success: function (result) {
                console.log(result);
                $("#content-id").empty();//清除
                $("#phop-amount-id").empty();
                let sun = 0;
                for (let i = 0; i <result.length; i++) {
                    //下架狀態不顯示
                    if(result[i].PHOP_STATUS!=0){
                        let element = 
                        `<ul class="program">
                            <li class="program-images"><a href="programContent.html?phop_id=${result[i].PHOP_ID}"><img src="../../photoprogramimages/phopImgServlet?phopi_id=${getImg(result[i].PHOP_ID)}"></a></li>
                            <li class="program-content">
                                <a href="programContent.html?phop_id=${result[i].PHOP_ID}">
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
                        $("#content-id").append(element);
                        sun++;
                    }
                }$("#phop-amount-id").append(`<p>搜尋到${sun}筆方案</p>`);
            }
        });
    } else {
        alert("價格格式錯誤");
    }
});
//獲取圖片方法
function getImg(PHOP_ID){//方案ID
    let phopi_id = 0;
    $.ajax({
        type: "post",
        url: "../../photoprogramimages/phopFKservlet",//取得方案圖片FK
        data: {"phop_id":PHOP_ID},
        dataType: "json",
        async:false,
        success: function (phopImg) {
            phopi_id=phopImg[0].phopi_id;//取得FK ID第一張片作為封面圖
        }
    });
    return phopi_id;
}
//搜尋綁定a標籤
$("#search-id").on("change", function () {
    let control = $("#control").val();
    $("#phopname").prop("href", `browseProgram.html?phop_name=${control}`);
});
//地區設定
$("#twzipcode").twzipcode({
    css: ['form-control city', 'addr-area', 'addr-zip']
});
//驗證價格格式是否正確
function checkPhop_price() {
    if ($("#phop_price_1").val() > 0 && $("#phop_price_2").val() > 0) {
        return true;
    } else {
        return false;
    }
}