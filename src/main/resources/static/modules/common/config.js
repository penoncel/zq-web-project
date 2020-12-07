

var req = {
    status: {
        ok: 200,
        fail: 400,
        other: 333
    },
    type: {
        get: 'GET',
        post: 'POST'
    }
};

/**
 * Api接口
 * @type {{}}
 */

var api = {
    homeView: '', // kvf-admin主页地址
    login:  'login',
    captchaUrl: 'captcha.jpg',
    comm: {
        selUserView:  'common/selUser',
        userMenus: 'index/menus',  // 用户目录菜单（左侧）
        userNavMenus: 'index/navMenus',  // 用户导航菜单（横向）
        fileUpload: 'common/fileUpload'
    },
    user:{
        datas:  "WebUser/listData"
    }
};


var local = {
	Token:"access_token",
	UserInfo:"UserInfo"	
};
