package com.ltl.weba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {


    /**
     * 方法类	方法名称	必填	请求头字段	说明
     * CorsRegistry	    addMapping	       是	无, 非Cors属性, 属于SpringBoot配置	 配置支持跨域的路径
     * CorsRegistration	allowedOrigins	   是	Access-Control-Allow-Origin	配置允许的源
     * CorsRegistration	allowedMethods	   是	Access-Control-Allow-Methods	配置支持跨域请求的方法, 如：GET、POST，一次性返回
     * CorsRegistration	maxAge	           否	  Access-Control-Max-Age	配置预检请求的有效时间
     * CorsRegistration	allowCredentials   否	Access-Control-Allow-Credentials	配置是否允许发送Cookie, 用于 凭证请求
     * CorsRegistration	allowedHeaders	   否	Access-Control-Request-Headers	配置允许的自定义请求头, 用于 预检请求
     * CorsRegistration	exposedHeaders	   否	Access-Control-Expose-Headers	配置响应的头信息, 在其中可以设置其他的头信息
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        WebMvcConfigurer e = new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowCredentials(true)
                        .maxAge(3600L);
            }
        };
        return e;
    }

}
