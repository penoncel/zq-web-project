package com.mer.project.Service;


import com.mer.common.Base.Service.BaseService;
import com.mer.project.Pojo.SysFtp;

public interface SysFtpService extends BaseService {

    /**
     * 获取用户对象
     * **/
    SysFtp getOne(Integer id);


    /**
     * 添加诺漫斯交易
     * @param parameter
     */
    void insert_nuomansitrad(Object parameter);


}
