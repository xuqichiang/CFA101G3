$(function() {
    let path = window.location.pathname;
    let webCtx = path.substring(0, path.indexOf('/', 1));
    let cartTmp = `    <div class="cartModal">
    <div class="cartModal-dialog">
        <div class="cartModal-content">
            <div class="cartModal-header">
                <h5 class="cartModal-title">購物車<img src="${webCtx}/front_end/index/images/MHlogo_04.svg" alt=""></h5>
            </div>
            <div class="cartModal-body">
                <table class="cartModal-table">
                    <thead>
                        <tr>
                            <th>商品資訊</th>
                            <th>商品名稱</th>
                            <th>價格</th>
                            <th>數量</th>
                            <th>小計</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody id="cart"></tbody>
                </table>
                <div id="total-list" class="total-list">
                    <h5 class="site-color">合計金額</h5>
                    <h5 class="site-color">$<span id="total"></span></h5>
                </div>
            </div>
            <div class="cartModal-footer">
                <button type="button" id="cartModal-cancel">取消</button>
                <button type="button" id="checkout">結帳</button>
            </div>
        </div>
    </div>
</div>`;
    //加載到頁面
    $('body').append(cartTmp);

    getCart(); //頁面載入時取得購物車資料

    //點擊Icon圖片購物車時取得購物車資料
    $('#cartModal').on('click', function() {
        getCart();
    })

    //點擊加入購物車時後端同步新增
    $('#buycar').on('click', function(e) {
        let pro_id = param;
        addCart(pro_id);
    });

    //更新商品數量時後端同步更新
    $('#cart').on('change', 'input[type=number]', function(e) {
        let spoi_quantity = e.target.value; //取得商品數量
        //判斷商品數量是否在1-20內
        let reg = /^([1-9][0-9]*){1,3}$/;
        if (!reg.test(spoi_quantity) || spoi_quantity > 20) {
            getCart();
            return;
        }
        let pro_id = e.target.dataset.proid; //取得商品ID
        let pro_price = e.target.dataset.proprice //取得商品單價
        let spoi_totalprice = spoi_quantity * pro_price; //取得小計金額
        $(e.target).parent().next().html(spoi_totalprice);
        updateCart(pro_id, spoi_quantity);
        getTotal(); //顯示總金額
    })


    //監聽點擊刪除鈕後送至後端刪除購物車內商品
    $('#cart').on('click', 'a', function(e) {
        let pro_id = e.currentTarget.dataset.proid;
        deleteCartProduct(pro_id);
    })

    //點擊結帳進入結帳頁面
    $('#checkout').on('click', function() {
        $.ajax({
            type: "post",
            url: "../member/buyProfileServlet",
            dataType: "json",
            success: function(response) {
                if (response != "0") {
                    if (response.mem_role == 10) {
                        window.location.href = "../product/Checkout.html";
                    } else {
                        alert("您非買家會員身份");
                    }
                } else {
                    window.location.href = "../member/login.html";
                }
            }
        });
    });

    //加入購物車
    function addCart(pro_id) {
        $.ajax({
            type: "post",
            url: "../shop_order_item/spoiServlet",
            dataType: 'json',
            data: {
                "action": "addCart",
                "pro_id": pro_id
            },
            success: function(data) {
                let count = 0;
                let total = 0;
                for (const key in data) {
                    count += data[key].spoi_quantity;
                    total += data[key].spoi_totalprice;
                }
                let CountTmp = `<div id="count" class="count">${count}</div>`;
                $('#cartIcon').html(CountTmp);
                $('#total').text(total);
                getCart(); //新增完後更新資料
            }
        });
    }

    //取得購物車資料
    function getCart() {
        let count = 0;
        let total = 0;
        $.ajax({
            type: "get",
            url: "../shop_order_item/spoiServlet",
            dataType: 'json',
            data: {
                "action": "getCart"
            },
            async: false,
            success: function(data) {
                let set = new Set();
                console.log(data);
                $('#cart').html("");
                for (const key in data) {
                    set.add(data[key].provo.pro_smem_id);
                    count += data[key].spoi_quantity;
                    total += data[key].spoi_totalprice;
                    let ProTmp = ` <tr>
                            <th><img src="../../product/ProImgOutServlet?proi_id=${getProductImg(key)}"></th>
                            <td>${data[key].provo.pro_name}</td>
                            <td>${data[key].provo.pro_price}</</td>
                            <td><input type="number" name="" min="1" max="20" step="1" data-proid="${key}" data-proprice="${data[key].provo.pro_price}" value="${data[key].spoi_quantity}"></td>
                            <td>${data[key].spoi_totalprice}</td>
                            <td><a href="javascript:void(0)" data-proid="${key}"><i class="fas fa-minus-circle"></i></a></td>
                        </tr>`;
                    $('#cart').append(ProTmp);
                }
                $('#total').text(total);
                let CountTmp = `<div id="count" class="count">${count}</div>`;
                $('#cartIcon').html(CountTmp);
                console.log(set);
            }
        });
        if (count == 0) {
            $('#cart').html(`<div style="width:200px;padding:5px">購物車尚未有任何商品</div>`);
            $('#cartIcon').html("");
            $('#total-list').addClass("hide");
            $('#checkout').addClass("hide");
        } else {
            $('#total-list').removeClass("hide");
            $('#checkout').removeClass("hide");
        }
    }

    //更新購物車
    function updateCart(pro_id, spoi_quantity) {
        $.ajax({
            type: "post",
            url: "../shop_order_item/spoiServlet",
            dataType: 'json',
            async: false,
            data: {
                "action": "updateCart",
                "pro_id": pro_id,
                "spoi_quantity": spoi_quantity
            },
            success: function(data) {
                let count = 0;
                for (const key in data) {
                    count += data[key].spoi_quantity;
                }
                let CountTmp = `<div id="count" class="count">${count}</div>`;
                $('#cartIcon').html(CountTmp);
            }
        });
    }
    //顯示總金額
    function getTotal() {
        let total = 0;
        $.ajax({
            type: "get",
            url: "../shop_order_item/spoiServlet",
            dataType: 'json',
            data: {
                "action": "getCart"
            },
            async: false,
            success: function(data) {
                for (const key in data) {
                    total += data[key].spoi_totalprice;
                }
                $('#total').text(total);
            }
        });
    }

    //取得商品照片ID
    function getProductImg(pro_id) {
        let proi_id = 0;
        $.ajax({
            type: "post",
            url: "../product/ProImgSelServlet",
            data: {
                "proi_pro_id": pro_id
            },
            dataType: "json",
            async: false,
            success: function(data) {
                proi_id = data[0].proi_id;
            }
        });
        return proi_id;
    }

    //商除購物車商品
    function deleteCartProduct(pro_id) {
        $.ajax({
            type: "post",
            url: "../shop_order_item/spoiServlet",
            dataType: 'json',
            data: {
                "action": "delete",
                "pro_id": pro_id,
            },
            success: function(response) {
                getCart(); //刪除後更新購物車資料
            }
        });
    }

    //關閉購物車面板
    $('#cartModal-cancel').on('click', function(e) {
        $('.cartModal').slideUp();
    })

    //開關購物車面板
    $('#cartModal').on('click', function(e) {
        $('.cartModal').slideToggle();
    })

    //直接購買
    $('#tobuy').on('click', function(e) {
        $.ajax({
            type: "post",
            url: "../member/buyProfileServlet",
            dataType: "json",
            success: function(response) {
                if (response != "0") {
                    if (response.mem_role == 10) {
                        let pro_id = param;
                        console.log(pro_id);
                        addCart(pro_id);
                        window.location.href = "../product/Checkout.html";
                    } else {
                        alert("您非買家會員身份");
                    }
                } else {
                    window.location.href = "../member/login.html";
                }
            }
        });

    });
});