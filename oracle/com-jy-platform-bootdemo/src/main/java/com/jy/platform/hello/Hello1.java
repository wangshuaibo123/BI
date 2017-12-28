package com.jy.platform.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello1 {

	@RequestMapping("/hello1")
    public String index() {
        return "Hello1 - " + new java.util.Date();
    }
}
