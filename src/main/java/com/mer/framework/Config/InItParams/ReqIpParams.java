package com.mer.framework.Config.InItParams;

import com.mer.common.RedisKeySet.ReqIpKey;
import com.mer.common.RedisKeySet.RoleKey;
import com.mer.common.Utils.WriteFrom;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.project.Dao.WebIpDao;
import com.mer.project.Domain.RoleMain;
import com.mer.project.Pojo.WebIp;
import com.mer.project.Pojo.WebRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 访问ip
 */
@Service
@Slf4j
public class ReqIpParams {
    @Autowired
    RedisService redisService;

    @Autowired
    WebIpDao webIpDao;

    /**
     * 初始化 ip 信息
     */
    public void init_ipParams() {
        try{
            log.warn("system ip parameters ");
            redisService.delete(ReqIpKey.reqIpKey);
            List<WebIp> ip_list =  webIpDao.getList();
            if(Objects.isNull(ip_list)){
                log.error("System parameter loading error: the webIpDao.getList() is null ");
            }else {
                for (WebIp webIp : ip_list) {
                    redisService.set(ReqIpKey.reqIpKey,webIp.getIp(),webIp);
                    log.warn("Loading  ip "+webIp.toString());
                }
            }
            log.warn("system ip parameters - Over");
        }catch (Exception e){
            e.printStackTrace();
            log.error("初始化参数失败"+WriteFrom.WriterEx(e));
        }
    }
}
