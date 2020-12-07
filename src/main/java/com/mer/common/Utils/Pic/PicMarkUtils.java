package com.mer.common.Utils.Pic;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@SuppressWarnings("all")
public class PicMarkUtils {

    //定义水印文字样式
    private static final String FONT_NAME = "华文中宋";
    private static final int FONT_STYLE = Font.PLAIN;
    private static final int FONT_SIZE = 30;

    //设置水印颜色
//    private static final Color FONT_COLOR = Color.white;
    private static final Color FONT_COLOR = new Color(255,255,255,128);
    //设置水印文字透明度
    private static final float ALPHA = 0.8F;

    //设置水印文字的旋转角度
    private static final Integer degrees = -40;

    public static void main(String[] args) {
        moreTextWaterMark("C:\\Users\\HP\\Desktop\\正面.jpg","C:\\Users\\HP\\Desktop\\水印后.jpg","专用的信息");
    }


    /**
     * 添加多条文字 水印
     * @param sourceImgPath 原图
     * @param tarImgPath  保存的图片路径
     * @param mark_text  水印内容
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String moreTextWaterMark(String sourceImgPath, String tarImgPath, String mark_text) {
        InputStream is =null;
        OutputStream os =null;
        int X = 636;
        int Y = 763;
        File logossss = new File(sourceImgPath);
        try {
            Image image = ImageIO.read(logossss);
            //计算原始图片宽度长度
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            //创建图片缓存对象
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //创建java绘图工具对象
            Graphics2D graphics2d = bufferedImage.createGraphics();
            //参数主要是，原图，坐标，宽高
            graphics2d.drawImage(image, 0, 0, width, height, null);
            //设置字体
            graphics2d.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
            //设置水印颜色
            graphics2d.setColor(FONT_COLOR);
            //设置水印文字透明度
            graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));


            graphics2d.rotate(Math.toRadians(degrees), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
            //使用绘图工具将水印绘制到图片上
            //计算文字水印宽高值
            int waterWidth = 25*getTextLength(mark_text);
            int waterHeight = -20;
            int x = -width/2;
            int y = -height/2;

            while(x < width*1.5){
                y = -height/2;
                while(y < height*1.5){
                    graphics2d.drawString(mark_text, x, y);
                    y+=waterHeight+100;
                }
                x+=waterWidth+100;
            }
            graphics2d.dispose();

            os = new FileOutputStream(tarImgPath);
            //创建图像编码工具类
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            //使用图像编码工具类，输出缓存图像到目标文件
            en.encode(bufferedImage);
            if(is!=null){
                is.close();
            }
            if(os!=null){
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     *  计算水印文本长度
     *  1、中文长度即文本长度 2、英文长度为文本长度二分之一
     * @param text 水印内容
     * @return
     */
    public static int getTextLength(String text){
        //水印文字长度
        int length = text.length();

        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length>1) {
                length++;
            }
        }
        length = length%2==0?length/2:length/2+1;
        return length;
    }
}
