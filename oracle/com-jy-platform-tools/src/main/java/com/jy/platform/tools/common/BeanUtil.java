package com.jy.platform.tools.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanUtil {
    
    /**Description: 利用Java反射机制将Bean转成Map
     * Create Date: 2014年11月4日上午11:48:26<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param obj
     * @return
     */
    public static Map<String,Object> transBean2Map(Object obj){  
        Map<String,Object> map = new LinkedHashMap<String,Object>();  
        //key值 应该是 obj类中的属性名，利用反射机制  
        Field[] fields = obj.getClass().getDeclaredFields();  
        for(int i=0; i<fields.length; i++){  
            String field = fields[i].toString();  
            String[] keys = field.split("\\.");  
            String key = keys[keys.length-1];  
            char toUpperCase = (char)(key.charAt(0)-32);  
            String keyUpper = key.replaceFirst(new String(new char[]{key.charAt(0)}) ,new String(new char[]{toUpperCase}) );//key.replace(key.charAt(0),toUpperCase);  
            Method getMethod;  
            try {  
            	if("SerialVersionUID".equals(keyUpper)){//屏蔽SerialVerSionUID属性
            		continue;
            	}
                getMethod = obj.getClass().getDeclaredMethod("get"+keyUpper);//根据 field得到对应的get方法  
                Object value = getMethod.invoke(obj);  
                map.put(key, value);  
            } catch (NoSuchMethodException e) {  
                e.printStackTrace();  
            } catch (SecurityException e) {  
                e.printStackTrace();  
            }catch (IllegalAccessException e) {  
                e.printStackTrace();  
            } catch (IllegalArgumentException e) {  
                e.printStackTrace();  
            } catch (InvocationTargetException e) {  
                e.printStackTrace();  
            }         
        }  
        return map;  
    } 
}
