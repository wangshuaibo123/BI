package com.fintech.modules.sqluldr2.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.modules.sqluldr2.ExecuteCommand;
import com.fintech.modules.sqluldr2.LogWriterFactory;
import com.fintech.modules.sqluldr2.TaskInfo;

public class DefaultDBFSourceDataGetter extends SourceDataGetter {
    private static final Logger logger =  LoggerFactory.getLogger(DefaultDBFSourceDataGetter.class);
	//日期格式
	private static final SimpleDateFormat COUNT_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

	@Override
	public void collectionCount(TaskInfo taskInfo) throws Exception {
		
		String id = taskInfo.getId();
		String srcSql = taskInfo.getSrcSql();
		String expdb = taskInfo.getExpdb();
		String tagtable = taskInfo.getTagtable();
		String toolsPath = taskInfo.getToolsPath();
		File logFile = taskInfo.getLogFile();

		String dbfFilePath = toolsPath+"db/"+expdb;
		File dbfFile = new File(dbfFilePath);
		try {
			String dbfCommand = "java -jar "+toolsPath+"bin/dbfread.jar "+srcSql+" " + dbfFile.getPath();// +" >>"+logCmdPath;
			new ExecuteCommand().execCommand(dbfCommand, true, "java", logFile); 
		} catch(Exception e) {
			StringBuffer errorTips = new StringBuffer();
			errorTips.append(id);
			errorTips.append("任务 查询数据源数据量阶段发生错误。");
			errorTips.append(e.getMessage());
			errorTips.append("这可能是以下原因导致的：数据源dbf文件不存在或路径错误；java工具不存在或路径错误；输出txt文件路径错误。建议检查文件路径和a_dts_taskconfig表");
			errorTips.append(id);
			errorTips.append("任务的配置信息");
			
			throw new Exception(errorTips.toString());
			
		}
		int v_txtcount = 0;

		if(!dbfFile.exists()) {
			logger.info("文件：\""+dbfFile.getPath()+"\"不存在!请确认是否具备采集条件！");
			LogWriterFactory.writeString2Log("文件：\""+srcSql+"\"不存在!请确认是否具备采集条件！", logFile);
			return ;
		}
		
		BufferedWriter v_count_impdb_ctl_writer = null;
		BufferedReader dtsvaluebfReader = null;
		FileWriter writer = null;
		try {
			String dbfCountFilePath = toolsPath+"db/count_"+expdb;
			dtsvaluebfReader =  new BufferedReader(new FileReader(dbfCountFilePath));
			String countString;
			if ((countString = dtsvaluebfReader.readLine()) == null) {
				countString = "0";
			}
			v_txtcount = new Integer(countString);
			
			String countContent = id+":|count_"+tagtable+":|"+v_txtcount+":|SRCDB";
			logger.debug(countContent);

			String count_impdb_db_path = toolsPath+"db/count_"+tagtable+".db";
			File count_impdb_db = new File(count_impdb_db_path);
			count_impdb_db.deleteOnExit();
			writer = new FileWriter(count_impdb_db);
			writer.write(countContent);
			writer.flush();
			writer.close();
			
			String count_impdb_ctl_path = toolsPath+"ctl/count_"+id+"_"+tagtable+".ctl";
			File count_impdb_ctl = new File(count_impdb_ctl_path);
			if(count_impdb_ctl.exists())
				FileUtils.forceDelete(count_impdb_ctl);
			v_count_impdb_ctl_writer = new BufferedWriter(new FileWriter(count_impdb_ctl, true));
			v_count_impdb_ctl_writer.write("load data");
			v_count_impdb_ctl_writer.newLine();
			v_count_impdb_ctl_writer.write("infile '"+toolsPath+"db/count_"+tagtable+".db'");
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
			LogWriterFactory.writeString2Log("生成"+count_impdb_ctl_path,  logFile);
		} catch (IOException e) {
			logger.info(e.getMessage());
			LogWriterFactory.writeString2Log(e.getMessage(), logFile);
		} finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.info(e.getMessage());
				}
			}
			if (dtsvaluebfReader != null) {
				try {
					dtsvaluebfReader.close();
				} catch (IOException ee) {
					LogWriterFactory.writeString2Log(ee.getMessage(), logFile);
					logger.info(ee.getMessage());
				}
			}
			
			if (v_count_impdb_ctl_writer != null) {
				try {
					v_count_impdb_ctl_writer.close();
				} catch (IOException ee) {
					LogWriterFactory.writeString2Log(ee.getMessage(), logFile);
					logger.info(ee.getMessage());
				}
			}
			
		}
		
		
	}

	@Override
	public void collectionValue(TaskInfo taskInfo) {

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
		
		logger.debug("\n开始执行DBF源数据库采集");
		
		logger.debug("\n开始生成CTL控制文件");
		LogWriterFactory.writeString2Log("开始生成CTL控制文件",  logFile);
		BufferedWriter v_impdb_ctl_writer = null;
		try {
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
			v_impdb_ctl_writer.close();
			
			logger.debug("\n生成CTL控制文件 "+impdb_ctl_path+" 完毕");
			LogWriterFactory.writeString2Log("生成CTL控制文件 "+impdb_ctl_path+" 完毕",  logFile);
		} catch(IOException e) {
			logger.info(e.getMessage());
			LogWriterFactory.writeString2Log(e.getMessage(), logFile);
		} finally {
			if (v_impdb_ctl_writer != null) {
				try {
					v_impdb_ctl_writer.close();
				} catch (IOException ee) {
					logger.info(ee.getMessage());
					LogWriterFactory.writeString2Log(ee.getMessage(), logFile);
				}
			}
			
		}
		
	}

}
