package myboot.myblog.config;

import myboot.myblog.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){

        WebMvcConfigurer configurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("blog/amaze/index");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //添加拦截器，放行静态资源和admin/login登录方法。
                registry.addInterceptor(new MyInterceptor()).addPathPatterns("/admin/**")
                        .excludePathPatterns("/admin/login").excludePathPatterns("/admin/dist/**")
                        .excludePathPatterns("/admin/plugins/**");
            }

//            @Override
//            public void addFormatters(FormatterRegistry registry) {
//                //添加日期转换器
//                registry.addConverter(new DateConverter());
//            }
        };
        return configurer;
    }

}
