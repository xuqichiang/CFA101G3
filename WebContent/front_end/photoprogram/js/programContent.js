let urlParams = new URLSearchParams(window.location.search);
let phop_id = urlParams.get("phop_id");
$.ajax({
    url: "../../photoprogram/getOneContentServlet",
    type: "POST",
    data: { "phop_id": phop_id },
    dataType: "json",
    success: function(result) {
        // console.log(result);
        //對方案內容做換行處理
        let content = result.PHOP_CONTENT
        for (let i = 0; i < content.length; i++) {
            if (content[i] == "\n") {
                content = content.replace("\n", "<br>");
            }
        }
        //麵包屑
        let name =
            `<li class="breadcrumb-item active" aria-current="page">${result.PHOP_NAME}</li>`;
        $("#name-id").append(name);
        //店家資訊
        let template =
            `<div class="mem-logo"><img src="data:image/*;base64,${result.MEM_SHOP_LOGO}"></div>
                <ul class="mem-wed" id="mem-wed-id">
                    <li class="mem-shop">${result.MEM_SHOP_NAME}</li>
                    <li class="mem-map"><img src="https://img.icons8.com/ios-glyphs/30/000000/map-pin.png"/>${result.MEM_CITY}</li>
                </ul>`;
        $("#mem-information-id").append(template);
        //瀏覽方案資訊
        let element =
            `<h1>${result.PHOP_NAME}</h1>
                <p class="phop-price"><span>NT</span>${result.PHOP_PRICE}</p>`;
        $("#phop-name-id").prepend(element);
        //瀏覽方案內容
        let html =
            `<div class="phop-rigth" id="phop-rigth-id">${content}</div>`;
        $("#phop-content-id").append(html);
        //我要預約
        $("#reserve").on("click", function() {
            if(chat_mem_id != undefined){
                location.href = `../photoorder/reserveWeddimg.html?mem_id=${result.MEM_ID}`;
            }else{
                alert("請先登入後在預約");
                window.location.href="../member/login.html";
            }
        });
        let mem_id = result.MEM_ID;
        document.getElementById('talk').dataset.mem_id = mem_id; //我要聊聊

        // console.log(mem_id);
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
})
$.ajax({
    url: "../../photoprogramimages/phopFKservlet",
    type: "post",
    data: { "phop_id": phop_id },
    dataType: "json",
    success: function(result) {
        // console.log(result);
        for (let i = 0; i < result.length; i++) {
            let element =
                `<img class="phop-img" src="../../photoprogramimages/phopImgServlet?phopi_id=${result[i].phopi_id}">`;
            $("#container-id").append(element);
        }
    }
})