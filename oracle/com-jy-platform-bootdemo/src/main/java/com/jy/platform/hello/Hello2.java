package com.jy.platform.hello;

import org.springframework.web.bind.annotation.RequestMapping;

public class Hello2 {

	@RequestMapping("/hello2")
    public String index() {
        return "Hello2 - " + new java.util.Date();
    }
	
	private String name;
	
	public void start() {
		System.out.println("##Hello2在配置文件" + name + "中的定义生效了");
		
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
