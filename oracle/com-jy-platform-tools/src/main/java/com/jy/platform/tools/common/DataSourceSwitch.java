package com.jy.platform.tools.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 多数据源
 * @author chen_gang
 * @date: 2016年1月28日 下午3:47:59
 */
public abstract class DataSourceSwitch {
 
    public final static String DS_master = "master";
    public final static String DS_slaveA = "slaveA";
    public final static String DS_slaveB = "slaveB";
    public final static String DS_slaveC = "slaveC";
    public final static String DS_slaveD = "slaveD";
    public final static String DS_slaveE = "slaveE";
    public final static String DS_slaveF = "slaveF";
    public final static String DS_slaveG = "slaveG";
    public final static String DS_slaveH = "slaveH";
    public static final Map<Long,String> contextHolderThreadMap = new ConcurrentHashMap<Long,String>();//此 类是线程安全的
    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    
    public static void setDataSourceType(String dataSourceType) {
    	//contextHolderThreadMap.put(Thread.currentThread().getId(), dataSourceType);
    	//System.out.println("=========getDataSourceType==ID===="+Thread.currentThread().getId()+"==contextHolderThreadMap:"+contextHolderThreadMap);
        contextHolder.set(dataSourceType);
    }
    public static String getDataSourceType() {
        return contextHolder.get();
    	//System.out.println("=========getDataSourceType==ID===="+Thread.currentThread().getId()+"==contextHolderThreadMap:"+contextHolderThreadMap);
    	//return contextHolderThreadMap.get(Thread.currentThread().getId());
    }
    public static void clearDataSourceType() {
    	//contextHolderThreadMap.remove(Thread.currentThread().getId());
    	//System.out.println("=========getDataSourceType==ID===="+Thread.currentThread().getId()+"==contextHolderThreadMap:"+contextHolderThreadMap);
        contextHolder.remove();
    }
}