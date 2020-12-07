package com.mer.project.Pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WebTreeNode implements Serializable {
    private static final long serialVersionUID = 1L;
    private String pid;
    private String id;
    private String name;
    private boolean spread;//是否展开节点 false 不展开
    private List<WebTreeNode> children;
    private String lever;
}
