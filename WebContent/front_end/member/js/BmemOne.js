let urlParams = new URLSearchParams(window.location.search);
let order = urlParams.get("phoo_id");
let phoo_paytype = null;
console.log(order);
$.ajax({
    url: "../../photoorder/phooByBmemServlet",
    type: "post",
    dataType: "json",
    data: {
        "action": "getOneBmem",
        "phoo_id": order
    },
    success: function (data) {
        console.log(data);
        let phoo_paytype = data.PHOO_PAYTYPE;
        let tmp =
            `<div class="mibblebox">
					<div class="mb-3">
						<label class="form-label">婚攝店家</label>
						<input type="text" class="form-control" name="smem_name" value="${data.MEM_SHOP_NAME}" disabled>
					</div>
					<div class="mb-3">
						<label class="form-label">攝影師</label>
						<input type="text" class="form-control" name="phog_name" value="${data.PHOG_NAME}" disabled>
					</div>
					<div class="mb-3">
						<label class="form-label">方案</label>
						<input type="text" class="form-control" name="phop_name" value="${data.PHOP_NAME}" disabled>
					</div>
					<div class="mb-3">
						<label class="form-label">預約日期</label> 
						<input type="text" class="form-control" name="phoo_reserve_time" value="${data.PHOO_RESERVE_TIME}" disabled>
					</div>
					<div class="mb-3">
						<label class="form-label">方案內容</label> 
						<textarea class="form-control" rows="9" name="phoo_content" disabled>${data.PHOP_CONTENT}</textarea>
					</div>
					<div class="mb-3">
						<label class="form-label">訂金</label> 
						<input type="text" class="form-control" name="phoo_dipsoit" value="${data.PHOO_DEPOSIT}" disabled>
					</div>
					<div class="mb-3">
						<label class="form-label">總金額</label> 
						<input type="text" class="form-control" name="phoo_totalprice" value="${data.PHOO_TOTALPRICE}" disabled>
					</div>
					<div class="mb-3">
						<label class="form-label">付款方式</label> 
						<input type="text" class="form-control" name="phoo_paytype" value="${getpaytype(data.PHOO_PAYTYPE)}" disabled>
					</div>
					<div class="mb-3">
						<label class="form-label">訂單狀況</label> 
						<input type="text" class="form-control" name="phoo_order_status" value="${getstatus(data.PHOO_ORDER_STATUS)}" disabled>
					</div>
					<div class="mb-3">
						<label class="form-label">付款狀況</label> 
						<input type="text" class="form-control" name="phoo_pay_status" value="${getpaystatus(data.PHOO_PAY_STATUS)}" disabled>
					</div>
					<div class="mb-3">
						<label class="form-label">備註</label> 
						<textarea class="form-control" rows="5" name="phoo_note" disabled>${data.PHOO_NOTE}</textarea>
					</div>
				</div>`;
        $('#orderlist').prepend(tmp);
        $('#phoo_paytype').val(phoo_paytype);//訂金
        $('#phoo_paytype_total').val(phoo_paytype);//尾款

        //loco_ORDER_STATUS   0:預約中	1:預約成功	2:預約失敗	3:訂單已完成	4:已取消	
        //loco_PAY_STATUS     0:未付款	1:付款失敗	2:超過付款時間	3:已付訂金	4:已付尾款	5:退款中

        if (data.PHOO_ORDER_STATUS == 0 && data.PHOO_PAY_STATUS == 0) { //預約中 未付款
            let botton =
                `<button type="button" class="btn btn-primary" value="${data.PHOO_ID}" data-action="deleteorder">取消預約</button>`;
            $('#orderbtn').append(botton);
        } else if (data.PHOO_ORDER_STATUS == 1 && data.PHOO_PAY_STATUS == 0) { //預約成功 未付款 
            let botton =
                `<button type="button" class="btn btn-primary" value="${data.PHOO_ID}" data-action="paydeposit">訂金付款</button>
					<button type="button" class="btn btn-primary" value="${data.PHOO_ID}" data-action="deleteorder">取消預約</button>`;
            $('#orderbtn').append(botton);
        } else if (data.PHOO_ORDER_STATUS == 1 && data.PHOO_PAY_STATUS == 3) { //預約成功 已付訂金
            let botton =
                `<button type="button" class="btn btn-primary" value="${data.PHOO_ID}" data-action="finishtotal">結清尾款</button>`;
            $('#orderbtn').append(botton);

            // } else if (data.PHOO_ORDER_STATUS == 0 && data.PHOO_PAY_STATUS == 3) { 
            // 	let botton =
            // 	`<button type="button" class="btn btn-primary" value="${data.PHOO_ID}" data-action="finishtotal">結清尾款</button>`;
            // 	$('#orderbtn').append(botton);

            // } else if (data.PHOO_ORDER_STATUS == 3 && data.PHOO_PAY_STATUS == 3) {
            // 	let botton = 
            // 	`<button type="button" class="btn btn-primary" value="${data.PHOO_ID}" data-action="finishtotal">結清尾款</button>`;
            // 	$('#orderbtn').append(botton);
            // } else if (data.PHOO_ORDER_STATUS == 4 && data.PHOO_PAY_STATUS == 5) {
            // 	let botton = 
            // 	`<button type="button" class="btn btn-primary" value="${data.PHOO_ID}" data-action="rebackcheck">確認退款</button>`;
            // 	$('#orderbtn').append(botton);
            // }
        }
    }
})
$('#orderlist').on('click', "button", function (e) {
    let phoo = e.currentTarget.dataset.action;
    //訂金
    if (phoo == "paydeposit") {
        phoo_id = e.target.value;
        // console.log(phoo_id);
        $('#phoo_id').val(phoo_id);
        Swal.fire({
            title: '確認付款嗎?',
            showCancelButton: true,
            confirmButtonText: `前往付款`,
        }).then((result) => {
            if (result.isConfirmed) {
                $('#submit-form').submit();
            }
        })
        //尾款
    } else if (phoo == "finishtotal") {
        phoo_id = e.target.value;
        // console.log(phoo_id);
        $('#phoo_id_total').val(phoo_id);
        Swal.fire({
            title: '確認付清尾款嗎?',
            showCancelButton: true,
            confirmButtonText: `前往付款`,
        }).then((result) => {
            if (result.isConfirmed) {
                $('#submit-formtotal').submit();
            }
        })
        //取消訂單
    } else if (phoo == "deleteorder") {
        Swal.fire({
            title: '您確定要取消訂單嗎?',
            text: "取消後將無法返回唷～",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '是的, 忍痛取消!'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: "../../photoorder/phooByBmemServlet",
                    type: 'POST',
                    data: {
                        "phoo_id": order,
                        "action": "deleteorder"
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data) {
                            Swal.fire(
                                '已經取消了!',
                                '您已經將此筆訂單取消',
                                'success'
                            ).then((response) => {
                                window.location.reload();
                            });
                        } else {
                            swal("發生異常", "取消失敗，請聯絡客服人員", "error").then((response) => {
                                window.location.reload();
                            });
                        }
                    }
                });
            }
        })
        //退款
        // }else if (phoo == "rebackcheck") {
        // 	console.log("1113");
        // 	Swal.fire({
        // 		title: '您有收到退款嗎?',
        // 		text: "確定後將無法更改唷～",
        // 		icon: 'warning',
        // 		showCancelButton: true,
        // 		confirmButtonColor: '#3085d6',
        // 		cancelButtonColor: '#d33',
        // 		confirmButtonText: '是的, 已收退款!'
        // 	}).then((result) => {
        // 		if (result.isConfirmed) {
        // 			$.ajax({
        // 				url: "../../photoorder/phooByBmemServlet",
        // 				type: 'POST',
        // 				data: {
        // 					"PHOO_ID": order,
        // 					"action": "rebackcheck"
        // 				},
        // 				dataType: "json",
        // 				success: function (data) {
        // 					if (data) {
        // 						Swal.fire(
        // 							'狀態已更新了!',
        // 							'您已收取此筆訂單退款',
        // 							'success'
        // 						).then((response) => {
        // 							window.location.reload();
        // 						});
        // 					} else {
        // 						swal("發生異常", "更新失敗，請聯絡客服人員", "error").then((response) => {
        // 							window.location.reload();
        // 						});
        // 					}
        // 				}
        // 			});
        // 		}
        // 	})
    }
    //取消form表單的跳轉畫面
    e.preventDefault();
});
//跟資料庫請求個人頭像
Ajaxheadshot();
function Ajaxheadshot() {
    $.ajax({
        type: "post",
        url: "../../member/headshotBuyServlet",
        data: { "headshot": "headshot" },
        xhrFields: {
            // 將回傳結果以Blob保持原本二進位的格式回傳
            //jquery的dataType無法設定返回格式為blob需要手動修改
            responseType: "blob"
        },
        success: function (result) {
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
//getpaystatus 0:未付款	1:付款失敗	2:超過付款時間	3:已付訂金	4:已付尾款	5:退款中 6:已退款
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
// function TIME(time) {
//     //new Date()可以接毫秒數
//     let date = new Date(time);
//     //date.toLocaleString(); 可以轉換成當地格式
//     datetime = date.toLocaleString();
//     return datetime;
//     console.log(date)
// }