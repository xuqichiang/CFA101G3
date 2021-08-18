$(function(){
    let setting = 
    `<h2>帳號設定</h2>
    <div class="setting">
        <span>帳號信箱</span><span id="email-status"></span><br>
        <div id="username"></div>
        <span>登入密碼</span>
        <input type="button" value="修改密碼" id="password-edit" class="password-edit">
        <div class="edit-password-wrap">
        <div class="edit-password">
            <label for="new-password">請輸入您的新密碼</label><br>
            <input type="password" id="new-password" name="new-password" class="new-password">
            <p id="new-prompt"></p>
            <label for="check-password">請再次輸入您的新密碼</label><br>
            <input type="password" id="check-password" name="check-password" class="check-password">
            <p id="check-prompt"></p>
            <button id="edit-password-submit" class="edit-password-submit">確定</button> 
            <button id="edit-password-cancel" class="edit-password-cancel">取消</button>
        </div> 
    </div>
    </div>`;
    let urlParams = new URLSearchParams(window.location.search);
    let param = urlParams.get("action");
    if(param=="setting"){
        $('#content').html(setting);
        //跟資料庫請求個人資料
        $.ajax({
            type:"get",
            url:"../../member/buyProfileServlet",
            dataType : 'json',
            success:function (result) {
                if(result == "0"){
                    window.location.href="index.html";
                }else{
                    $('#username').html(result.username);
                    if(result.mem_status==1){
                        $('#email-status').html(`<i class="fas fa-check">已驗證`);
                        $('#email-status').css("color","#33ba4a");
                    }else{
                        $('#email-status').html(`<i class="fas fa-times"></i>未驗證`);
                        $('#email-status').css("color","red");
                    }
                }
            }
        });
    }
    
    //顯示修改密碼頁面
    $('#content').on('click','#password-edit',function(){
        $(".edit-password-wrap").fadeIn(250);
        $(".edit-password").fadeIn(250); 
    });
    //關閉修改密碼頁面
    $('#content').on('click','#edit-password-cancel',function(){
        $('#new-password').val("");
        $('#check-password').val("");
        $('#new-prompt').html("");
        $('#check-prompt').html("");
        $('#new-password').css('border','1px solid #a1a0a0')
        $('#check-password').css('border','1px solid #a1a0a0')
        $(".edit-password-wrap").fadeOut(250);
        $(".edit-password").fadeOut(250); 
    });
     //監聽新密碼格式是否正確
    $('#content').on('input','#new-password',validateNewPassword);
    function validateNewPassword(){
        let newPassword = $('#new-password').val();
        const re = /^[0-9A-Za-z]{6,20}$/;
        $('#new-prompt').text("");
        if($('#check-password').val()!=""){
            checkNewPassword();
        }
        if(re.test(newPassword)){
            $('#new-password').css('border','2px solid #27da80')
            return true;
        }else{
            $('#new-prompt').text("密碼長度限制6-20");
            $('#new-prompt').css('color','red');
            $('#new-prompt').css('font-size','10px');
            $('#new-password').css('border','2px solid red')
            return false;
        }
    };
    //確認新密碼是否相同
    $('#content').on('input','#check-password',checkNewPassword);
    function checkNewPassword(){
        $('#check-prompt').text("");
        if($('#new-password').val() == $('#check-password').val()){
            $('#check-password').css('border','2px solid #27da80')
            return true;
        }else{
            $('#check-prompt').text("密碼不相同");
            $('#check-prompt').css('color','red');
            $('#check-prompt').css('font-size','10px');
            $('#check-password').css('border','2px solid red')
            return false;
        }
    }

    //送出修改密碼表單
    $('#content').on('click','#edit-password-submit',function(){
        if(validateNewPassword() && checkNewPassword()){
            let newPassword = $('#new-password').val();
            $.ajax({
                type:"post",
                url:"../../member/passwordServlet",
                data:{
                    "action":"update",
                    "newPassword":newPassword
                },
                success:function(result){
                    if(result == "success"){
                        swal("恭喜您!", "密碼已修改成功", "success").then((result) => {
                            window.location.reload();
                        });
                    }
                }
            })
        }
    });
   
});