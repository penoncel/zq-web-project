<!DOCTYPE html>
<html >
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
    <div class="layui-card">

        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">

                <label >用户名</label>
                <div class="layui-inline">
                    <input type="text" name="user_name" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>

                <div class="layui-inline">
                    <button class="layui-btn layuiadmin-btn-useradmin" lay-submit lay-filter="LAY-user-front-search">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                    </button>
                </div>

            </div>

        </div>

        <div class="layui-card-body">

            <table class="layui-hide" id="demo" lay-filter="demo"></table>

            <script id="barDemo" type="text/html">
                <@shiro.hasPermission name="WebUser/del">
                    <a class="layui-btn  layui-btn-primary layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-close"></i>下线</a>
                </@shiro.hasPermission>
            </script>

        </div>

    </div>
</div>


<script >
var $;//初始化jq
var table;//table
var form;//表单
var admin;
layui .use(['table'], function(){
        $=layui.jquery,table = layui.table ,form = layui.form,admin = layui.admin;

    layui.form.on('submit(LAY-user-front-search)', function(data){
        var field = data.field;
        //执行条件重载
        layui.table.reload('demo', {where: field});
    });
    table.render({
            elem: '#demo'
            ,url:'/OnleUser/listData'
            ,height: 'full-140'
            ,cellMinWidth: 80
            ,limit:10
            ,page: true // 开启分页
            ,limits:[10,20,50,100,200,500]
            ,cols: [[
                {field:'user_name', width:'10%', title: '用户名'}
                ,{field:'host', width:'10%', title: 'IP'}
                ,{field:'sessiond', width:'20%', title: 'ID'}
                ,{field:'startAccessTime', width:'11%', title: '登入时间',templet:'<div>{{ layui.util.toDateString(d.startAccessTime, "yyyy-MM-dd HH:mm:ss") }}</div>' }
                ,{field:'lastAccessTime', width:'11%', title: '操作时间',templet:'<div>{{ layui.util.toDateString(d.lastAccessTime, "yyyy-MM-dd HH:mm:ss") }}</div>' }
                ,{fixed: 'right', width:'10%', align:'center', toolbar: '#barDemo',title: '操作'}
            ]]
           ,where:{user_name:""}
           ,initSort:{field:"startAccessTime",type:'desc'

            }
        });

    table.on('tool(demo)', function(obj){
        var data = obj.data ,layEvent = obj.event;
        switch(layEvent){
            case 'del':
                layer.confirm('确定要强制当前用户下线嘛?', function(index){
                    layer.close(index);
                    reqAjax.aPostNoContnet('/OnleUser/outOnleUser',{"sessionId":data.sessiond});
                });
                break;


        }

    });

});

</script>


</body>
</html>
