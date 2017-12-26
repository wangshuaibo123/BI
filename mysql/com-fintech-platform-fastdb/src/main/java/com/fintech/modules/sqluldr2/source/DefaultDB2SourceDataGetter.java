package com.fintech.modules.sqluldr2.source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.modules.sqluldr2.ExecuteCommand;
import com.fintech.modules.sqluldr2.LogWriterFactory;
import com.fintech.modules.sqluldr2.TaskInfo;

public class DefaultDB2SourceDataGetter extends SourceDataGetter {
    private static final Logger logger =  LoggerFactory.getLogger(DefaultDB2SourceDataGetter.class);
	@Override
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
		
		//生成查询源数据条数的bat文件
		logger.debug("开始生成查询源数据条数的bat文件");
		
		BufferedWriter v_impdb_bat_writer = null;
		
		String db2_bat_path = toolsPath+"db2/count_"+tagtable+".bat";
		File impdb_bat = new File(db2_bat_path);
		try{
			
			if(impdb_bat.exists())
				FileUtils.forceDelete(impdb_bat);
				
			v_impdb_bat_writer = new BufferedWriter(new FileWriter(impdb_bat, true));
			v_impdb_bat_writer.write("db2 "+srcdbconn);
			v_impdb_bat_writer.newLine();
			v_impdb_bat_writer.write("db2 export to "+toolsPath+"db/count_"+expdb+" of del modified by codepage=1386 coldel$ messages "+logFile+" \""+srcCountSql+"\"");
			v_impdb_bat_writer.newLine();
			v_impdb_bat_writer.write("db2 connect reset");
	        v_impdb_bat_writer.close();
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			if (v_impdb_bat_writer != null) {
				try {
					v_impdb_bat_writer.close();
				} catch (IOException ee) {
					logger.info(ee.getMessage());
					LogWriterFactory.writeString2Log(ee.getMessage(),  logFile);
				}
			}
		}
		
		logger.debug("开始执行DB2源数据条数查询");
		String db2_cmd="db2cmd /c -c -w -i "+db2_bat_path;
	    try {
			new ExecuteCommand().execCommand(db2_cmd, true, "db2cmd", logCmdFile);
		} catch (Exception e) {
			StringBuffer errorTips = new StringBuffer();
			errorTips.append(id);
			errorTips.append("任务 查询数据源条数调用bat发生错误。");
			errorTips.append(e.getMessage());
			errorTips.append("这可能是以下原因导致的：bat文件不存在；"+db2_bat_path+"内命令错误");
			throw new Exception(errorTips.toString());
		}

		logger.debug("开始生成CTL控制文件");
		BufferedWriter v_count_impdb_ctl_writer = null;
		try {
			//LogWriterFactory.writeString2Log("开始生成CTL控制文件", logFile);
			String count_impdb_ctl_path = toolsPath+"ctl/count_"+id+"_"+tagtable+".ctl";
			File count_impdb_ctl = new File(count_impdb_ctl_path);
			if(count_impdb_ctl.exists())
				FileUtils.forceDelete(count_impdb_ctl);
			v_count_impdb_ctl_writer = new BufferedWriter(new FileWriter(count_impdb_ctl, true));
			v_count_impdb_ctl_writer.write("load data");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("infile '"+toolsPath+"db/count_"+expdb+"'");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("APPEND INTO table T_KODS_RESULT ");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("FIELDS TERMINATED BY '$'");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("OPTIONALLY ENCLOSED BY '\"'");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("TRAILING   NULLCOLS ");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("(");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("id, tablename, num, type");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write(")");
			v_count_impdb_ctl_writer.close();
			logger.debug("生成"+count_impdb_ctl_path);
			LogWriterFactory.writeString2Log("生成"+count_impdb_ctl_path,  logFile);
		} catch (IOException e) {
			logger.info(e.getMessage());
			LogWriterFactory.writeString2Log(e.getMessage(),  logFile);
		} finally {
			if (v_count_impdb_ctl_writer != null) {
				try {
					v_count_impdb_ctl_writer.close();
				} catch (IOException ee) {
					logger.info(ee.getMessage());
					LogWriterFactory.writeString2Log(ee.getMessage(),  logFile);
				}
			}
			
		}

	}

	@Override
	public void collectionValue(TaskInfo taskInfo) throws Exception {
		
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
		
		
		//生成该任务的bat导出文件
		logger.debug("开始生成源数据库采集的bat命令");
		
		BufferedWriter v_impdb_bat_writer = null;
		
		String db2_bat_path = toolsPath+"db2/"+tagtable+".bat";
		File impdb_bat = new File(db2_bat_path);
		try{
			
			if(impdb_bat.exists())
				FileUtils.forceDelete(impdb_bat);
				
			v_impdb_bat_writer = new BufferedWriter(new FileWriter(impdb_bat, true));
			v_impdb_bat_writer.write("db2 "+srcdbconn);
			v_impdb_bat_writer.newLine();
			v_impdb_bat_writer.write("db2 export to "+toolsPath+"db/"+expdb+" of del modified by codepage=1386 coldel$ messages "+logFile+" \""+srcSql+"\"");
			v_impdb_bat_writer.newLine();
			v_impdb_bat_writer.write("db2 connect reset");
	        v_impdb_bat_writer.close();
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			if (v_impdb_bat_writer != null) {
				try {
					v_impdb_bat_writer.close();
				} catch (IOException ee) {
					logger.info(ee.getMessage());
					LogWriterFactory.writeString2Log(ee.getMessage(),  logFile);
				}
			}
		
	    }
		
		logger.debug("开始执行DB2源数据库采集");
		String db2_cmd="db2cmd /c -c -w -i "+db2_bat_path;
	    try {
			new ExecuteCommand().execCommand(db2_cmd, true, "db2cmd", logCmdFile);
		} catch (Exception e) {
			StringBuffer errorTips = new StringBuffer();
			errorTips.append(id);
			errorTips.append("任务 采集数据源调用bat发生错误。");
			errorTips.append(e.getMessage());
			errorTips.append("这可能是以下原因导致的：bat文件不存在；"+db2_bat_path+"内命令错误");
			
			throw new Exception(errorTips.toString());
			
		}
		
		
		BufferedWriter v_impdb_ctl_writer = null;
		try {

			logger.debug("开始生成CTL控制文件");
			LogWriterFactory.writeString2Log("开始生成CTL控制文件",  logFile);
			
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
			v_impdb_ctl_writer.write("FIELDS TERMINATED BY '$'");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write("OPTIONALLY ENCLOSED BY '\"'");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write("TRAILING   NULLCOLS ");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write("(");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write(tagdbcols);
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write(")");
			v_impdb_ctl_writer.close();
			
			logger.debug("生成CTL控制文件 "+impdb_ctl_path+" 完毕");
			LogWriterFactory.writeString2Log("生成CTL控制文件 "+impdb_ctl_path+" 完毕",  logFile);
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			if (v_impdb_ctl_writer != null) {
				try {
					v_impdb_ctl_writer.close();
				} catch (IOException ee) {
					logger.info(ee.getMessage());
					LogWriterFactory.writeString2Log(ee.getMessage(),  logFile);
				}
			}
			
		}
	}

}
