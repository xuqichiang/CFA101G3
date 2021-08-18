document.addEventListener('DOMContentLoaded', function() {
    let calendarEl = document.getElementById("calendar");
    let calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale:'zh-tw',
        navLinks:true,
        headerToolbar:{
        //上方的左邊按鈕
        left:'prev,next,today',
        //上方中間顯示
        center:'title',
        //上方右邊按鈕
        right:'dayGridMonth,timeGridWeek,timeGridDay'
        },
        //加入活動
        events: {
                url: '../../calendar/getAllStrokeServlet',
                method: 'POST',
                extraParams: {
                //攝影
                action: 'photography'
                //場地
                // action: 'location'
                },
                failure: function() {
                    alert('there was an error while fetching events!');
                },
                color: '#eee',   // a non-ajax option
                textColor: 'black' // a non-ajax option
            }
        });
        calendar.render();
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