<#--公共顶部-->
<#macro header title='XXXXXXXXX'>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>${title}</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <script src="/static/layuiadmin/layui/layui.js"></script>
    <#--<link rel="shortcut icon" href="/static/zzpic/sand.ico">-->
    <#nested>
</head>
</#macro>

<#macro myJs>
    <div class="">
        <script src="/static/modules/common/config.js"></script>
        <script src="/static/modules/common/common.js"></script>
    </div>
    <div class="">
        <#nested>
    </div>
</#macro>



<#macro indexSc>
    <script>
        layui.config({
            base: '/static/layuiadmin/'
        }).extend({
            index: 'lib/index'
        }).use(['index','layer','element'],function () {
            <#nested>
        });
    </script>
</#macro>