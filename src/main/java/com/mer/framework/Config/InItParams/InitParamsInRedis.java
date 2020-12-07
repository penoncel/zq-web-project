package com.mer.framework.Config.InItParams;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * 启动时 加载参数 至 reids 中
 */
@Service
@Slf4j
public class InitParamsInRedis implements ApplicationRunner {
    @Autowired
    ReqIpParams reqIpParams;
    @Autowired
    RoleParams roleParams;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.warn("Loading system parameters");
        reqIpParams.init_ipParams();
        log.warn("Loading  。。。。。。。。");
        roleParams.init_RolesParams();
        log.warn("System parameters loaded .......................................................");
    }
}
