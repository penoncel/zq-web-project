package com.mer.common.Utils.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 文件路径处理
 */
@SuppressWarnings("all")
public class FilePath {

    /**
     * 处理新旧图片
     * **/
    public static String oldNewPic(String path){
        //是否又多个文件
        if (path.contains(",")){
            String[] path_arr  = path.split(",");
            //刚上传的文件
            File source = new File(path_arr[1]);
            //将新文件存放地址  老地址的父类目录+新文件名
            File dest =  new File(new File(path_arr[0]).getParentFile().toString()+ File.separator+source.getName());
            //将刚上传的文件，输出到 dest的指定路径中
            FilePath.copyFileUsingFileChannels(source.getPath(),dest.getPath());
            //删除老的文件
            FilePath.delFile(new File(path_arr[0]));
            //返回一个新地址信息
            path = dest.getPath();
        }
        return path;
    }

    /**
     * 删除单个文件
     * **/
    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    /**
     * 删除整个目录
     * **/
    public static void deleteDir(String dirPath){
        File file = new File(dirPath);
        if(file.isFile()){
            file.delete();
        }else{
            File[] files = file.listFiles();
            if(files == null){
                file.delete();
            }else{
                for (int i = 0; i < files.length; i++){
                    deleteDir(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }


    /**
     *拷贝文件
     *
     * @param source_path 文件来源 绝对路径;
     * @param dest_path 目的地 绝对路径;
     * @return ;
     *  Java NIO包括transferFrom方法,根据文档应该比文件流复制的速度更快，FileChannels拷贝大文件
     * **/
    public static String copyFileUsingFileChannels(String source_path,String dest_path){
        //父文件不存在,进行创建
        File locaPa  = new File(dest_path);
        if (!locaPa.getParentFile().exists()){
            locaPa.mkdirs();
        }
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
//            inputChannel = new FileInputStream(new File("D:\\aaa\\1.jpg")).getChannel();
//            outputChannel = new FileOutputStream(new File("D:\\bbb\\1.jpg")).getChannel();
            inputChannel = new FileInputStream(new File(source_path)).getChannel();
            outputChannel = new FileOutputStream(new File(dest_path)).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                inputChannel.close();
                outputChannel.close();
            }catch (IOException ie){
                ie.printStackTrace();
            }
        }
        return dest_path;
    }

    /**
     *拷贝文件
     *
     * @param source_path 文件来源 绝对路径;
     * @param dest_path 目的地 相对路径;
     * @return ;
     * **/
    public static String copyFile(String source_path,String dest_path){
        //父文件不存在,进行创建
        File locaPa  = new File(dest_path);
        if (!locaPa.exists()){
            locaPa.mkdirs();
        }
        //源文件 D:\\aaa\\1.jpg
        File source = new File(source_path);
        //新文件 D:\\bbb\\1.jpg
        File dest = new File(locaPa.getPath()+File.separator+source.getName());
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
//            inputChannel = new FileInputStream(new File("D:\\aaa\\1.jpg")).getChannel();
//            outputChannel = new FileOutputStream(new File("D:\\bbb\\1.jpg")).getChannel();
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                inputChannel.close();
                outputChannel.close();
            }catch (IOException ie){
                ie.printStackTrace();
            }
        }
        return dest.getPath();
    }



    /**
     * 代理商图片文件夹移动
     * [参数]：代理商编号、目标文件
     * */
    public static void copyPic(String agnet_num,String objpath){
        File folder = new File("C:\\AgentPicFile\\"+agnet_num);
        //文件夹路径不存在
        if (!folder.exists() && !folder.isDirectory()) {
//            System.out.println("文件夹路径不存在，创建路径:" + folder.getPath());
            folder.mkdirs();
        } else {
//            System.out.println("文件夹路径存在:" + folder.getPath());
        }
        try{
            //移动文件
            Files.move(Paths.get(objpath),Paths.get(folder.getPath()), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
