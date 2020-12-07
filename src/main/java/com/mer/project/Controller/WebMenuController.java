package com.mer.project.Controller;

import com.mer.common.Base.Controller.BaseController;
import com.mer.common.Constant.Constant;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.common.Utils.WriteFrom;
import com.mer.framework.Annotction.LOG;
import com.mer.framework.Annotction.RequestLimit;
import com.mer.project.Pojo.WebMenu;
import com.mer.project.Pojo.WebTreeNode;
import com.mer.project.Service.WebMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoaqi 15701556037
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/WebMenu")
@Slf4j
@RequestLimit(maxCount = Constant.maxCount_RequestLimit,second = Constant.second_RequestLimit)
public class WebMenuController extends BaseController {

    @Autowired
    private WebMenuService webMenuService;

    @RequestMapping("/list")
    public String list(){
        return this.toAction();
    }

    @LOG(operModul = "系统菜单模块",operType = "菜单信息",operDesc = "查看菜单信息")
    @GetMapping("/listData")
    @ResponseBody
    public Result listData(){
        return Result.success(webMenuService.getList());
    }

    @GetMapping("/add")
    public String add(@RequestParam(value="id",required=false) Integer id) {
        try{
            WebMenu webMenus = null;
            Map map = null;//父亲的东西
            if(id!=null){
                webMenus = webMenuService.getOneInt(id);
                map = new HashMap();
                //根据当前级别查看所属上级信息
                //如果当前父类编号为0说明当前是顶级，就看自己，如果不是0，就看上级。
                if(webMenus.getParents().equals("0")){
                    map.put("parentid", webMenus.getId());//父类
                    map.put("p_name", webMenus.getMenu_name());
                }else{
                    WebMenu p_msg  = webMenuService.getOneInt(Integer.parseInt(webMenus.getParents()));
                    map.put("parentid",p_msg.getParentid());//父类
                    map.put("p_name",p_msg.getMenu_name());
                }
                this.setAttribute("update","update");
            }
            this.setAttribute("webMenus", webMenus);
            this.setAttribute("map",map);
        }catch (Exception e){
            log.error(WriteFrom.WriterEx(e));
        }
        return this.toAction();
    }

