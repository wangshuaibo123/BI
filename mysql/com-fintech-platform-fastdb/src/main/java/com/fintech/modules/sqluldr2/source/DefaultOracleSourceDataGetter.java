package com.fintech.modules.sqluldr2.source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fintech.modules.sqluldr2.ExecuteCommand;
import com.fintech.modules.sqluldr2.LogWriterFactory;
import com.fintech.modules.sqluldr2.TaskInfo;

@Component("com.fintech.modules.sqluldr2.source.DefaultOracleSourceDataGetter")
public class DefaultOracleSourceDataGetter extends SourceDataGetter {
    private static final Logger logger =  LoggerFactory.getLogger(DefaultOracleSourceDataGetter.class);
	
	public void collectionCount(TaskInfo taskInfo) throws Exception {
		
		String id = taskInfo.getId();
		String srcCountSql = taskInfo.getSrcCountSql();
		String srcdbconn = taskInfo.getSrcdbconn();
		String expdb = taskInfo.getExpdb();
		String tagtable = taskInfo.getTagtable();
		String toolsPath = taskInfo.getToolsPath();
		File logFile = taskInfo.getLogFile();
		File logCmdFile = taskInfo.getLogCmdFile();
		
		srcCountSql = srcCountSql.replaceAll("\\s+", " ");
		srcCountSql = srcCountSql.replaceAll("\\s*,\\s*", ",");
		srcCountSql = srcCountSql.replaceAll("\\s*\\)\\s*", ")");
		srcCountSql = srcCountSql.replaceAll("\\s*\\(\\s*", "(");
		
		
		logger.debug("开始执行ORACLE源数据库采集统计");
		LogWriterFactory.writeString2Log("开始执行ORACLE源数据库采集统计", logFile);
		 
		BufferedWriter v_count_impdb_ctl_writer = null;
		BufferedWriter count_sqlfile_writer = null;
		try {
			
			logger.debug("开始采用SQL语句直接作为参数的方式生成源数据DBF文件");
		    LogWriterFactory.writeString2Log("开始采用SQL语句直接作为参数的方式生成源数据DBF文件", logFile);

			try {
				String v_expdb_command = "cmd /c "+toolsPath+"bin/sqluldr2.exe "+srcdbconn+" query=\""+srcCountSql+"\" field=0x3a0x7c file=\""+toolsPath+"db/count_"+expdb+"\" >>"+logCmdFile;
				new ExecuteCommand().execCommand(v_expdb_command, true, "sqluldr2", logFile);
				} catch(Exception e) {
					StringBuffer errorTips = new StringBuffer();
					errorTips.append(id);
					errorTips.append("任务 查询数据源数据量阶段发生错误。");
					errorTips.append(e.getMessage());
					errorTips.append("这可能是以下原因导致的：源数据库不能访问；源数据库链接方式错误；sql语句错误。建议检查t_kods_taskconfig表");
					errorTips.append(id);
					errorTips.append("任务的配置信息");
					
					throw new Exception(errorTips.toString());
					
				}
			
			String count_impdb_ctl_path = toolsPath+"ctl/count_"+id+"_"+tagtable+".ctl";
			File count_impdb_ctl = new File(count_impdb_ctl_path);
			if(count_impdb_ctl.exists()) FileUtils.forceDelete(count_impdb_ctl);
				
			v_count_impdb_ctl_writer = new BufferedWriter(new FileWriter(count_impdb_ctl));
			v_count_impdb_ctl_writer.write("load data");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("infile '"+toolsPath+"db/count_"+expdb+"'");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("APPEND INTO table T_KODS_RESULT ");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("FIELDS TERMINATED BY X'3a7c' TRAILING NULLCOLS");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("(");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("id, tablename, num, type");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write(")");
			v_count_impdb_ctl_writer.close();
			logger.debug("生成"+count_impdb_ctl_path);
			LogWriterFactory.writeString2Log("生成"+count_impdb_ctl_path, logFile);

		} catch(Exception e) {
			logger.info(e.getMessage());
			LogWriterFactory.writeString2Log(e.getMessage(), logFile);
		} finally {
			if (count_sqlfile_writer != null) {
				try {
					count_sqlfile_writer.close();
				} catch (IOException ee) {
					logger.info(ee.getMessage());
					LogWriterFactory.writeString2Log(ee.getMessage(), logFile);
				}
			}
			if (v_count_impdb_ctl_writer != null) {
				try {
					v_count_impdb_ctl_writer.close();
				} catch (Exception ee) {
					logger.info(ee.getMessage());
					LogWriterFactory.writeString2Log(ee.getMessage(), logFile);
				}
			}
			
		}
	}

