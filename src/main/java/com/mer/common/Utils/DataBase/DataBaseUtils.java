package com.mer.common.Utils.DataBase;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


/**
 * @Date 2019年2月18日15:13:38
 * @author 杜丰铭
 *  数据库备份
 */
public class DataBaseUtils {
    public static void main(String[] args) {
//        System.out.println(recover("D:\\backup_database_2019_02_19_11_57_16.sql", "localhost", "web-mastar", "root", "root"));
//        System.out.println(restore("localhost", "root", "root", "web-mastar", "D:\\backup_database_2019_02_19_11_57_16.sql"));;
    	/*Runtime runtime = Runtime.getRuntime();
    	Process process = runtime.exec("mysql -hlocalhost -uroot -proot --set-charset=UTF-8 mofujinfu < D:/backup_database_2019_02_19_11_57_16.sql");

    	OutputStream os = process.getOutputStream();
    	System.out.println(os==null);*/
        System.out.println(exportDatabaseTool("localhost","root","root","D:\\","backup_database_2019_02_19_11_57_16.sql","web-mastar"));

    }
    /**
     * Java代码实现MySQL数据库导出
     *
     * @author GaoHuanjie
     * @param hostIP MySQL数据库所在服务器地址IP
     * @param userName 进入数据库所需要的用户名
     * @param password 进入数据库所需要的密码
     * @param savePath 数据库导出文件保存路径
     * @param fileName 数据库导出文件文件名
     * @param databaseName 要导出的数据库名
     * @return 返回true表示导出成功，否则返回false。
     */
    public static boolean exportDatabaseTool(String hostIP, String userName, String password, String savePath, String fileName, String databaseName)   {
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        if(!savePath.endsWith(File.separator)){
            savePath = savePath + File.separator;
        }

        PrintWriter pw = null;
        BufferedReader bufferedReader = null;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
            Process process = Runtime.getRuntime().exec(" mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine())!= null){
                pw.println(line);
            }
            pw.flush();
            if(process.waitFor() == 0){//0 表示线程正常终止。
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (pw != null) {
                    pw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * @param filepath 数据库备份的脚本路径
     * @param ip IP地址
     * @param database 数据库名称
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    public static boolean recover(String filepath,String ip,String database, String userName,String password) {


        String stmt1 = "mysqladmin -h "+ip+" -u "+userName+" -p"+password+" create "+database;

        String stmt2 = "mysql -h "+ip+" -u "+userName+" -p "+password+" "+database+" < " + filepath;

        String[] cmd = { "cmd", "/c", stmt2 };

        try {
            Runtime.getRuntime().exec(stmt1);
            Runtime.getRuntime().exec(cmd);
            System.out.println("数据已从 " + filepath + " 导入到数据库中");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean restore(String url,String user,String password,String databaseName,String path) {
        System.out.println("mysql -h"+url+" -u"+user+" -p"+password+" --default-character-set=utf8 "+ databaseName);
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime
                    .exec("mysql -h"+url+" -u"+user+" -p"+password+" --default-character-set=utf8 "
                            + databaseName);
            OutputStream outputStream = process.getOutputStream();
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            str = sb.toString();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf-8");
            writer.write(str);
            writer.flush();
            if(writer!=null){
                writer.close();
            }

            if(br!=null){
                br.close();
            }
            if(isr!=null){
                isr.close();
            }
            if(fis!=null){
                fis.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
