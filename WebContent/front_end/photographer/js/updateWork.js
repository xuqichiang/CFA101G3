$(function(){
    //動態選擇攝影師
    $.ajax({
        type:"get",
        url:"../../photographer/phogServlet",
        dataType:"json",
        success:function(phog){
            for(let i=0; i<phog.length; i++){
                let element = 
                `<option value="${phog[i].phog_id}">${phog[i].phog_name}</option>`;
                $("#phog_id").append(element);
            }
        }
    });
    //動態取得作品
    $("#phog_id").on("change", function(e){
        let phog_id = $(e.target).val();
        $("#wor-logo-id").prop("src","");//清除封面，更變屬性
        $("#wed-imgs-id").empty();//清除作品圖片
        $.ajax({
            type:"get",
            url:"../../workphoto/worGetFKServlet",
            dataType:"json",
            data:{
                "phog_id":phog_id
            },
            success:function(work){
                console.log(work);
                $("#phog_work").empty();//執行前先刪除選單元素
                let element = 
                `<option value="">---請選擇---</option>`;
                $("#phog_work").prepend(element);
                for(let i=0; i<work.length; i++){//新增元素
                    let element = 
                    `<option value="${work[i].wor_id}">${work[i].wor_name}</option>`;
                    $("#phog_work").append(element);
                }
            }
        });
    });
});
//找到作品集ID
$("#phog_work").on("change", function(e){
    let wor_id = $(e.target).val();
    $("#wor-logo-id").prop("src","");//清除封面，更變屬性
    $("#wed-imgs-id").empty();//清除作品圖片
    //show出作品集圖片
    $.ajax({
        type:"post",
        url:"../../workphoto/worGetOneServlet",
        data:{
            "wor_id":wor_id
        },
        xhrFields:{
            //將圖片以blob格式回傳頁面
            responseType:"blob"
        },
        success:function(worLogo){
            let logo = document.getElementById("wor-logo-id");
            let url = URL.createObjectURL(worLogo);
            logo.src = url;
        }
    });
});
//修改作品集圖片
$("#phog_work").on("change", function(e){
    let wor_id = $(e.target).val();
    //show出作品集所有圖片
    $.ajax({
        type:"get",
        url:"../../weddingphoto/wedServlet",
        dataType:"json",
        data:{
            "wed_wor_id":wor_id
        },
        success:function(wedImg){
            for(let i=0; i<wedImg.length; i++){
                let element = 
                `<label class="col-sm wed-img" id="wed-img-id">
                    <img src="../../weddingphoto/wedPhotoServlet?wed_id=${wedImg[i].wed_id}" alt="">
                    <a class="delimg" href="javascript:void(0)" data-wedid="${wedImg[i].wed_id}"><i class="fas fa-trash-alt"></i></a>
                </label>`;
                $("#wed-imgs-id").append(element);
            }
        }
    });
});
//更變作品集封面圖片
$("#file_logo").on("change", function () {
    if (this.files && this.files[0]) {
        var reader = new FileReader(); //創建檔案讀取物件  //e事件的物件
        reader.onload = function (e) {
            let imgData = e.target.result;//獲取檔案讀取後的資料
            $("#wor-logo-id").prop("src", imgData);//改變屬性值
        }
        reader.readAsDataURL(this.files[0]);
    }
});
//瀏覽多張圖片
$("#file_img").on("change", function () {
    if(this.files && this.files[0]){//判斷是否有檔案存在
        for(let i=0; i<this.files.length; i++){
            let reader = new FileReader();//讀取檔案
            reader.onload = function(e){
                let imgData = e.target.result;
                let element = $("<img>").prop("src",imgData);
                $("#new-imgs-id").append(element);
            }
           reader.readAsDataURL(this.files[i]);
        }
    }
});
//上傳圖片的ajax，更新作品集圖片
$("#imgform").on("submit", function (e) {
    $.ajax({
        url:"../../workphoto/worUpdataServlet",
        type:"post",
        data: new FormData(this),
        processData: false,
        contentType: false,
        success: function (data) {
            if (data == "1") {
                alert("更新成功");
                location.reload(false);
            } else {
                alert("更新失敗");
            }
        },
        error: function (data) {
            alert("更新失敗，請重新檢查");
        }
    });
    //取消form表單的跳轉畫面
    e.preventDefault();
});
//刪除圖片
$("#wed-imgs-id").on("click",".delimg",function(e){
    //取得偽元素值
    let wed_id = e.currentTarget.dataset.wedid
    console.log(wed_id);
    let con = confirm("確定刪除嗎？");
    if(con){
        $(this).parent().remove();
    }
    $.ajax({
        type:"get",
        url:"../../weddingphoto/wedDeleteServlet",
        data:{
            "wed_id":wed_id
        }
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