	@Override
	public void collectionValue(TaskInfo taskInfo) throws Exception  {
	    String srcSql = taskInfo.getSrcSql();
        String srcdbconn = taskInfo.getSrcdbconn();
        String expdb = taskInfo.getExpdb();
        String tagtable = taskInfo.getTagtable();
        String toolsPath = taskInfo.getToolsPath();
        File logCmdFile = taskInfo.getLogCmdFile();
        String execmode = taskInfo.getExecmode();
        
        srcSql = srcSql.replaceAll("\\s+", " ");
        srcSql = srcSql.replaceAll("\\s*,\\s*", ",");
        srcSql = srcSql.replaceAll("\\s*\\)\\s*", ")");
        srcSql = srcSql.replaceAll("\\s*\\(\\s*", "(");

        
        String v_expdb_command = "cmd /c "+toolsPath+"bin/sqluldr2.exe "+srcdbconn+" query=\""+srcSql+"\" field=0x3a0x7c file=\""+toolsPath+"db/"+expdb+"\" control="+toolsPath+"ctl/"+tagtable+".ctl mode="+execmode+" >>"+logCmdFile;
        this.exeCMD(taskInfo, v_expdb_command);
        
	}

	private void exeCMD(TaskInfo taskInfo, String cmd)throws Exception {
	    String id = taskInfo.getId();
        String srcSql = taskInfo.getSrcSql();
        String srcdbconn = taskInfo.getSrcdbconn();
        String expdb = taskInfo.getExpdb();
        String tagtable = taskInfo.getTagtable();
        String execmode = taskInfo.getExecmode();
        String tagdbcols = taskInfo.getTagdbcols();
        String toolsPath = taskInfo.getToolsPath();
        File logFile = taskInfo.getLogFile();
        File logCmdFile = taskInfo.getLogCmdFile();
        
        srcSql = srcSql.replaceAll("\\s+", " ");
        srcSql = srcSql.replaceAll("\\s*,\\s*", ",");
        srcSql = srcSql.replaceAll("\\s*\\)\\s*", ")");
        srcSql = srcSql.replaceAll("\\s*\\(\\s*", "(");

        logger.debug("开始执行ORACLE源数据库采集");
        LogWriterFactory.writeString2Log("开始执行ORACLE源数据库采集", logFile);
         
        BufferedWriter sqlfile_writer = null;
        BufferedWriter v_impdb_ctl_writer = null;
        try {
            
            try {
                    //String v_expdb_command = "cmd /c "+toolsPath+"bin/sqluldr2.exe "+srcdbconn+" query=\""+srcSql+"\" field=0x3a0x7c file=\""+toolsPath+"db/"+expdb+"\" control="+toolsPath+"ctl/"+tagtable+".ctl mode="+execmode+" >>"+logCmdFile;
                    new ExecuteCommand().execCommand(cmd, true, "sqluldr2", logFile);
                } catch(Exception e) {
                    StringBuffer errorTips = new StringBuffer();
                    errorTips.append(id);
                    errorTips.append("任务 采集数据源生成*.dbf文件阶段发生错误。");
                    errorTips.append(e.getMessage());
                    errorTips.append("这可能是以下原因导致的：源数据库不能访问；源数据库链接方式错误；sql语句错误；sqluldr2工具不存在或者路径错误。建议检查t_kods_taskconfig表");
                    errorTips.append(id);
                    errorTips.append("任务的配置信息和sqluldr2工具文件路径");
                    
                    throw new Exception(errorTips.toString());
                }
            
            
            logger.debug("开始生成CTL控制文件");
            LogWriterFactory.writeString2Log("开始生成CTL控制文件", logFile);
            
            String impdb_ctl_path = toolsPath+"ctl/"+id+"_"+tagtable+".ctl";
            File impdb_ctl = new File(impdb_ctl_path);
            if(impdb_ctl.exists())
                FileUtils.forceDelete(impdb_ctl);
                
            v_impdb_ctl_writer = new BufferedWriter(new FileWriter(impdb_ctl, true));
            v_impdb_ctl_writer.write("load data");
            v_impdb_ctl_writer.newLine();
            v_impdb_ctl_writer.write("infile '"+toolsPath+"db/"+expdb+"'");
            v_impdb_ctl_writer.newLine();
            v_impdb_ctl_writer.write(execmode+" into table "+tagtable);
            v_impdb_ctl_writer.newLine();
            v_impdb_ctl_writer.write("FIELDS TERMINATED BY X'3a7c' TRAILING NULLCOLS");
            v_impdb_ctl_writer.newLine();
            v_impdb_ctl_writer.write("(");
            v_impdb_ctl_writer.newLine();
            v_impdb_ctl_writer.write(tagdbcols);
            v_impdb_ctl_writer.newLine();
            v_impdb_ctl_writer.write(")");
            v_impdb_ctl_writer.newLine();
            v_impdb_ctl_writer.close();
            
            logger.debug("生成"+impdb_ctl_path+"数据表CTL文件");
            LogWriterFactory.writeString2Log("生成"+impdb_ctl_path+"数据表CTL文件", logFile);
            logger.debug("程序执行完毕");
        } catch(Exception e) {
            logger.info(e.getMessage());
            LogWriterFactory.writeString2Log(e.getMessage(), logFile);
        } finally {
            if (sqlfile_writer != null) {
                try {
                    sqlfile_writer.close();
                } catch (IOException ee) {
                    logger.info(ee.getMessage());
                    LogWriterFactory.writeString2Log(ee.getMessage(), logFile);
                }
            }
            if (v_impdb_ctl_writer != null) {
                try {
                    v_impdb_ctl_writer.close();
                } catch (Exception ee) {
                    logger.info(ee.getMessage());
                    LogWriterFactory.writeString2Log(ee.getMessage(), logFile);
                }
            }
            
        }
	}
	
    @Override
    public void collectionValueToMysql(TaskInfo taskInfo) throws Exception {
        String srcSql = taskInfo.getSrcSql();
        String srcdbconn = taskInfo.getSrcdbconn();
        String expdb = taskInfo.getExpdb();
        String tagtable = taskInfo.getTagtable();
        String toolsPath = taskInfo.getToolsPath();
        File logCmdFile = taskInfo.getLogCmdFile();
        
        srcSql = srcSql.replaceAll("\\s+", " ");
        srcSql = srcSql.replaceAll("\\s*,\\s*", ",");
        srcSql = srcSql.replaceAll("\\s*\\)\\s*", ")");
        srcSql = srcSql.replaceAll("\\s*\\(\\s*", "(");

        
        String v_expdb_command = "cmd /c "+toolsPath+"bin/sqluldr2.exe "+srcdbconn+" query=\""+srcSql+"\" file=\""+toolsPath+"db/"+expdb+"\" control="+toolsPath+"ctl/"+tagtable+".ctl text=mysql >>"+logCmdFile;
        this.exeCMD(taskInfo, v_expdb_command);
        
    }

}
