<!DOCTYPE html>
<html lang="en" >
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

                <label >角色</label>
                <div class="layui-inline">
                    <input type="text" name="role" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>

                <label >名称</label>
                <div class="layui-inline">
                    <input type="text" name="name" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>

                <div class="layui-inline">
                    <button class="layui-btn layuiadmin-btn-useradmin" lay-submit lay-filter="LAY-user-front-search">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                    </button>
                </div>

            </div>

        </div>

        <div class="layui-card-body">
            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <@shiro.hasPermission name="WebRole/add">
                        <button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="add"><i class="layui-icon"></i>角色</button>
                    </@shiro.hasPermission>
                </div>
            </script>

            <table class="layui-hide" id="demo" lay-filter="demo"></table>
            <script id="status_Tpl" type="text/html">
                <@shiro.hasPermission name="WebRole/stauts">
                    <input type="checkbox" name="stauts" value="{{d.ID}},{{d.STATUS}}" lay-skin="switch" lay-text="禁用|启用" lay-filter="status" {{ d.STATUS == 1 ? 'checked' : '' }}>
                </@shiro.hasPermission>
            </script>

            <script id="barDemo" type="text/html">
                <@shiro.hasPermission name="WebRole/edit">
                    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
                </@shiro.hasPermission>

                <@shiro.hasPermission name="WebRole/power">
                    <a class="layui-btn  layui-btn-xs" lay-event="power"><i class="layui-icon layui-icon-auz"></i>设置权限</a>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="WebRole/del">
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
                </@shiro.hasPermission>

            </script>

            <script id="rownum" type="text/html">
                {{d.LAY_TABLE_INDEX+1}}
            </script>
        </div>

    </div>
</div>
</body>
<script >
    var $;//初始化jq
    var table;//table
    var form;//表单

    layui .use(['table'], function(){
        $=layui.jquery,table = layui.table ,form = layui.form;

        var msg = "权限只验证角色是否存在对应权限，所以给对应角色设置具体权限!";
        layer.open({
            type: 1
            ,title:'说明'
            ,offset: 'rb' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            ,id: 'layerDemorb' //防止重复弹出
            ,content: '<div style="padding: 20px 100px;">'+ msg +'</div>'
            ,btn: '关闭全部'
            ,btnAlign: 'c' //按钮居中
            ,shade: 0 //不显示遮罩
            ,yes: function(){
                layer.closeAll();
            }
        });


        //搜索事件
        table_serch();

        //表单
        table_data();

        //工具栏
        table_toolbar();

        //监听行
        table_tool();

    });

    //搜索事件
    function table_serch() {
        form.on('submit(LAY-user-front-search)', function(data){
            var field = data.field;
            //执行条件重载
            table.reload('demo', {where: field});
        });
    }

    //表格数据渲染
    function table_data() {
        table.render({
            elem: '#demo'
            ,height: 'full-140'
            ,cellMinWidth: 80
            ,url:'/WebRole/listData'
            ,limit:10
            ,toolbar:'#toolbarDemo'
            ,page: true//开启分页
            ,limits:[10,20,50,100,200,500]
            ,cols: [[
                {field:'rownum', width:'4%', title: 'No.' ,templet: '#rownum' }
                ,{field:'role', width:'10%', title: '角色'}
                ,{field:'name', width:'10%', title: '名称'}
                ,{field:'add_time', width:'15%', title: '添加时间'}
                ,{field:'edit_time', width:'15%', title: '最后操作时间'}
                ,{field:'note', width:'30%', title: '说明'}
                ,{fixed: 'right', width:'15%', align:'center', toolbar: '#barDemo',title: '操作'}
            ]]
            ,done:function(res, curr, count){

            }
        });

    }

    //工具栏事件
    function table_toolbar() {
        table.on('toolbar(demo)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id),data = checkStatus.data, layEvent = obj.event;
            switch(layEvent){
                case 'add':
                    layer.open({
                        type: 2
                        ,title: '添加角色信息'
                        ,area: ['456px', '425px']
                        ,anim: 3
                        ,shade: 0
                        ,maxmin:true
                        ,moveOut:true
                        ,content: '/WebRole/add'
                        ,btn: ['确定', '取消']
                        ,yes: function(index, layero){
                            var iframeWindow = window['layui-layer-iframe'+ index] ,submitID = 'LAYFrontSubmit',submit = layero.find('iframe').contents().find('#'+ submitID);
                            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                                reqAjax.aPost('/WebRole/saveOrUpdate',JSON.stringify(data.field));
                                table.reload('LAYFrontSubmit');
                                layer.close(index);
                            });
                            submit.trigger('click');
                        }
                    });
                    break;
            };

        });
    }

    //监听行工具事件
    function table_tool() {
        table.on('tool(demo)', function(obj){
            var data = obj.data ,layEvent = obj.event;
            switch(layEvent){

                case 'edit':
                    layer.open({
                        type: 2
                        ,title:'编辑角色：'+data.role
                        ,area: ['456px', '445px']
                        ,anim: 3
                        ,shade: 0
                        ,maxmin:true
                        ,moveOut:true
                        ,content: '/WebRole/add?id='+data.Id
                        ,btn: ['修改', '取消']
                        ,yes: function(index, layero){
                            var iframeWindow = window['layui-layer-iframe'+ index] ,submitID = 'LAYFrontSubmit',submit = layero.find('iframe').contents().find('#'+ submitID);
                            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                                reqAjax.aPost('/WebRole/saveOrUpdate',JSON.stringify(data.field));
                                table.reload('LAYFrontSubmit');
                                layer.close(index);
                            });
                            submit.trigger('click');
                        }
                    });
                    break;

                case 'del':
                    layer.confirm('真的删除行么', function(index){
                        layer.close(index);
                        reqAjax.aPostNoContnet('/WebRole/delete',{"id":data.Id});
                    });
                    break;

                case 'power':
                    // layer.msg(JSON.stringify(data.Id) )
                    layer.open({
                        type: 2
                        ,title:'角色：&nbsp;'+data.role+'&nbsp;&nbsp;&nbsp;权限设置'
                        ,area: ["320px", "80%"]
                        ,anim: 3
                        ,shade: 0
                        ,maxmin:true
                        ,moveOut:true
                        ,content: '/WebRole/power?id='+data.Id
                    });
                    break;

            }

        });
    }

</script>
</html>
