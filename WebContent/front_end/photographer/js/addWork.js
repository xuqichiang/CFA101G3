$(function () {
    //動態選擇攝影師
    $.ajax({
        type: "get",
        url: "../../photographer/phogServlet",
        dataType: "json",
        success: function (phog) {
            console.log(phog);
            for (let i = 0; i < phog.length; i++) {
                let element =
                `<option value="${phog[i].phog_id}">${phog[i].phog_name}</option>`;
                $("#phog_id").append(element);
            }
        }
    })
});
//瀏覽一張圖片
$("#file_logo").on("change", function () {
    if (this.files && this.files[0]) {
        var reader = new FileReader(); //創建檔案讀取物件  //e事件的物件
        reader.onload = function (e) {
            let imgData = e.target.result;//獲取檔案讀取後的資料
            $("#wor-img-id").prop("src", imgData);//改變屬性值
        }
        reader.readAsDataURL(this.files[0]);
        imgOne_flag = true;
    }checkFlag();
});
//瀏覽多張圖片
$("#file_img").on("change", function () {
    if(this.files && this.files[0]){//判斷是否有檔案存在
        for(let i=0; i<this.files.length; i++){
            let reader = new FileReader();//讀取檔案
            //console.log(reader);
            reader.onload = function(e){
                let imgData = e.target.result;
                let element = $("<img>").prop("src",imgData);
                $("#wed-img-id").append(element);
            }
           reader.readAsDataURL(this.files[i]); 
        }imgs_flag = true;
    }checkFlag();
});
//上傳圖片的ajax
$("#imgform").on("submit", function (e) {
    $.ajax({
        url:"../../workphoto/worAddServlet",
        type:"post",
        data: new FormData(this),
        processData: false,
        contentType: false,
        success: function (data) {
            if (data == "1") {
                alert("新增成功");
                location.reload(false);
            } else {
                alert("新增失敗");
            }
        },
        error: function (data) {
            alert("新增失敗，請重新上傳");
        },statusCode:{
            401: function () {
                alert("尚未選圖片，請選圖片");
            }
        },
    });
    //取消form表單的跳轉畫面
    e.preventDefault();
});
//設置旗幟
let name_flag = false;//攝影師
let imgOne_flag = false;//單圖片
let imgs_flag = false;//多圖片
//確認旗幟均為true按鈕才能按
function checkFlag(){
     if(name_flag && imgOne_flag && imgs_flag){
        $("#confirm").removeAttr("disabled"); 
     }else{
        $("#confirm").prop("disabled", "disabled");
     }
 };
 //監聽作品集格式是否正確
$("#form-name").on("input","#wor_name", function(){
    $("#name-prompt").text("");
    if(validateworkname()){
        $(".form-control:focus").css("border-color","#28a745");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        name_flag = true;
    }else{
        $(".form-control:focus").css("border-color","#ff5549");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#name-prompt").text("作品集名稱不得為空");
        $("#name-prompt").css("color","red");
        $("#name-prompt").css("font-size","6px");
        $("#name-prompt").css("margin-top","5px");
        name_flag = false;
    }
    checkFlag();
});
//作品名稱正則表達式
function validateworkname(){
    let workname = $("#wor_name").val();
    const re = /^[(\u4e00-\u9fa5)(A-za-z)]/;
    return re.test(workname);
}
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