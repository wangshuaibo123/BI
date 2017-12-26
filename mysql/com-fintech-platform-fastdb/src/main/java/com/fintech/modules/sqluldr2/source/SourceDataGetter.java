package com.fintech.modules.sqluldr2.source;

import java.io.File;

import com.fintech.modules.sqluldr2.TaskInfo;

public abstract class SourceDataGetter {
    
    public abstract void collectionCount(TaskInfo taskInfo) throws Exception;
    
    public abstract void collectionValue(TaskInfo taskInfo) throws Exception ;
    
    public void collectionValueToMysql(TaskInfo taskInfo) throws Exception{
        
    }
    
    public String getCountDBPath(TaskInfo taskInfo) {
        String toolsPath = taskInfo.getToolsPath();
        String expdb = taskInfo.getExpdb();
        return toolsPath+"db"+File.separator+"count_"+expdb;
    }

}