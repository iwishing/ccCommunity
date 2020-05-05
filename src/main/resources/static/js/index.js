$(function () {
    //登录
    $('#longinButton').click(function () {
        var username = $('#loginusername').val().trim();
        var password = $('#loginpassword').val().trim();

        var loginMessage = document.getElementById("loginMessage");
        var loginMessageContext = document.getElementById("loginMessageContext");
        loginMessage.style.display="none";

        //封装user
        var param = {
            username: username,
            password: password
        }
        $.ajax({
            type: 'post',
            url: '/userLogin',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(param),
            success:function (data) {
                if (data == "success"){
                    window.location.href = '/';
                }else {
                    loginMessage.style.display="block";
                    loginMessageContext.innerHTML=data;
                    return false;
                }
            },
            error:function (err) {
                console.log(err);
                return false
            }
        })
        return false;
    })
//注册按钮
    $('#registerButton').click(function () {
        var username = $('#registerUsername').val().trim();
        var password = $('#registerPassword').val().trim();
        var repassword = $('#registerRePassword').val().trim();
        var obtainCode = $('#checkcode').val().trim();

        //检验两次密码是否一致
        var registerMessage = document.getElementById("registerMessage");
        var registerMessageContext = document.getElementById("registerMessageContext");
        registerMessage.style.display="none";

        if (password != repassword){
            registerMessage.style.display="block";
            registerMessageContext.innerHTML="两次密码不一致！";
            return false;
        }
        //封装id，密码，验证码
        var param = {
            username: username,
            password: password,
            obtainCode: obtainCode,
        }
        //发送异步请求
        $.ajax({
            type: 'post',
            url: '/userRegister',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(param),
            success:function (data) {
                if (data == "success"){
                    window.location.href = '/';
                }else {
                    registerMessage.style.display="block";
                    registerMessageContext.innerHTML=data;
                    return false;
                }
            },
            error:function (err) {
                console.log(err);
                return false
            }
        })
        return false
    })
})

function codeFlush(val) {
    var waitcount=60;
    if(waitcount == 0){
        val.removeAttribute("disabled");
        val.value = "获取验证码";
        waitcount = 60;

    }else{
        val.setAttribute("disabled",true);
        val.value = "倒计时("+waitcount+")";
        waitcount--;
    }
}
// 获取验证码
function obtainCode(val) {
    var userName=$("#registerUsername").val();
    codeFlush(val);

    setTimeout(function () {
        codeFlush(val);
    },1000);

    $.ajax({
        type: 'post',
        url: 'obtainCode',
        dataType: "text",
        data:  "userName="+userName,
        success:function (data) {

        }
    })
    alert(userName);
}
