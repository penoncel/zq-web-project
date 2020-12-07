<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
    <script src="/static/layuiadmin/layui/layui.js"></script>

    <script src="/static/modules/common/config.js"></script>
    <script src="/static/modules/common/common.js"></script>

</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">修改密码</div>
                <div class="layui-card-body" pad15>
                    <form class="layui-form" action="" lay-filter="component-form-element">
                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <input type="hidden" name="id" value="${(id)!''}"/>
                            <label class="layui-form-label">旧密码</label>
                            <div class="layui-input-inline">
                                <input type="password" name="oldPassword" lay-verify="required" lay-verType="tips" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" name="password" lay-verify="pass" lay-verType="tips" autocomplete="off" id="LAY_password" class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux">数字和字母组合,且长度在6~16</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">确认新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" name="repassword" lay-verify="repass" lay-verType="tips" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="setmypass">确认修改</button>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>


<script>
    var $ ;
    layui.use(['form'], function () {
        $ = layui.$, form = layui.form;

        form.verify({
            pass: function (value, item) {
                var reg = new RegExp(/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/);
                if (!reg.test(value)) {
                    return '数字和字母组合,且长度在6~16';
                }
            },
            repass: function (value, item) {
                var password = $('input[name="password"]').val();
                if (password !== value) {
                    return '两次新密码不一致';
                }
            }
        });

        form.on('submit(setmypass)', function (data) {
            reqAjax.aPostBackNoContnet('/WebUser/editUserPwd', data.field,function (data) {
                if(data.code=='200' || data.code=='TimeOut'){
                    Msg.TimeOut(data);
                }else {
                    layer.alert(data.msg, {icon: -2})
                }
            });
            return false;
        });


    });
</script>
</body>
</html>