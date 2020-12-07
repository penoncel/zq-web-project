<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>分类选择</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <script src="/static/layuiadmin/layui/layui.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
    <style type="text/css">
        .downpanel .layui-select-title span {
            line-height: 38px;
        }

        /*继承父类颜色*/
        .downpanel dl dd:hover {
            background-color: inherit;
        }

        .hide {
            display: none;
        }

    </style>


</head>
<body>
<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 50px 0 0;">
<form class="layui-form" action="" lay-filter="component-form-element">
    <input type="hidden" name="id" value="${(webMenus.id?c)!''}"/>
    <input type="hidden" name="parents" value="${(webMenus.parents)!''}"/>
    <input type="hidden" name="lever" value="${(webMenus.lever)!''}"/>
    <input type="hidden" name="type" value="${(webMenus.type)!''}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">上级目录：</label>
        <div class="layui-input-block">
            <div class="layui-unselect layui-form-select downpanel">
                <div class="layui-select-title">
                    <span class="layui-input layui-unselect" id="treeclass">${(map.p_name)!'默认主目录'}</span>
                    <input type="hidden" name="parentid" value="${(map.id)!'0'}">
                    <i class="layui-edge"></i>
                </div>
                <dl class="layui-anim layui-anim-upbit">
                    <dd>
                        <ul id="classtree"></ul>
                    </dd>
                </dl>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">类型：</label>
        <div class="layui-input-block">
            <input type="checkbox" name="type" value="2" title="按钮" lay-skin="primary" <#if webMenus ??>disabled <#if ((webMenus.type)!)=='2'>checked </#if> </#if> >
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">图标：</label>
        <div class="layui-input-block">
            <input type="text" name="css"  value="${(webMenus.css)!''}" id="iconPicker" lay-filter="iconPicker" class="hide">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">名称：</label>
        <div class="layui-input-block">
            <input type="text" name="menu_name" required lay-verify="required" value="${(webMenus.menu_name)!''}" placeholder="请输入菜单或按钮名称" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">路径：</label>
        <div class="layui-input-block">
            <input type="text" name="href" required lay-verify="required" value="${(webMenus.href)!''}" placeholder="请输入菜单或按钮路径" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">说明：</label>
        <div class="layui-input-block">
            <textarea name="note" placeholder="请输入内容" class="layui-textarea">${(webMenus.note)!''}</textarea>
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="LAY-user-front-submit" id="LAY-user-front-submit" value="确认">
    </div>

</form>
</div>

<script type="text/javascript">
    layui.config({
        base: '/static/zqPlugin/'
    }).extend({
        iconPicker: 'iconpicker/iconPicker'
    }).
    use(['element', 'tree', 'layer', 'form', 'iconPicker'], function () {
        var $ = layui.jquery, tree = layui.tree,form = layui.form,iconPicker = layui.iconPicker;
        $.get({
            url: "/WebMenu/getTree",
            success: function (data) {
                var getNodes = [];
                // getNodes = [ {name: '手机',id: 1,spread: false, alias: 'changyong',children: [ {name: '小米手机', id: 11}, {name: '华为手机', id: 12}, {name: '苹果手机', id: 13}] },
                //              {name: '电脑', id: 2,spread: false, alias: 'diannao', children: [ { name: '电脑配件',id: 22}]}
                //            ];
                getNodes =data;
                tree({
                    elem: "#classtree",
                    nodes: getNodes,
                    click: function (node) {
                         var $select = $($(this)[0].elem).parents(".layui-form-select");
                        $select.removeClass("layui-form-selected").find(".layui-select-title span").html(node.name).end().find("input:hidden[name='parentid']").val(node.id);
                    }
                });
                $(".downpanel").on("click", ".layui-select-title", function (e) {
                    $(".layui-form-select").not($(this).parents(".layui-form-select")).removeClass("layui-form-selected");
                    $(this).parents(".downpanel").toggleClass("layui-form-selected");
                    layui.stope(e);
                }).on("click", "dl i", function (e) {
                    layui.stope(e);
                });
                $(document).on("click", function (e) {
                    $(".layui-form-select").removeClass("layui-form-selected");
                });
            }
        })

        iconPicker.render({
            // 选择器，推荐使用input
            elem: '#iconPicker',
            // 数据类型：fontClass/unicode，推荐使用fontClass
            type: 'fontClass',
            // 是否开启搜索：true/false，默认true
            search: true,
            // 是否开启分页：true/false，默认true
            webPage: true,
            // 每页显示数量，默认12
            limit: 16,
            // 点击回调
            click: function (data) {
                console.log(data);
            },
            // 渲染成功后的回调
            success: function(d) {
                console.log(d);
            }
        });

    });
</script>
</body>
</html>
</body>
</html>