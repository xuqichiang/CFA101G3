//日期選擇器datetimepicker註冊
$.datetimepicker.setLocale("zh");
$(function(){
    $("#phop_start_time").datetimepicker({
        timepicker:false,
        format:"Y-m-d",
        value: new Date(), 
        minDate: "-1970-01-01",
        onShow:function(){
            this.setOptions({
                maxDate:$("#phop_end_time").val()?$("#phop_end_time").val():false
            })
        }
    });
    $("#phop_end_time").datetimepicker({
        timepicker:false,
        format:"Y-m-d",
        onShow:function(){
            this.setOptions({
                minDate:$("#phop_start_time").val()?$("#phop_start_time").val():false
            })
        }
     });
});
//新增方案
// $("#phop_phoc_id").on("change", function(e){
//     let phop_phoc_id = $(e.target).val();
//     console.log(phop_phoc_id);
//     $("#confirm-program").on("click", function() {
//         let phop_name = $("#phop_name").val();
//         let phop_price = $("#phop_price").val();
//         let phop_start_time = $("#phop_start_time").val();
//         let phop_end_time = $("#phop_end_time").val();
//         let phop_content = $("#phop_content").val();
//         let phop_status = $("input[name=phop_status]:checked").val();
//         console.log(phop_status);
//         $.ajax({
//             type:"post",
//             url:"../../photoprogram/phopAddServlet",
//             dataType:"json",
//             data:{
//                 "phop_name" : phop_name,
//                 "phop_price" :phop_price,
//                 "phop_phoc_id" : phop_phoc_id,
//                 "phop_start_time" : phop_start_time,
//                 "phop_end_time" : phop_end_time,
//                 "phop_content" : phop_content,
//                 "phop_status" :phop_status
//             },
//             success : function(phop) {
//                 alert("新增成功");
//             },
//             error: function(phop){
//                 alert("新增失敗");
//             },
//         });
//     });
// });
//新增方案及上傳圖片
$("#phopform").on("submit", function (e) {
    $.ajax({
        url:"../../photoprogram/phopAddServlet",
        type:"post",
        data: new FormData(this),
        processData: false,
        contentType: false,
        success: function (phopimg) {
            if (phopimg == "1") {
                alert("新增成功");
                location.reload(false);
            }else {
                alert("新增失敗");
            }
        },
        error: function (phopimg) {
            alert("新增失敗，請重新檢查");
        },
        statusCode:{
            401: function () {
                alert("尚未選圖片，請選圖片");
            }
        },
    });
    //取消form表單的跳轉畫面
    e.preventDefault();
});
//設置驗證旗幟
let name_flag = false;//方案名稱
let price_flag = false;//金額
let categore_flag = false;//方案類別
let start_flag = true;//開始時間
let end_flag = false;//結束時間
let content_flag = false;//方案內容
let img_flag = false;//圖片
//確認旗幟均為true按鈕才能按
function checkFlag(){
    if(name_flag && price_flag && categore_flag && start_flag && end_flag && content_flag && img_flag){
        $("#confirm-program").removeAttr("disabled");
    }else{
        $("#confirm-program").prop("disabled","disabled");
    }
}
//瀏覽多張圖片+是否有選擇圖片
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
        img_flag = true;
    }checkFlag();
});
//監聽方案名稱是否填寫正確
$("#form-name").on("input","#phop_name", function(){
    $("#name-prompt").text("");
    if(validatephopname()){
        $(".form-control:focus").css("border-color","#28a745");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        name_flag = true;
    }else{
        $(".form-control:focus").css("border-color","#ff5549");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#name-prompt").text("方案名稱不得為空");
        $("#name-prompt").css("color","red");
        $("#name-prompt").css("font-size","6px");
        $("#name-prompt").css("margin-top","5px");
        name_flag = false;
    }
    checkFlag();
});
//監聽方案價錢是否填寫正確
$("#form-price").on("input","#phop_price", function(){
    $("#price-prompt").text("");
    if(validatephopprice()){
        $(".form-control:focus").css("border-color","#28a745");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        price_flag = true;
    }else{
        $(".form-control:focus").css("border-color","#ff5549");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#price-prompt").text("請輸入正確金額");
        $("#price-prompt").css("color","red");
        $("#price-prompt").css("font-size","6px");
        $("#price-prompt").css("margin-top","5px");
        price_flag = false;
    }
    checkFlag();
});
//監聽方案類別是否選擇
$("#phop_phoc_id").on("change", function(e){
    $("#categore-prompt").text("");
    if(e.target.value!=""){
        $(".form-control:focus").css("border-color","#28a745");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        categore_flag = true;
    }else{
        $(".form-control:focus").css("border-color","#ff5549");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#categore-prompt").text("請選擇方案類別");
        $("#categore-prompt").css("color","red");
        $("#categore-prompt").css("font-size","6px");
        $("#categore-prompt").css("margin-top","5px");
        categore_flag = false;
    }
    checkFlag();
});
//監聽方案開始日期是否點選
$("#phop_start_time").on("change", function(e) {
    $("#start-prompt").text("");
    if (e.target.value!=""){
        $(".form-control:focus").css("border-color","#28a745");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        start_flag = true;
    } else {
        $(".start").css("border-color","#ff5549");
        $(".start").css("box-shadow","0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#start-prompt").text("請選擇方案開始日期");
        $("#start-prompt").css("color","red");
        $("#start-prompt").css("font-size","6px");
        $("#start-prompt").css("margin-top","5px");
        start_flag = false;
    }
    checkFlag();
});
//監聽方案結束日期是否點選
$("#phop_end_time").on("change", function(e) {
    $("#end-prompt").text("");
    if (e.target.value!=""){
        $(".form-control:focus").css("border-color","#28a745");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        end_flag = true;
    } else {
        $(".end").css("border-color","#ff5549");
        $(".end").css("box-shadow","0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#end-prompt").text("請選擇方案結束日期");
        $("#end-prompt").css("color","red");
        $("#end-prompt").css("font-size","6px");
        $("#end-prompt").css("margin-top","5px");
        end_flag = false;
    }
    checkFlag();
});
//監聽方案內容是否填寫
$("#phop_content").on('input', function() {
    $("#content-prompt").text("");
    if ($("#phop_content").val()!="") {
        $(".form-control:focus").css("border-color","#28a745");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        content_flag = true;
    } else {
        $(".form-control:focus").css("border-color","#ff5549");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#content-prompt").text("請輸入方案內容");
        $("#content-prompt").css("color","red");
        $("#content-prompt").css("font-size","6px");
        $("#content-prompt").css("margin-top","5px");
        content_flag = false;
    }
    checkFlag();
});
//方案名稱正則表達式
function validatephopname(){
    let phopname = $("#phop_name").val();
    const re = /^[(\u4e00-\u9fa5)(A-za-z)]/;
    return re.test(phopname);
}
//方案價錢正則表達式
function validatephopprice(){
    let phopprice = $("#phop_price").val();
    const re = /^[0-9]{1,8}$/;//{1,8}可以輸入1~8個位數 例如:12345678
    return re.test(phopprice);
}
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