package com.mer.project.Controller;

import com.mer.common.Base.Controller.BaseController;
import com.mer.common.RedisKeySet.ReqIpKey;
import com.mer.common.Utils.DateUtils;
import com.mer.framework.Annotction.LOG;
import com.mer.common.Constant.Constant;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import com.mer.framework.Annotction.RequestLimit;
import lombok.extern.slf4j.Slf4j;
import com.mer.project.Service.WebIpService;
import com.mer.project.Pojo.WebIp;

import java.util.List;


/**
* <p>
 *     ip信息 控制器
* </p>
 * @author zhaoqi
* @since 2020-10-12
*/
@Controller
@RequestMapping("/WebIp" )
@Slf4j
@RequestLimit(maxCount = Constant.maxCount_RequestLimit,second = Constant.second_RequestLimit)
public class WebIpController extends BaseController{

    @Autowired
    private WebIpService  webIpService;

    @Autowired
    private RedisService redisService;

    //@LOG(operModul = "ip信息模块",operType = "信息查看",operDesc = "查询界面")
    @RequestMapping("/list")
    public String list(){
        return this.toAction();
    }

    @LOG(operModul = "ip信息模块",operType = "信息查看",operDesc = "分页查询数据")
    @RequestMapping("/listData")
    @ResponseBody
    public Result listData(){
        return  Result.success(webIpService.getPage(this.getPage(null)));
    }

    @LOG(operModul = "ip信息模块",operType = "信息查看",operDesc = "查看单个信息可添加或修改")
    @RequestMapping("/add")
    public String add(@RequestParam(value="id",required=false) Integer id) {
        WebIp webIp =null;
        if (id!=null){
            webIp = webIpService.getOne(id);
            webIp.setOld_ip(webIp.getIp());
        }else{
            webIp =  new WebIp();
        }
        this.setAttribute("webIp",webIp);
        return this.toAction();
    }

    @LOG(operModul = "ip信息模块",operType = "信息操作",operDesc = "添加修改数据")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdate(@RequestBody WebIp webIp){
        List<WebIp> webIpList = null;
        String nowTimes = DateUtils.getDateTime();
        webIp.setUpTime(nowTimes);
        if (webIp.getId() == null) {
            if(webIpService.findCountip(webIp.getIp())>0){
                return Result.error("当前IP地址："+webIp.getIp()+" 已存在！");
            }
            webIp.setAddTime(nowTimes);
            webIp.setStatus(1);
            webIpService.insertOne(webIp);
        } else {
            if(!webIp.getOld_ip().equals(webIp.getIp())){
                if(webIpService.findCountip(webIp.getIp())>0){
                    return Result.error("当前IP地址："+webIp.getIp()+" 已存在！");
                }
            }
            if(webIp.getType()==2){
                webIp.setStatus(2);
            }
            webIpService.updateOne(webIp);
        }

        if(redisService.exists(ReqIpKey.reqIpKey,webIp.getOld_ip())){
            redisService.delete(ReqIpKey.reqIpKey,webIp.getOld_ip());
        }
        if(redisService.exists(ReqIpKey.reqIpKey,webIp.getIp())){
            redisService.delete(ReqIpKey.reqIpKey,webIp.getIp());
        }
        redisService.set(ReqIpKey.reqIpKey,webIp.getIp(),webIp);
        return Result.success();
    }




    @LOG(operModul = "ip信息模块",operType = "信息操作",operDesc = "删除数据")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id") Integer id){
        WebIp webIp = webIpService.getOne(id);
        if(redisService.exists(ReqIpKey.reqIpKey,webIp.getIp())){
            redisService.delete(ReqIpKey.reqIpKey,webIp.getIp());
        }
        webIpService.deleteOne(id);
        return Result.success();
    }

 }