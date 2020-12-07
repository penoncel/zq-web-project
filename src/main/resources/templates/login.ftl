<#include 'common/default.ftl'>
<@header title='XXXMasterLogin'>
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" type="text/css" href="/static//layuiadmin/style/login.css" media="all">
    <script src="/static/layuiadmin/jquery-1.7.2.min.js"></script>
    <script src="/static/modules/Login/jquery.cookie.js"></script>
</@header>
<body>
<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>XXXXXX</h2>
            <p>XX</p>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-username" ></label>
                <input type="text" id="username" name="username" required lay-verify="required|name" placeholder="用户名" value="test" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password" ></label>
                <input type="password" name="password" required lay-verify="required|pwd" placeholder="密码" value="zq123456" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-item">
                <div class="layui-row">
                    <div class="layui-col-xs7">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" ></label>
                        <input type="text" id="validateCode" name="validateCode" value="12345" required lay-verify="required|code" placeholder="图形验证码"  autocomplete="off" class="layui-input" maxlength="5">
                    </div>
                    <div class="layui-col-xs5">
                        <div style="margin-left: 10px;">
                            <img id="loginValidateCode" src="/captcha"  class="layadmin-user-login-codeimg" id="LAY-user-get-vercode" onclick="uploadLoginValidateCode();" >
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 20px;">
                <input id="rememberMe" type="checkbox" name="rememberMe"  lay-filter="rememberMe" lay-skin="primary" title="记住我">
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登 入</button>
            </div>
            <div class="layui-trans layui-form-item layadmin-user-login-other">
                <label>社交账号登入</label>
                <a href="javascript:;"><i class="layui-icon layui-icon-login-wechat"></i></a>
            </div>
        </div>
    </div>

    <div class="layui-trans layadmin-user-login-footer">
        <p>© 2020 <a href="#" target="_blank">xxx</a></p>
        <p>
            <span><a href="" target="_blank">XXX</a></span>
        </p>
    </div>
</div>
    <@myJs>
        <script src="/static/modules/Login/login.js"></script>
        <script src="/static/modules/Login/ajaxSetup.js"></script>
    </@myJs>
</body>
</html>