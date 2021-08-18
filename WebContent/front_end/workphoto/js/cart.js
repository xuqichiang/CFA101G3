getCart();//頁面載入時取得購物車資料

//點擊Icon圖片購物車時取得購物車資料
$('#cartModal').on('click', function () {
    getCart();
})

//點擊加入購物車時後端同步新增
$('#buycar').on('click', function (e) {
    // let pro_id = e.target.dataset.proid;
    let pro_id = param;
    console.log(pro_id);
    addCart(pro_id);
});

//更新商品數量時後端同步更新
$('#cart').on('change', 'input[type=number]', function (e) {
    let spoi_quantity = e.target.value;//取得商品數量
    //判斷商品數量是否在1-20內
    let reg = /^([1-9][0-9]*){1,3}$/;
    if (!reg.test(spoi_quantity) || spoi_quantity > 20) {
        getCart();
        return;
    }
    let pro_id = e.target.dataset.proid;//取得商品ID
    let pro_price = e.target.dataset.proprice//取得商品單價
    let spoi_totalprice = spoi_quantity * pro_price;//取得小計金額
    $(e.target).parent().next().html(spoi_totalprice);
    updateCart(pro_id, spoi_quantity);
    getTotal();//顯示總金額
})


//監聽點擊刪除鈕後送至後端刪除購物車內商品
$('#cart').on('click', 'a', function (e) {
    let pro_id = e.currentTarget.dataset.proid;
    deleteCartProduct(pro_id);
})

//點擊結帳進入結帳頁面
$('#checkout').on('click',function(){
    window.location.href="Checkout.html";
});

//加入購物車
function addCart(pro_id) {
    
    $.ajax({
        type: "post",
        url: "../../shop_order_item/spoiServlet",
        dataType: 'json',
        data: {
            "action": "addCart",
            "pro_id": pro_id
        },
        success: function (data) {
            let count = 0;
            let total = 0;
            for (const key in data) {
                count += data[key].spoi_quantity;
                total += data[key].spoi_totalprice;
            }
            let CountTmp = `<div id="count" class="count">${count}</div>`;
            $('#cartIcon').html(CountTmp);
            $('#total').text(total);
        }
    });
}

//取得購物車資料
function getCart() {
    let count = 0;
    let total = 0;
    $.ajax({
        type: "get",
        url: "../../shop_order_item/spoiServlet",
        dataType: 'json',
        data: {
            "action": "getCart"
        },
        async: false,
        success: function (data) {
            let set = new Set();
            console.log(data);
            $('#cart').html("");
            for (const key in data) {
                set.add(data[key].provo.pro_smem_id);
                count += data[key].spoi_quantity;
                total += data[key].spoi_totalprice;
                let ProTmp = ` <tr>
                        <th scope="row"><img src="../../product/ProImgOutServlet?proi_id=${getProductImg(key)}"></th>
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
        $('#cart').html("購物車尚未有任何商品");
        $('#cartIcon').html("");
        $('#total-list').addClass("hide");
    }else{
        $('#total-list').removeClass("hide");
    }
}

//更新購物車
function updateCart(pro_id, spoi_quantity) {
    $.ajax({
        type: "post",
        url: "../../shop_order_item/spoiServlet",
        dataType: 'json',
        async:false,
        data: {
            "action": "updateCart",
            "pro_id": pro_id,
            "spoi_quantity": spoi_quantity
        },
        success: function (data) {
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
function getTotal(){
    let total = 0;
    $.ajax({
        type: "get",
        url: "../../shop_order_item/spoiServlet",
        dataType: 'json',
        data: {
            "action": "getCart"
        },
        async: false,
        success: function (data) {
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
        url: "../../product/ProImgSelServlet",
        data: { "proi_pro_id": pro_id },
        dataType: "json",
        async: false,
        success: function (data) {
            proi_id = data[0].proi_id;
        }
    });
    return proi_id;
}

//商除購物車商品
function deleteCartProduct(pro_id) {
    $.ajax({
        type: "post",
        url: "../../shop_order_item/spoiServlet",
        dataType: 'json',
        data: {
            "action": "delete",
            "pro_id": pro_id,
        },
        success: function (response) {
            getCart();//刪除後更新購物車資料
        }
    });
}