package com.mer.project.Domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色 按钮权限 菜单权限
 * @author zhaoqi
 * @date 2020/5/20 17:20
 */
@Data
@NoArgsConstructor
public class RoleMain implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 按钮
     */
    Set<String> rolePsermissions;
    /**
     * 菜单
     */
    private List<Map<String,Object>> roleMenus;

}
