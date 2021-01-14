package myboot.myblog.controller.common;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
//@RequestMapping("")
public class KaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @GetMapping("/common/kaptcha")
    public void DefaultKaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //创建字节数组用来存放图片信息
        byte[] kaptchaOutputStream = null;
        //创建二进制输出流
        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
        //生成随机验证码
        String successCode = defaultKaptcha.createText();
        //将验证码存入session中
        request.getSession().setAttribute("successCode",successCode);
        //生成图片验证码
        BufferedImage image = defaultKaptcha.createImage(successCode);
        try {
            ImageIO.write(image,"jpg",imageOutputStream);
        } catch (IOException e) {
            System.out.println("图片写入流时出错。");
            e.printStackTrace();
        }
        kaptchaOutputStream = imageOutputStream.toByteArray();
        //no-store用于防止重要的信息被无意的发布。在请求消息中发送将使得请求和响应消息都不使用     缓存。
        response.setHeader("Cache-Control","no-store");
        //no-cache指示请求或响应消息不能缓存
        response.setHeader("Pragma","no-cache");
        //expires是response的一个属性,它可以设置页面在浏览器的缓存里保存的时间,超过设定的时间后就过期。
        //过期后再次浏览该页面就需要重新请求服务器发送页面数据，如果在规定的时间内再次访问次页面 就不需从服务器传送 直接从缓存中读取。
        response.setDateHeader("Expires", 0);
        // servlet接受request请求后接受图片形式的响应
        response.setContentType("image/jpeg");
        //通过response获得输出流
        ServletOutputStream stream = response.getOutputStream();
        stream.write(kaptchaOutputStream);
        stream.flush();
        stream.close();
    }

}
