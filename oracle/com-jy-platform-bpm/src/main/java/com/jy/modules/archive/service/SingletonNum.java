package com.jy.modules.archive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author xyz
 *
 */
public class SingletonNum {
	private static final Logger logger = LoggerFactory.getLogger(SingletonNum.class);
	private static SingletonNum instance = new SingletonNum();  
	     private SingletonNum (){}
	     private int i=0;
	     public static SingletonNum getInstance() {  
	     return instance;  
	     }  
	     public  synchronized  void getNum(){
	    	 i=i+1;
	    	 logger.debug("第"+i+"次。。。。。。。。。。。。。。");
	     }
}
