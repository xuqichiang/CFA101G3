<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/back_end/admin/css/cssreset.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/back_end/admin/css/login.css">
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <title>嫁給幸福後台系統登入｜MarryHappiness</title>
</head>
<body>
    <div class="login-wrap">
        <div class="login-container">
            <a href="#"><img src="<%=request.getContextPath() %>/back_end/admin/images/MHlogo_03.svg" alt=""></a>
            <h2>登入您的 MarryHappiness 嫁給幸福管理員帳號</h2>
            <form action="<%=request.getContextPath()%>/admin/adminServlet" class="login-form" method="post">
                <label for="username">Email</label><br>
                <input type="text" id = "username" name="username" placeholder="請輸入您的Email">
                <h3 id="u-prompt" class="u-prompt">${errorMsgs.username}</h3>
                <label for="password">密碼</label><br>
                <input type="password" id="password" name="password" maxlength="20" placeholder="請輸入您的密碼">
                <h3 id="p-prompt" class="p-prompt">${errorMsgs.password}</h3>
                <input type="submit" id="login" value="登入">
                <input type="hidden" name="action" value="login"/>
            </form>
        </div>
    </div>
    <script>
        if($('#u-prompt').html()!= ""){
            $('#username').css('border','2px solid red');
        }
        if($('#p-prompt').html()!= ""){
            $('#password').css('border','2px solid red');
        }

        //送出表單驗證
        $('#login').on('click',function(){
            if(validateUsername() && validatePassword()){
                return true;
            }else{
                return false;
            }
        });


        //監聽帳號格式是否正確
        $('#username').on('input',function(){
            $('#u-prompt').text("");
            if(validateUsername()){
                $('#username').css('border','2px solid #27da80')
            }else{
                $('#u-prompt').text("請輸入有效的電子郵件地址");
                $('#u-prompt').css('color','red');
                $('#u-prompt').css('font-size','10px');
                $('#username').css('border','2px solid red')
            }
        });

        //監聽密碼格式是否正確
        $('#password').on('input',function(){
            $('#p-prompt').text("");
            if(validatePassword()){
                $('#password').css('border','2px solid #27da80')
            }else{
                $('#p-prompt').text("密碼長度限制6-20");
                $('#p-prompt').css('color','red');
                $('#p-prompt').css('font-size','10px');
                $('#password').css('border','2px solid red')
            }
        });

        //帳號正則表達式驗證
        function validateUsername() {
            let username = $('#username').val();
            const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(username);
        }

        //密碼正則表達式驗證
        function validatePassword(){
            let password = $('#password').val();
            const re = /^[0-9A-Za-z]{6,20}$/;
            return re.test(password);
        }
    </script>
</body>
</html>