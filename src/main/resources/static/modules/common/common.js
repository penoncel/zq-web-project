/**
 * layui 公共
 */
var _LAY ={
    /**
     * 初始化table
     */
    initTableSet:function (layui) {
        layui.form.on('submit(LAY-user-front-search)', function(data){
            var field = data.field;
            //执行条件重载
            layui.table.reload('demo', {where: field});
        });
        layui.table.set({
            height: 'full-140'
            ,cellMinWidth: 80
            ,limit:10
            ,toolbar:'#toolbarDemo'
            ,page: true // 开启分页
            ,limits:[10,20,50,100,200,500]
            ,done:function(res, curr, count){
                // //数据的回调用，可不写
                // console.log("表格渲染完成：");
                // console.log(res);
                // console.log(curr);
                // console.log(count);
            }
        })
    },
    /**
     *  选中 select
     * @param selectID
     * @param value
     */
    checkSelect: function selectOptionSelect(selectID,value) {
        if(value == "")return;
        // 一级目录回显
        $("#"+selectID).each(function () {// 遍历select
            $(this).children("option").each(function () {// 遍历option
                if (this.value == value) {// 跟后台传过来的id相同就显示selected
                    // console.log("一级目录"+$(this).text());
                    $(this).attr("selected", "selected");
                    form.render('select');// 注意：一定要调用该方法进行中心渲染，否则数据是显示不出来的
                }
            });
        });
    }

}


/**
 * 提示信息
 * @type {{no: alert.no, ok: alert.ok}}
 */
var Msg ={

    TimeOut:function(data){
        var msg = data.msg ;
        var icon = 7;
        var time = 5000;
        if( data.code == "200" ){
            msg = msg +'，即将跳转请重新登入。';
            icon = 1;
            time = 2000;
        }
        parent.layer.msg(msg,{ anim: 6,icon:icon,time:time,shade: 1},function(){
            top.location.href= '/login';
        });
    },
    ok:function (data) {
        layer.msg(data.msg,{icon: 1,time:1000,shade: 0.5},function(){
            layui.table.reload('demo');
        });
    },
    no:function (data) {
        layer.msg(data.msg, {icon: -2,shade: 0.4,anim: 6})
    },
    MyEr:function(data){
        if(data.status=="401"){
            var ss  = eval("("+data.responseText+")");
            layer.msg(ss.msg,{icon: 4 ,time:ss.times * 1000 ,shade: 0.7,anim: 6},function(){
                top.location.href= '/login';
            });
        }else{
            layer.msg("服务器飞走了", {icon: 2,shade: 0.4,anim: 6})
        }

    },
    MyOk:function (data) {
        var CODE = data.code;
        var MSG =  data.msg;

        console.log(data)
        switch (CODE) {

            //跳转登入
            case "7777":
                layer.msg(MSG,{icon: 1,time:1000,shade: 0.5},function(){
                    top.location.href="/index";
                });
                break;
            //超时
            case "TimeOut":
                parent.layer.msg(MSG,{ anim: 6,icon: 7,time:5000,shade: 1},function(){
                    top.location.href= '/login';
                });
                break;

            //拒绝访问无权限
            case "403":
                layer.msg(MSG ,{icon: 5,anim: 6});
                break;

            //成功
            case "200":
                layer.msg(MSG,{icon: 1,time:1000,shade: 0.5},function(){
                    layui.table.reload('demo');
                });
                break;

            //正常失败
            case "500":
                layer.msg(MSG, {icon: 5,anim: 6,offset: '100px'})
                break;

            //其他错误
            default:
                layer.msg(MSG, {icon: 2,anim: 6})
        }
    }

}

 /*
  * js工具
  */
var reqAjax ={
	
	/** 
	 * ajax 同步请求
     * @param url
     * @param params
     * @param method
     * @param callback
     * @returns {string}
     */
    ajx: function (url, params, method, callback) {
        var result = "";
        if (!url) {
            return result;
        }
        params = params || {};
        method = method || req.type.get;
        callback = callback || function (r) {
            result = r;
        };
        $.ajax({
            type: method,
            url: url,
            data: params,
            async: false,
            success: callback
        });
        return result;
    },
    
    /**
     * 异步get请求
     * @param url
     * @param params
     * @param callback
     */
    aGet: function (url, params, callback) {
        if (arguments.length === 2 && $.isFunction(arguments[1])) {
            callback = arguments[1];
            params = {};
        }
        $.ajax({
            type: req.type.get,
            url: url,
            data: params,
            success: callback
        });
    },

    /**
     * 异步post请求
     * @param url
     * @param params
     * @param callback
     */
    aPost: function (url, params, callback) {
        if (arguments.length === 2 && $.isFunction(arguments[1])) {
            callback = arguments[1];
            params = {};
        }
        $.ajax({
            url: url,
            type: req.type.post,
            dataType : 'JSON',
            contentType : 'application/json;charset=utf-8',
            async:false,
            data: params,
            success:function (data) {
                Msg.MyOk(data)
            },
            error:function (data) {
                Msg.MyEr(data)
            }
        });
    },

    /**
     * 异步post请求 - 自定义 callback 后面的动作
     * @param url
     * @param params
     * @param callback
     */
    aPostBack: function (url, params, callback) {
        if (arguments.length === 2 && $.isFunction(arguments[1])) {
            callback = arguments[1];
            params = {};
        }
        $.ajax({
            url: url,
            type: req.type.post,
            dataType : 'JSON',
            contentType : 'application/json;charset=utf-8',
            async:false,
            data: params,
            success: callback,
            error:function (data) {
                Msg.MyEr(data)
            }
        });
    },


    /**
     * 异步post请求
     * @param url
     * @param params
     * @param callback
     */
    aPostNoContnet: function (url, params, callback) {
        if (arguments.length === 2 && $.isFunction(arguments[1])) {
            callback = arguments[1];
            params = {};
        }
        $.ajax({
            url: url,
            type: req.type.post,
            dataType : 'JSON',
            async:false,
            data: params,
            success:function (data) {
                Msg.MyOk(data)
            },
            error:function (data) {
                Msg.MyEr(data)
            }
        });
    },

    /**
     * 异步post请求 - 自定义 callback 后面的动作
     * @param url
     * @param params
     * @param callback
     */
    aPostBackNoContnet: function (url, params, callback) {
        if (arguments.length === 2 && $.isFunction(arguments[1])) {
            callback = arguments[1];
            params = {};
        }
        $.ajax({
            url: url,
            type: req.type.post,
            dataType : 'JSON',
            async:false,
            data: params,
            success: callback,
            error:function (data) {
                Msg.MyEr(data)
            }
        });
    },


    /**
     * 同步get
     * @param url 请求地址：image/ascii
     * @param params 参数：{name:'kalvin'}
     * @param callback 回调函数
     */
    get: function (url, params, callback) {
        if (arguments.length === 2 && $.isFunction(arguments[1])) {
            callback = arguments[1];
            params = {};
        }
        return this.ajx(url, params, req.type.get, callback);
    },
    
    /**
     * 同步post
     * @param url 请求地址：image/ascii
     * @param params 参数：{name:'kalvin'}
     * @param callback 回调函数
     */
    post: function (url, params, callback) {
        if (arguments.length === 2 && $.isFunction(arguments[1])) {
            callback = arguments[1];
            params = {};
        }
        return this.ajx(url, params, req.type.post, callback);
    }
}


