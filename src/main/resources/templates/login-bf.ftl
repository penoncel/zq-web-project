<!DOCTYPE html>
<html lang="en" >
<head>
    <title>欢迎使用久久付PLUS后台</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="shortcut icon" href="/static/zzpic/sand.ico">
    <link rel="stylesheet" type="text/css" href="/static/admin/layui/css/layui.css" />
    <link rel="stylesheet" type="text/css" href="/static/admin/css/login.css" />
    <script src="/static/layuiadmin/layui/layui.js"></script>
    <script src="/static/layuiadmin/jquery-1.7.2.min.js"></script>
</head>


<body>
<div class="m-login-bg">
    <div class="m-login">

        <div class="m-login-warp">
            <form class="layui-form">
                <!--<h3>久久付Plus版后台</h3>-->
                <div class="layui-form-item">
                    <h2><img  src="/static/zzpic/jiujiufu-left-max.png"></h2>
                </div>
                <div class="layui-form-item">
                    <input type="text" name="username" required lay-verify="required|name" placeholder="用户名" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-item">
                    <input type="password" name="password" required lay-verify="required|pwd" placeholder="密码" autocomplete="off" class="layui-input">
                </div>

                <div class="layui-form-item">
                    <div class="layui-inline">
                        <div class="layui-input-inline" style="width: 100px;">
                            <input type="text" id="validateCode" name="validateCode" required lay-verify="required|code" placeholder="验证码"  autocomplete="off" class="layui-input" maxlength="5">
                        </div>
                        <div class="layui-input-inline" style="width: 100px;">
                            <img id="loginValidateCode" height="40" width="150"  style="cursor: pointer;" src="/captcha" onclick="uploadLoginValidateCode();">
                        </div>
                    </div>
                </div>

                <div class="layui-form-item m-login-btn">
                    <div class="layui-inline">
                        <!--layui-btn-disabled-->
                        <button class="layui-btn layui-btn-normal " lay-submit lay-filter="login">登录</button>
                    </div>
                    <div class="layui-inline">
                        <button type="reset" class="layui-btn layui-btn-primary">取消</button>
                    </div>
                </div>

            </form>
        </div>

        <p class="copyright">Copyright </p>
    </div>
</div>
<script src="/static/admin/login.js"></script>
<script src="/static/ajaxSetup.js"></script>
</body>

</html>
