package com.fintech.modules.sqluldr2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteCommand {
    private static final Logger logger =  LoggerFactory.getLogger(ExecuteCommand.class);
	//map记录了程序名称与结果集
	private static Map<String, HashMap<Integer, String>> programMap = new HashMap<String, HashMap<Integer, String>>();
	
	static {
		//sqluldr2程序的结果集
		HashMap<Integer, String> sqluldr2ResultMap = new HashMap<Integer, String>();
		sqluldr2ResultMap.put(0, "Successful");
		sqluldr2ResultMap.put(1, "Cannot login to database");
		sqluldr2ResultMap.put(2, "Cannot create cursor handle");
		sqluldr2ResultMap.put(3, "Cannot prepare SQL statement");
		sqluldr2ResultMap.put(4, "Cannot execute SQL query");
		sqluldr2ResultMap.put(5, "Cannot get the metadata of the result set");
		sqluldr2ResultMap.put(6, "Cannot create output file");
		sqluldr2ResultMap.put(7, "Oracle error found when fetching rows, such as ORA-01555 etc");
		programMap.put("sqluldr2", sqluldr2ResultMap);
		
		HashMap<Integer, String> sqlldrResultMap = new HashMap<Integer, String>();
		sqlldrResultMap.put(0, "EX_SUCC");
		sqlldrResultMap.put(2, "EX_WARN");
		sqlldrResultMap.put(3, "EX_FAIL");
		sqlldrResultMap.put(4, "EX_FTL");
		programMap.put("sqlldr", sqlldrResultMap);
		
		HashMap<Integer, String> sqlplusResultMap = new HashMap<Integer, String>();
		sqlplusResultMap.put(0, "SUCC");
		sqlplusResultMap.put(1, "FAIL");
		programMap.put("sqlplus", sqlplusResultMap);
	}
	
	/*
	 * 执行命令
	 * command：命令字符串
	 * isSynchronized：是否同步执行。true则后面程序会等待命令执行结束，false则主程序与命令进程并行
	 * showAnySqlResult：是否显示anysql工具执行结果。必须为同步执行。
	 * showCostTime：是否显示执行耗时。必须为同步执行。
	 */
	public void execCommand(String command, boolean isSynchronized, String programName, File logFile) throws Exception {
		
		try {
			logger.info("\n开始执行"+command+"...");
			Long begin = System.currentTimeMillis();
			
			Process process ;
			
			//db2cmd只能以此方式调用
			if((programName!=null) && (programName.equals("db2cmd"))){
			    process = Runtime.getRuntime().exec(command);
			}else{//此方式解决window server 2008下sql语句不能含"<"和">"的问题
				
			    StringTokenizer st = new StringTokenizer(command);
			    String[] cmdarray = new String[st.countTokens()];
			    for (int i = 0; st.hasMoreTokens(); i++)
			    {   cmdarray[i] = st.nextToken();
			    }
				
			    ProcessBuilder pb = new ProcessBuilder(cmdarray);
			    pb.redirectErrorStream(true);
	            process = pb.start();
			}
			
			InputStream is = process.getInputStream();
			is.close();
			
			if(isSynchronized) {
				int waitFor = process.waitFor();
				logger.info("isSynchronized:"+isSynchronized+",waitFor:"+waitFor);
				
				StringBuffer resultTips = new StringBuffer();
				resultTips.append("\n执行'");
				resultTips.append(command);
				resultTips.append("'结果：");
				int exitValue = process.exitValue();
				resultTips.append(exitValue);
				if(programName!=null) {
					HashMap<Integer, String> resultMap = programMap.get(programName);
					String result;
					if(resultMap!=null && (result=resultMap.get(process.exitValue()))!=null) {
						resultTips.append("(");
						resultTips.append(result);
						resultTips.append(")");
					}
					
				}

				Long costTime = System.currentTimeMillis()-begin;
				if(costTime<1000)
					resultTips.append(" 耗时："+costTime+"毫秒");
				else
					resultTips.append(" 耗时："+costTime/1000+"秒");
				
				if(exitValue!=0) {//如果返回结果不正常
					logger.info("??????????????????????????????????????");
					logger.info(resultTips.toString());
					logger.info("??????????????????????????????????????");
					throw new Exception("执行["+command+"]命令过程中出现异常！");
				} else {
					logger.info(resultTips.toString());
					
				}
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
		} catch (InterruptedException e) {
			logger.info(e.getMessage());
		}
	}
	
}
