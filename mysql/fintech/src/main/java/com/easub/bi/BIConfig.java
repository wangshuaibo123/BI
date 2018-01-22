package com.easub.bi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BIConfig {

	private static Properties pro = new Properties();
	static {
		try {
//			BIConfig.class.getClassLoader().getResourceAsStream("biconfig.properties");
//			pro.load(new FileInputStream("src/biconfig.properties"));
			pro.load(BIConfig.class.getClassLoader().getResourceAsStream("biconfig.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key) {
		return pro.getProperty(key);
	}
	
}