    @LOG(operModul = "系统菜单模块",operType = "菜单信息提交",operDesc = "添加修改菜单")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdate(@RequestBody WebMenu webMenus){

        if(webMenus.getCss().equals("") && webMenus.getType().equals("")){
          webMenus.setCss("layui-icon-table");
        }

        if(webMenus.getId()==null){
            //如果选择了按钮，则上级目录不能是主目录
            if(webMenus.getType().toString().equals("2") && webMenus.getParentid()==0){
                return Result.error("按钮信息,请勿使用\"默认主目录\",请选择对应菜单!");
            }
            //目标编号
            WebMenu init_menu = webMenuService.getOneInt(webMenus.getParentid());
            String lever = init_menu!=null?String.valueOf((Integer.parseInt(init_menu.getLever())+1)):"1";
            if(lever.equals("4") && !webMenus.getType().equals("2")){
                return Result.error("菜单等级开放至3级！（您操作的是4级菜单）");
            }
            webMenus.setLever(lever);
            /**
             * Parentid = 0 标识是目录
             * Parentid != 0 表示 是按钮或者是菜单，根据 type确认
             * 0目录 1菜单，2按钮
             * */
            webMenus.setType(webMenus.getParentid()==0?"0": webMenus.getType().equals("")?"1":"2");
            //所属父类
            webMenus.setParents(webMenus.getParentid().toString());

            //设置页面左侧菜单查询规则
            if(webMenus.getType().toString().equals("2")){
                webMenus.setSuperior("");
                webMenus.setLowerlevel("");
            }else{
                //设置上级
                if(webMenus.getParentid()==0){
                    String superior = getParentId(webMenuService.getMax_Superior(),1);
                    webMenus.setSuperior(superior);//如果父类标识为0标识是一个目录，设置上级为0，且lowerlevel 取当前最大的superior +1
                    webMenus.setLowerlevel(getParentId(superior,2));
                }else{
                    //反之，说明添加的是一个菜单有对应的上级目录，此时当前菜单的上级编号为它的上级编号
                    webMenus.setSuperior(init_menu.getSuperior());//如果父类标识为0标识是一个目录，设置上级为0，且lowerlevel 取当前最大的superior +1
                    String lowerlevel = webMenuService.getMax_Lowerlevel(init_menu.getSuperior());//最大的下级
                    webMenus.setLowerlevel(getParentId(lowerlevel,2));
                }
            }
            webMenuService.insert(webMenus);

        }else{
            WebMenu init_menu = null;
            //如果parentid = 0 表示没有选择上级进行修改信息
            if(webMenus.getParentid()==0){
                //没有变动获取本身
                init_menu = webMenuService.getOneInt(webMenus.getId());//根据当前页的menu id 对象
            }else{
                //有变动获取所选择的对象
                init_menu = webMenuService.getOneInt(webMenus.getParentid());//根据当前页 动态的 parentid 获取menu 对象
            }
//                System.out.println("目标对象："+init_menu.toString());
            String parents = "";
            String lever ="";
            String type="";
            String  superior="";
            String  lowerlevel="";
            //菜单 目录操作
//                    webMenus.setParents();//父类标识
//                    webMenus.setLever();//级别
//                    webMenus.setType();//类型 目录 还是菜单
//                    webMenus.setSuperior();//上级
//                    webMenus.setLowerlevel();//下级

            //是否是按钮变更信息或级别
            if(!webMenus.getType().equals("2")){

                //如果页面中的parentid = 0 表示无需变动parents
                if(webMenus.getParentid()==0){
                    //无需变动 parents，获取页面中原始 parents
                    parents = webMenus.getParents();
                    //无需变动lever
                    lever = webMenus.getLever();
                    //无需变动 type
                    type = webMenus.getType();
                    //无需变动 上下级
                    superior = init_menu.getSuperior();
                    lowerlevel = init_menu.getLowerlevel();
                }else {
                    //parents 变动时，parents 为 页面选中的等级编号
                    parents = webMenus.getParentid().toString();
                    //变动 lever 情况：1，目录 变菜单，2 菜单变更目录
                    //1、目录变更为子级菜单无论多少级

                    //1.1 是否超出级别限制
                    int row = webMenuService.getNumber(webMenus.getId());
                    // 1.3 、如果子级存在 提示不可直接变更父类
                    if(row>0){
                        return Result.error("当前目录下存在子节点无法变更上级目录！");
                    }
                    //lever 1.2检查当前目录下是否存在子级菜单，如果不存在则可以变更到其他目录下作为子级菜单，但是需要注意 lever 对应到变更对象菜单的的级别下 //2、菜单变更目录，无论多少级
                    lever = init_menu!=null?String.valueOf((Integer.parseInt(init_menu.getLever())+1)):"1";
                    if(lever.equals("4") && !webMenus.getType().equals("2")){
                        return Result.error("菜单等级开放至3级！（您操作的是4级菜单）");
                    }
                    //type 如果当前是菜单变更的目标下也是菜单那type =1 如果 当前是目录 变更对象后是菜单 那 type =1
                    type = "1";

                    //superior  为目标的 上级
                    superior = init_menu.getSuperior();
                    //lowerlevel 为目标最大下级+1
                    lowerlevel = webMenuService.getMax_Lowerlevel(init_menu.getSuperior());//最大的下级
                    lowerlevel = getParentId(lowerlevel,2);
                }
            }else{
                //按钮操作
                //无需变动 parents，获取页面中原始 parents
                if(webMenus.getParentid()!=0){
                    parents = webMenus.getParentid().toString();
                }else{
                    parents = webMenus.getParents();
                }
                //无需变动lever
                lever = webMenus.getLever();
                //无需变动 type
                type = webMenus.getType();
                //无需变动 上下级
                superior = "";
                lowerlevel = "";
            }
            webMenus.setParents(parents);//父类标识
            webMenus.setLever(lever);//级别
            webMenus.setType(type);//类型 目录 还是菜单
            webMenus.setSuperior(superior);//上级
            webMenus.setLowerlevel(lowerlevel);//下级
//                System.out.println("最终对象："+webMenus.toString());
            webMenuService.update(webMenus);
            log.info("修改菜单成功:"+ webMenus.toString());
        }
        return Result.success();
    }

    @LOG(operModul = "系统菜单模块",operType = "菜单信息提交",operDesc = "删除菜单")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id){
        int row = webMenuService.getNumber(id);
        if(row>0){
            return Result.error("当前目录下存在子节点无法删除！");
        }
        webMenuService.delete(id);
        return Result.success();

    }


    /**
     * 生成parentId
     */
    public static String getParentId(String parent_id,int levels){
        String res = Integer.parseInt(parent_id.substring(levels*2-2,levels*2))+1+"";
        return parent_id.substring(0,levels*2-2)+(res.length()==2?res:"0"+res)+parent_id.substring(levels*2);
    }

