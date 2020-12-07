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

        <input type="hidden" name="id" value="${(webIp.id?c)!''}"/>
        <input type="hidden" name="old_ip" value="${(webIp.old_ip)!''}"/>
        <input id="type" type="hidden" value="${(webIp.type)!''}"/>
        <input id="status" type="hidden" value="${(webIp.status)!''}"/>
        <div class="layui-form-item">
            <label class="layui-form-label">Ip地址：</label>
            <div class="layui-input-block">
                <input type="text" name="ip" required lay-verify="required|ip" value="${(webIp.ip)!''}" placeholder="请输入IP地址" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">Ip类型：</label>
            <div class="layui-input-block">
                <select id="select_lever" name="type"  lay-verify="required" >
                    <option value="1">白名单</option>
                    <option value="2">黑名单</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">Ip类型：</label>
            <div class="layui-input-block">
                <select id="select_status" name="status"  lay-verify="required" >
                    <option value="1">允许访问</option>
                    <option value="2">禁止访问</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">说明：</label>
            <div class="layui-input-block">
                <textarea name="note" placeholder="请输入内容" class="layui-textarea">${(webIp.note)!''}</textarea>
            </div>
        </div>


        <div class="layui-form-item layui-hide">
            <input type="button" lay-submit lay-filter="LAYFrontSubmit" id="LAYFrontSubmit" value="确认">
        </div>
</form>


</div>

<script>
    var form ,$ ;
    layui.use(['form'], function() {
         form = layui.form ,$ = layui.$;
        var type =  $("#type").val();
        if(type!=''){
            selectOptionSelect('select_lever',type);
        }

        var status =  $("#status").val();
        if(status!=''){
            selectOptionSelect('select_status',status);
        }


        form.verify({
            ip: [
                /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
                ,'IP地址不符合规则'
            ]
        });

    });

    /**
     * select option 回显选中
     * **/
    function selectOptionSelect(selectID,value) {
        // 一级目录回显
        $("#"+selectID).each(function () {// 遍历select
            $(this).children("option").each(function () {// 遍历option
                if (this.value == value) {// 跟后台传过来的id相同就显示selected
                    // console.log("一级目录"+$(this).text());
                    $(this).attr("selected", "selected");
                    form.render('select');// 注意：一定要调用该方法进行中心渲染，否则数据是显示不出来的
                }
            });
        });
    }

</script>



</body>
</html>