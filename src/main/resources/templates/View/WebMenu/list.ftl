<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
    <script src="/static/modules/common/config.js"></script>
    <script src="/static/modules/common/common.js"></script>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-card">

        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">

                <label >关键字：</label>
                <div class="layui-inline">
                    <input id="edt-search" type="text"  placeholder="请输入关键字" autocomplete="off" class="layui-input">
                </div>

                <div class="layui-inline">
                    <button class="layui-btn layuiadmin-btn-useradmin" id="btn-search">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn" ></i>查找
                    </button>

                    <button class="layui-btn layuiadmin-btn-useradmin" id="btn-refresh" >
                        <i class="layui-icon layui-icon-refresh layuiadmin-button-btn" ></i>刷新
                    </button>
                </div>

            </div>
        </div>


        <div class="layui-card-body">
            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <div class="layui-btn-group">
                        <@shiro.hasPermission name="WebMenu/addMenu">
                            <button type="button" class="layui-btn layui-btn-sm" lay-event="addMenu"><i class="layui-icon">&#xe654;</i>添加目录</button>
                        </@shiro.hasPermission>

                        <@shiro.hasPermission name="WebMenu/openmenu">
                            <button type="button" class="layui-btn layui-btn-sm" lay-event="btn-expand"> <i class="layui-icon layui-icon-triangle-d"></i>全部展开</button>
                        </@shiro.hasPermission>

                        <@shiro.hasPermission name="WebMenu/closeMenu">
                            <button type="button" class="layui-btn layui-btn-sm" lay-event="btn-fold"><i class="layui-icon layui-icon-triangle-r"></i>全部折叠</button>
                        </@shiro.hasPermission>

                    </div>
                </div>
            </script>
            <!-- 3表格体区域 -->
            <table id="table1" class="layui-table" lay-filter="table1"></table>
            <script id="barDemo" type="text/html">
                <@shiro.hasPermission name="WebMenu/edit">
                    <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
                </@shiro.hasPermission>

                <@shiro.hasPermission name="WebMenu/del">
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
                </@shiro.hasPermission>

            </script>

        </div>

    </div>
