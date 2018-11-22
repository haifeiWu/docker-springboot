package com.whforever.dockerspringboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhf
 * @Date 2018/11/22 17:23
 **/
@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String home() {
        return "Hello Docker World";
    }

}
