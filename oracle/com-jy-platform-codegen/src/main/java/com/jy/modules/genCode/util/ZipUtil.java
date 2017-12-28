package com.jy.modules.genCode.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipUtil {
    
    /**
     * 压缩整个文件夹中的所有文件，生成指定名称的zip压缩包
     * @param filepath 文件所在目录
     * @param zippath 压缩后zip文件名称
     * @param dirFlag zip文件中第一层是否包含一级目录，true包含；false没有
     */
    public static void zipMultiFile(String filepath ,String zippath, boolean dirFlag) {
        ZipOutputStream zipOut = null;
        try {
            File file = new File(filepath);// 要被压缩的文件夹
            File zipFile = new File(zippath);
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File fileSec:files){
                    if(dirFlag){
                        recursionZip(zipOut, fileSec, file.getName() + File.separator);
                    }else{
                        recursionZip(zipOut, fileSec, "");
                    }
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(zipOut != null){
                try{zipOut.close();}catch(Exception e){e.printStackTrace();}
            }
        }
    }
     
    private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception{
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File fileSec:files){
                recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
            }
        }
        else{
            byte[] buf = new byte[1024];
            InputStream input = null;
            try{
                input = new FileInputStream(file);
                zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
                int len;
                while((len = input.read(buf)) != -1){
                    zipOut.write(buf, 0, len);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                if(input != null){
                    try{input.close();}catch(Exception e){e.printStackTrace();}
                }
            }
        }
    }   
    
    public static void main(String[] args) {
        zipMultiFile("D:\\jieyue\\DP\\branches\\DP_IT_DP20170608001\\apache-tomcat-7.0.56\\webapps\\com-jy-platform-codegen\\WEB-INF\\classes\\gencode\\20170623181827", "D:\\jieyue\\DP\\branches\\DP_IT_DP20170608001\\apache-tomcat-7.0.56\\webapps\\com-jy-platform-codegen\\WEB-INF\\classes\\gencode\\111.zip", false);
    }
}
