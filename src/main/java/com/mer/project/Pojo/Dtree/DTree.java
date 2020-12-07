package com.mer.project.Pojo.Dtree;
import lombok.Data;

/** 树类*/
@Data
public class DTree {
    /** 节点ID*/
    private String id;
    /** 上级节点ID*/
    private String parentId;
    /** 节点名称*/
    private String title;
    /** 是否展开节点*/
    private Boolean spread;
    /** 是否最后一级节点*/
    private Boolean last;
    /** 是否隐藏*/
    private Boolean hide;
    /** 是否禁用*/
    private Boolean disabled;
    /** 自定义图标class*/
    private String iconClass;
    /** 表示用户自定义需要存储在树节点中的数据*/
    private Object basicData;
//    /** 复选框集合*/
//    private List<CheckArr> checkArr = new ArrayList<CheckArr>();
//    /** 子节点集合*/
//    private List<DTree> children = new ArrayList<DTree>();
}
