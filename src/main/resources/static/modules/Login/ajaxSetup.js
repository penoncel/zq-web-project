$(function() {
    // alert("您的登录信息已超时，请重新登录");解决iframe子页面嵌套登入页面问题
    var _topWin = window;
    while (_topWin != _topWin.parent.window) {
        _topWin = _topWin.parent.window;
    }
    if (window != _topWin)_topWin.document.location.href = '/login';
})
