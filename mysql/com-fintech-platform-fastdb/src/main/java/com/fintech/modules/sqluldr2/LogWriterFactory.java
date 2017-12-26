package com.fintech.modules.sqluldr2;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class LogWriterFactory {
	
	private static Map<String, LogWriter> logWriterMap = new HashMap<String, LogWriter>();
	
	private static Object o = new Object();
	
	//工厂方法找到日志文件对应的LogWriter，调用其writeString2Log方法写入日志
	public static void writeString2Log(String content, File logFile) {
		LogWriter writer = null;
		synchronized(o) {
			if(logFile==null||(logFile.exists()&&!logFile.isFile()))return;
			String filePath = logFile.getPath();
			writer = logWriterMap.get(filePath);
			if(writer==null) {
				writer = new LogWriter(logFile);
				writer.start();
				logWriterMap.put(filePath, writer);
			}
		}
		writer.add(content);
	}

	//工厂方法找到日志文件对应的LogWriter，调用其writeFile2Log方法写入日志
	public static void writeFile2Log(File file, File logFile) {
		LogWriter writer = null;
		synchronized(o) {
			if(logFile==null||(logFile.exists()&&!logFile.isFile()))return;
			String filePath = logFile.getPath();
			writer = logWriterMap.get(filePath);
			if(writer==null) {
				writer = new LogWriter(logFile);
				writer.start();
				logWriterMap.put(filePath, writer);
			}
		}
		writer.add(file);
	}
	
	public static void clearLogWriterMap() {
		synchronized(o) {
			Map<String, LogWriter> templogWriterMap = new HashMap<String, LogWriter>();
			Iterator<Entry<String, LogWriter>> iterator = logWriterMap.entrySet().iterator();
			while(iterator.hasNext()) {
				Entry<String, LogWriter> entry = iterator.next();
				
				if(entry.getValue()!=null&&!entry.getValue().isEmtry()) {
					templogWriterMap.put(entry.getKey(), entry.getValue());
				} else if(entry.getValue()!=null){
					entry.getValue().preShutDownThread();
				}
			}
			logWriterMap = templogWriterMap;
		}
	}
	
}
