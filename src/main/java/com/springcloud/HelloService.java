package com.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Copyright © 2017年 ziniuxiaozhu. All rights reserved.
 *
 * @Author 临江仙 hzqiuxm@163.com
 * @Date 2017/12/14 0014 21:22
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 负载方法
     * @param name
     * @return
     * SERVICE-HI ：虚拟主机名
     * restTemplate+Ribbon 可以应对简单的URL 如果url复杂，使用Feign,它整合了Ribbon
     */

    @HystrixCommand(fallbackMethod = "hiFallback")
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }


    /**
     * 降级服务处理
     * 加入Hysrix使用回调方法，使用注解方式时必须和调用服务在一个类中
     * @return
     */
    public String hiFallback(String name){

        return "hi service has a error!" + name;
    }
}
