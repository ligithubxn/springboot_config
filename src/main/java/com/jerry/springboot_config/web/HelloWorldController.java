package com.jerry.springboot_config.web;

import com.jerry.springboot_config.config.MyProperties;
import com.jerry.springboot_config.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @Autowired
    MyProperties myProperties;
    @Autowired
    Environment env;
    @RequestMapping("/hello")
    public String index()
    {
        System.out.println("---------开始----------");
        System.out.println("title:"+myProperties.getTitle());
        System.out.println("describe:"+myProperties.getDescription());
        System.out.println("server.port:"+env.getProperty("server.port"));
        return "Hello World";
    }
    @RequestMapping("/getUser")
    public User getUser() {
        System.out.println("---------开始----------");
        User user=new User();
        user.setId(2);
        user.setName("李四");
        return user;
    }
}
