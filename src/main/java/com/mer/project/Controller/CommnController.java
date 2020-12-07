package com.mer.project.Controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.mer.common.Base.Controller.BaseController;
import com.mer.common.Constant.Constant;
import com.mer.common.RedisKeySet.RedisSessionKey;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.framework.Config.Kaptcha.KaptchaUtil;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.framework.UserUtils;
import com.mer.project.Pojo.WebUser;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


/**
 * @author zhoaqi 15701556037
 *
 * 所有不需要权限的控制器
 * **/
@Controller
@RequestMapping("/")
@Slf4j
public class CommnController extends BaseController {
    @Resource
    DefaultKaptcha captchaProducer;

    @Resource
    RedisService redisService;

    /**
     * 默认登入视图
     * **/
    @GetMapping("login")
    public String login(HttpServletRequest request,HttpServletResponse response)throws Exception{
        if(request.getRequestURI().contains("JSESSIONID")){
            response.sendRedirect("login");
        }
        return "login";
    }

    @GetMapping("index")
    public String index(){
        return "index";
    }


    @GetMapping("/Lock")
    public String Lock(ModelMap modelMap) {
        modelMap.put("avatar","avatar");
        WebUser webUser = (WebUser) SecurityUtils.getSubject().getPrincipal();
        webUser.setLockStatus(-1);
        UserUtils.reloadUser(webUser);
        return "Lock";
    }


    /**
     * 登录验证码图片
     */
    @RequestMapping(value = {"/loginValidateCode"})
    public void loginValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        KaptchaUtil.validateCode(request,response,captchaProducer, Constant.LOGIN_VALIDATE_CODE);
    }
    /**
     * 检查验证码是否正确
     */
    @PostMapping("/checkLoginValidateCode")
    @ResponseBody
    public HashMap checkLoginValidateCode(@RequestParam("validateCode")String validateCode) {
        String loginValidateCode = getSession().getAttribute(Constant.LOGIN_VALIDATE_CODE).toString();
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(loginValidateCode == null){
            map.put("status",null);//验证码过期
        }else if(loginValidateCode.equals(validateCode)){
            map.put("status",true);//验证码正确
        }else if(!loginValidateCode.equals(validateCode)){
            map.put("status",false);//验证码不正确
        }
        map.put("code",200);
        return map;
    }



    /**
     * 登录验证码图片
     * https://github.com/whvcse/EasyCaptcha
     */
    @GetMapping(value = {"/captcha"})
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //换成redis 便于后面判断 失效否
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        GifCaptcha gifCaptcha = new GifCaptcha();
        gifCaptcha.setLen(5);
        gifCaptcha.setFont(0, 32f);
        gifCaptcha.setFont(Captcha.FONT_1);

//        String code =gifCaptcha.text().toLowerCase();
        String code = "12345";

//        System.out.println("code:"+code);
        // 验证码存入session
        String sessionid = request.getSession().getId();
//        request.getSession().setAttribute(sessionid, code);

        if(redisService.exists(RedisSessionKey.sessionCode,sessionid)){
            redisService.delete(RedisSessionKey.sessionCode,sessionid);
        }
        redisService.set(RedisSessionKey.sessionCode,sessionid,code);

        // 输出图片流
        gifCaptcha.out(response.getOutputStream());
    }
    /**
     * 检查验证码是否正确
     */
    @PostMapping("/captchaValidateCode")
    @ResponseBody
    public Result captchaValidateCode(@RequestParam("validateCode")String validateCode,HttpServletRequest request) {
        String sessionid = request.getSession().getId();
        if(!redisService.exists(RedisSessionKey.sessionCode,sessionid)){
            return Result.error("501","验证码过期请重新输入");
        }
        String loginValidateCode = redisService.getString(RedisSessionKey.sessionCode,sessionid);
        validateCode =validateCode.toLowerCase();
        if(StringUtils.isEmpty(loginValidateCode)){
            return Result.error("验证码不可为空");
        }

        if(!loginValidateCode.equals(validateCode)){
            return Result.error("验证码不正确");
        }

        if(loginValidateCode.equals(validateCode)){
            return Result.success("验证码正确");
        }
        return null;
    }



    @RequestMapping("killOut")
    public String killOut(){
        return "killOut";
    }

}
