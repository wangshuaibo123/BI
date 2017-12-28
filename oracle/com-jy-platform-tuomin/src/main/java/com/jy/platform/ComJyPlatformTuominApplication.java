package com.jy.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jy.platform.tuomin.Main;

/**
 * spring boot 启动类
 *
 */
@SpringBootApplication
public class ComJyPlatformTuominApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComJyPlatformTuominApplication.class, args);
		System.out.println("Spring Boot初始化完毕");
		if (args != null && args.length > 0 && "autorun".equals(args[0])) {
			System.out.println("程序为自动执行模式");
			if(args.length>=2){
			    Main.main(new String[]{args[1]});
			}else{
			    Main.main(new String[]{});
			}
			
			System.out.println("脱敏进程已经自动开始处理");
		}
	}

}
