package com.mer.common.Base.Dao;

import com.mer.project.Pojo.WebPage;

import java.util.List;
import java.util.Map;

public interface BaseDao {

    /**
     * 获取分页信息
     * */
    List<Map<String,Object>> getPageList(WebPage WebPage);

}
