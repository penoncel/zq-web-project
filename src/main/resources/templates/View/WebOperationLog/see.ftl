<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 50px 0 0;">
    <form class="layui-form" action="" lay-filter="component-form-element">

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">硬件设备</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.device)!''}" autocomplete="off" class="layui-input">
                </div>
                <label class="layui-form-label">设备系统</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.deviceSys)!''}" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">设备版本</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.deviceV)!''}" autocomplete="off" class="layui-input">
                </div>

            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">操作模块</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.operModule)!''}" autocomplete="off" class="layui-input">
                </div>

                <label class="layui-form-label">操作类型</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.operType)!''}" autocomplete="off" class="layui-input">
                </div>

            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">操作描述</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.operMsg)!''}" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>


        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">请求ip</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.reqIp)!''}" autocomplete="off" class="layui-input">
                </div>
                <label class="layui-form-label">请求URI</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.reqUri)!''}" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">操作方法</label>
            <div class="layui-input-block" style="width: 500px">
                <input type="text"  value="${(logOp.operMethod)!''}" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">操作时间</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.operTimes)!''}" autocomplete="off" class="layui-input">
                </div>

                <label class="layui-form-label">结束时间</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.respTimes)!''}" autocomplete="off" class="layui-input">
                </div>

            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">请求参数</label>
            <div class="layui-input-block" style="width: 500px;">
                <textarea class="layui-textarea" style="height: 180px">${(logOp.reqParameter)!''}</textarea>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">返回结果</label>
            <div class="layui-input-block" style="width: 500px">
                <textarea class="layui-textarea" style="height: 180px">${(logOp.respParameter)!''}</textarea>
            </div>
        </div>


        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">耗时(S)</label>
                <div class="layui-input-inline" >
                    <input type="text"  value="${(logOp.takeUpTime)!''}"  class="layui-input">
                </div>
            </div>
        </div>



    </form>


</div>

<script>


</script>



</body>
</html>