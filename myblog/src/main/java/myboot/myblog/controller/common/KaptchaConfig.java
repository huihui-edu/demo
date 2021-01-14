package myboot.myblog.controller.common;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 生成验证码工具类
 */
@Component
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        //创建DefaultKaptcha对象
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        //创建配置文件对象
        Properties properties = new Properties();
        //不设置边框
        properties.put("kaptcha.border","no");
        //设置验证码字体颜色
        properties.put("kaptcha.textproducer.font.color","blue");
        //设置验证码高度和宽度
        properties.put("kaptcha.image.width","150");
        properties.put("kaptcha.image.height","40");
        //设置验证码字体大小
        properties.put("kaptcha.textproducer.font.size","30");
        //设置session中验证码的key值
        properties.put("kaptcha.session.key","verifyCode");
        //设置文字间隔
        properties.put("kaptcha.textproducer.char.space","5");
        //将properties对象添加到katpcha对象中
        kaptcha.setConfig(new Config(properties));
        return kaptcha;
    }

}
