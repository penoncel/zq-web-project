package com.mer.project.Service;

import com.mer.project.Pojo.WebOperationLog;
import com.mer.common.Base.Service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志记录 服务类
 * </p>
 *
 * @author zhaoqi
 * @since 2020-10-09
 */
public interface WebOperationLogService extends BaseService {

    /**
     * 查询单个 系统日志记录
     **/
    WebOperationLog getOne(int id);

    /**
     * 添加单个 系统日志记录
     **/
    int insertOne(WebOperationLog webOperationLog);

    /**
     * 修改单个 系统日志记录
     **/
    int updateOne(WebOperationLog webOperationLog);

    /**
     * 删除单个 系统日志记录
     **/
    int deleteOne(int id);

}

