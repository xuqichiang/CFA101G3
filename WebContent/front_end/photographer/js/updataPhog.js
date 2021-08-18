$(function(){
    //動態選擇攝影師
    $.ajax({
        type:"get",
        url:"../../photographer/phogServlet",
        dataType:"json",
        success:function(phog){
            console.log(phog);
            for(let i=0; i<phog.length; i++){
                let element = 
                `<option value="${phog[i].phog_id}">${phog[i].phog_name}</option>`;
                $("#phog-id").append(element);
            }
        }
    });
});
//找到攝影師ID
$("#phog-id").on("change", function(e){
    let phog_id = $(e.target).val();
    //更新攝影狀態
    $.ajax({
        type:"get",
        url:"../../photographer/phogGetOneSetvlet",
        dataType:"json",
        data:{
            "phog_id":phog_id,
        },
        success:function(phog){
            let status = phog.phog_status;
            if(status == 1){
                $("#phog_status_1").prop('checked', true);
            }else{
                $("#phog_status_0").prop('checked', true);
            }
        }
    });
});
//用name取到攝影師狀態的value
$("#phog-status-id").on("change","input[name=phog_status]:checked",function(e){
    let phog_status = e.target.value;
    //送回後端做更新
    $("#confirm").on("click", function(){
        let phog_id = $("#phog-id").val();
        $.ajax({
            type: "post",
            url: "../../photographer/phogUpdateServlet",
            dataType: "json",
            data: {
                "phog_id": phog_id,
                "phog_status": phog_status
            },
            success: function (phog) {
                alert("更新成功");
                window.location.href="listAllPhotographer.html";//新增成功直接到查詢頁面
            },
            error: function (phog) {
                alert("更新失敗");
            }
        });
    });
});
//選單
$(document).ready(function(){
    $(".demo2").on("click",function(){
        $("#test").slideToggle("slow");
    });
});
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