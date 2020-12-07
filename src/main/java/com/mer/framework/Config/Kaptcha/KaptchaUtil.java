package com.mer.framework.Config.Kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * 作用：生成图片验证码<br>
 * 说明：(无)
 *
 * @author zq
 */
public class KaptchaUtil {
    /**
     * 生成验证码图片
     * @param request 设置session
     * @param response 转成图片
     * @param captchaProducer 生成图片方法类
     * @param validateSessionKey session名称
     * @throws Exception
     */
    public static void validateCode(HttpServletRequest request, HttpServletResponse response, DefaultKaptcha captchaProducer, String validateSessionKey) throws Exception{
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image 验证码
        String capText = captchaProducer.createText();

        /**
         * 纯数字验证码
         * */
//        // store the text in the session 验证码
//        request.getSession().setAttribute(validateSessionKey, capText);
//
//        // create the image with the text
//        BufferedImage bi = captchaProducer.createImage(capText);


        /**
         * 计算形验证码
         * */
        //个位数字相加
        String s1 = capText.substring(0, 1);
        String s2 = capText.substring(1, 2);
        int count = Integer.valueOf(s1).intValue() + Integer.valueOf(s2).intValue();
        request.getSession().setAttribute(validateSessionKey, count);
        BufferedImage bi = captchaProducer.createImage(s1 + "+" + s2 + "=?");



        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
