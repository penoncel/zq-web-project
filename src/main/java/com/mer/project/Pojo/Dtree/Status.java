package com.mer.project.Pojo.Dtree;

import lombok.Data;

/** 信息状态类*/
@Data
public class Status {
    /** 状态码*/
    private int code = 200;
    /** 信息标识*/
    private String message = "success";







//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //1. 从request中获取dtree传来的参数
//        String nodeId = request.getParameter("nodeId");
//        //2. 根据参数查询数据库数据，假设查的是User表的数据
//        List<User> users = userService.query(nodeId);
//        //3. 此时你可以直接将users放在DTreeResponse中然后将DTreeResponse变成json格式返回，只要在在树中配置了response映射字段，树组件也会成功识别数据
//        DTreeResponse resp = new DTreeResponse();
//        resp.setData(users);
//        printJson(resp); // 输出JSON格式
//
//        //3.或者user中没有树需要的全部对象，你可以将users转换为List<DTree>对象，根据业务逻辑分配checkArr参数，或者是递归将数据放在children中使数据具有层级关系，最后将List<dtree>放在DTreeResponse中。
//        List<DTree> dtrees = new ArrayList<DTree>();
//        // 遍历数据
//        DTree d = null;
//        for(User user : users){
//            d = new DTree();
//            d.setId(user.getId());
//            //... 做出转换，最后放进集合中
//
//            dtrees.add(d);
//        }
//        DTreeResponse resp = new DTreeResponse();
//        resp.setData(dtrees);
//        printJson(resp); // 输出JSON格式
//    }
//</dtree>
}
