package com.mer.project.Controller;


import cn.hutool.core.util.StrUtil;
import com.mer.common.Base.Controller.BaseController;
import com.mer.common.Constant.Constant;
import com.mer.common.RedisKeySet.LoginUserInfoKey;
import com.mer.common.RedisKeySet.RoleKey;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.framework.Annotction.LOG;
import com.mer.framework.Annotction.LogingOutLOG;
import com.mer.framework.Annotction.RequestLimit;
import com.mer.framework.Config.OnleSession.OnleUser;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.framework.Config.Shiro.RedisShiro.RedisCacheManager;
import com.mer.framework.UserUtils;
import com.mer.project.Dao.WebRoleDao;
import com.mer.project.Domain.RoleMain;
import com.mer.project.Pojo.WebUser;
import com.mer.project.Service.WebMenuService;
import com.mer.project.Service.WebRoleService;
import com.mer.project.Service.WebUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author zhoaqi 15701556037
 * @RestController
 * @RequiresPermissions(value={"root","SysUser:delete"},logical = Logical.OR)
 * shiro 权限注解，如果 设定了多个权限，会遍历多次Realm中的权限，也就是会进行权限遍历后在授权。（注意：需要释放掉 自动代理 DefaultAdvisorAutoProxyCreator 避免再次重复）
 *  RequiresRoles         如果设置了，roles 会先确认是否存在对应的角色
 *  RequiresPermissions   如果设置了，permissions，当设置了roles 是并且的的关系，如果没有roles 只验证 permissions 权限，不验证角色
 * */
@Controller
@RequestMapping("/WebUser")
@RequestLimit(maxCount = Constant.maxCount_RequestLimit,second = Constant.second_RequestLimit)
@Slf4j
@SuppressWarnings("all")
public class WebUserController extends BaseController {

    @Autowired
    OnleUser onleUser;

    @Autowired
     WebUserService sysWebUserService;

    @Autowired
     WebMenuService webMenuService;

    @Autowired
     WebRoleService sysWebRoleService;

    @Autowired
     WebRoleDao webRoleDao;

    @Autowired
     RedisService redisService;



    @LOG(operModul = "用户模块",operType = "用户登入",operDesc = "账号密码登入")
    @PostMapping("/goLogin")
    @ResponseBody
    public Result goLogin(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("rememberMe") boolean rememberMe){
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        this.getSession().setAttribute("loginName",username);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return Result.error("账户不存在");
        } catch (IncorrectCredentialsException ice) {
            sysWebUserService.update_login_errors(username);
            return Result.error("密码不正确");
        } catch (LockedAccountException lae) {
            return Result.error("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return Result.error("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            return Result.error("用户名或密码不正确");
        }
        //验证是否认证成功
        if (subject.isAuthenticated()) {
            //用户对象
            WebUser user = UserUtils.getUserInfo();
//            if(redisService.exists(LoginUserInfoKey.UserInfo,username)){
//                user  =redisService.get(LoginUserInfoKey.UserInfo,username,WebUser.class);
//            }else {
//                user = sysWebUserService.findByUsername(username);
//                if(!Objects.isNull(user)){
//                    redisService.set(LoginUserInfoKey.UserInfo,username,user);
//                }
//            }

            //Role 权限
            RoleMain roleMain =  null;
            if(redisService.exists(RoleKey.roleKey,user.getLever())){
                roleMain =redisService.get(RoleKey.roleKey,user.getLever(),RoleMain.class);
            }else{
                roleMain = new RoleMain();
                roleMain.setRolePsermissions(webRoleDao.fingRolePsermission(Integer.valueOf(user.getLever())));
                roleMain.setRoleMenus(webRoleDao.findRoleMenus(Integer.valueOf(user.getLever())));
                redisService.set(RoleKey.roleKey,user.getLever(),roleMain);
            }
            this.getSession().setAttribute("loginUser",user);
            this.getSession().setAttribute("loginName",user.getUsername());
            this.getSession().setAttribute("menuList", roleMain.getRoleMenus());
            sysWebUserService.reset_login_errors(username);
            return Result.success("7777","信息认证通过");
        } else {
            token.clear();
        }
        return null;
    }

    @LogingOutLOG(operModul = "用户模块",operType = "退出登入",operDesc = "账号退出")
    @GetMapping("/logout")
    @ResponseBody
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();//取出当前验证主体

        if (subject == null) {
            return Result.error("1","退出失败(用户不存在)");
        }

