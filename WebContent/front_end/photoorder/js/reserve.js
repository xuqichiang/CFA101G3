let urlParams = new URLSearchParams(window.location.search);
let param = urlParams.get("mem_id");
// console.log(param);
//買家資訊
$.ajax({
    type: "get",
    url: "../../photographer/phogGetShopServlet",
    data: {
        "action": "getBmemId",
    },
    dataType: "json",
    success: function(member) {
        $("#bmem_id").prop("value", member.MEM_ID);
        $("#bmem_name").prop("value", member.MEM_NAME);
    }
});
//店家資訊
$.ajax({
    type: "get",
    url: "../../photographer/phogGetShopServlet",
    data: {
        "action": "getSmemId",
        "phog_smem_id": param
    },
    dataType: "json",
    success: function(member) {
        let element =
            `<div class="mem-logo"><img src="data:image/*;base64,${member.MEM_SHOP_LOGO}"></div>
                <ul class="mem-wed" id="mem-wed-id">
                    <li class="mem-shop">${member.MEM_SHOP_NAME}</li>
                    <li class="mem-map"><img src="https://img.icons8.com/ios-glyphs/30/000000/map-pin.png"/>${member.MEM_CITY}</li>
                </ul>`;
        $("#mem-information-id").append(element);
        $("#smem_id").prop("value", member.PHOG_SMEM_ID);
        //店家方案總數量
        $.ajax({
            type: "get",
            url: "../../photoprogram/phopOneSmemIdServlet",
            data: {
                "phop_smem_id": param
            },
            dataType: "json",
            success: function(phop) {
                if (phop.phop_status != 0) {
                    let element =
                        `<li class="wor-phop" id="wor-phop-id">
                        <span>方案(${phop.length})</span>
                    </li>`;
                    $("#mem-wed-id").append(element);
                }
            }
        });
        //店家作品集總數量
        $.ajax({
            type: "get",
            url: "../../photographer/phogGetWork",
            data: {
                "phog_smem_id": param
            },
            dataType: "json",
            success: function(phog) {
                // console.log(phog);
                if (phog.phog_status != 0) {
                    let element =
                        `<span>作品(${phog.length})</span>`;
                    $("#wor-phop-id").append(element);
                }
            }
        });
    }
});
//攝影師
$.ajax({
    type: "get",
    url: "../../photographer/phogOneSmemIdServlet",
    data: {
        "phog_smem_id": param
    },
    dataType: "json",
    success: function(phog) {
        for (let i = 0; i < phog.length; i++) {
            //狀態0不顯示
            if (phog[i].phog_status != 0) {
                let element =
                    `<option value="${phog[i].phog_id}">${phog[i].phog_name}</option>`;
                $("#phog_name").append(element);
            }
        }
    }
});
//選擇攝影師過濾已被預約的時間
$(function() {
    $("#phog_name").on("change", function(e) {
        let phog = $(e.target).val();
        // console.log(phog);
        $.ajax({
            type: "get",
            url: "../../photoorder/phooBySmemServlet",
            data: {
                "action": "getTime",
                "phoo_phog_id": phog
            },
            dataType: "json",
            success: function(reserve) {
                // console.log(reserve);
                $.datetimepicker.setLocale("zh");
                $("#phoo_reserve_time").datetimepicker({
                    timepicker: false,
                    format: "Y-m-d",
                    disabledDates: reserve, //阻擋店家攝影師已被預約的時間
                    value: new Date(),
                    minDate: "-1970-01-01",
                });
            }
        });
    });
});
//方案
$.ajax({
    type: "get",
    url: "../../photoprogram/phopOneSmemIdServlet",
    data: {
        "phop_smem_id": param
    },
    dataType: "json",
    success: function(phop) {
        for (let i = 0; i < phop.length; i++) {
            //狀態0不顯示
            if (phop[i].phop_status != 0) {
                let element =
                    `<option value="${phop[i].phop_id}">${phop[i].phop_name}</option>`;
                $("#phop_name").append(element);
            }
        }
    }
});
//方案價錢
$("#phop_name").on("change", function(e) {
    let program = $(e.target).val();
    $.ajax({
        type: "get",
        url: "../../photoprogram/getOneContentServlet",
        data: {
            "phop_id": program
        },
        dataType: "json",
        success: function(phop) {
            $("#phoo_totalprice").prop("value", phop.PHOP_PRICE);
            $("#phoo_deposit").prop("value", (phop.PHOP_PRICE) * 0.2); //收取二成$訂金
        }
    });
});
//送出預約表
$("#confirm-reserve").on("click", function(e) {
    let phoo_place = $("input[name=phoo_place]:checked").val();
    let phoo_paytype = $("input[name=phoo_paytype]:checked").val();
    let phoo_bmem_id = $("#bmem_id").val();
    let phoo_smem_id = $("#smem_id").val();
    let phoo_phop_id = $("#phop_name").val();
    let phoo_phog_id = $("#phog_name").val();
    let phoo_totalprice = $("#phoo_totalprice").val();
    let phoo_deposit = $("#phoo_deposit").val();
    let phoo_reserve_time = $("#phoo_reserve_time").val();
    let phoo_note = $("#phoo_note").val();
    console.log(phoo_reserve_time);
    $.ajax({
        type: "post",
        url: "../../photoorder/phooAddFormServlet",
        data: {
            "phoo_bmem_id": phoo_bmem_id,
            "phoo_smem_id": phoo_smem_id,
            "phoo_phop_id": phoo_phop_id,
            "phoo_phog_id": phoo_phog_id,
            "phoo_totalprice": phoo_totalprice,
            "phoo_deposit": phoo_deposit,
            "phoo_reserve_time": phoo_reserve_time,
            "phoo_place": phoo_place,
            "phoo_paytype": phoo_paytype,
            "phoo_note": phoo_note
        },
        dataType: "json",
        success: function(reserve) {
            if (reserve == 0) {
                alert("日期已被預約，請重選擇日期");
            } else {
                alert(reserve.msg);
                window.location.href = "../member/BuyerOrder.html"
            }
        }
    });
    //取消form表單的跳轉畫面
    e.preventDefault();
});
//設置驗證旗幟
let phop_flag = false; //方案
let phog_flag = false; //攝影師
let reserve_flag = false; //預約日期
//確認旗幟均為true按鈕才能按
function checkFlag() {
    if (phop_flag && phog_flag && reserve_flag) {
        $("#confirm-reserve").removeAttr("disabled");
    } else {
        $("#confirm-reserve").prop("disabled", "disabled");
    }
}
//監聽方案是否選擇
$("#phop_name").on("change", function(e) {
    $("#phop-prompt").text("");
    if (e.target.value != "") {
        $(".form-control:focus").css("border-color", "#28a745");
        $(".form-control:focus").css("box-shadow", "0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        phop_flag = true;
    } else {
        $(".form-control:focus").css("border-color", "#ff5549");
        $(".form-control:focus").css("box-shadow", "0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#phop-prompt").text("請選擇方案");
        $("#phop-prompt").css("color", "red");
        $("#phop-prompt").css("font-size", "6px");
        $("#phop-prompt").css("margin-top", "5px");
        phop_flag = false;
    }
    checkFlag();
});
//監聽攝影師是否選擇
$("#phog_name").on("change", function(e) {
    $("#phog-prompt").text("");
    if (e.target.value != "") {
        $(".form-control:focus").css("border-color", "#28a745");
        $(".form-control:focus").css("box-shadow", "0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        phog_flag = true;
    } else {
        $(".form-control:focus").css("border-color", "#ff5549");
        $(".form-control:focus").css("box-shadow", "0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#phog-prompt").text("請選擇攝影師");
        $("#phog-prompt").css("color", "red");
        $("#phog-prompt").css("font-size", "6px");
        $("#phog-prompt").css("margin-top", "5px");
        phog_flag = false;
    }
    checkFlag();
});
$("#phoo_reserve_time").on("change", function(e) {
    $("#reserve-prompt").text("");
    if (e.target.value != "") {
        $(".form-control:focus").css("border-color", "#28a745");
        $(".form-control:focus").css("box-shadow", "0 0 0 0.2rem rgba(40, 167, 69, 0.25)");
        reserve_flag = true;
    } else {
        $(".reserve").css("border-color", "#ff5549");
        $(".reserve").css("box-shadow", "0 0 0 0.2rem rgba(255, 76, 76, 0.25)");
        $("#reserve-prompt").text("請選擇預約日期");
        $("#reserve-prompt").css("color", "red");
        $("#reserve-prompt").css("font-size", "6px");
        $("#reserve-prompt").css("margin-top", "5px");
        reserve_flag = false;
    }
    checkFlag();
});