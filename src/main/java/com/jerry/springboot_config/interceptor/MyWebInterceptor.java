package com.jerry.springboot_config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerry.springboot_config.config.IpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyWebInterceptor implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getMyInterceptor(){return new MyInterceptor();}
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");

    }
    class MyInterceptor implements HandlerInterceptor
    {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           String ip=getIpAddr(request);
           String ipStr=ipConfig.getIpWhiteList();
           if (ipStr.contains(ip))
           {
               System.out.println("ip通过："+ip+" pass");
               return true;
           }else
           {
               System.out.println("ip不通过："+ip+" pass");
               Map<String,Object> messageMap=new HashMap<>();
               messageMap.put("status","1");
               messageMap.put("message","ip "+ip+"暂时无权限");
               ObjectMapper objectMapper=new ObjectMapper();
               String write=objectMapper.writeValueAsString(messageMap);
               response.getWriter().write(write);
               return false;
           }
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            System.out.println("post");

        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            System.out.println("afterCompletion");
        }

        public  String getIpAddr(HttpServletRequest request) {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }

        @Autowired
        private IpConfig ipConfig;

    }
}
