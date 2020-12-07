package com.mer.project.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoaqi 15701556037
 */

@TableName("web_menu")
@Data
public class WebMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer parentid;//顶级编号

    private Integer id;
    private String menu_name;
    private String parents;//父类标识
    private String css;
    private String href;
    private String lever;
    private String note;
    private String type;
    private String parent_id;//菜单标识

    private String superior;//上级
    private String lowerlevel;//下级
    private Integer belogin;//所属平台


}
