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
                    <input type="text" name="username" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>

                <label >昵称</label>
                <div class="layui-inline">
                    <input type="text" name="nickname" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>

                <label >手机号</label>
                <div class="layui-inline">
                    <input type="text" name="phone" placeholder="请输入" autocomplete="off" class="layui-input">
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
                    <@shiro.hasPermission name="WebUser/add">
                        <button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="add"><i class="layui-icon"></i>添加用户</button>
                    </@shiro.hasPermission>
                </div>
            </script>

            <table class="layui-hide" id="demo" lay-filter="demo"></table>
            <script id="status_Tpl" type="text/html">
                <@shiro.hasPermission name="WebUser/stauts">
                 <input type="checkbox" name="stauts" value="{{d.Id}},{{d.status}}" lay-skin="switch" lay-text="禁用|启用" lay-filter="status" {{ d.status == 1 ? 'checked' : '' }}>
                </@shiro.hasPermission>
            </script>

            <script id="barDemo" type="text/html">
                <@shiro.hasPermission name="WebUser/edit">
                    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
                </@shiro.hasPermission>

                <@shiro.hasPermission name="WebUser/del">
                    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
                </@shiro.hasPermission>
            </script>

            <script id="rownum" type="text/html">
                {{d.LAY_TABLE_INDEX+1}}
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
    _LAY.initTableSet(layui);
    table.render({
            elem: '#demo'
            ,url:'/WebUser/listData'
            ,cols: [[
                {field:'rownum', width:'4%', title: 'No.' ,templet: '#rownum' }
                ,{field:'username', width:'10%', title: '用户名'}
                ,{field:'nickname', width:'10%', title: '昵称'}
                ,{field:'phone', width:'10%', title: '手机号'}
                ,{field:'roleName', width:'10%', title: '角色类型',templet:function (d) { return d.roleName==null?"":d.roleName; } }
                ,{field:'status', width:'10%', toolbar:'#status_Tpl',title: '状态'}
                ,{fixed: 'right', width:'10%', align:'center', toolbar: '#barDemo',title: '操作'}
            ]]
        });

    /**
     * 工具栏
     */
    table.on('toolbar(demo)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id),data = checkStatus.data, layEvent = obj.event;
        if(layEvent=="add"){
            layer.open({
                type: 2
                ,skin: 'demo-class'
                ,title: '添加用户信息'
                ,area: ['350px', '450px']
                ,anim: 3
                ,shade: 0
                ,maxmin:true
                ,moveOut:true
                ,content: '/WebUser/add'
                ,btn: ['确定', '取消']
                ,yes: function(index, layero){
                    var iframeWindow = window['layui-layer-iframe'+ index] ,submitID = 'LAYFrontSubmit',submit = layero.find('iframe').contents().find('#'+ submitID);
                    iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                        reqAjax.aPost('/WebUser/saveOrUpdate',JSON.stringify(data.field));
                        table.reload(submitID);
                        layer.close(index);
                    });
                    submit.trigger('click');
                }

            });
        }
    });

    /**
     * 监听行
     */
    table.on('tool(demo)', function(obj){
        var data = obj.data ,layEvent = obj.event;
        switch(layEvent){
            case 'del':
                layer.confirm('真的删除行么', function(index){
                    layer.close(index);
                    reqAjax.aPostNoContnet('/WebUser/delete',{"id":data.Id});
                });
                break;

            case 'edit':
                layer.open({
                    type: 2
                    ,title:'<span style="font-size:15px;font-famliy:"宋体";">编辑用户信息:'+''+data.nickname+'</sapn>'
                    ,area: ['456px', '445px']
                    ,anim: 3
                    ,shade: 0
                    ,maxmin:true
                    ,moveOut:true
                    ,content: '/WebUser/add?id='+data.Id
                    ,btn: ['修改', '取消']
                    ,yes: function(index, layero){
                        var iframeWindow = window['layui-layer-iframe'+ index] ,submitID = 'LAYFrontSubmit',submit = layero.find('iframe').contents().find('#'+ submitID);
                        iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                            reqAjax.aPost('/WebUser/saveOrUpdate',JSON.stringify(data.field));
                            table.reload(submitID); //数据刷新
                            layer.close(index); //关闭弹层
                        });
                        submit.trigger('click');
                    }
                });
                break;
        }

    });

    /**
     * 监听行级按钮动作
     */
    form.on('switch(status)', function(obj){
        reqAjax.aPost('/WebUser/updateStatus?str_arr='+this.value+','+obj.elem.checked , null);
    });

});

</script>


</body>
</html>
