package com.fintech.modules.sqluldr2.source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.modules.sqluldr2.LogWriterFactory;
import com.fintech.modules.sqluldr2.TaskInfo;

public class DefaultTXTSourceDataGetter extends SourceDataGetter {
    private static final Logger logger =  LoggerFactory.getLogger(DefaultTXTSourceDataGetter.class);
	@Override
	public void collectionCount(TaskInfo taskInfo) throws Exception  {
	   
		String id = taskInfo.getId();
		String expdb = taskInfo.getExpdb();
		String tagtable = taskInfo.getTagtable();
		String toolsPath = taskInfo.getToolsPath();
		File logFile = taskInfo.getLogFile();
		FileWriter writer = null;
		Integer txtcount = countLineNumber(expdb);
		
		BufferedWriter v_count_impdb_ctl_writer = null;
		try { 
		
			String countContent = id+":|count_"+tagtable+":|"+txtcount+":|:|SRCDB";
			logger.info(countContent);
	
			String count_impdb_db_path = toolsPath+"db/count_"+tagtable+".db";
			File count_impdb_db = new File(count_impdb_db_path);
			if(count_impdb_db.exists())
				FileUtils.forceDelete(count_impdb_db);
			writer = new FileWriter(count_impdb_db);
			writer.write(countContent);
			
			logger.info("开始生成CTL控制文件");
			LogWriterFactory.writeString2Log("开始生成CTL控制文件", logFile);
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
			logger.info("生成"+count_impdb_ctl_path);
			LogWriterFactory.writeString2Log("生成"+count_impdb_ctl_path,  logFile);
		} catch(Exception e) {
			logger.info(e.getMessage());
			LogWriterFactory.writeString2Log(e.getMessage(),  logFile);
		} finally {
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					logger.info(e.getMessage());
				}
			}
			if (v_count_impdb_ctl_writer != null) {
				try {
					v_count_impdb_ctl_writer.close();
				} catch (IOException ee) {
					LogWriterFactory.writeString2Log(ee.getMessage(),  logFile);
				}
			}
			
		}
	}

	@Override
	public void collectionValue(TaskInfo taskInfo) {
		
		String id = taskInfo.getId();
		String expdb = taskInfo.getExpdb();
		String tagtable = taskInfo.getTagtable();
		String execmode = taskInfo.getExecmode();
		String tagdbcols = taskInfo.getTagdbcols();
		String toolsPath = taskInfo.getToolsPath();
		File logFile = taskInfo.getLogFile();
		
		BufferedWriter v_impdb_ctl_writer = null;
		try {
			logger.info("为TXT数据源生成ctl控制文件");
			String impdb_ctl_path = toolsPath+"ctl/"+id+"_"+tagtable+".ctl";
			File impdb_ctl = new File(impdb_ctl_path);
			if(impdb_ctl.exists())
				FileUtils.forceDelete(impdb_ctl);
			v_impdb_ctl_writer = new BufferedWriter(new FileWriter(impdb_ctl, true));
			v_impdb_ctl_writer.write("load data");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write("infile '"+expdb+"'");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write(execmode+" into table "+tagtable);
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write("FIELDS TERMINATED BY X'3a7c'");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write("OPTIONALLY ENCLOSED BY X'22'");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write("TRAILING   NULLCOLS ");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write("(");
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write(tagdbcols);
			v_impdb_ctl_writer.newLine();
			v_impdb_ctl_writer.write(")");
			v_impdb_ctl_writer.close();
	
			logger.info("生成CTL控制文件 "+impdb_ctl_path+" 完毕");
			LogWriterFactory.writeString2Log("生成CTL控制文件 "+impdb_ctl_path+" 完毕",  logFile);
		} catch(IOException e) {
			LogWriterFactory.writeString2Log(e.getMessage(),  logFile);
		} finally {
			if (v_impdb_ctl_writer != null) {
				try {
					v_impdb_ctl_writer.close();
				} catch (IOException ee) {
					LogWriterFactory.writeString2Log(ee.getMessage(),  logFile);
				}
			}
			
		}
	}
	
	protected Integer countLineNumber(String path) {
		File test= new File(path); 
		long fileLength = test.length(); 
		LineNumberReader rf = null; 
		try { 
			rf = new LineNumberReader(new FileReader(test)); 
			if (rf != null) { 
				int lines = 0; 
				rf.skip(fileLength); 
				lines = rf.getLineNumber(); 
				rf.close(); 
				return lines;
			} 
		} catch (IOException e) { 
			if (rf != null) { 
				try { 
					rf.close(); 
				} catch (IOException ee) { 
					logger.info(ee.getMessage());
				} 
			} 
		}
		return null;
	}

}
