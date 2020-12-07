var form,$;
layui.use(['form'], function() {
     form = layui.form
        ,$ = layui.$;
    //输入时验证
    // $("#validateCode").keyup(function(){
    //     checkLoginValidateCode($(this).val());
    // });

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
        $.ajax({
            url: "/WebUser/goLogin",
            type:"POST",
            dataType:"json",
            data: {"username" : name,"password" : pwd},
            success:function(data){
                console.log(data);
                if(data.code=='0'){
                    layer.msg(data.msg,{icon: 1,time:1000,shade: 0.5},function(){
                        top.location.href="/index";
                    });
                }else{
                    layer.msg(data.msg, {icon: 5})
                }
            },
            error:function(){
                layer.alert("请求错误", {icon: -2})
            }
        });
        return false;
    });

});

function checkCode(value) {
    var result='';
    $.ajax({
        type: "POST",
        async:false,
        url: "/captchaValidateCode?validateCode="+value,
        success : function(json) {
            if(json != null && json.code == 200 && json.status != null) {
                if (json.status == true) {
                    result = 200;
                } else
                if(json.status == false){
                    result =  "验证码错误，请重新输入";
                }else{
                    result = "验证码过期，请重新输入";
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

//初始化验证码
function uploadLoginValidateCode() {
    $("#loginValidateCode").attr("src","/captcha?random="+new Date().getMilliseconds());
}


