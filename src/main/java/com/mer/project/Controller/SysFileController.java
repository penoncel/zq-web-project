package com.mer.project.Controller;


import com.mer.common.Constant.Constant;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.common.Utils.WriteFrom;
import com.mer.framework.Annotction.RequestLimit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhoaqi 15701556037
 * */
@RestController
@RequestMapping("/api/file")
@Slf4j
@RequestLimit(maxCount = Constant.maxCount_RequestLimit,second = Constant.second_RequestLimit)
public class SysFileController  {
 

    /**
     * 图片查看
     * */
    @RequestMapping(value="/ShowImg")
    public void ShowImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
        FileInputStream fileIs=null;
        try {
            System.out.println(request.getParameter("path"));
            fileIs = new FileInputStream(request.getParameter("path"));
        } catch (Exception e) {
            return;
        }
        int i=fileIs.available(); //得到文件大小
        byte data[]=new byte[i];
        fileIs.read(data);  //读数据
        response.setContentType("image/*"); //设置返回的文件类型
        OutputStream outStream=response.getOutputStream(); //得到向客户端输出二进制数据的对象
        outStream.write(data);  //输出数据
        outStream.flush();
        outStream.close();
        fileIs.close();
    }

    /**
     * 文件上传
     * **/
    @RequestMapping(value = "/picbatch", method = RequestMethod.POST)
    @ResponseBody
    public Result picbatch(@RequestParam(value = "file") MultipartFile files[], String iframtimes, String picName) {
        try{
            String uploadPath =  iframtimes;
            File uploadDirectory = new File(uploadPath);
            if (uploadDirectory.exists()) {
                if (!uploadDirectory.isDirectory()) {
                    uploadDirectory.delete();
                }
            } else {
                uploadDirectory.mkdir();
            }
             String tmpPath = "";
            //这里可以支持多文件上传
            if (files != null && files.length >= 1) {
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    //判断是否有文件且是否为图片文件
                    if(fileName!=null && !"".equalsIgnoreCase(fileName.trim()) && isImageFile(fileName)) {
                        //创建输出文件对象
                        File outFile = new File(uploadPath + "/" + UUID.randomUUID().toString()+ getFileType(fileName));
                        tmpPath = outFile.getPath();
                        log.info("上传图片："+outFile.getPath());
                        //拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                    }
                }
            }

            Map map = new HashMap<String, Object>();
            map.clear();
            map.put("data","上传成功");
            map.put("path",tmpPath);
            return Result.success(map);
        }catch (Exception e){
            log.error(WriteFrom.WriterEx(e));
            return  Result.error("上传失败");
        }
    }

    private Boolean isImageFile(String fileName) {
        String[] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if (fileName == null) {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type) {
            if (fileName.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        if (fileName != null && fileName.indexOf(".") >= 0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }



    @RequiresPermissions(value={"root","/api/file:downFile"},logical = Logical.OR)
    @PostMapping(value = "/downFile")
    @ResponseBody
    public void downFile(@RequestParam("filePath") String filePath,HttpServletRequest request, HttpServletResponse response){
        System.out.println("PAth:"+filePath);
       try{
           File file =  new File(filePath);
           //获取输入流对象（用于读文件）
           FileInputStream fis = new FileInputStream(file);
           //获取文件后缀（.txt）
//           String extendFileName = fileName.substring(fileName.lastIndexOf('.'));
           String extendFileName = file.getName();
           //动态设置响应类型，根据前台传递文件类型设置响应类型
           response.setContentType(request.getSession().getServletContext().getMimeType(extendFileName));
           //设置响应头,attachment表示以附件的形式下载，inline表示在线打开
           response.setHeader("content-disposition","attachment;fileName="+URLEncoder.encode(extendFileName,"UTF-8"));
           //获取输出流对象（用于写文件）
           ServletOutputStream os = response.getOutputStream();
           //下载文件,使用spring框架中的FileCopyUtils工具
           FileCopyUtils.copy(fis,os);

       }catch (Exception e){

       }
//        return null;
    }




}
