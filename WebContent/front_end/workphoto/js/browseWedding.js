let urlParams = new URLSearchParams(window.location.search);
let param = urlParams.get("mem_shop_name");
$.ajax({
    type: "get",
    url: "../../workphoto/worSearchServlet",
    dataType: "json",//接收到回應->json格式
    data: {
        "mem_shop_name": param
    },
    success: function (result) {
        console.log(result);
        let sun = 0;
        for (let i = 0; i < result.length; i++) {
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
                sun++;
            }
        }
        $("#wor-amount-id").append(`<p>共${sun}本作品</p>`);
    }
});
//圖片：data:image/*;base64,${}，base64寫法
//搜尋綁定a標籤
$("#search-id").on("change", function () {
    let control = $("#control").val();
    $("#workname").prop("href", `browseWedding.html?mem_shop_name=${control}`);
});
