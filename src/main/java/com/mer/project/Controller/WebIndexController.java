package com.mer.project.Controller;


import com.mer.common.Base.Controller.BaseController;
import com.mer.common.Constant.Constant;
import com.mer.framework.Annotction.RequestLimit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhoaqi 15701556037
 * */
@Controller
@RequestMapping("/SysIndex")
@RequestLimit(maxCount = Constant.maxCount_RequestLimit,second = Constant.second_RequestLimit)
public class WebIndexController extends BaseController {

    @GetMapping("/console")
    public String console(){
        return this.toAction();
    }


    /**
     * 文件管理
     * **/
    @GetMapping("/locaFile")
    public String locaFile(String id){
        System.out.println(id);
        return this.toAction();
    }


    /**
     * 消息中心
     * **/
    @GetMapping("/message")
    public String message(){
        return this.toAction();
    }




}
