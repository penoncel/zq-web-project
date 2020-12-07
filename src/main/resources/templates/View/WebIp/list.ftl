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

                <label >Ip地址</label>
                <div class="layui-inline">
                    <input type="text" name="ip" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>

                <label >类型</label>
                <div class="layui-inline">
                    <select  name="type"   >
                        <option value="">直接选择或搜索选择</option>
                        <option value="1">白名单</option>
                        <option value="2">黑名单</option>
                    </select>
                </div>

                <label >状态</label>
                <div class="layui-inline">
                    <select  name="status"   >
                        <option value="">直接选择或搜索选择</option>
                        <option value="1">允许访问</option>
                        <option value="2">禁止访问</option>
                    </select>
                </div>

                <label >说明</label>
                <div class="layui-inline">
                    <input type="text" name="note" placeholder="请输入" autocomplete="off" class="layui-input">
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
                    <@shiro.hasPermission name="WebIp/add">
                        <button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="add">设置IP</button>
                    </@shiro.hasPermission>
                </div>
            </script>

            <table class="layui-hide" id="demo" lay-filter="demo"></table>
            <script id="status_Tpl" type="text/html">
                <@shiro.hasPermission name="WebIp/stauts">
                    <input type="checkbox" name="stauts" value="{{d.ID}},{{d.STATUS}}" lay-skin="switch" lay-text="禁用|启用" lay-filter="status" {{ d.STATUS == 1 ? 'checked' : '' }}>
                </@shiro.hasPermission>
            </script>

            <script id="barDemo" type="text/html">
                <@shiro.hasPermission name="WebIp/edit">
                    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i></a>
                </@shiro.hasPermission>

                <@shiro.hasPermission name="WebIp/del">
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon">&#xe640;</i></a>
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
            ,url:'/WebIp/listData'
            ,limit:10
            ,toolbar:'#toolbarDemo'
            ,page: true//开启分页
            ,limits:[10,20,50,100,200,500]
            ,cols: [[
                {field:'rownum', width:'4%', title: 'No.' ,templet: '#rownum' }
                ,{field:'ip', width:'15%', title: 'ip地址'}
                ,{field:'type', width:'5%', title: 'ip类型',templet:function (d){
                        if(d.type==1){
                            return '<span style="color: #009688">白名单</span>';
                        }else{
                            return '<span style="color: #FF5722">黑名单</span>';
                        }
                    }}

                ,{field:'status', width:'6%', title: 'ip状态',templet:function (d){
                        if(d.status==1){
                            return '<span style="color: #009688">允许访问</span>';
                        }else{
                            return '<span style="color: #FF5722">禁止访问</span>';
                        }
                    }}
                ,{field:'add_time', width:'10%', title: '设置时间'}
                ,{field:'up_time', width:'10%', title: '操作时间'}
                ,{field:'note', width:'30%', title: '说明'}
                ,{fixed: 'right', width:'8%', align:'center', toolbar: '#barDemo',title: '操作'}
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
                        ,title: '设置IP信息'
                        ,area: ['456px', '425px']
                        ,anim: 3
                        ,shade: 0
                        ,maxmin:true
                        ,moveOut:true
                        ,content: '/WebIp/add'
                        ,btn: ['确定', '取消']
                        ,yes: function(index, layero){
                            var iframeWindow = window['layui-layer-iframe'+ index] ,submitID = 'LAYFrontSubmit',submit = layero.find('iframe').contents().find('#'+ submitID);
                            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                                reqAjax.aPost('/WebIp/saveOrUpdate',JSON.stringify(data.field));
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
                        ,title:'编辑角色：'+data.ip
                        ,area: ['456px', '445px']
                        ,anim: 3
                        ,shade: 0
                        ,maxmin:true
                        ,moveOut:true
                        ,content: '/WebIp/add?id='+data.id
                        ,btn: ['修改', '取消']
                        ,yes: function(index, layero){
                            var iframeWindow = window['layui-layer-iframe'+ index] ,submitID = 'LAYFrontSubmit',submit = layero.find('iframe').contents().find('#'+ submitID);
                            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                                reqAjax.aPost('/WebIp/saveOrUpdate',JSON.stringify(data.field));
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
                        reqAjax.aPostNoContnet('/WebIp/delete',{"id":data.id});
                    });
                    break;

            }

        });
    }

</script>
</html>