</div>
<script id="rownum" type="text/html">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<script src="/static/layuiadmin/layui/layui.js"></script>
<script>
    var $ ;//初始化jq
    var table ;
    var layer ;
    var treetable ;
    var renderTable;
    layui.config({
        base: '/static/zqPlugin/'
    }).extend({
        treetable: 'treetable-lay/treetable'
    }).use(['layer', 'table', 'treetable'], function () {
         $ = layui.jquery;
         table = layui.table;
         layer = layui.layer;
         treetable = layui.treetable;
        // 渲染表格
         renderTable = function () {
            layer.load(2);
            treetable.render({
                treeColIndex: 1,//树形图标显示在第几列
                treeSpid: 0,//最上级的父级id
                treeIdName: 'ID',//id字段的名称
                treePidName: 'PID',//pid字段的名称
                treeDefaultClose: true,//是否默认折叠
                treeLinkage: false,//父级展开时是否自动展开所有子级
                toolbar: '#toolbarDemo',
                elem: '#table1',
                url: '/WebMenu/listData',
                webPage: false,
                limits:[10,20,50,100,200,500],
                cols: [[
                    {field:'rownum', width:60, title: '序号' ,templet: '#rownum' }
                    ,{field:'menu_name',width:250, minWidth: 20, title: '菜单名称'}
                    ,{field:'type', width:80, title: '类型', templet: function (d) {
                            if (d.type == 0) {
                                return '<span class="layui-badge layui-bg-blue">目录</span>';
                            }
                            if (d.type == 1) {
                                return '<span class="layui-badge layui-bg-gray">菜单</span>';
                            }
                            if (d.type == 2) {
                                return '<span class="layui-badge layui-bg-orange">按钮</span>';
                            }
                        }
                    }
                    // ,{field:'pid', width:280, title: '父类菜单标识'}
                    ,{field:'css', width:60, title: '图标',templet:function (d) {
                            return '<i class="layui-icon '+d.css+'" ></i>';
                        }}
                    ,{field:'href', width:230, title: '菜单路径'}
                    // ,{field:'lever', width:135, title: '菜单级别'}
                    ,{field:'note', width:670, title: '说明'}
                    ,{fixed: 'right', width:175, align:'center', toolbar: '#barDemo',title: '操作'}
                ]],
                done: function () {
                    console.log("表格渲染完成：");
                    layer.closeAll('loading');
                }
            });
        };

        renderTable();

        table_toolbar();

        //关键字搜索
        $('#btn-search').click(function () {
            var keyword = $('#edt-search').val();
            var searchCount = 0;
            $('#table1').next('.treeTable').find('.layui-table-body tbody tr td').each(function () {
                $(this).css('background-color', 'transparent');
                var text = $(this).text();
                if (keyword != '' && text.indexOf(keyword) >= 0) {
                    $(this).css('background-color', 'rgba(250,230,160,0.5)');
                    if (searchCount == 0) {
                        treetable.expandAll('#auth-table');
                        $('html,body').stop(true);
                        $('html,body').animate({scrollTop: $(this).offset().top - 150}, 500);
                    }
                    searchCount++;
                }
            });
            if (keyword == '') {
                layer.msg("请输入搜索内容", {icon: 5});
            } else if (searchCount == 0) {
                layer.msg("没有匹配结果", {icon: 5});
            }
        });

        //刷新表格
        $('#btn-refresh').click(function () {
            $("#edt-search").val('');
            layer.load(2);
            renderTable();
        });


        //监听工具条
        table.on('tool(table1)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'del') {

                layer.confirm('真的删除行么', function(index){
                    layer.close(index);
                    reqAjax.aPostBackNoContnet('/WebMenu/delete',{"id":data.id},function (data) {
                        if(data.code=='TimeOut'){
                            Msg.TimeOut(data);
                        } else if(data.code == '200'){
                            Msg.ok(data);
                            layer.load(2);
                            renderTable();
                        }else{
                            Msg.no(data);
                        }
                    });
                });

            } else if (layEvent === 'edit') {
                // layer.msg('修改' + data.id);
                layer.open({
                    type: 2
                    ,id: 'idTest'
                    ,title: '修改菜单或按钮'
                    ,area: ['456px', '570px']//定义打开窗口的宽度和高度
                    ,anim: 3//出场模式从左滑入0-6随便玩，默认0
                    ,maxmin:true//最大化按钮
                    ,moveOut:true//是否允许拖拽到窗口外
                    ,content: '/WebMenu/add?id='+data.id
                    ,btn: ['修改', '退出']
                    ,yes: function(index, layero){
                        var iframeWindow = window['layui-layer-iframe'+ index],submitID = 'LAY-user-front-submit' ,submit = layero.find('iframe').contents().find('#'+ submitID);
                        //监听提交
                        iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                            reqAjax.aPostBack('/WebMenu/saveOrUpdate',JSON.stringify(data.field),function (data) {
                                if(data.code=='TimeOut'){
                                    Msg.TimeOut(data);
                                } else if(data.code == '200'){
                                    layer.msg(data.msg,{icon: 1,time:1000,shade: 0.5},function(){
                                        var index = parent.layer.getFrameIndex(window.name);
                                        parent.layer.close(index);
                                        renderTable();
                                    });
                                }else{
                                    Msg.no(data);
                                }
                            })
                           layer.close(index); //关闭弹层
                        });

                        submit.trigger('click');
                    }
                });


            }
        });

    });

    //工具栏事件
    function table_toolbar() {
        table.on('toolbar(table1)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id),data = checkStatus.data, layEvent = obj.event;
            switch(layEvent){
                case 'addMenu':
                    layer.open({
                        type: 2
                        ,id: 'idTest'
                        ,title: '添加菜单或按钮'
                        ,area: ['456px', '570px']//定义打开窗口的宽度和高度
                        ,anim: 3//出场模式从左滑入0-6随便玩，默认0
                        ,maxmin:true//最大化按钮
                        ,moveOut:true//是否允许拖拽到窗口外
                        ,content: '/WebMenu/add'
                        ,btn: ['保存', '退出']
                        ,yes: function(index, layero){
                            var iframeWindow = window['layui-layer-iframe'+ index],submitID = 'LAY-user-front-submit',submit = layero.find('iframe').contents().find('#'+ submitID);
                            //监听提交
                            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                                reqAjax.aPostBack('/WebMenu/saveOrUpdate',JSON.stringify(data.field),function (data) {
                                    if(data.code=='TimeOut'){
                                        Msg.TimeOut(data);
                                    } else if(data.code == '200'){
                                        layer.msg(data.msg,{icon: 1,time:1000,shade: 0.5},function(){
                                            var index = parent.layer.getFrameIndex(window.name);
                                            parent.layer.close(index);
                                            renderTable();
                                        });
                                    }else{
                                        Msg.no(data);
                                    }
                                })
                                layer.close(index); //关闭弹层
                            });

                            submit.trigger('click');
                        }
                    });
                    break;
                //展开
                case 'btn-expand':
                    treetable.expandAll('#table1');
                    break;

                //折叠
                case 'btn-fold':
                    treetable.foldAll('#table1');
                    break;
            };

        });
    }


</script>


</body>
</html>
