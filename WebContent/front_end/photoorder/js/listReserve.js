$.ajax({
    type: "get",
    url: "../../photoorder/phooBySmemServlet",
    dataType: "json",
    data: {
        "action": "getSmemId"
    },
    success: function (phooSeme) {
        console.log(phooSeme);
        let list =
            `<tr>
            <th scope="col">姓名</th>
            <th scope="col">方案</th>
            <th scope="col">攝影師</th>
            <th scope="col">拍攝時間</th>
            <th scope="col">付款狀態</th>
            <th scope="col">訂單狀態</th>
            <th scope="col">詳細資訊</th>
        </tr>`;
        $("#table-phoo-id").html(list);
        for (let i = 0; i < phooSeme.length; i++) {
            let payStatus = phooSeme[i].PHOO_PAY_STATUS;
            let pay = "";
            switch (payStatus) {
                case 0:
                    pay = "未付款";
                    break;
                case 1:
                    pay = "付款失敗";
                    break;
                case 2:
                    pay = "超過付款時間";
                    break;
                case 3:
                    pay = "已付訂金";
                    break;
                case 4:
                    pay = "已付尾款";
                    break;
                case 5:
                    pay = "退款中";
                    break;
                case 6:
                    pay = "已退款";
                    break;
            }
            let orderStatus = phooSeme[i].PHOO_ORDER_STATUS;
            let order = "";
            switch (orderStatus) {
                case 0:
                    order = "預約中";
                    break;
                case 1:
                    order = "預約成功";
                    break;
                case 2:
                    order = "預約失敗";
                    break;
                case 3:
                    order = "訂單已完成";
                    break;
                case 4:
                    order = "已取消";
                    break;
            }
            let element =
                `<tr>
                <td>${phooSeme[i].MEM_NAME}</td>
                <td>${phooSeme[i].PHOP_NAME}</td>
                <td>${phooSeme[i].PHOG_NAME}</td>
                <td>${phooSeme[i].PHOO_RESERVE_TIME}</td>
                <td>${pay}</td>
                <td>${order}</td>
                <td ><a href="listReserve.html?phoo_id=${phooSeme[i].PHOO_ID}">查看</a></td>
            </tr>`;
            $("#table-phoo-id").append(element);
        }
    }
});
//a標籤預約資訊
let urlParams = new URLSearchParams(window.location.search);
let param = urlParams.get("phoo_id");
$.ajax({
    type: "get",
    url: "../../photoorder/phooBySmemServlet",
    data: {
        "action": "getOneSmem",
        "phoo_id": param
    },
    dataType: "json",
    success: function (reserve) {
        console.log(reserve);
        let payStatus = reserve.PHOO_PAY_STATUS;
        let pay = "";
        switch (payStatus) {
            case 0:
                pay = "未付款";
                break;
            case 1:
                pay = "付款失敗";
                break;
            case 2:
                pay = "超過付款時間";
                break;
            case 3:
                pay = "已付訂金";
                break;
            case 4:
                pay = "已付尾款";
                break;
            case 5:
                pay = "退款中";
                break;
            case 6:
                pay = "已退款";
                break;
        }
        let orderStatus = reserve.PHOO_ORDER_STATUS;
        let order = "";
        switch (orderStatus) {
            case 0:
                order = "預約中";
                break;
            case 1:
                order = "預約成功";
                break;
            case 2:
                order = "預約失敗";
                break;
            case 3:
                order = "訂單已完成";
                break;
            case 4:
                order = "已取消";
                break;
        }
        //方案內容換行處理
        let content = reserve.PHOP_CONTENT;
        for (let i = 0; i < content.length; i++) {
            if (content[i] == "\n") {
                content = content.replace("\n", "<br>");//替代
            }
        }
        //備註內容換行處理
        let note = reserve.PHOO_NOTE;
        for (let i = 0; i < note.length; i++) {
            if (note[i] == "\n") {
                note = note.replace("\n", "<br>");//替代
            }
        }
        let payType = reserve.PHOO_PAYTYPE;
        let payment = "";
        if (payType == 0) {
            payment = "信用卡";
        } else {
            payment = "Web ATM";
        }
        let element =
            `<h5>訂單詳情</h5>
            <table class="table phoo">
                <tr><th scope="row">姓名</th><td>${reserve.MEM_NAME}</td></tr>
                <tr><th scope="row">訂金</th><td>${reserve.PHOO_DEPOSIT}</td></tr>
                <tr><th scope="row">總金額</th><td>${reserve.PHOO_TOTALPRICE}</td></tr>
                <tr><th scope="row">付款方式</th><td>${payment}</td></tr>
                <tr><th scope="row">訂單狀態</th><td>${order}</td></tr>
                <tr><th scope="row">付款狀態</th><td>${pay}</td></tr>
                <tr><th scope="row">拍攝地點</th><td>${reserve.PHOO_PLACE}</td></tr>
                <tr><th scope="row">拍攝時間</th><td>${reserve.PHOO_RESERVE_TIME}</td></tr>
                <tr><th scope="row">攝影師</th><td>${reserve.PHOG_NAME}</td></tr>
                <tr><th scope="row">方案名</th><td>${reserve.PHOP_NAME}</td></tr>
                <tr><th scope="row">方案內容</th><td>${content}</td></tr>
                <tr><th scope="row">備註</th><td>${note}</td></tr>
            </table>`;
        $("#reserver-show-id").append(element);
        if (reserve.PHOO_ORDER_STATUS == 0) { //預約中
            let btn =
            `<button type="submit" class="btn btn-light" value="${reserve.PHOO_ID}" data-phoo="confirm">確認預約</button>
            <button type="submit" class="btn btn-light" value="${reserve.PHOO_ID}" data-phoo="cancel">婉拒預約</button>`;
            $("#reserver-show-id").append(btn);
        // } else if (reserve.PHOO_ORDER_STATUS == 1 && reserve.PHOO_PAY_STATUS == 3) {//預約成功 已付訂金
        //     let btn =
        //     `<button type="submit" class="btn btn-light" value="${reserve.PHOO_ID}" data-phoo="finish">完成預約</button>`;
        //     $("#reserver-show-id").append(btn);
        } else if (reserve.PHOO_ORDER_STATUS == 1 && reserve.PHOO_PAY_STATUS == 4) {//預約成功 已付尾款
            let btn =
            `<button type="submit" class="btn btn-light" value="${reserve.PHOO_ID}" data-phoo="finish">完成訂單</button>`;
            $("#reserver-show-id").append(btn);
        }
    }
});
$("#reserver-show-id").on("click", "button", function (e) {
    let orderbtn = e.currentTarget.dataset.phoo;
    console.log(orderbtn);
    if (orderbtn == "confirm") {
        let phoo_id = e.target.value;
        console.log(phoo_id);
        let con = confirm("確定接收訂單嗎？");
        $.ajax({
            type: "post",
            url: "../../photoorder/phooBySmemServlet",
            data: {
                "phoo_id": phoo_id,
                "action": "confirm"//確認訂單
            },
            dataType: "json",
            success: function (response) {
                if (response) {
                    alert("確認接收訂單");
                    window.location.reload();//刷新頁面
                }
            }
        });
    } else if (orderbtn == "cancel") {
        let phoo_id = e.target.value;
        console.log(phoo_id);
        let con = confirm("確定婉拒訂單嗎？");
        $.ajax({
            type: "post",
            url: "../../photoorder/phooBySmemServlet",
            data: {
                "phoo_id": phoo_id,
                "action": "cancel"//婉拒訂單
            },
            dataType: "json",
            success: function (response) {
                if (response) {
                    alert("已經婉拒訂單");
                    window.location.reload();
                }
            }
        });
    } else if(orderbtn == "finish"){
        let phoo_id = e.target.value;
        console.log(phoo_id);
        $.ajax({
            type: "post",
            url: "../../photoorder/phooBySmemServlet",
            data: {
                "phoo_id": phoo_id,
                "action": "finish"//預約完成
            },
            dataType: "json",
            success: function (response) {
                if (response) {
                    alert("已經完成這筆訂單");
                    window.location.reload();
                }
            }
        });
    }
});
// function TIME(time){
//     //new Date()可以接毫秒數
//     let date = new Date(time);
//     //date.toLocaleString(); 可以轉換成當地格式
//     datetime = date.toLocaleString();
//     return datetime;    
//     console.log(date)
// }
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