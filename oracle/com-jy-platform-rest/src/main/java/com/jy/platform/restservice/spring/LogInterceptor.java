package com.jy.platform.restservice.spring;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogInterceptor  {
	static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    //有参并有返回值的方法
    public void logArgAndReturn(JoinPoint point, Object returnObj) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("#method:").append(point.getSignature()).append(" #args:");
        Object[] args = point.getArgs();
        if (args != null) {
            for (Object obj : args) {
               sb.append(obj+",");
            }
        }
        sb.append(" #return:");
        if(returnObj!=null){
           sb.append(returnObj);
        }
       // System.out.println(sb);
        logger.debug(sb.toString());
    }

}
