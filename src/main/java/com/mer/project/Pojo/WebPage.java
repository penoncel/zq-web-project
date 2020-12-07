package com.mer.project.Pojo;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoaqi 15701556037
 */
@Data
@Scope("prototype")
public class WebPage implements Serializable {
    private static final long serialVersionUID = 1L;
    private int page ;//起始页
    private int limit ;//页数大小
    private int count; //数据数量
    private String code;//代码
    private String msg;//信息
    //其他的参数我们把它分装成一个Map对象
    private Map<String, Object> params=new HashMap<String,Object>();

    private HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    public WebPage(){
        //定义条件构造器，用来封装查询条件
        EntityWrapper<Object> ew = new EntityWrapper<>();

        //layuiPage在请求后台的时候会默认 传递 页码以及每页显示的数量

        //设置请求页码
        this.setPage(Integer.valueOf(request.getParameter("page")));
        //设置请求每页展示的数据
        this.setLimit(Integer.valueOf(request.getParameter("limit")));

        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            String notInclude="_,page,limit";//参数，排除分页等参数
            if(notInclude.indexOf(paraName)==-1){
//                params.put(paraName.replace("[", "").replace("]", ""), request.getParameter(paraName).trim());
                params.put(paraName, request.getParameter(paraName).trim());
            }
        }
//        getParams().put("sysUser",new BaseController().getSysUser().toString());//用户信息
//        System.out.println("监听TableForm事件所有的条件："+params.toString());
    }


    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().setAttribute("params", params);
        this.params = params;
    }

}
