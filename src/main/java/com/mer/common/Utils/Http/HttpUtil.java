package com.mer.common.Utils.Http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static void main(String[] args) {
        post();
    }
    public static void post(){
        // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
        HttpURLConnection conn=null;
        // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
        BufferedReader in=null;
        PrintWriter writer=null;

        try {
            String param = "Hell!!!!";
            //设置URL
            URL url = new URL("http://127.0.0.1:3333/WebUser/setMsg");

            //建立URl链接
            conn= (HttpURLConnection)url.openConnection();
            //设置请求方式为post
            conn.setRequestMethod("POST");

            conn.setDoOutput(true);// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            conn.setDoInput(true);//  设置连接输入流为true

            //链接超时  防止请求被阻塞
            conn.setConnectTimeout(1000*60);
            //读取超时
            conn.setReadTimeout(1000*60);

            // post请求缓存设为false
            conn.setUseCaches(false);
            // 设置该HttpURLConnection实例是否自动执行重定向
            conn.setInstanceFollowRedirects(true);


            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
            // ;charset=utf-8 必须要，不然会出现乱码【★★★★★】
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            conn.connect();


//            // 获取所有响应头字段
//            Map<String, List<String>> map = conn.getHeaderFields();
//            // 遍历所有的响应头字段，获取到cookies等
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//
//            }

            //输入URLConnection对象的输出流
            writer = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
            //输入参数
            writer.println(param);
            //刷新缓存
            writer.flush();

            in  = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            int len;
//            byte[] arr = new byte[1024];
//            while((len=in.read(arr))!= -1){
//                bos.write(arr,0,len);
//                bos.flush();
//            }
//            bos.close();




            String line;
            String result="";
            while ((line = in.readLine()) != null){
                result +=  line;
            }

            System.out.println("响应："+result);

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                conn.disconnect(); // 销毁连接

            }


        }


    }


}
