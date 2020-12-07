package com.mer.project.Controller;

import com.mer.common.Base.Controller.BaseController;
import com.mer.common.Constant.Constant;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.framework.Annotction.RequestLimit;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import com.mer.project.Service.WebOperationLogService;
import com.mer.project.Pojo.WebOperationLog;


/**
* <p>
 *     系统日志记录 控制器
* </p>
 * @author zhaoqi
* @since 2020-10-09
*/
@Controller
@RequestMapping("/WebOperationLog" )
@Slf4j
@RequestLimit(maxCount = 50,second = Constant.second_RequestLimit)
public class WebOperationLogController extends BaseController{

    @Autowired
    private WebOperationLogService  webOperationLogService;

    @GetMapping("/see")
    public String see(Integer id) {
        WebOperationLog webOperationLog =webOperationLogService.getOne(id);
        this.setAttribute("logOp",webOperationLog);
        return this.toAction();
    }


    @GetMapping("/list")
    public String list(){
        return this.toAction();
    }

    @GetMapping("/listData")
    @ResponseBody
    public Result listData(){
        return  Result.success(webOperationLogService.getPage(this.getPage(null)));
    }
 

 }