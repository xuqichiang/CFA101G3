//用name屬性來註冊事件，取得攝影師狀態
// let phog_statue = null;
// $("input[name='phog_status']").on("change", function(e){
//     phog_statue = e.target.value;
//     console.log(phog_statue);
// });
//進階版本
let phog_status = $("input[name=phog_status]:checked").val();
//送出新增
$("#confirm").on("click", function () {
    let phog_name = $("#phog_name").val()
    $.ajax({
        url: "../../photographer/phogAddServlet",
        type: "post",
        dataType: "json",
        data: {
            "phog_name": phog_name,
            "phog_status": phog_status
        },
        success: function (data) {
            alert("新增成功");
            window.location.href="listAllPhotographer.html";//新增成功直接到查詢頁面
        },
        error: function (data) {
            alert("新增失敗");
        }
    });
});
//設置旗幟
let name_flag = false;
//確認旗幟均為true按鈕才能按
function checkFlag(){
     if(name_flag){
        $("#confirm").removeAttr("disabled"); 
     }else{
        $("#confirm").prop("disabled", "disabled");
     }
 };
 //監聽攝影師格式是否正確
$("#form-name").on("input","#phog_name", function(){
    $("#name-prompt").text("");
    if(validatephogname()){
        $(".form-control:focus").css("border-color","#28a745");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        name_flag = true;
    }else{
        $(".form-control:focus").css("border-color","#ff5549");
        $(".form-control:focus").css("box-shadow","0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#name-prompt").text("攝影師名字不得為空");
        $("#name-prompt").css("color","red");
        $("#name-prompt").css("font-size","6px");
        $("#name-prompt").css("margin-top","5px");
        name_flag = false;
    }
    checkFlag();
});
//攝影師名子正則表達式
function validatephogname(){
    let phogname = $("#phog_name").val();
    const re = /^[(\u4e00-\u9fa5)(A-za-z)]/;
    return re.test(phogname);
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
