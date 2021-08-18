$.ajax({
    type:"get",
    url:"../../photographer/phogServlet",
    dataType:"json",
    success:function (phog) {
        console.log(phog);
        let list =
        `<tr>
            <th scope="col">攝影師名字</th>
            <th scope="col">攝影師狀態</th>
        </tr>`;
        $("#table-phog-id").html(list);
        for (let i=0; i<phog.length; i++) {
            //console.log(phog[i].phog_name);
            let status = phog[i].phog_status;
            let job="";
            if(status==1){
                job="在職中";
            }else{
                job="已離職";
            }
            let element =
            `<tr>
                <td>${phog[i].phog_name}</td>
                <td>${job}</td>
            </tr>`;
            $("#table-phog-id").append(element);   
        }
    }
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