        Object name  = subject.getPrincipal();
        if(Objects.isNull(name)){
            return Result.error("1","退出失败(关键信息不存在)");
        }
        subject.logout();
        redisService.delete(RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX,name.toString());//干掉 shiro:cache
        return Result.success("0","退出成功");

    }



    @LOG(operModul = "用户模块",operType = "信息查看",operDesc = "个人资料")
    @GetMapping("/UserInfo")
    public String UserInfo() {
        this.setAttribute("webUser",getSysUser());
        this.setAttribute("role", sysWebRoleService.getOne_Role(Integer.valueOf(getSysUser().getLever())));
        return this.toAction();
    }

    @LOG(operModul = "用户模块",operType = "信息变更",operDesc = "打开修改密码窗口")
    @GetMapping("/UserPassword")
    public String UserPassword() {
        this.setAttribute("id",getSysUser().getId());
        return this.toAction();
    }

    @LogingOutLOG(operModul = "用户模块",operType = "信息变更",operDesc = "修改登入密码")
    @PostMapping("/editUserPwd")
    @ResponseBody
    public Result editUserPwd(String id ,String oldPassword,String password,String repassword){
        if (StrUtil.isBlank(oldPassword) && StrUtil.isBlank(password)) {
            return Result.error("非法的参数!");
        }
        //用户修改密码
        WebUser user = sysWebUserService.findById(Integer.parseInt(id));
        //旧密码是否一致
        oldPassword= String.valueOf(new SimpleHash("md5", oldPassword, null, 2));
        if(!user.getUserpass().equals(oldPassword)){
            return Result.error("旧密码不正确!");
        }
        //新密码与旧密码是否一致
        repassword= String.valueOf(new SimpleHash("md5", repassword, null, 2));
        if(user.getUserpass().equals(repassword)){
            return Result.error("新旧密码不可相同!");
        }
        user.setUserpass(repassword);
        user.setStatus(1);
        user.setLogin_errors(0);
        sysWebUserService.updateUserPwd(user);

        if(redisService.exists(LoginUserInfoKey.UserInfo,user.getUsername())){
            redisService.delete(LoginUserInfoKey.UserInfo,user.getUsername());
        }
        redisService.set(LoginUserInfoKey.UserInfo,user.getUsername(),user);

        Subject subject = SecurityUtils.getSubject();//取出当前验证主体
        if (subject != null) {
            subject.logout();//不为空，执行一次logout的操作，将session全部清空
            redisService.delete(RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX,user.getUsername());//干掉 shiro:cache
        }
        return Result.success("密码修改成功");
    }
























  /*************************************************/
    @GetMapping("/list")
    public String list(){
      return this.toAction();
  }

    @GetMapping("/listData")
    @ResponseBody
    @LOG(operModul = "用户模块",operType = "查看用户",operDesc = "查看用户详情信息")
    public Result listData(){
        return Result.success(sysWebUserService.getPage(this.getPage(null)));
    }

    @LOG(operModul = "用户模块",operType = "用户信息操作",operDesc = "查看单个用户详情")
    @RequestMapping("/add")
    public String add(@RequestParam(value="id",required=false) Integer id) {
        WebUser webUser =null;
        if(id!=null){
            webUser = sysWebUserService.findById(id);
        }else{
            //暂无操作
            webUser = new WebUser();
        }
        this.setAttribute("webUser", webUser);
        this.setAttribute("role", sysWebRoleService.getSysRoleList());
        return this.toAction();
    }

    @LOG(operModul = "用户模块",operType = "用户信息操作",operDesc = "保存或修改用户信息")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdate(@RequestBody WebUser webUser){
        if (webUser.getId() == null) {
            if(sysWebUserService.findCountUser(webUser.getUsername())<=0){
                webUser.setAgent_num("");
                webUser.setStatus(1);
                webUser.setBlog_form(1);
                sysWebUserService.insert_one_user(webUser);
            }else{
                return Result.error("用户名："+ webUser.getUsername()+"已存在,不可重复！");
            }
        } else {
            sysWebUserService.update_one_user(webUser);
            if(redisService.exists(LoginUserInfoKey.UserInfo,webUser.getUsername())){
                redisService.delete(LoginUserInfoKey.UserInfo,webUser.getUsername());
                redisService.set(LoginUserInfoKey.UserInfo,webUser.getUsername(),webUser);
            }
        }
        return Result.success();
    }


    @LOG(operModul = "用户模块",operType = "用户信息操作",operDesc = "状态更改启用或禁用用户")
    @PostMapping("/updateStatus")
    @ResponseBody
    public Result updateStatus(@RequestParam String str_arr) {
        return sysWebUserService.updateStatus(str_arr);
    }

    @LOG(operModul = "用户模块",operType = "用户信息操作",operDesc = "删除用户")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id") Integer id){
       return sysWebUserService.delete_one_user(id);
    }



}
