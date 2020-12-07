package com.mer.project.Service;

import com.mer.common.Base.Service.BaseService;
import com.mer.project.Pojo.WebUser;
import com.mer.project.Pojo.WebMenu;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhoaqi 15701556037
 */
public interface WebMenuService extends BaseService {

    /**
     * 获取最大的 下级编号
     * **/
    String getMax_Lowerlevel(String superior);

    /**
     * 获取最大的 上级编号
     * */
    String getMax_Superior();

    /**
     * 获取用户菜单权限
     * */
    List<Map<String,Object>> getUserMenu(WebUser webUser);

    /**
     * 添加菜单
     * */
    int insert(WebMenu webMenu);

    /**
     * 修改菜单
     * */
    int update(WebMenu webMenu);

    /**
     * 删除菜单
     * */
    int delete(int id);

    /**
     * 查询菜单
     * */
    List<Map<String,Object>> getList();

    /**
     * 查询菜单
     * */
    List<Map<String,Object>> getTree();

    /**
     * 单个
     * */
    WebMenu getOneInt(int id);

    /**
     * 数量
     * */
    int getNumber(int parents);
}
