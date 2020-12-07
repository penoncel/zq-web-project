package com.mer.project.Controller;

import com.mer.common.Constant.Constant;
import com.mer.common.Utils.Files.Excel.ExcelUtil;
import com.mer.framework.Annotction.RequestLimit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/Excel")
@RequestLimit(maxCount = Constant.maxCount_RequestLimit,second = Constant.second_RequestLimit)
public class SysExcelController {

    /**
     * 测试一个到处
     * */
    @GetMapping(value = "/test")
    public void expotExcelCode(HttpServletResponse response,String locadata) throws Exception{
        //数据
        List<Map<String,Object>> list = new ArrayList<>();
        Map map = null;
        for (int i = 0; i < 100; i++) {
            map = new HashMap();
            map.put("name","帅哥"+i);
            map.put("age",i*10);
            list.add(map);
        }

        // 表头
        LinkedHashMap<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("name","姓名");
        headMap.put("age","年龄");

        //列宽度
        int[] width = {
                200,200
        };
        String fileName = "测试"+new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        ExcelUtil.XlSX_outExcel(headMap,list,width,response,fileName);

    }







}
