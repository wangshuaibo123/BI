package com.fintech.modules.sqluldr2.demo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fintech.modules.sqluldr2.TaskInfo;
import com.fintech.modules.sqluldr2.grant.DefaultOracleOuterFlagGetter;
import com.fintech.modules.sqluldr2.source.DefaultOracleSourceDataGetter;


public class Test {

    public static void main(String[] args) {
        DefaultOracleSourceDataGetter oracle = new DefaultOracleSourceDataGetter();
        
        try {
            //grantSQL();
            
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setId("1");
            taskInfo.setSrcCountSql("select count(*) from sys_app_error_info");
            
            taskInfo.setSrcdbconn("user=ptuser/ptuser@172.18.100.86:1521/testdb");
            taskInfo.setExpdb("sys_app_error_info.log");
            taskInfo.setTagtable("sys_app_error_info");
            taskInfo.setToolsPath("F:/cgJYwork/fintech/src/main/webapp/component/tools/");
            taskInfo.setLogFile(new File("F:/cgJYwork/fintech/src/main/webapp/component/tools/sqlunloader_logs.log"));
            taskInfo.setLogCmdFile(new File("F:/cgJYwork/fintech/src/main/webapp/component/tools/log/exe_log.log"));
            
            //统计收集的数据总数
            //oracle.collectionCount(taskInfo);
            //收集数据
            //collectionConten(oralce);

            collectionContenToMysql(oracle);
            System.exit(0);
            
            /*
             * 将导出的平面文件 导入mysql中 参考
             * \bin>mysql -h172.18.100.102 -upt_dev -ppt_dev
                    mysql> use ptdb
                    Database changed
                    mysql>LOAD DATA LOCAL INFILE "F:/cgJYwork/fintech/src/main/webapp/component/tools/db/sys_app_error_info.sql" INTO TABLE sys_app_error_info FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n';
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static void grantSQL(){
        Map<String,String> params = new HashMap<String,String>();
        
        params.put("grantdbconn","user=ptuser/ptuser@172.18.100.86:1521/testdb");
        params.put("srcdbconn","user=ptuser/ptuser@172.18.100.86:1521/testdb");
        params.put("grantsql","select ID ,NODE_NAME,APP_FLAG ,CREATE_TIME,CONCENT,FILE_NAME,LOG_LEVEL,LEVEL_SETUP_ID ,MATCHED_FLAG  from sys_app_error_info");
        params.put("id","1");
        params.put("toolsPath","F:/cgJYwork/fintech/src/main/webapp/component/tools/");
        DefaultOracleOuterFlagGetter out = new DefaultOracleOuterFlagGetter();
     
        String str =out.getOuterFlag(params);
        System.out.println("=========="+str);
    }
    
    public static void collectionConten(DefaultOracleSourceDataGetter oralce) throws Exception{
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId("1");
        
        taskInfo.setSrcdbconn("user=ptuser/ptuser@172.18.100.86:1521/testdb");
        taskInfo.setExpdb("sys_app_error_info.log");
        taskInfo.setTagtable("sys_app_error_info");
        taskInfo.setToolsPath("F:/cgJYwork/fintech/src/main/webapp/component/tools/");
        taskInfo.setLogFile(new File("F:/cgJYwork/fintech/src/main/webapp/component/tools/sqlunloader_logs.log"));
        taskInfo.setLogCmdFile(new File("F:/cgJYwork/fintech/src/main/webapp/component/tools/log/exe_log.log"));
        
        
        taskInfo.setExecmode("APPEND");
        taskInfo.setTagdbcols("ID ,NODE_NAME,APP_FLAG ,CREATE_TIME,CONCENT,FILE_NAME,LOG_LEVEL,LEVEL_SETUP_ID ,MATCHED_FLAG ");
        
        taskInfo.setSrcSql("select ID ,NODE_NAME,APP_FLAG ,CREATE_TIME,CONCENT,FILE_NAME,LOG_LEVEL,LEVEL_SETUP_ID ,MATCHED_FLAG  from sys_app_error_info");
        oralce.collectionValue(taskInfo);
    }
    
    public static void collectionContenToMysql(DefaultOracleSourceDataGetter oralce) throws Exception{
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId("1");
        
        taskInfo.setSrcdbconn("user=ptuser/ptuser@172.18.100.86:1521/testdb");
        taskInfo.setExpdb("sys_app_error_info.sql");
        taskInfo.setTagtable("sys_app_error_info");
        taskInfo.setToolsPath("F:/cgJYwork/fintech/src/main/webapp/component/tools/");
        taskInfo.setLogFile(new File("F:/cgJYwork/fintech/src/main/webapp/component/tools/sqlunloader_logs.log"));
        taskInfo.setLogCmdFile(new File("F:/cgJYwork/fintech/src/main/webapp/component/tools/log/exe_log.log"));
        
        
        taskInfo.setExecmode("APPEND");
        taskInfo.setTagdbcols("ID ,NODE_NAME,APP_FLAG ,CREATE_TIME,CONCENT,FILE_NAME,LOG_LEVEL,LEVEL_SETUP_ID ,MATCHED_FLAG ");
        
        taskInfo.setSrcSql("select ID ,NODE_NAME,APP_FLAG ,CREATE_TIME,CONCENT,FILE_NAME,LOG_LEVEL,LEVEL_SETUP_ID ,MATCHED_FLAG  from sys_app_error_info");
        oralce.collectionValueToMysql(taskInfo);
    }

}
