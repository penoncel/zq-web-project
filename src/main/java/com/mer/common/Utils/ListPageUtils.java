package com.mer.common.Utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mer.common.Utils.LayUIResult.CodeState;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.project.Vo.UserOnlineVO;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * list 分页 模糊查找
 */
public class ListPageUtils {

    /**
     * 分页 模糊查询 在线用户信息
     * @param pageSize
     * @param pageIndex
     * @param list
     * @param name
     * @return
     */
    public static Map setDataList(int pageSize, int pageIndex, List<Map<String,Object>> list,String name){
        Map map =  new HashMap();
        List<Map<String,Object>> listData =null;
        if(StringUtils.isBlank(name)){
            pageSize = pageSize==0 ? 1:pageSize;
            listData = Pager(pageSize,pageIndex,list);
            map.put("count",list.size());
        }else{
            listData = seach(list,name);
            map.put("count",listData.size());
        }

        map.put("data",listData);
        return map;
    }




    /*
        @param pageSize 当前页面大小
        @param pageIndex 当前页码
        @param list 需要分页的集合
        @return
    */
    public static List<Map<String,Object>> Pager(int pageSize, int pageIndex, List<Map<String,Object>> list) {
        //使用list 中的 sublist 方法分页
        List<Map<String,Object>> dataList = new ArrayList();

        // 每页显示多少条记录
        int currentPage; //当前第几页数据

        // 一共多少条记录
        int totalRecord = list.size();
        int totalPage = totalRecord % pageSize; // 一共多少页
        if (totalPage > 0) {
            totalPage = totalRecord / pageSize + 1;
        } else {
            totalPage = totalRecord / pageSize;
        }

//        System.out.println("总页数：" + totalPage);
        // 当前第几页数据
        currentPage = totalPage < pageIndex ? totalPage : pageIndex;

        // 起始索引
        int fromIndex = pageSize * (currentPage - 1);
        // 结束索引
        int toIndex = pageSize * currentPage > totalRecord ? totalRecord : pageSize * currentPage;
        try {
            if (list.size() > 0) {
                dataList = list.subList(fromIndex, toIndex);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    //执行模糊查询
    public static List seach(List<Map<String,Object>> list, String title) {
        List<Map<String,Object>> results = new ArrayList();
        Pattern pattern = Pattern.compile(title);
        for (int n = 0; n < list.size(); n++) {
            Matcher matcher = pattern.matcher(list.get(n).get("user_name").toString());
            if (matcher.find()) {
                results.add(list.get(n));
            }
        }
        return results;
    }






}
