package com.mer.framework.Config.Shiro.Realm;

import com.mer.common.RedisKeySet.LoginUserInfoKey;
import com.mer.common.RedisKeySet.RoleKey;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.project.Dao.WebRoleDao;
import com.mer.project.Domain.RoleMain;
import com.mer.project.Pojo.WebUser;
import com.mer.project.Service.WebUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Objects;

/**
 *  @author zhoaqi 15701556037
 *
 * 自定义realm
 * 数据源
 * 身份认证=》权限认证
 */
@SuppressWarnings("all")
@Slf4j
public class UserRealm extends AuthorizingRealm {


    @Autowired
    private WebUserService webUserService;

    @Autowired
    private WebRoleDao webRoleDao;

    @Autowired
    private RedisService redisService;

    /**
     * 获取授权信息
     * @param principals principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("监测授权 doGetAuthorizationInfo......");
        WebUser user = (WebUser)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

//        WebUser user = null;
//        if(redisService.exists(LoginUserInfoKey.UserInfo,username)){
//            user  =redisService.get(LoginUserInfoKey.UserInfo,username,WebUser.class);
//        }else {
//            user = webUserService.findByUsername(username);
//            if(!Objects.isNull(user)){
//                redisService.set(LoginUserInfoKey.UserInfo,username,user);
//            }
//        }

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
//        log.info("用户名："+username+"，角色权限："+roleMain.getRolePsermissions().toString());
        simpleAuthorizationInfo.setStringPermissions(roleMain.getRolePsermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 获取身份认证信息
     * @param auth authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        log.info("权限配置-->UserRealm.doGetAuthorizationInfo()");
        //从token获取用户信息，token代表用户输入
        String username = (String)token.getPrincipal();
        WebUser user = null;
        if(redisService.exists(LoginUserInfoKey.UserInfo,username)){
            user  =redisService.get(LoginUserInfoKey.UserInfo,username,WebUser.class);
        }else {
             user = webUserService.findByUsername(username);
            if(!Objects.isNull(user)){
                 redisService.set(LoginUserInfoKey.UserInfo,username,user);
            }
        }
        if(user==null){
            //账户不存在
            throw new UnknownAccountException();
        }
        if(user.getStatus()==2){
            //账户被锁定
            throw new LockedAccountException();
        }
        //取密码
        String pwd = user.getUserpass();
        if(pwd == null || "".equals(pwd)){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo  = new SimpleAuthenticationInfo(
                user,
                user.getUserpass(),
                this.getClass().getName()
        );
        return authenticationInfo;
    }


}
