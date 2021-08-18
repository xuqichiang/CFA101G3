$(function() {
    //個人資料template
    let profile =
        `<h2>個人資料</h2>
            <div class="profile">
                <h3>基本資料</h3>
                <span>會員名稱</span><small>在MarryHappiness 使用的䁥稱</small><input type="button" value="編輯" id="edit" class="edit">
                <div id="name" class="user-name"></div>
                <span>手機號碼</span>
                <div id="phone" class="h50"></div>
                <span>居住縣市</span>
                <div id="city" class="h50"></div>
                <span>居住鄉鎮區</span>
                <div id="cityarea" class="h50"></div>
                <span>居住道路或街名</span>
                <div id="street" class="h50"></div>
            </div>
            <div class="profile-form">
                <form action="" id="profile-form" name="profile-form">
                    <h3>編輯基本資料</h3>
                    <div id="prompt"></div>
                    <label for="name-form">會員名稱</label><small>在MarryHappiness 使用的䁥稱</small><br>
                    <input type="text" name="name-form" id="name-form">
                    <div id="name-prompt"></div>
                    <label for="phone-form">手機號碼</label><br>
                    <input type="text" name="phone-form" id="phone-form" maxlength="10">
                    <div id="phone-prompt"></div>
                    <label for="city">縣市及鄉鎮區</label><br>
                    <div id="twzipcode"></div>
                    <label for="street">居住道路或街名</label><br>
                    <input type="text" name="street-form" id="street-form">
                    <input type="button" value="修改" id="edit-submit" class="edit-submit">
                    <input type="button" value="取消" id="cancel" class="cancel">
                </form>
            </div>
            <div class="headshot-edit">
                <h3>編輯個人頭像</h3>
                <img id="headshot-img" class="imgdata" src="" alt=""> 
                <div class="actions"> 
                    <button class="file-btn" id="file-btn"> 
                    <span>選擇檔案</span> 
                    <input type="file" id="upload" value="選擇圖片檔案" accept="image/*"> 
                    </button>
                    <div class="popup-wrap">
                        <div class="crop"> 
                            <div id="upload-demo"></div> 
                            <button class="upload-result">確定</button> 
                            <button id="crop-cancel">取消</button>
                        </div> 
                    </div>
                </div> 
            </div>`;
    //店家資料template
    let SellerProfile = `<h2>店家資料</h2>
    <div class="shop-form">
        <form action="" id="shop-form" name="shop-form">
            <h3>編輯商店資料</h3>
            <div id="shop-prompt"></div>
            <label for="shop-name">商店名稱</label><br>
            <input type="text" name="shop-name" id="shop-name" class="shop-name" maxlength="30">
            <div id="shop-name-prompt"></div>
            <label for="shop-content-form">商店介紹</label><br>
            <div id="shop-content-form" class="shop-content-form" contenteditable="true"></div>
            <div id="shop-content-prompt"></div>
            <label for="shop-logo-form">商店Logo</label><a href="javascript:void(0)" id="edit-logo"><i class="far fa-edit"></i></a><br>
            <input type="file" name="file-logo" id="file-logo" accept="image/*">
            <div id="shop-logo-form"></div>

            <label for="shop-banner-form">商店Banner</label><a href="javascript:void(0)" id="edit-banner"><i class="far fa-edit"></i></a><br>
            <input type="file" name="file-banner" id="file-banner" accept="image/*">
            <div id="shop-banner-form"></div>

            <input type="button" value="上傳修改" id="shop-edit-submit" class="edit-submit">


            <div class="popup-wrap-logo" id="popup-wrap-logo">
                <div class="crop-logo" id="crop-logo">
                    <div id="upload-demo-logo"></div>
                    <input type="button" value="確定" id="upload-result-logo" class="upload-result-logo">
                    <input type="button" value="取消" id="crop-cancel-logo" class="crop-cancel-logo">
                </div>
            </div>

            <div class="popup-wrap-banner" id="popup-wrap-banner">
                <div class="crop-banner" id="crop-banner">
                    <div id="upload-demo-banner"></div>
                    <input type="button" value="確定" id="upload-result-banner" class="upload-result-banner">
                    <input type="button" value="取消" id="crop-cancel-banner" class="crop-cancel-banner">
                </div>
            </div>
        </form>
    </div>`;

    var resultData;
    let logoBlob; //商店logo照片
    let bannerBlob; //商店banner照片


    let urlParams = new URLSearchParams(window.location.search);
    let param = urlParams.get("action");
    if (param == "profile") {
        $('#content').html(profile);
    }

    if (param == "sellerProfile") {
        $('#content').html(SellerProfile).promise().done(function() {
            //取得商店logo和banner的blob後新增img物件顯示
            getLogoAndBanner();
            //取得商店資料
            getSellerShop();
        });
    }

    //profile跟資料庫請求個人資料
    Ajaxprofile();
    //跟資料庫請求個人頭像
    Ajaxheadshot();

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



    //編輯按鈕事件
    $('#content').on('click', '#edit', function() {
        //呼叫縣市及鄉鎮區選單並預設初始值
        $('#twzipcode').twzipcode({
            'zipcodeSel': '106',
            'countySel': '臺北市',
            'districtSel': '中正區'
        });
        $('#prompt').text("");
        $('#phone-form').css('border', '1px solid #27da80');
        $('#phone-form').css('box-shadow', '');
        $('#phone-prompt').text("");
        $('#name-form').css('border', '1px solid #27da80');
        $('#name-form').css('box-shadow', '');
        $('#name-prompt').text("");
        $('.profile-form').css('display', 'block');
        $('.profile').css('display', 'none');
        $('#name-form').val(resultData.name);
        $('#phone-form').val(resultData.phone);
        $('#street-form').val(resultData.street);
        if (resultData.city != null) {
            $('select[name="city"]').val(resultData.city);
            $('select[name="city"]').change();
        }
        if (resultData.cityarea != null) {
            $('select[name="cityarea"]').val(resultData.cityarea);
        }
        checkFlag();
    });



    //取消按鈕事件
    $('#content').on('click', '#cancel', function() {
        $('.profile-form').css('display', 'none');
        $('.profile').css('display', 'block');
    });


    //設置驗證旗幟
    let name_flag = true;
    let phone_flag = true;

    //確認旗幟均為true按鈕才能按
    function checkFlag() {
        if (name_flag && phone_flag) {
            $("#edit-submit").removeAttr("disabled")
        } else {
            $("#edit-submit").attr("disabled", "disabled")
        }
    }

    //監聽會員名稱格式是否正確
    $('#content').on('input', '#name-form', function() {
        $('#name-prompt').text("");
        if ($('#name-form').val() != "") {
            $('#name-form').css('border', '1px solid #27da80');
            $('#name-form').css('box-shadow', '');
            name_flag = true;
        } else {
            $('#name-prompt').text("會員名稱不得為空");
            $('#name-prompt').css('color', 'red');
            $('#name-prompt').css('font-size', '10px');
            $('#name-form').css('border', '2px solid red');
            $('#name-form').css('box-shadow', '0 0 0 2px #f1a7c0');
            name_flag = false;
        }
        checkFlag();
    });

    //監聽電話格式是否正確
    $('#content').on('input', '#phone-form', function() {
        $('#phone-prompt').text("");
        if (validatePhone()) {
            $('#phone-form').css('border', '1px solid #27da80')
            $('#phone-form').css('box-shadow', '');
            phone_flag = true;
        } else {
            $('#phone-prompt').text("請輸入有效的手機號碼");
            $('#phone-prompt').css('color', 'red');
            $('#phone-prompt').css('font-size', '10px');
            $('#phone-form').css('border', '2px solid red');
            $('#phone-form').css('box-shadow', '0 0 0 2px #f1a7c0');
            phone_flag = false;
        }
        checkFlag();
    });

    //電話正則表達式驗證
    function validatePhone() {
        let phone = $('#phone-form').val();
        const re = /^09[0-9]{8}$/;
        return re.test(phone);
    }

    //送出修改表單
    $('#content').on('click', '#edit-submit', function() {
        $.ajax({
            type: "post",
            url: "../../member/updateBuyProfileServlet",
            data: $('#profile-form').serialize(),
            success: function(result) {
                if (result == "1") {
                    window.location.reload();
                } else {
                    $('#prompt').text("更新失敗，請重新填寫");
                    $('#prompt').css('color', 'red');
                    $('#prompt').css('font-size', '10px');
                }
            }
        });
    });


    var $uploadCrop;
    //讀取上傳圖片
    function readFile(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $uploadCrop.croppie('bind', {
                    url: e.target.result
                });
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    //設定croppie初始值
    $uploadCrop = $('#upload-demo').croppie({
        viewport: {
            width: 200,
            height: 200,
            type: 'circle',
        },
        showZoomer: false,
        boundary: {
            width: 300,
            height: 300
        }
    });

    //點擊button觸發input[type="file"]
    $('#content').on('click', '#file-btn', function() {
        document.getElementById('upload').click();
    });

    //點選選擇檔案後開啟圈選範圍面板
    $('#content').on('change', '#upload', function() {
        $(".popup-wrap").fadeIn(250);
        $(".crop").fadeIn(250);
        readFile(this);
    });

    //關閉圈選面板
    $('#content').on('click', '#crop-cancel', function() {
        $(".crop").fadeOut();
        $(".popup-wrap").fadeOut(250);
        $('#upload').val("");
    });

    //送出圈選後的頭像至資料庫
    $('#content').on('click', '.upload-result', function() {
        $uploadCrop.croppie('result', 'blob').then(function(blob) {
            var myForm = new FormData();
            myForm.append("blob", blob);
            $.ajax({
                type: "post",
                enctype: 'multipart/form-data',
                url: "../../member/headshotBuyServlet",
                data: myForm,
                processData: false,
                contentType: false,
                xhrFields: {
                    // 將回傳結果以Blob保持原本二進位的格式回傳
                    //jquery的dataType無法設定返回格式為blob需要手動修改
                    responseType: "blob"
                },
                success: function(result) {
                    window.location.reload();
                }
            });
        });
    });


    /*********以下為店家資料js **********/

    //取得商店logo照片
    function AjaxLogo() {
        return new Promise(function(resolve, reject) {
            $.ajax({
                url: "../../member/memImgServlet",
                type: "post",
                data: {
                    "action": "logoBlob",
                },
                xhrFields: {
                    responseType: "blob"
                },
                success: function(res) {
                    if (res.size != 0) {
                        resolve(res);
                    }
                },
                error: function(res) {
                    reject("error");
                }
            });
        });
    }
    //取得商店banner照片
    function AjaxBanner() {
        return new Promise(function(resolve, reject) {
            $.ajax({
                url: "../../member/memImgServlet",
                type: "post",
                data: {
                    "action": "bannerBlob",
                },
                xhrFields: {
                    responseType: "blob"
                },
                success: function(res) {
                    if (res.size != 0) {
                        resolve(res);
                    }
                },
                error: function(res) {
                    reject("error");
                }
            });
        });
    }

    //取得商店logo和banner的blob後新增img物件顯示
    async function getLogoAndBanner() {
        logoBlob = await AjaxLogo();
        bannerBlob = await AjaxBanner();
        let logoUrl = URL.createObjectURL(logoBlob);
        let bannerUrl = URL.createObjectURL(bannerBlob);
        let logoImg = new Image()
        let bannerImg = new Image()
        logoImg.src = logoUrl;
        logoImg.width = 200;
        bannerImg.src = bannerUrl;
        bannerImg.width = 500;
        $('#shop-logo-form').append(logoImg);
        $('#shop-banner-form').append(bannerImg);
    };

    //取得商店資料
    function getSellerShop() {
        let shopForm = new FormData();
        shopForm.append("action", "getSellerShop");
        $.ajax({
            type: "post",
            url: "../../member/sellerProfileServlet",
            data: shopForm,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            dataType: "json",
            success: function(response) {
                $('#shop-name').val(response.mem_shop_name);
                $('#shop-content-form').html(response.mem_shop_content);
            },
            error: function(response) {
                alert("修改失敗,系統發生異常");
            }
        });
    }

    //點擊logo圖示開啟file物件
    $('#content').on('click', '#edit-logo', function() {
        $('#file-logo').click();
    });

    //點擊banner圖示開啟file物件
    $('#content').on('click', '#edit-banner', function() {
        $('#file-banner').click();
    });

    //點擊logo裁切畫面的取消關閉面板
    $('#content').on('click', '#crop-cancel-logo', function() {
        $('#popup-wrap-logo').fadeOut();
        $('#crop-logo').fadeOut();
        $('#file-logo').val("");
    })

    //點擊banner裁切畫面的取消關閉面板
    $('#content').on('click', '#crop-cancel-banner', function() {
        $('#popup-wrap-banner').fadeOut();
        $('#crop-banner').fadeOut();
        $('#file-banner').val("");
    })

    //讀取Logo上傳圖片
    function readFileLogo(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                uploadLogo.croppie('bind', {
                    url: e.target.result
                });
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    //讀取Banner上傳圖片
    function readFileBanner(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                uploadBanner.croppie('bind', {
                    url: e.target.result
                });
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    //設定Logo-圖片裁切初始值
    let uploadLogo = $('#upload-demo-logo').croppie({
        viewport: {
            width: 200,
            height: 200,
        },
        showZoomer: false,
        boundary: {
            width: 300,
            height: 300
        }
    });

    //設定Banner-圖片裁切初始值
    let uploadBanner = $('#upload-demo-banner').croppie({
        viewport: {
            width: 500,
            height: 300,
        },
        showZoomer: false,
        boundary: {
            width: 600,
            height: 400
        }
    });

    //點擊logo裁切畫面的確定將圖片append到logo展示div
    $('#content').on('click', '#upload-result-logo', function() {
        $('#popup-wrap-logo').fadeOut();
        $('#crop-logo').fadeOut();
        uploadLogo.croppie('result', 'blob').then(function(blob) {
            let url = URL.createObjectURL(blob);
            let image = new Image();
            image.src = url;
            $('#shop-logo-form').html("");
            $('#shop-logo-form').append(image);
            // shopForm.append("logoBlob", blob);
            logoBlob = blob;
        });
    });

    //點擊banner裁切畫面的確定將圖片append到banner展示div
    $('#content').on('click', '#upload-result-banner', function() {
        $('#popup-wrap-banner').fadeOut();
        $('#crop-banner').fadeOut();
        uploadBanner.croppie('result', 'blob').then(function(blob) {
            let url = URL.createObjectURL(blob);
            let image = new Image();
            image.src = url;
            $('#shop-banner-form').html("");
            $('#shop-banner-form').append(image);
            // shopForm.append("bannerBlob", blob);
            bannerBlob = blob;
        });
    });

    //logo選擇圖片後綁定事件，開啟裁切畫面並讀取圖片
    $('#content').on('change', '#file-logo', function() {
        $('#popup-wrap-logo').fadeIn();
        $('#crop-logo').fadeIn();
        readFileLogo(this);
    });

    //banner選擇圖片後綁定事件，開啟裁切畫面並讀取圖片
    $('#content').on('change', '#file-banner', function() {
        $('#popup-wrap-banner').fadeIn();
        $('#crop-banner').fadeIn();
        readFileBanner(this);
    });

    //限制商店介紹字數
    $('#content').on('keydown', '#shop-content-form', function(e) {
        let KeyID = e.keyCode;
        if (KeyID != 46 && KeyID != 8) {
            if (this.innerText.length > 240) {
                e.preventDefault();
            }
        }
    });

    //送出表單
    $('#content').on('click', '#shop-edit-submit', function() {
        if (checkShopName() && checkShopContent()) {
            let shopForm = new FormData();
            let shopContentForm = $('#shop-content-form').html();
            let shopName = $('#shop-name').val();
            shopForm.append("action", "update");
            shopForm.append("shopContentForm", shopContentForm);
            shopForm.append("shopName", shopName);
            shopForm.append("logoBlob", logoBlob);
            shopForm.append("bannerBlob", bannerBlob);

            $.ajax({
                type: "post",
                url: "../../member/sellerProfileServlet",
                data: shopForm,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                dataType: "json",
                success: function(response) {
                    if (response) {
                        alert("修改成功");
                        window.location.reload();
                    } else {
                        alert("修改失敗，請重新檢查表單內容");
                    }
                },
                error: function(response) {
                    alert("修改失敗,系統發生異常");
                }
            });
        } else {
            alert("內容尚未填寫完整")
        }
    });


    $('#content').on('input', '#shop-name', checkShopName)
        //確認商店名稱是否有值
    function checkShopName() {
        let shopName = $('#shop-name').val();
        if (shopName == "") {
            $('#shop-name-prompt').text("請輸入內容");
            $('#shop-name-prompt').css('color', 'red');
            $('#shop-name-prompt').css('font-size', '10px');
            $('#shop-name').css('border', '2px solid red');
            $('#shop-name').css('box-shadow', '0 0 0 2px #f1a7c0');
            return false;
        } else {
            $('#shop-name-prompt').text("");
            $('#shop-name').css('border', '1px solid #27da80')
            $('#shop-name').css('box-shadow', '');
            return true;
        }
    }

    $('#content').on('input', '#shop-content-form', checkShopContent)
        //確認商店內容是否有值
    function checkShopContent() {
        let shopContent = $('#shop-content-form').html();
        if (shopContent == "") {
            $('#shop-content-prompt').text("請輸入內容");
            $('#shop-content-prompt').css('color', 'red');
            $('#shop-content-prompt').css('font-size', '10px');
            $('#shop-content-form').css('border', '2px solid red');
            $('#shop-content-form').css('box-shadow', '0 0 0 2px #f1a7c0');
            return false;
        } else {
            $('#shop-content-prompt').text("");
            $('#shop-content-form').css('border', '1px solid #27da80')
            $('#shop-content-form').css('box-shadow', '');
            return true;
        }
    }
    /*********以上為店家資料js **********/
});