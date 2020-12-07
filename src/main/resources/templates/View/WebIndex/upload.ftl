<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="./layui/css/layui.css"  media="all">
    <script src="./layui/layui.js" charset="utf-8"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
    <style type="text/css">
        .mask{position:fixed;width:100%;height:100%;top:0;left:0;background:#000;opacity:0.8;filter:alpha(Opacity=80);-moz-opacity:0.8;z-index:999;display:none;}
        .loading{position:fixed;width:300px;left:50%;margin-left:-150px;top:200px;height:18px;border-radius:10px;background:#fff;z-index:9999;overflow:hidden;display:none;}
    </style>
</head>
<body>
<div class="layui-main">
    <form class="layui-form" method="post" action="">
        <input type="hidden" name="form_submit" value="ok" />
        <div class="layui-form-item">
            <label class="layui-form-label">安装包：</label>
            <input type="hidden" id="total_page" value="0"/>
            <input type="hidden" id="webPage" value="1"/>
            <input type="hidden" id="status" value="0"/>
            <div class="layui-input-block">
                <button type="button" class="layui-btn" id="soft_upload"><i class="layui-icon"></i>上传文件</button>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">安装包名：</label>
            <div class="layui-input-block">
                <input type="text" name="name" id="name" value="" lay-verify="title" autocomplete="off" readonly="true" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">下载地址</label>
            <div class="layui-input-block">
                <input type="text" name="down_url" id="down_url" value="" lay-verify="down_url" autocomplete="off" readonly="true" placeholder="" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="submit">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<div class="mask"></div>
<div class="loading">
    <div class="layui-progress layui-progress-big" lay-showpercent="true" lay-filter="upload_jindu">
        <div class="layui-progress-bar layui-bg-red" lay-percent="0%"></div>
    </div>
</div>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use(['form', 'upload', 'element'], function () {
        var form = layui.form;
        var upload = layui.upload;
        var element = layui.element;
        var $ = layui.$;
        upload.render({
            elem: '#soft_upload',
            url: 'upload.php',
            accept: 'file',
            auto: false,
            acceptMime: '.exe,.zip,.rar,.gz',
            choose: function (obj) {
                element.progress('upload_jindu', '0%');
                $('.mask').show();
                $('.loading').show();
                var data = this.data;
                var files = obj.pushFile();
                var LENGTH = 500 * 1024;
                obj.preview(function (index, file, result) {
                    var totalSize = file.size;
                    var total_page = Math.ceil(totalSize / LENGTH);
                    $('#total_page').val(total_page);
                    $('#webPage').val('1');
                    $('#status').val('1');
                    var file_name = file.name;
                    $('#name').val(file_name);
                    var file_ext = file_name.substr(file_name.lastIndexOf('.') + 1);
                    file_name = file_name.substr(0, file_name.lastIndexOf('.'));
                    var jindu_timer = setInterval(function () {
                        var total_page = parseInt($('#total_page').val());
                        var webPage = parseInt($('#webPage').val());
                        var status = $('#status').val();
                        if (parseInt(total_page) == parseInt(webPage) && (parseInt(status) == 2 || parseInt(status) == -1)) {
                            clearInterval(jindu_timer);
                        } else {
                            if (status == 1) {
                                $('#status').val('0');
                                data.file_name = file_name;
                                data.webPage = webPage;
                                data.total_page = total_page;
                                data.file_ext = file_ext;
                                obj.upload(index, file.slice((webPage - 1) * LENGTH, webPage * LENGTH));
                            }
                        }
                    }, 100);
                });
            },
            done: function (res) {
                if (res.status == 1) {
                    var webPage = parseInt($('#webPage').val());
                    var total_page = parseInt($('#total_page').val());
                    element.progress('upload_jindu', Math.ceil(webPage * 100 / total_page) + '%');
                    webPage = webPage + 1;
                    console.log(webPage);
                    $('#webPage').val(webPage);
                    $('#status').val('1');
                } else if (res.status == 2) {
                    element.progress('upload_jindu', '100%');
                    $('#status').val('2');
                    $('#down_url').val(res.down_url);
                    layer.msg('上传成功', {time: 1000, anim: 0}, function () {
                        $('.mask').hide();
                        $('.loading').hide();
                    });
                } else {
                    $('#status').val('-1');
                    element.progress('upload_jindu', '0%');
                    console.log(!typeof (res.down_url) == "undefined");
                    if (typeof (res.down_url) == "undefined") {
                    } else {
                        $('#down_url').val(res.down_url);
                    }
                    layer.msg(res.msg, {time: 3000, anim: 0}, function () {
                        $('.mask').hide();
                        $('.loading').hide();
                    });
                }
            }
        });
    });
</script>
</body>
</html>