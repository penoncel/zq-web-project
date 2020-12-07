<#include '../../common/default.ftl'>
<@header />
<body>
<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 50px 0 0;">
    <form class="layui-form" action="" lay-filter="component-form-element">

        <input type="hidden" name="id" value="${(webUser.id?c)!''}"/>
        <input type="hidden" id="testlever" value="${(webUser.lever)!''}"/>
        <div class="layui-form-item">
            <label class="layui-form-label">用户名：</label>
            <div class="layui-input-block">
                <input type="text" name="username" required lay-verify="required|username" value="${(webUser.username)!''}" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">昵称：</label>
            <div class="layui-input-block">
                <input type="text" name="nickname" required lay-verify="required" value="${(webUser.nickname)!''}" placeholder="请输入昵称" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">手机号：</label>
            <div class="layui-input-block">
                <input type="tel" name="phone" required lay-verify="required|phone" value="${(webUser.phone)!''}" placeholder="请输入手机号" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">角色类型：</label>
            <div class="layui-input-block">
                <select id="select_lever" name="lever"  lay-verify="required" >
                    <option value="">直接选择或搜索选择</option>
                    <#if role ??>
                        <#list role as list>
                            <option value="${list.Id}">${list.name}</option>
                        </#list>
                    </#if>
                </select>
            </div>
        </div>

       <div class="layui-form-item layui-hide">
            <input type="button" lay-submit lay-filter="LAYFrontSubmit" id="LAYFrontSubmit" value="确认">
        </div>

    </form>
</div>
<@myJs/>
<script>
    var form ,$ ;
    layui.use(['form'], function() {
         form = layui.form ,$ = layui.$;
        _LAY.checkSelect('select_lever',$("#testlever").val());
        form.verify({
            username: function(value, item){ //value：表单的值、item：表单的DOM对象
                if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                    return '用户名不能有特殊字符';
                }
                if(/(^\_)|(\__)|(\_+$)/.test(value)){
                    return '用户名首尾不能出现下划线\'_\'';
                }
                if(/^\d+\d+\d$/.test(value)){
                    return '用户名不能全为数字';
                }
            }
            //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
            ,pass: [/^[\S]{6,12}$/,'密码必须6到12位，且不能出现空格']
            //验证用户名是否存在
        });
    });
</script>

</body>
</html>