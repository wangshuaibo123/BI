package com.fintech.platform.cfg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * RestClient的配置
 * @author zhangyu
 * @since 2016/11/15
 *
 */
public class RestConfig {
	
	/**
	 * 配置文件名
	 */
	private static final String CONFIG_FILE = "biz_app.properties";
	private static Properties props;
	
	/*
	* 初始化时加载配置文件
	*/
	static {
		try {
			load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加载配置文件，配置文件不存在时不加载。[以后可以通过调用达到配置的实时更新]
	 * @throws IOException
	 */
	public static synchronized void load() throws IOException {
		String fileName = RestConfig.class.getResource("/").getFile() + CONFIG_FILE;
		File propFile = new File(fileName);
		Properties p = new Properties();
		
		if (propFile.exists()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(propFile), "UTF-8"));
			p.load(br);
		}
		props = p;
	}
	
	public static String get(String key) {
		return props.getProperty(key);
	}

	public static String get(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
	
	public static Integer getInteger(String key) {
		String s = props.getProperty(key);
		if (s == null) {
			return null;
		} else {
			return Integer.valueOf(s);
		}
	}

}
