package com.easub;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easub.bi.service.IBIService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-base.xml","classpath:spring-bi.xml" })
public class Test {

	@Resource(name="com.easub.bi.service.impl.BIService")
	private IBIService biService;
	
	@org.junit.Test
	public void test() {
		Integer count = this.biService.getVideosStatCount(new HashMap());
		System.out.println(count);
		
	}
	
}
