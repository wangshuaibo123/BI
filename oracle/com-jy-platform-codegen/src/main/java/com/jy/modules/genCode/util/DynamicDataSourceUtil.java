package com.jy.modules.genCode.util;

import javax.sql.DataSource;


public class DynamicDataSourceUtil {
    private static final ThreadLocal<DataSource> dynamicDataSource = new ThreadLocal<DataSource>();  
    
    public static void setDataSource(DataSource dataSource) {
        dynamicDataSource.set(dataSource);
    }
    
    public static DataSource getDataSource() {
        return dynamicDataSource.get();
    }
}
