/**
 扩展一个test模块
 **/
layui.define(function(exports){ //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var obj = {
        hello: function(){
            alert('Hello '+ ('mymod'));
        }
    };

    //输出test接口
    exports('zhaoqi', obj);
});
