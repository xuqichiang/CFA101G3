$.ajax({
    type:"get",
    url:"../../photoprogram/listAllPhotoProgram",
    dataType:"json",
    success:function (phop) {
        console.log(phop);
        let list =
        `<tr>
            <th scope="col">名稱</th>
            <th scope="col">開始時間</th>
            <th scope="col">結束時間</th>
            <th scope="col">狀態</th>
            <th scope="col">詳細資訊</th>
        </tr>`;
        $("#table-phop-id").html(list);
        for (let i=0; i<phop.length; i++) {
            let status = phop[i].phop_status;
            let program="";
            if(status==1){
                program="上架";
            }else{
                program="下架";
            }
            let element =
            `<tr>
                <td>${phop[i].phop_name}</td>
                <td>${phop[i].phop_start_time}</td>
                <td>${phop[i].phop_end_time}</td>
                <td>${program}</td>
                <td ><a href="listAllPhotoProgram.html?phop_id=${phop[i].phop_id}">查看</a></td>
            </tr>`;
            $("#table-phop-id").append(element);   
        }
    }
});
//點擊a標籤後,動態產生該方案的所有資訊
let urlParams = new URLSearchParams(window.location.search);
let param = urlParams.get("phop_id");
console.log(param);
    if(param!=null){
        $.ajax({
            type:"get",
            url:"../../photoprogram/phopFindByPhopId",
            dataType:"json",
            data:{
                "phop_id":param
            },
            success:function(phop){
                console.log(phop);
                //狀態判斷
                let status = phop.phop_status;
                let program="";
                if(status==1){
                    program="上架";
                }else{
                    program="下架";
                }
                //類別判斷
                let categore = phop.phop_phoc_id;
                let sort = "";
                switch(categore){
                    case 1:
                    sort = "美式簡約風";
                    break;
                    case 2:
                    sort = "韓式唯美風";
                    break;
                    case 3:
                    sort = "中式復古風";
                    break;
                    case 4:
                    sort = "經典大氣風";
                    break;
                    case 5:
                    sort = "青春活潑風";
                    break;
                }
                //方案內容換行處理
                let content = phop.phop_content;
                for(let i=0; i<content.length; i++){
                    if(content[i] == "\n"){
                        content = content.replace("\n","<br>");//替代
                    }
                }
                let element =
                `<h5>內容詳情</h5>
                <table class="table phop">
                    <tr><th scope="row">方案名稱</th><td>${phop.phop_name}</td></tr>
                    <tr><th scope="row">方案類別</th><td>${sort}</td></tr>
                    <tr><th scope="row">開始時間</th><td>${phop.phop_start_time}</td></tr>
                    <tr><th scope="row">結束時間</th><td>${phop.phop_end_time}</td></tr>
                    <tr><th scope="row">方案金額</th><td>${phop.phop_price}</td></tr>
                    <tr><th scope="row">方案狀態</th><td>${program}</td></tr>
                    <tr><th scope="row">方案內容</th><td>${content}</td></tr>
                </table>`;
                $("#phop-show-id").append(element);   
            }
        });
    };
//跟資料庫請求個人頭像
Ajaxheadshot();
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
//請求個人資料
Ajaxprofile();
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