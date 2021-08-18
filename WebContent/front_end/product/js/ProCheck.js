//旗幟管控
$(function(){
    let n_flag = false;
    let p_flag = false;
    let s_flag =false;
    let c_flag =false;

//全部都成立才能送出
    function checkflag(){
       if (n_flag&&p_flag&&s_flag&&c_flag){
           $("#submit").removeAttr("disabled")
       }else{
           $("#submit").attr("disabled","disabled")
       }
    }

    $('#login').on('click',function(){
        if(validateUsername() && validatePassword()){
            return true;
        }else{
            return false;
        }
    });

//監聽輸入

    $("#pro_name").on('input',function(){
        if(($("#pro_name").val()).trim() !=""){
            $("#pro_name").removeClass("error").addClass("ok");
            n_flag = true;
            $("#checkpoint").html("");
        }else{
            $("#pro_name").removeClass("ok").addClass("error");
            n_flag = false;
            $("#checkpoint").html("商品名稱尚未填寫");
        }
        checkflag();
    })
    $("#pro_price").on('input',function(){
        if($("#pro_price").val() > 0){
            $("#pro_price").removeClass("error").addClass("ok");
            p_flag = true;
            $("#checkpoint").html("");
        }else{
            $("#pro_price").removeClass("ok").addClass("error");
            p_flag = false;
            $("#checkpoint").html("商品價格格式錯誤")
        }
        checkflag();
    })

    $("#pro_status").on('input',function(){
        if($('input[name=pro_status]:checked').val() == (1 || 0) ){
            // $("#pro_status").removeClass("error").addClass("ok");
            s_flag = true;
            $("#checkpoint").html("");
        }else{
            // $("#pro_status").removeClass("error").addClass("ok");
            s_flag = false;
            $("#checkpoint").html("商品狀態尚未選擇")
        }
        checkflag();
    })

    $("#pro_content").on('input',function(){
        if(($("#pro_content").val()).trim() !=""){
            $("#pro_content").removeClass("error").addClass("ok");
            c_flag = true;
            $("#checkpoint").html("");
        }else{
            $("#pro_content").removeClass("ok").addClass("error");
            c_flag = false;
            $("#checkpoint").html("商品內容尚未填寫")
        }
        checkflag();
    })
});