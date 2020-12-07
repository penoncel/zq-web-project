<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <script src="/static/layuiadmin/layui/layui.js"></script>
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 50px 0 0;">
    <form class="layui-form" action="" lay-filter="component-form-element">

        <input type="hidden" name="id" value="${(webRole.id?c)!''}"/>
        <div class="layui-form-item">
            <label class="layui-form-label">角色：</label>
            <div class="layui-input-block">
                <input type="text" name="role" required lay-verify="required|role" value="${(webRole.role)!''}" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">名称：</label>
            <div class="layui-input-block">
                <input type="text" name="name" required lay-verify="required" value="${(webRole.name)!''}" placeholder="请输入昵称" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">说明：</label>
            <div class="layui-input-block">
                <textarea name="note" placeholder="请输入内容" class="layui-textarea">${(webRole.note)!''}</textarea>
            </div>
        </div>


        <div class="layui-form-item layui-hide">
            <input type="button" lay-submit lay-filter="LAYFrontSubmit" id="LAYFrontSubmit" value="确认">
        </div>
</form>


</div>

<script>
    layui.use(['form'], function() {
        var form = layui.form ,$ = layui.$;

        form.verify({
            role: function(value, item){ //value：表单的值、item：表单的DOM对象
                if(!new RegExp("^[a-zA-Z]+$").test(value)){
                    return '角色只能是大写或小写字母';
                }
            }
        });

    });
</script>



</body>
</html>