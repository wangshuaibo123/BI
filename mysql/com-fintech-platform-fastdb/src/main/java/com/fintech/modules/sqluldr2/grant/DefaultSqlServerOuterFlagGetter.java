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

public class DefaultSqlServerOuterFlagGetter extends OuterFlagGetter {
    private static final Logger logger =  LoggerFactory.getLogger(DefaultSqlServerOuterFlagGetter.class);
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
			String grantFolderPath =  toolsPath+"grant";
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
					// TODO Auto-generated catch block
					logger.info(e.getMessage());
				}
			}

			String bcpCommand = "cmd /c "+toolsPath+"bin/bcp \""+grantsql+"\" queryout "+v_expdb+" -c -t\":|\" "+V_CONNECT;

			new ExecuteCommand().execCommand(bcpCommand, true, "bcp", null); 
			
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
