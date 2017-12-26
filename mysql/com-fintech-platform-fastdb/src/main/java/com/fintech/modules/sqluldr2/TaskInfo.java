package com.fintech.modules.sqluldr2;

import java.io.File;


/*
 *  采集任务相关信息
 * */
public class TaskInfo {
    
    private String id;
    private String sort;
    private String srcSql;
    private String srcCountSql;
    private String expdb;
    private String srcdbconn;
    private String tagdbconn;
    private String tagtable;
    private String execmode;
    private String tagdbcols;
    private String toolsPath;
    private File logFile;
    private File logCmdFile;
    
    public TaskInfo(){}
    
    public TaskInfo(String id, String sort, String srcSql, String srcCountSql,
            String expdb, String srcdbconn,
            String tagdbconn, String tagtable, String execmode, String tagdbcols) {
        this.id = id;
        this.sort = sort;
        this.srcSql = srcSql;
        this.srcCountSql = srcCountSql;
        this.expdb = expdb;
        this.srcdbconn = srcdbconn;
        this.tagdbconn = tagdbconn;
        this.tagtable = tagtable;
        this.execmode = execmode;
        this.tagdbcols = tagdbcols;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getSrcSql() {
        return srcSql;
    }
    public void setSrcSql(String srcSql) {
        this.srcSql = srcSql;
    }
    public String getSrcCountSql() {
        return srcCountSql;
    }
    public void setSrcCountSql(String srcCountSql) {
        this.srcCountSql = srcCountSql;
    }
    public String getExpdb() {
        return expdb;
    }
    public void setExpdb(String expdb) {
        this.expdb = expdb;
    }
    public String getSrcdbconn() {
        return srcdbconn;
    }
    public void setSrcdbconn(String srcdbconn) {
        this.srcdbconn = srcdbconn;
    }
    public String getTagdbconn() {
        return tagdbconn;
    }
    public void setTagdbconn(String tagdbconn) {
        this.tagdbconn = tagdbconn;
    }
    public String getTagtable() {
        return tagtable;
    }
    public void setTagtable(String tagtable) {
        this.tagtable = tagtable;
    }
    public String getExecmode() {
        return execmode;
    }
    public void setExecmode(String execmode) {
        this.execmode = execmode;
    }
    public String getTagdbcols() {
        return tagdbcols;
    }
    public void setTagdbcols(String tagdbcols) {
        this.tagdbcols = tagdbcols;
    }
    public String getToolsPath() {
        return toolsPath;
    }
    public void setToolsPath(String toolsPath) {
        this.toolsPath = toolsPath;
    }
    public File getLogFile() {
        return logFile;
    }
    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }
    public File getLogCmdFile() {
        return logCmdFile;
    }
    public void setLogCmdFile(File logCmdFile) {
        this.logCmdFile = logCmdFile;
    }

    @Override
    public String toString() {
        return "TaskInfo [id=" + id + ", sort=" + sort + ", srcSql=" + srcSql + ", srcCountSql=" + srcCountSql
                + ", expdb=" + expdb + ", srcdbconn=" + srcdbconn + ", tagdbconn=" + tagdbconn + ", tagtable="
                + tagtable + ", execmode=" + execmode + ", tagdbcols=" + tagdbcols + ", toolsPath=" + toolsPath
                + ", logFile=" + logFile + ", logCmdFile=" + logCmdFile + "]";
    }
    
}