//    /**
//     * 重新整理菜单
//     * */
//    @PostMapping("/saveOrUpdates")
//    @ResponseBody
//    public JSONObject saveOrUpdates(@RequestBody WebMenu sysMenus){
//        System.out.println(sysMenus.toString());
////       目录 WebMenu(parentid=0, id=null, menu_name=名称, parents=, css=, href=路径, lever=, note=说明, type=, parent_id=null)
////       菜单 WebMenu(parentid=63, id=null, menu_name=ces, parents=, css=, href=ces, lever=, note=ces, type=, parent_id=null)
//        try {
//            //父类parents编号
//            String parents = sysMenus.getParents().toString();
//
//            //菜单标识 parent_id,如果父类parents编号 如果为0 标识是目录,对应菜单标识 parent_id 为 数据库中对应最大的 parent_id
//            String parentId="";
//            if(parents.equals("0")){
//                //查看当前系统最大的 parent_id
//                String max_parentId = webMenuService.getMaxParent_id().toString();
//                //如果当前系统中没有最大值则默认给个初始值编号
//                parentId  = max_parentId.equals("0")?"01000000":getParentId(max_parentId,2);
//                sysMenus.setParent_id(parentId);
//            }else{
//                sysMenus.setParents(sysMenus.getParent_id());
//            }
//
//            //如果选择了按钮，则上级目录不能是主目录
//            if(sysMenus.getType().toString().equals("2") && sysMenus.getParent_id().toString().equals("0")){
//                return LayuiResult.Error_Result_msg("按钮信息,请勿使用\"默认主目录\",请选择对应菜单!");
//            }
//            //菜单等级
//            String lever ="";
//            if(parents.equals("0")){
//                sysMenus.setLever("0");
//            }else {
//                WebMenu init_menu = webMenuService.getOneInt(sysMenus.getId());
//                lever = init_menu!=null?String.valueOf((Integer.parseInt(init_menu.getLever())+1)):"1";
//                if(lever.equals("4") && !sysMenus.getType().equals("2")){
//                    return LayuiResult.Error_Result_msg("菜单等级开放至3级！（您操作的是4级菜单）");
//                }
//                sysMenus.setLever(lever);
//            }
//            /**
//             * Parentid = 0 标识是目录
//             * Parentid != 0 表示 是按钮或者是菜单，根据 type确认
//             * 0目录 1菜单，2按钮
//             * */
//            sysMenus.setType(sysMenus.getParents().toString().equals("0")?"0":sysMenus.getType().equals("")?"1":"2");
//            webMenuService.insert(sysMenus);
//            log.info("添加菜单成功:"+sysMenus.toString());
//            return LayuiResult.Success_Result(null);
//        }catch (Exception e){
//            log.error(WriteFrom.WriterEx(e));
//            return LayuiResult.Error_Result(null);
//        }
//    }
//    /**
//     *
//     *********************************************************.<br>
//     * [方法] getParentId <br>
//     * [描述] 生成parentId <br>
//     * [参数] TODO(对参数的描述) <br>
//     * [返回] String <br>
//     * [时间] 2015-4-8 下午10:27:01 <br>
//     *********************************************************.<br>
//     */
//    public static String getParentId(String parent_id,int levels){
//        String res = Integer.parseInt(parent_id.substring(levels*2-2,levels*2))+1+"";
//        return parent_id.substring(0,levels*2-2)+(res.length()==2?res:"0"+res)+parent_id.substring(levels*2);
//    }




    @GetMapping("/getTree")
    @ResponseBody
    public Object getTree(){
        List parnt = new ArrayList<>();
        try {
            list = webMenuService.getTree();
            //父亲
            WebTreeNode treeNode=null;
            Map parents_map = null;
            for (int i = 0; i < list.size(); i++) {
                parents_map = new HashMap();
                if(list.get(i).get("lever").equals("1")){
                    String id = list.get(i).get("id").toString();
                    parents_map.put("id",id);
                    parents_map.put("name",list.get(i).get("menu_name").toString());
                    parents_map.put("spread",false);
                    parents_map.put("children",getChildList(id,list));
                    parnt.add(parents_map);
                }
            }
        }catch (Exception e){
            log.error(WriteFrom.WriterEx(e));
        }
//        System.out.println(parnt);
        return  parnt;
    }

    /**
     * 找儿子
     * **/
    private static List getChildList(String id,List<Map<String,Object>> list){
        List sun_list = new ArrayList<>();
        //儿子
        Map sun_map = null;
        for (int i= 0; i < list.size(); i++) {
            if(list.get(i).get("lever").equals("2")){
                sun_map = new HashMap();
                //如果父类标识相等视为 父亲找到了儿子
                if(id.equals(list.get(i).get("pid"))){
                    String sun_id = list.get(i).get("id").toString();
                    sun_map.put("id",sun_id);
                    sun_map.put("name",list.get(i).get("menu_name").toString());
                    sun_map.put("spread",false);
                    sun_map.put("children",getGrandsonChildList(sun_id,list));//三级菜单再说
                    sun_list.add(sun_map);
                }
            }
        }
        return sun_list;
    }

    /**
     * 找孙子
     * **/
    private static List getGrandsonChildList(String id,List<Map<String,Object>> list){
        List sun_list = new ArrayList<>();
        //儿子
        Map sun_map = null;
        for (int i= 0; i < list.size(); i++) {
            if(list.get(i).get("lever").equals("3")){
                sun_map = new HashMap();
                //如果父类标识相等视为 父亲找到了儿子
                if(id.equals(list.get(i).get("pid"))){
                    sun_map.put("id",list.get(i).get("id").toString());
                    sun_map.put("name",list.get(i).get("menu_name").toString());
                    sun_map.put("spread",false);
//                    sun_map.put("children",getChildList(id,list));//三级菜单再说
                    sun_list.add(sun_map);
                }
            }
        }
        return sun_list;
    }



}
