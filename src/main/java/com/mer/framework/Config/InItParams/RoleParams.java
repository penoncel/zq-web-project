package com.mer.framework.Config.InItParams;

import com.mer.common.RedisKeySet.ReqIpKey;
import com.mer.common.RedisKeySet.RoleKey;
import com.mer.common.Utils.WriteFrom;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.project.Dao.WebRoleDao;
import com.mer.project.Domain.RoleMain;
import com.mer.project.Pojo.WebIp;
import com.mer.project.Pojo.WebRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 角色权限
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class RoleParams {

    @Autowired
    RedisService redisService;

    @Autowired
    WebRoleDao webRoleDao;
    /**
     * 初始化 ip 信息
     */
    public void init_RolesParams(){

        try{
            log.warn("Roles parameters...... ");
            redisService.delete(RoleKey.roleKey);
            //获取所有角色
            List<WebRole> webRoleList = webRoleDao.getAllRole();
            if(Objects.isNull(webRoleList)){
                log.error("System parameter loading error: the webRoleDao.getAllRole() is null ");
            }else {
                RoleMain roleMain ;
                for (WebRole webRole :webRoleList){
                    roleMain = new RoleMain();
                    roleMain.setRolePsermissions( webRoleDao.fingRolePsermission(webRole.getId()));
                    roleMain.setRoleMenus(webRoleDao.findRoleMenus(webRole.getId()));
                    redisService.set(RoleKey.roleKey,webRole.getId().toString(),roleMain);
                    log.warn("Loading  Role "+webRole.toString());
                }
            }
            log.warn("Roles parameters - Over");
        }catch (Exception e){
            e.printStackTrace();
            log.error("初始化参数失败"+ WriteFrom.WriterEx(e));
        }
    }

}
