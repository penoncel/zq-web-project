//package com.mer.common.Utils.Ftp;
//
//
//import com.mer.Pojo.SysFtp;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
//import org.apache.commons.net.ftp.FTPReply;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author zhoaqi 15701556037
// */
//@SuppressWarnings("all")
//public class FtpUtil {
//    private static final Logger log = LoggerFactory.getLogger(FtpUtil.class);
//    private static FTPClient ftpClient;
//
//    public static void main(String[] args) {
//        try {
////          SysFtp ftp = new SysFtp();
////          ftp.setIp("61.129.71.106");
////          ftp.setPort(21);
////          ftp.setUserName("carredata");
////          ftp.setUserPwd("datacarre");
////          ftp.setFtp_filepath("/newsddata/");
//////          ftp.setFtp_filename("first_txn_20200201.TXT");
////
////          ftp.setFtp_filename("newsdsn_20201030.TXT");
////
////          ftp.setDownloadpath("G:\\测试批量FTPDOW\\");
//
////          System.out.println(connectFtp(ftp));
////          System.out.println(downloadBatchFile(ftp));
//
////          File file =  new File(ftp.getDownloadpath()+File.separator+ftp.getFtp_filename());
////          if(file.exists()){
////              System.out.println("文件存在");
////              List<String[]> list=new ArrayList<String[]>();
////              try {
////                  InputStreamReader input = new InputStreamReader(new FileInputStream(file),"GBK");
////                  BufferedReader bf = new BufferedReader(input);
////                  // 按行读取字符串
////                  String str;
////                  int row =0;
////                  while ((str = bf.readLine()) != null) {
////                      row++;
////                      if(row!=1){
////                          list.add(str.split(","));
////                      }
////                  }
////                  bf.close();
////                  input.close();
////              } catch (IOException e) {
////                  e.printStackTrace();
////              }
////              List<Map<String,Object>> datamap = new ArrayList<>();
////              Map<String,Object> map = null;
////              for (String[] str: list) {
////                  map = new HashMap<>();
////                  map.put("locadata",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
////                  map.put("system_from","久付");
////                  map.put("agent",str[0].replaceAll(" +",""));
////                  map.put("all_num",str[1].replaceAll(" +",""));
////                  map.put("all_money",str[2].replaceAll(" +",""));
////                  map.put("busi_fee",str[3].replaceAll(" +",""));
////                  map.put("chan_fee",str[4].replaceAll(" +",""));
////                  map.put("agnet_profit",str[5].replaceAll(" +",""));
////                  map.put("sand_profit",str[6].replaceAll(" +",""));
////                  map.put("type",2);
////                  datamap.add(map);
////              }
////              for (int i = 0; i < datamap.size(); i++) {
////                  System.out.println(datamap.get(i));
////              }
////
////          }else{
////              System.out.println("不存在");
////          }
//
//            List<SysFtp> ftpList = new ArrayList<>();
//
//            //模拟批量
//            SysFtp ftp1 = new SysFtp();
//            ftp1.setIp("61.129.71.106");
//            ftp1.setPort(21);
//            ftp1.setUserName("carredata");
//            ftp1.setUserPwd("datacarre");
//            ftp1.setFtp_filepath("/newsddata/");
//            ftp1.setFtp_filename("newsdInsDtl"+"20201101"+".txt");
//            ftp1.setDownloadpath("G:\\测试批量FTPDOW\\每日交易量\\");
//            ftpList.add(ftp1);
//
//            SysFtp ftp12 = new SysFtp();
//            ftp12.setIp(ftp1.getIp());
//            ftp12.setPort(ftp1.getPort());
//            ftp12.setUserName(ftp1.getUserName());
//            ftp12.setUserPwd(ftp1.getUserPwd());
//            ftp12.setFtp_filepath(ftp1.getFtp_filepath());
//            ftp12.setFtp_filename("AddMchtByDay_"+"20201101"+".xlsx");
//            ftp12.setDownloadpath("G:\\测试批量FTPDOW\\每日久付新增商户\\");
//            ftpList.add(ftp12);
//
//            SysFtp ftp13 = new SysFtp();
//            ftp13.setIp(ftp1.getIp());
//            ftp13.setPort(ftp1.getPort());
//            ftp13.setUserName(ftp1.getUserName());
//            ftp13.setUserPwd(ftp1.getUserPwd());
//            ftp13.setFtp_filepath(ftp1.getFtp_filepath());
//            ftp13.setFtp_filename("first_txn_"+"20201101"+".TXT");
//            ftp13.setDownloadpath("G:\\测试批量FTPDOW\\每日终端激活\\");
//            ftpList.add(ftp13);
//
//            SysFtp ftp14 = new SysFtp();
//            ftp14.setIp(ftp1.getIp());
//            ftp14.setPort(ftp1.getPort());
//            ftp14.setUserName(ftp1.getUserName());
//            ftp14.setUserPwd(ftp1.getUserPwd());
//            ftp14.setFtp_filepath(ftp1.getFtp_filepath());
//            ftp14.setFtp_filename("newsdtxn_"+"20201101"+".TXT");
//            ftp14.setDownloadpath("G:\\测试批量FTPDOW\\每日交易明细\\");
//            ftpList.add(ftp14);
//
//            SysFtp ftp15 = new SysFtp();
//            ftp15.setIp(ftp1.getIp());
//            ftp15.setPort(ftp1.getPort());
//            ftp15.setUserName(ftp1.getUserName());
//            ftp15.setUserPwd(ftp1.getUserPwd());
//            ftp15.setFtp_filepath(ftp1.getFtp_filepath());
//            ftp15.setFtp_filename("newsdInsDtl_ByObjMchtType"+"20201101"+".txt");
//            ftp15.setDownloadpath("G:\\测试批量FTPDOW\\每日交易量交易类型分组\\");
//            ftpList.add(ftp15);
//
//
//
//            SysFtp bendi = new SysFtp();
//            bendi.setIp("192.168.13.230");
//            bendi.setPort(22);
//            bendi.setUserName("root");
//            bendi.setUserPwd("123456");
//            bendi.setFtp_filepath(ftp1.getFtp_filepath());
//            bendi.setFtp_filename("newsdInsDtl_ByObjMchtType"+"20201101"+".txt");
//            bendi.setDownloadpath("G:\\测试批量FTPDOW\\每日交易量交易类型分组\\");
//
//            System.out.println(connectFtp(ftp1));
////            System.out.println(connectFtp(bendi));
////            System.out.println(downloadBatchFile(ftpList));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 登入 ftp
//     * */
//    public static boolean connectFtp(SysFtp ftp) {
//        ftpClient = new FTPClient();
//        try {
//            //连接
//            ftpClient.connect(ftp.getIp(), ftp.getPort()==null?21:ftp.getPort());
//            // 登录
//            ftpClient.login(ftp.getUserName(), ftp.getUserPwd());
//            // 检验登陆操作的返回码是否正确
//            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
//                ftpClient.disconnect();
//                log.info("FTP服务器连接失败："+ftp.toString());
//                return false;
//            }
//            //设置下载文件为二进制模式
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            //传输文件为流的形式
//            ftpClient.setFileTransferMode(ftpClient.STREAM_TRANSFER_MODE);
//            // 转移到FTP服务器目录
//            ftpClient.changeWorkingDirectory(ftp.getFtp_filepath());
//            log.info("FTP 服务器连接 成功......");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("FTP登录失败："+e.getMessage());
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 下载ftp文件信息
//     * */
//    public static boolean downloadFile(SysFtp ftp) {
//        if(ftpClient!=null){
//            log.info("[单个操作] ftpClient 不为空");
//            try {
//                File locafilepath = new File(ftp.getDownloadpath());
//                if (!locafilepath.exists()) {
//                    locafilepath.mkdirs();
//                }
//
//                // 判断是否存在该目录
//                if(!ftpClient.changeWorkingDirectory(ftp.getFtp_filepath())){
//                    log.info("FTP服务器目录不存在 "+ftp.getFtp_filepath());
//                    return false;
//                }
//
//                FTPFile[] fs = ftpClient.listFiles(ftp.getFtp_filename());
//                //文件是否存在
//                if(fs.length==0){
//                    log.info("文件不存在:"+ftp.getFtp_filepath()+ftp.getFtp_filename());
//                    return false;
//                }
//
//                // 设置被动模式，开通一个端口来传输数据
//                ftpClient.enterLocalPassiveMode();
//
//                //文件存在直接下载对应文件
//                File ftpfile=new File(ftp.getDownloadpath()+ File.separator + ftp.getFtp_filename());
//                log.info("------------------- start ---------------------");
//                log.info("下载 文件 "+ftpfile.getPath());
//                OutputStream os = new FileOutputStream(ftpfile);
//                ftpClient.retrieveFile(ftp.getFtp_filename(), os);
//                os.close();
//                log.info("下载 完成:"+ftpfile.getPath());
//                long size = ftpfile.length();
//                if(size==0){
//                    log.info("文件为空,"+ftpfile.getName());
//                    return false;
//                }
//                log.info("文件不为空:"+ftpfile.getPath());
//                log.info("------------------- end ---------------------");
//            } catch (IOException e) {
//                e.printStackTrace();
//                log.info("文件下载异常："+e.getMessage());
//            } finally {
//                closeConnect();
//            }
//            return true;
//        }else{
//            log.info("[单个操作] ftpClient 对象为空 所以不能进行下载操作");
//            return false;
//        }
//    }
//
//
//
//    /**
//     * 批量下载ftp文件信息
//     * */
//    public static boolean downloadBatchFile(List<SysFtp> ftps) {
//        if(ftpClient!=null){
//            log.info("[批量操作] ftpClient 不为空");
//            try {
//                //所有文件是否都存在
//                for (SysFtp ftp:ftps) {
//                    // 判断是否存在该目录
//                    if(!ftpClient.changeWorkingDirectory(ftp.getFtp_filepath())){
//                        log.info( "FTP服务器目录不存在 " + ftp.getFtp_filepath());
//                        return false;
//                    }
//                    //这里特殊指定路径
//                    FTPFile[] fs = ftpClient.listFiles(ftp.getFtp_filename());
//                    //文件是否存在
//                    if(fs.length==0){
//                        log.info("目录下没有发现此文件:"+ftp.getFtp_filepath()+ftp.getFtp_filename());
//                        return false;
//                    }
//                }
//                // 设置被动模式，开通一个端口来传输数据
//                ftpClient.enterLocalPassiveMode();
//                for (SysFtp ftp:ftps) {
//                    //这里特殊指定路径
//                    File locafilepath = new File(ftp.getDownloadpath());
//                    if (!locafilepath.exists()) {
//                        locafilepath.mkdirs();
//                    }
//                    //文件存在直接下载对应文件
//                    File ftpfile=new File(ftp.getDownloadpath()+ File.separator + ftp.getFtp_filename());
//                    log.info("------------------- start ---------------------");
//                    log.info("下载 文件 "+ftpfile.getPath());
//                    OutputStream os = new FileOutputStream(ftpfile);
//                    ftpClient.retrieveFile(ftp.getFtp_filename(), os);
//                    os.close();
//                    log.info("下载 成功:"+ftpfile.getPath());
//                    long size = ftpfile.length();
//                    if(size==0){
//                        log.info("是个空文件:"+ftpfile.getName());
//                        return false;
//                    }
//                    log.info("文件不为空;"+ftpfile.getName());
//                    log.info("------------------- end ---------------------");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                log.info("文件下载异常："+e.getMessage());
//            } finally {
//                closeConnect();
//            }
//            return true;
//        }else{
//            log.info("[批量操作] ftpClient 对象为空 所以不能进行下载操作");
//            return false;
//        }
//    }
//
//
//
//    /**
//     * 关闭FTP连接
//     */
//    private static void closeConnect() {
//        if (ftpClient != null && ftpClient.isConnected()) {
//            try {
//                ftpClient.logout();
//                ftpClient.disconnect();
//                log.info("连接正常关闭");
//            } catch (IOException e) {
//                log.info("关闭FTP连接失败", e);
//            }
//        }
//    }
//
//
//
//
//    /**
//     * 上传Ftp
//     * */
//    public static boolean uploadFile(SysFtp ftp, InputStream input) {
//        boolean flag = false ;
//        try {
//            ftpClient.enterLocalPassiveMode();
//            flag = ftpClient.storeFile(ftp.getFtp_filename(), input);
//            input.close();
//            if(flag==true){
//                log.info("文件上传成功");
//            }else{
//                log.warn("传输文件Server建立失败");
//            }
//        } catch (IOException e) {
//            log.warn("文件上传失败！"+e.getMessage());
//        } finally {
//            closeConnect();
//        }
//        return flag;
//    }
//
//
//
//
//
//}
//
//
