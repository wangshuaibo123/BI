package com.fintech.modules.sqluldr2.grant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.modules.sqluldr2.ExecuteCommand;

public class DefaultOracleOuterFlagGetter extends OuterFlagGetter {
    private static final Logger logger =  LoggerFactory.getLogger(DefaultOracleOuterFlagGetter.class);
    @Override
    public String getOuterFlag(Map<String, String> params) {
        String grantDate = null;
        String grantdbconn = params.get("grantdbconn");
        String srcdbconn = params.get("srcdbconn");
        String grantsql = params.get("grantsql");
        String id = params.get("id");
        String toolsPath = params.get("toolsPath");
        
        String V_CONNECT = grantdbconn;
        if(V_CONNECT==null||V_CONNECT.trim().equals(""))
            V_CONNECT = srcdbconn;
        
        BufferedReader grantReader = null;

        try { 
            
            String grantFolderPath =  toolsPath+"/grant";
            File grantFolder = new File(grantFolderPath);

            if(!grantFolder.exists()) {
                FileUtils.forceMkdir(grantFolder);
            }
            
            String v_expdb = toolsPath+"grant/"+id+"_"+String.valueOf(System.currentTimeMillis())+".db";
            
            File expdb = new File(v_expdb);

            //删除临时文件
            if(expdb!=null && expdb.exists()) {
                try {
                    FileUtils.forceDelete(expdb);
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }

            String v_expdb_command = "cmd /c "+toolsPath+"bin/sqluldr2.exe "+V_CONNECT+" query=\""+grantsql+"\" field=0x3a0x7c file=\""+v_expdb+"\"";
            
            new ExecuteCommand().execCommand(v_expdb_command, true, "sqluldr2", null);
            
            if(expdb!=null && expdb.exists()) {
                    grantReader= new BufferedReader(new FileReader(expdb));
                    grantDate = grantReader.readLine();
                    
                    if ( grantDate != null && !grantDate.trim().equals("")) {
                        logger.debug("任务"+id+"在采集日"+grantDate+"的数据已经准备好，可以采集！");
                        
                    } else {
                        logger.debug("任务"+id+"的数据还不具备采集条件！");
                    }
                    grantReader.close();
            }
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            logger.info(e.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            if(grantReader!=null) {
                try {
                    grantReader.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
                
        }
        return grantDate;
    }

}
