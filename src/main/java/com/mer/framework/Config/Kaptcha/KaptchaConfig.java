package com.mer.framework.Config.Kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 作用：生成图片验证码配置<br>
 * 说明：(无)
 *
 * @author zq
 */
@Configuration
public class KaptchaConfig {
    // https://blog.csdn.net/elephantboy/article/details/52795309
//    https://github.com/whvcse/EasyCaptcha
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 图片边框，合法值yes，no，默认值yes
        properties.setProperty("kaptcha.border", "no");
        // 边框颜色，合法值rgb(and optional alpha)或者 white,black,blue，默认值black
        properties.setProperty("kaptcha.border.color", "blue");
        //边框厚度
        properties.setProperty("kaptcha.border.thickness", "1");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "200");
        // 图片高
        properties.setProperty("kaptcha.image.height", "50");
        //图片实现类
        properties.setProperty("kaptcha.producer.impl", "com.google.code.kaptcha.impl.DefaultKaptcha");
        //文本实现类
        properties.setProperty("kaptcha.textproducer.impl", "com.google.code.kaptcha.text.impl.DefaultTextCreator");
        //文本集合，验证码值从此集合中获取
        properties.setProperty("kaptcha.textproducer.char.string", "01234567890");
        //验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //字体 Arial, Courier,宋体 彩云,宋体,楷体,微软雅黑
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅");
        //字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        //文字间隔
        properties.setProperty("kaptcha.textproducer.char.space", "5");
        //干扰实现类
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
        //干扰颜色
        properties.setProperty("kaptcha.noise.color", "blue");
        //干扰图片样式
        // 水纹com.google.code.kaptcha.impl.WaterRipple
        // 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
        // 阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.FishEyeGimpy");
        //背景实现类
        properties.setProperty("kaptcha.background.impl", "com.google.code.kaptcha.impl.DefaultBackground");
        // 背景颜色渐变，开始颜色，默认值lightGray/192,193,193  255,255,255  ，103,153,228
        properties.setProperty("kaptcha.background.clear.from", "192,193,193");
        //背景颜色渐变，结束颜色
        properties.setProperty("kaptcha.background.clear.to", "white");
        //文字渲染器
        properties.setProperty("kaptcha.word.impl", "com.google.code.kaptcha.text.impl.DefaultWordRenderer");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}