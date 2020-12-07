<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
    <script src="/static/layuiadmin/layui/layui.js"></script>
    <link rel="stylesheet" href="/static/zqPlugin/dtree/dtree/dtree.css" media="all">
    <link rel="stylesheet" href="/static/zqPlugin/dtree/dtree/font/dtreefont.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">我的资料</div>
                <div class="layui-card-body" pad15>

                    <div class="layui-form" lay-filter="">

                        <div class="layui-form-item">
                            <label class="layui-form-label">用户名：</label>
                            <div class="layui-input-inline">
                                <input type="text" name="username" required lay-verify="required" value="${(webUser.username)!''}" placeholder="用户名" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">昵称：</label>
                            <div class="layui-input-inline">
                                <input type="text" name="nickname" required lay-verify="required" value="${(webUser.nickname)!''}" placeholder="昵称" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">手机号：</label>
                            <div class="layui-input-inline">
                                <input type="tel" name="phone" required lay-verify="required|phone" value="${(webUser.phone)!''}" placeholder="手机号" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">角色：</label>
                            <div class="layui-input-inline">
                                <input type="tel" name="phone" required lay-verify="required" value="${(role.name)!''}" placeholder="角色" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">权限：</label>
                            <div class="layui-input-inline">

                                    <input type="hidden" id="id" name="id" value="${(role.id)!''}"/>
                                    <input type="hidden" id="role" name="role" value="${(role.role)!''}"/>


                                            <div class="layui-row layui-col-space10" style="margin-top: 0px;">
                                                <div class="">
                                                    <div id="toolbarDiv" style="overflow: auto">
                                                        <ul id="demoTree" class="dtree" data-id="0"></ul>
                                                    </div>
                                                </div>
                                            </div>


                            </div>

                        </div>

                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    layui.config({
        base: '/static/zqPlugin/'
    }).extend({
        dtree: 'dtree/dist/dtree'
    }).
    use(['dtree', 'tree', 'layer', 'form'], function () {
        var $ = layui.jquery, tree = layui.tree,form = layui.form,dtree = layui.dtree;

        var menubarTips = {
            toolbar: [],  //依附工具栏
            group: ["moveDown", "moveUp","searchNode"], //按钮组
            freedom: [] // 自由指定
        };

        var role_id = $("#id").val();

        var DTree = dtree.render({
            url: "/WebRole/getTree?role_id="+role_id,    //异步数据接口相关参数。其中 url 参数为必填项
            elem: "#demoTree",                    //树绑定的元素ID
            height:"550",                         //定义树组件的高度
            width:"100%",
            initLevel: 3,                         //默认展开层级，当该值大于level时，则会展开树的节点，直到不大于当前待展开节点的level。
            cache:true ,                          //数据缓存，开启后，当节点收缩后展开时，不会重新访问url。
            skin: "laySimple" ,                   // layui主题风格  laySimple  layui  zdy
            checkbar: true,                       //开启复选框模式
            menubar:true,                         //开启菜单栏
            menubarTips : menubarTips ,           //自定义工具栏
            load: true,
            // ,icon: -1 //图标  -1 隐藏图标
            line: true,                           // 显示树线
            formatter: {
                title: function(data) {           // 示例给有子集的节点返回节点统计
                    var s = data.title;
                    if (data.children){
                        s += ' <span style=\'color:blue\'>(' + data.children.length + ')</span>';
                    }
                    return s;
                }
            }
            ,accordion: true                      // 开启手风琴
            ,dataFormat: "list"                   //配置data的风格为list
        });


    });
</script>
</html>