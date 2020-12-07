package com.mer.project.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mer.common.Base.Controller.BaseController;
import com.mer.common.Constant.Constant;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.common.Utils.WriteFrom;
import com.mer.framework.Annotction.LOG;
import com.mer.framework.Annotction.RequestLimit;
import com.mer.project.Pojo.WebRole;
import com.mer.project.Service.WebRoleService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/WebRole")
@Slf4j
@RequestLimit(maxCount = Constant.maxCount_RequestLimit,second = Constant.second_RequestLimit)
public class WebRoleController extends BaseController {

    @Autowired
    private WebRoleService webRoleService;

    @GetMapping("/list")
    public String list(){
        return this.toAction();
    }

    @LOG(operModul = "角色模块",operType = "角色查看",operDesc = "查看系统角色信息")
    @GetMapping("/listData")
    @ResponseBody
    public Result listData(){
        return  Result.success(webRoleService.getPage(this.getPage(null)));
    }


    @LOG(operModul = "角色模块",operType = "打开角色框",operDesc = "角色添加或修改")
    @GetMapping("/add")
    public String add(@RequestParam(value="id",required=false) Integer id) {
        WebRole webRole =null;
        if (id!=null){
            webRole = webRoleService.getOne_Role(id);
        }else{
            webRole =  new WebRole();
        }
        this.setAttribute("webRole", webRole);
        return this.toAction();
    }

    @LOG(operModul = "角色模块",operType = "系统提交",operDesc = "修改保存角色信息")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdate(@RequestBody WebRole webRole){
        if (webRole.getId() == null) {
            if(webRoleService.getCountString_Role(webRole.getRole())<=0){
                webRoleService.insert_Role(webRole);
            }else{
                return Result.error("角色："+ webRole.getRole()+"已存在,不可重复！");
            }
        } else {
            webRoleService.update_Role(webRole);
        }
        return Result.success();
    }


    @LOG(operModul = "角色模块",operType = "删除角色",operDesc = "删除角色信息,同时删除角色的权限")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id") Integer id){
        webRoleService.delete_Role(id);
        return Result.success();
    }

    /**
     * 设置权限-窗口
     * */
    @GetMapping("/power")
    public String power(Integer id) {
        this.setAttribute("webRole", webRoleService.getOne_Role(id));
        return this.toAction();
    }

    @LOG(operModul = "角色模块",operType = "设置角色权限",operDesc = "给角色批量设置权限")
    @PostMapping("/setUserRole")
    @ResponseBody
    public Result setUserRole(Integer role_id,String data){
        List<Map<String,Object>> result = new ArrayList<>();
        Map dataMap = null;
        List<Map<String, String>> list = JSON.parseObject(data, new TypeReference<List<Map<String, String>>>() {});
        for (int i = 0; i <list.size() ; i++) {
            dataMap = new HashMap();
            String treeId = list.get(i).get("nodeId");
            dataMap.put("role_id",role_id);
            dataMap.put("menu_id",treeId);
            result.add(dataMap);
        }
        webRoleService.setPermission(role_id,result);
        /**
         * 角色权限（按钮、菜单）设置在缓存中。
         * 登入时获取 设置缓存
         * 验证时 取缓存
         */
        return Result.success();

    }


    @LOG(operModul = "角色模块",operType = "获取角色权限",operDesc = "根据角色获得对应权限信息")
    @PostMapping("/getTree")
    @ResponseBody
    public Object getTree(Integer role_id){
        JSONObject respData = new JSONObject();
        JSONObject status = new JSONObject();
        List<Map<String,Object>> treeList = new ArrayList<>();
        try {
            JSONObject treeMap = null;
            list = webRoleService.getTree();
            if(list.size()<=0){
                status.put("code","500");
                status.put("message","无数据");
                respData.put("status",status);
                respData.put("data",treeList);
                return respData;
            }else {
                //已设置的权限
                List<Map<String,Object>> role_check = webRoleService.getRole_CheckPerimssions(role_id);
                for (Map tree : list){
                    treeMap = new JSONObject();
                    String treeId = tree.get("id")+"";
                    treeMap.put("id",treeId);
                    treeMap.put("title",tree.get("menu_name"));
                    treeMap.put("checkArr","0");//如果存在权限则选中权限
//                treeMap.put("checkArr","[{\"type\": \"0\", \"checked\": \"1\"}]");//如果存在权限则选中权限
                    treeMap.put("checkArr",getCheckArr(treeId,role_check));//如果存在权限则选中权限
                    treeMap.put("parentId",tree.get("pid"));
                    treeMap.put("iconClass",iconClass(tree.get("type").toString()));
                    treeList.add(treeMap);
                }
                status.put("code","200");
                status.put("message","操作成功");
                respData.put("status",status);
                respData.put("data",treeList);
                return respData;
            }
        }catch (Exception e){
            log.error(WriteFrom.WriterEx(e));
            status.put("code","500");
            status.put("message","操作失败");
            respData.put("status",status);
            respData.put("data",treeList);
            return respData;
        }
    }

    /**
     * 是否存在权限，存在则设置选中
     * treeId  权限 id
     * role_check 已选中的权限
     * **/
    public JSONObject getCheckArr(String treeId,List<Map<String,Object>> role_check){
        JSONObject checkArr = new JSONObject();
        try{
            String checked = "0";// 不选中
            //角色所有权限中，是否有已设置过的权限。
            for (int i = 0; i < role_check.size(); i++) {
                String ck_tree = role_check.get(i).get("menu_id").toString();
                if (treeId.equals(ck_tree)){
                    checked = "1";//选中
                }
            }
            checkArr.put("type","0");
            checkArr.put("checked",checked);

//        boolean bool = role_check.contains(treeId);
//        System.out.println(bool+","+treeId);
//        checkArr.put("checked",bool==true?"1":"0");

        }catch (Exception e){
            log.error(WriteFrom.WriterEx(e));
        }
        return checkArr;
    }


    /**
     * 配置tree图标
     * */
    public String iconClass(String type){
        String ioc ="";
        switch (type){
            case "0":
                ioc = "dtree-icon-fenzhijigou";
                break;
            case "1":
                ioc = "dtree-icon-shuye1";
                break;
            case "2":
                ioc = "dtree-icon-bianji";
                break;
        }
        return ioc;
    }












}
