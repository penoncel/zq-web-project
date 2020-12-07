var form,$;
layui.use(['form'], function() {
     form = layui.form,$ = layui.$;
    if($.cookie("remember_user")!=null && $.cookie("username")!=''){
        $("#username").val($.cookie("username"));
        $("#rememberMe").attr("checked", true);
        form.render();
    }
    form.on('checkbox(rememberMe)',function (data) {
        form.render();
    })

    form.verify({
        name:[/^[a-zA-Z0-9]+$/,'格式不正确(数字、字母)'],
        pwd: [/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/ ,'数字和字母组合,且长度在6~16位之间'],
        code:function (value) {
            var result = checkCode(value);
            if(result !='200'){
                return result;
            }
        }
    });

    form.on('submit(login)', function(data){
        var name = data.field.username;
        var pwd = data.field.password;
        var rememberMe = data.field.rememberMe;

        if(data.field.rememberMe=="on"){
            $.cookie("remember_user","true",{ expires: 7 })
            $.cookie("username",name,{ expires: 7 })
        }else {
            rememberMe=false;
            $.cookie("remember_user",'false',{ expires: -1})
            $.cookie("username",'',{ expires: -1 })
        }
        reqAjax.aPostNoContnet('/WebUser/goLogin',{"username" : name,"password" : pwd,"rememberMe":rememberMe});
        return false;
    });

});

function checkCode(value) {
    var result='';
    $.ajax({
        type: "POST",
        async:false,
        url: "/captchaValidateCode?validateCode="+value,
        success : function(data) {
            if(data.code!="200"){
                result = data.msg
                if(data.code == "501"){
                    uploadLoginValidateCode();
                }
            }
        },
        error:function(){
            result = "验证码服务异常!";
        }
    });
    return result;
}

function uploadLoginValidateCode() {
    $("#loginValidateCode").attr("src","/captcha?random="+new Date().getMilliseconds());
}


