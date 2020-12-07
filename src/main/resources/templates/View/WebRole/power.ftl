<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/zqPlugin/dtree/dtree/dtree.css" media="all">
    <link rel="stylesheet" href="/static/zqPlugin/dtree/dtree/font/dtreefont.css" media="all">
    <script src="/static/layuiadmin/layui/layui.js"></script>
</head>
<body>
<div class="container">
    <div class="layui-row">
        <!--layui-col-space3-->
        <div class="">
            <input type="hidden" id="id" name="id" value="${(webRole.id)!''}"/>
            <input type="hidden" id="role" name="role" value="${(webRole.role)!''}"/>
            <div class="layui-form-item">
                <div class="layui-field-box">
                    <div class="layui-row layui-col-space10" style="margin-top: 5px;">
                        <div class="layui-col-xs12">
                            <div id="toolbarDiv" style="overflow: auto">
                                <ul id="demoTree" class="dtree" data-id="0"></ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" id="checkbarTreea_btna">设置权限</button>
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

        var data =
            [
                {"id":69,"title":"系统设置",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"0","iconClass":"dtree-icon-fenzhijigou"},
                {"id":70,"title":"菜单列表",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"69","iconClass":"dtree-icon-shuye1"},
                {"id":71,"title":"系统用户",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"0","iconClass":"dtree-icon-fenzhijigou"},
                {"id":72,"title":"用户管理",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"71","iconClass":"dtree-icon-shuye1"},
                {"id":73,"title":"代理商管理",       "checkArr":[{"type":"0","checked":"1"}],"parentId":"0","iconClass":"dtree-icon-fenzhijigou"},
                {"id":74,"title":"代理商新增",       "checkArr":[{"type":"0","checked":"1"}],"parentId":"73","iconClass":"dtree-icon-shuye1"},
                {"id":75,"title":"正式代理商",       "checkArr":[{"type":"0","checked":"1"}],"parentId":"73","iconClass":"dtree-icon-shuye1"},
                {"id":76,"title":"商户管理",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"0","iconClass":"dtree-icon-fenzhijigou"},
                {"id":77,"title":"非正式商户查询",   "checkArr":[{"type":"0","checked":"1"}],"parentId":"76","iconClass":"dtree-icon-shuye1"},
                {"id":78,"title":"商户审核",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"76","iconClass":"dtree-icon-shuye1"},
                {"id":80,"title":"正式商户",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"76","iconClass":"dtree-icon-shuye1"},
                {"id":81,"title":"交易查询",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"0","iconClass":"dtree-icon-fenzhijigou"},
                {"id":82,"title":"POS后台交易",      "checkArr":[{"type":"0","checked":"0"}],"parentId":"81","iconClass":"dtree-icon-shuye1"},
                {"id":83,"title":"交易查询",         "checkArr":[{"type":"0","checked":"0"}],"parentId":"81","iconClass":"dtree-icon-shuye1"},
                {"id":84,"title":"辅助功能",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"0","iconClass":"dtree-icon-fenzhijigou"},
                {"id":85,"title":"文件管理",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"84","iconClass":"dtree-icon-shuye1"},
                {"id":86,"title":"添加目录",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"70","iconClass":"dtree-icon-bianji"},
                {"id":88,"title":"修改目录",         "checkArr":[{"type":"0","checked":"0"}],"parentId":"70","iconClass":"dtree-icon-bianji"},
                {"id":89,"title":"删除目录",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"70","iconClass":"dtree-icon-bianji"},
                {"id":90,"title":"添加用户",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"72","iconClass":"dtree-icon-bianji"},
                {"id":91,"title":"编辑用户",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"72","iconClass":"dtree-icon-bianji"},
                {"id":92,"title":"删除用户",         "checkArr":[{"type":"0","checked":"1"}],"parentId":"72","iconClass":"dtree-icon-bianji"},
                {"id":98,"title":"anniu",            "checkArr":[{"type":"0","checked":"1"}],"parentId":"95","iconClass":"dtree-icon-bianji"},
                {"id":106,"title":"角色管理",        "checkArr":[{"type":"0","checked":"1"}],"parentId":"69","iconClass":"dtree-icon-shuye1"}
            ]
        ;

        var menubarTips = {
            toolbar: [],  //依附工具栏
            group: ["moveDown", "moveUp", "checkAll", "unCheckAll", "invertAll", "searchNode"], //按钮组
            freedom: [] // 自由指定
        };

        var role_id = $("#id").val();

        var DTree = dtree.render({
            url: "/WebRole/getTree?role_id="+role_id,    //异步数据接口相关参数。其中 url 参数为必填项
            // data: data,                           //树数据参数
            elem: "#demoTree",                    //树绑定的元素ID
            height:"550",                         //定义树组件的高度
            initLevel: 3,                         //默认展开层级，当该值大于level时，则会展开树的节点，直到不大于当前待展开节点的level。
            cache:true ,                          //数据缓存，开启后，当节点收缩后展开时，不会重新访问url。
            skin: "laySimple" ,                   // layui主题风格  laySimple  layui  zdy
            checkbar: true,                       //开启复选框模式
            menubar:true,                         //开启菜单栏
            menubarTips : menubarTips ,           //自定义工具栏
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
            // ,accordion: true                      // 开启手风琴
            ,dataFormat: "list"                   //配置data的风格为list
        });

        $("#checkbarTreea_btna").click(function(){
            var params = dtree.getCheckbarNodesParam("demoTree");
            if(params==''){
                layer.alert("至少选择一个权限", {icon: 5});
            }else{
                $.ajax({
                    url:'/WebRole/setUserRole',
                    type:"POST",
                    dataType:"json",
                    data: {"role_id":role_id,"data":JSON.stringify(params)},
                    success:function(data){
                        if(data.code=='200'){
                            layer.msg(data.msg,{icon: 1,time:1000,shade: 0.5},function(){
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            });
                        }else{
                            layer.alert(data.msg, {icon: -2})
                        }
                    },
                    error:function(){
                        layer.alert("请求错误", {icon: -2})
                    }
                });

                return false;
            }


        });

    });
</script>
</html>