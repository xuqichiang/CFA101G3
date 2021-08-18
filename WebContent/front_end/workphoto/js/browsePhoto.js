let urlParams = new URLSearchParams(window.location.search);
let param = urlParams.get("wor_id");
//console.log(param);
$.ajax({
    type: "post",
    url: "../../workphoto/worGetOneServlet?action=getOne",
    data: { "wor_id": param },
    dataType: "json",
    success: function(result) {
        console.log(result);
        //麵包屑
        let name =
            `<li class="breadcrumb-item active" aria-current="page">${result.wor_name}</li>`;
        $("#name-id").append(name);
        //作品集資料
        let element =
            `<h1>${result.mem_shop_name}｜${result.wor_name}</h1>
                <p class="phog-name"><span>${result.phog_name}</span></p>`;
        $("#wor-content-id").prepend(element);
    }
});
$.ajax({
    type: "get",
    url: "../../weddingphoto/wedServlet",
    dataType: "json",
    data: {
        "wed_wor_id": param
    },
    success: function(images) {
        // console.log(images);
        $("#wor-amount-id").append(`<span>共${images.length}張</span>`);
        for (let i = 0; i < images.length; i++) {
            // console.log(images[i].wed_images);
            let template = `<a class="wed-images swipebox" id="wed-images-id" href="../../weddingphoto/wedPhotoServlet?wed_id=${images[i].wed_id}"><img src="../../weddingphoto/wedPhotoServlet?wed_id=${images[i].wed_id}">`;
            $("#photo-content-id").append(template).justifiedGallery({
                lastRow: "nojustify", //不對齊最後一行
                rowHeight: 200,
                margins: 7,
                randomize: true, //開啟自動隨機照片的順序
            }).on("jg.complete", function() {
                $("#wed-images-id").swipebox(); //放大圖片
            });
        }

    }
});
//店家資訊
$.ajax({
    type: "get",
    url: "../../workphoto/worShopInfoServlet",
    dataType: "json",
    data: {
        "wor_id": param
    },
    success: function(shopInfo) {
        // console.log(shopInfo);
        let element =
            `<div class="mem-logo"><img src="data:image/*;base64,${shopInfo.MEM_SHOP_LOGO}"></div>
        <ul class="mem-wed" id="mem-wed-id">
            <li class="mem-shop">${shopInfo.MEM_SHOP_NAME}</li>
            <li class="mem-map"><img src="https://img.icons8.com/ios-glyphs/30/000000/map-pin.png"/>${shopInfo.MEM_CITY}</li>
        </ul>`;
        $("#mem-information-id").append(element);
        //我要預約
        $("#reserve").on("click", function() {
            if (chat_mem_id != undefined) {
                location.href = `../photoorder/reserveWeddimg.html?mem_id=${shopInfo.MEM_ID}`;
            } else {
                alert("請先登入後在預約");
                window.location.href = "../member/login.html";
            }
        });
        let mem_id = shopInfo.MEM_ID;
        document.getElementById('talk').dataset.mem_id = mem_id; //我要聊聊
        //店家方案總數量
        $.ajax({
            type: "get",
            url: "../../photoprogram/phopOneSmemIdServlet",
            data: {
                "phop_smem_id": mem_id
            },
            dataType: "json",
            success: function(phop) {
                if (phop.phop_status != 0) {
                    let element =
                        `<li class="wor-phop" id="wor-phop-id">
                        <span>方案(${phop.length})</span>
                    </li>`;
                    $("#mem-wed-id").append(element);
                }
            }
        });
        //店家作品集總數量
        $.ajax({
            type: "get",
            url: "../../photographer/phogGetWork",
            data: {
                "phog_smem_id": mem_id
            },
            dataType: "json",
            success: function(phog) {
                // console.log(phog);
                if (phog.phog_status != 0) {
                    let element =
                        `<span>作品(${phog.length})</span>`;
                    $("#wor-phop-id").append(element);
                }
            }
        });
    }
});