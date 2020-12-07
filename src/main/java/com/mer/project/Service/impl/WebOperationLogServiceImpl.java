package com.mer.project.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mer.project.Dao.WebOperationLogDao;
import com.mer.project.Pojo.WebOperationLog;
import com.mer.project.Service.WebOperationLogService;
import com.mer.project.Pojo.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志记录 服务实现类
 * </p>
 *
 * @author zhaoqi
 * @since 2020-10-09
 */
@Service
@Transactional
@SuppressWarnings("/all")
public class WebOperationLogServiceImpl implements WebOperationLogService {

    @Autowired
    private WebOperationLogDao webOperationLogDao;

    @Override
    public WebOperationLog getOne(int id) {
        return webOperationLogDao.getOne(id);
    }

    @Override
    public int insertOne(WebOperationLog webOperationLog){
        return webOperationLogDao.insertOne(webOperationLog);
    }

    @Override
    public int updateOne(WebOperationLog webOperationLog){
        return webOperationLogDao.updateOne(webOperationLog);
    }

    @Override
    public int deleteOne(int id){
        return webOperationLogDao.deleteOne(id);
    }

    @Override
    public Map<String, Object> getPage(WebPage webPage) {
        PageHelper.startPage(webPage.getPage(), webPage.getLimit());
        List<Map<String,Object>> listDATA  = webOperationLogDao.getPageList(webPage);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo(listDATA);
        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("code", "0");
        jsonMap.put("msg", "SUCCESS");
        jsonMap.put("data", listDATA);//数据结果
        jsonMap.put("count", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }


}

