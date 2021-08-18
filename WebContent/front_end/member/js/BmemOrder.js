$.ajax({
        url: "../../photoorder/phooByBmemServlet",
        type: "post",
        dataType: "json",
        data: { "action": "getBmemId" },
        success: function(data) {
            console.log(data);
            for (let i = 0; i < data.length; i++) {
                let tmp =
                    `<tr>
                <td>${data[i].MEM_SHOP_NAME}</td>
                <td>${data[i].PHOO_RESERVE_TIME}</td>
                <td>${data[i].PHOO_DEPOSIT}</td>
                <td>${data[i].PHOO_TOTALPRICE}</td>
                <td>${getstatus(data[i].PHOO_ORDER_STATUS)}</td>
                <td>${getpaystatus(data[i].PHOO_PAY_STATUS)}</td>
                <td><a href="BuyerReserveOne.html?phoo_id=${data[i].PHOO_ID}"><button type="button" class="btn btn-primary" onclick="">詳細資訊</button></a></td>	
            </tr>`
                $('#orderlist').append(tmp);
            }
        }
    })
    //getpaytype:0:信用卡付款	1:web ATM
function getpaytype(id) {
    if (id == 0) {
        return "信用卡付款";
    } else if (id == 1) {
        return "web ATM";
    }
}
//status:預約中1:預約成功2:預約失敗3:訂單已完成4:已取消
function getstatus(id) {
    if (id == 0) {
        return "預約中";
    } else if (id == 1) {
        return "預約成功";
    } else if (id == 2) {
        return "預約失敗";
    } else if (id == 3) {
        return "訂單已完成";
    } else if (id == 4) {
        return "已取消";
    }
}
//getpaystatus 0:未付款	1:付款失敗	2:超過付款時間	3:已付訂金	4:已付尾款	5:退款中
function getpaystatus(id) {
    if (id == 0) {
        return "未付款";
    } else if (id == 1) {
        return "付款失敗";
    } else if (id == 2) {
        return "超過付款時間";
    } else if (id == 3) {
        return "已付訂金";
    } else if (id == 4) {
        return "已付尾款";
    } else if (id == 5) {
        return "退款中";
    } else if (id == 6) {
        return "已退款";
    }
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