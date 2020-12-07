package com.mer.common.Base.Service;

import com.mer.project.Pojo.WebPage;

import java.util.Map;

public interface BaseService {
    /**
     * 获取对应分页数据
     * */
    Map<String,Object> getPage(WebPage webPage);


}

