package com.jerry.springboot_config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyWebFilter {
    @Bean
    public FilterRegistrationBean testFilterRegistration()
    {
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/getUser","/hello");
        registrationBean.addInitParameter("paramName","paramValue");
        registrationBean.setName("MyFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    class MyFilter implements Filter
    {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("参数初始化 "+filterConfig);
        }

        @Override
        public void destroy() {
        System.out.println("destroy ");
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest request=(HttpServletRequest) servletRequest;
            System.out.println("url "+request.getRequestURI());
            Calendar ca=Calendar.getInstance();
            int hour=ca.get(Calendar.HOUR_OF_DAY);
            if(0<hour&&hour<2)
            {
                HttpServletResponse response=(HttpServletResponse)servletResponse;
                Map<String,Object> messageMap=new HashMap<>();
                messageMap.put("status","1");
                messageMap.put("message","请求时间2-24点");
                ObjectMapper objectMapper=new ObjectMapper();
                String write=objectMapper.writeValueAsString(messageMap);
                response.getWriter().write(write);
                return;
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
