package com.mer.project.Service;

import com.mer.project.Pojo.WebIp;
import com.mer.common.Base.Service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * ip信息 服务类
 * </p>
 *
 * @author zhaoqi
 * @since 2020-10-12
 */
public interface WebIpService extends BaseService {

    /**
     * 查询单个 ip信息
     **/
    WebIp getOne(int id);

    /**
     * 添加单个 ip信息
     **/
    int insertOne(WebIp webIp);

    /**
     * 修改单个 ip信息
     **/
    int updateOne(WebIp webIp);

    /**
     * 删除单个 ip信息
     **/
    int deleteOne(int id);

    /**
     *查看ip数量
     * **/
    int findCountip(String ip);
}

