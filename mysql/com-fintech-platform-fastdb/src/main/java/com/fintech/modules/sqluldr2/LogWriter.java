package com.fintech.modules.sqluldr2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWriter extends Thread{
    private static final Logger logger =  LoggerFactory.getLogger(LogWriter.class);
    private File logFile;
    private List<Object> queue = new LinkedList<Object>();
    
    private boolean flag = true;
    
    public LogWriter(File logFile) {
        this.logFile = logFile;
    }
    
    public boolean isEmtry() {
        if(queue.size()>0)
            return false;
        return true;
    }
    
    public void run() {
        
        while(flag) {
            if(queue.size()>0) {
                Object object = queue.get(0);
                if("java.lang.String".equals(object.getClass().getName())) {
                    writeString2Log((String)object);
                } else {
                    writeFile2Log((File)object);
                }
                queue.remove(0);
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    logger.info(e.getMessage());
                }
            }
        }
        
    }
    
    public void preShutDownThread() {
        this.flag = false;
    }
    
    public void add(Object object) {
        queue.add(object);
    }
    
    private void writeString2Log(String content) {
        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(logFile, true));
            logWriter.write(content);
            logWriter.newLine();
        } catch (Exception e2) {
            logger.info(content);
            logger.info(e2.getMessage());
        } finally {
            if(logWriter!=null)
                try {
                    logWriter.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
        }
    }
    
    private void writeFile2Log(File file) {
        BufferedWriter logWriter = null;
        BufferedReader reader = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(logFile, true));
            
            reader = new BufferedReader(new FileReader(file));
            String line;
            while((line=reader.readLine())!=null) {
                logWriter.write(line);
                logWriter.newLine();
            }
        } catch (Exception e2) {
            logger.info(e2.getMessage());
        } finally {
            if(reader!=null)
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.info(e1.getMessage());
                }
            if(logWriter!=null)
                try {
                    logWriter.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
        }
    }
}
