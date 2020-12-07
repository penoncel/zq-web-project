package com.mer.common.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : zhaoqi
 * @CreateTime : 2020-09-22
 * @Description : request.getParameterMap() 转 map
 **/
public class RequestToMap {

    /**
     * 转换request 请求参数
     * @param paramMap request获取的参数数组
     */
    public static Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

}
