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
</head>
<body>
<div class="layui-fluid">
    <div class="layui-card">

        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">

                <div class="layui-inline">
                    <input type="text" name="user_name" placeholder="用户名" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-inline">
                    <input type="text" name="req_ip" placeholder="IP地址" autocomplete="off" class="layui-input">
                </div>

                <div class="layui-inline" style="width: 100px">
                    <select  name="log_type"   >
                        <option value="">日志类型</option>
                        <option value="1">登入</option>
                        <option value="2">操作</option>
                    </select>
                </div>

                <div class="layui-inline" style="width: 100px">
                    <select  name="log_status"  >
                        <option value="">日志状态</option>
                        <option value="1">成功</option>
                        <option value="2">失败</option>
                    </select>
                </div>

                <div class="layui-inline">
                    <input type="text" name="oper_module" placeholder="操作模块" autocomplete="off" class="layui-input">
                </div>

                <div class="layui-inline">
                    <input type="text" name="oper_type" placeholder="操作类型" autocomplete="off" class="layui-input">
                </div>

                <div class="layui-inline">
                    <input type="text" name="oper_msg" placeholder="操作描述" autocomplete="off" class="layui-input">
                </div>

                <div class="layui-inline">
                    <input type="text" name="oper_method" placeholder="Method" autocomplete="off" class="layui-input">
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

        form.on('submit(LAY-user-front-search)', function(data){
            table.reload('demo', {where: data.field});
        });

        table.render({
            elem: '#demo'
            ,height: 'full-140'
            ,even: true
            ,url:'/WebOperationLog/listData'
            ,limit:20
            ,page: true//开启分页
            ,limits:[20,50,100,200,500]
            ,cols: [[
                {field:'user_name', width:'7%', title: '用户'}
                ,{field:'req_ip', width:'7%', title: 'IP'}
                ,{field:'log_type', width:'6%', title: '日志类型',templet:function (d){
                        if(d.log_type==1){
                            return '用户登入';
                        }else{
                            return '系统操作';
                        }
                    }}
                ,{field:'log_status', width:'6%', title: '状态',templet:function (d){
                        if(d.log_status==1){
                            return '<a class="layui-btn layui-bg-green layui-btn-xs" lay-event="see"><i class="layui-icon layui-icon-search"></i>成功</a>';
                        }else{
                            return '<a class="layui-btn layui-bg-red layui-btn-xs" lay-event="see"><i class="layui-icon layui-icon-search"></i>失败</a>';
                        }
                    }}
                ,{field:'oper_module', width:'10%', title: '操作模块'}
                ,{field:'oper_type', width:'10%', title: '操作类型'}
                ,{field:'oper_msg', width:'10%', title: '操作描述'}
                ,{field:'oper_times', width:'10%', title: '操作时间'}
                ,{field:'resp_times', width:'10%', title: '结束时间'}
                ,{field:'take_up_time', width:'5%', title: '耗时(S)'}
                ,{field:'req_uri', width:'15%', title: 'URI'}
            ]]
        });

        table.on('tool(demo)', function(obj){
            var data = obj.data ,layEvent = obj.event;
            if(layEvent=='see'){
                var log_type = data.log_type==1?"用户登入":"系统操作";
                var log_status = data.log_status==1?"<span class='layui-bg-green'>成功</span>":"<span class='layui-bg-red'>失败</span>";
                var title  = "用户「"+data.user_name+"」进行「"+log_type+"」结果「"+log_status+"」";
                top.layer.open({
                    type: 2
                    ,title:title
                    // ,offset: 'r'
                    ,anim: 5
                    ,shade: 0.7
                    ,shadeClose:true
                    ,area: ['700px', '100%']
                    ,content: '/WebOperationLog/see?id='+data.id
                    ,resize : false
                });
            }
        });
    });





</script>


</body>
</html>
