package com.mer.project.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mer.project.Dao.WebIpDao;
import com.mer.project.Pojo.WebIp;
import com.mer.project.Service.WebIpService;
import com.mer.project.Pojo.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ip信息 服务实现类
 * </p>
 *
 * @author zhaoqi
 * @since 2020-10-12
 */
@Service
@Transactional
@SuppressWarnings("/all")
public class WebIpServiceImpl implements WebIpService {

    @Autowired
    private WebIpDao webIpDao;

    @Override
    public WebIp getOne(int id) {
        return webIpDao.getOne(id);
    }

    @Override
    public int insertOne(WebIp webIp){
        return webIpDao.insertOne(webIp);
    }

    @Override
    public int updateOne(WebIp webIp){
        return webIpDao.updateOne(webIp);
    }

    @Override
    public int deleteOne(int id){
        return webIpDao.deleteOne(id);
    }

    @Override
    public Map<String, Object> getPage(WebPage webPage) {
        PageHelper.startPage(webPage.getPage(), webPage.getLimit());
        List<Map<String,Object>> listDATA  = webIpDao.getPageList(webPage);
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

    @Override
    public int findCountip(String ip) {
        return webIpDao.findCountip(ip);
    }
}

