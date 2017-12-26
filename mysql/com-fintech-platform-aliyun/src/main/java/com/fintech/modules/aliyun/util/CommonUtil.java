package com.fintech.modules.aliyun.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class CommonUtil {
	
	/**
	 * 
	 * @description  功能描述: 
	 * @author       作        者: 周志伟
	 * @description  功能描述: 组装文件名称
	 * @Createdate   建立日期： 2017年3月24日下午2:57:47
	 * @Projectname  项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	   public static String getUUid() {
	        String id = UUID.randomUUID().toString();
	        String name="";
	        id = id.replace("-", "");
	        name="ct_"+id+System.currentTimeMillis();
	        return  name;
	    }
	   
	   
	   public static final byte[] input2byte(InputStream inStream) throws IOException {  
	        try {
				ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
				byte[] buff = new byte[100];
				int rc = 0;
				while ((rc = inStream.read(buff, 0, 100)) > 0) {
					swapStream.write(buff, 0, rc);
				}
				byte[] in2b = swapStream.toByteArray();
				return in2b;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;  
	    } 
	   
	   /**
		 * 
		 *  @Description    : 读取文件数组
		 *  @Method_Name    : fileBuff
		 *  @param filePath
		 *  @return
		 *  @throws IOException
		 *  @return         : byte[]
		 *  @Creation Date  : 2015年1月27日 下午5:26:49 
		 *  @author Administrator*/
	    public static byte[] fileBuff(String filePath) throws IOException {  
	        File file = new File(filePath);  
	        long fileSize = file.length();  
	        if (fileSize > Integer.MAX_VALUE) {  
	            //System.out.println("file too big...");  
	            return null;  
	        }  
	        FileInputStream fi = new FileInputStream(file);  
	        byte[] file_buff = new byte[(int) fileSize];  
	        int offset = 0;  
	        int numRead = 0;  
	        while (offset < file_buff.length && (numRead = fi.read(file_buff, offset, file_buff.length - offset)) >= 0) {  
	            offset += numRead;  
	        }  
	        // 确保所有数据均被读取  
	        if (offset != file_buff.length) {  
	        throw new IOException("Could not completely read file "  
	                    + file.getName());  
	        }  
	        fi.close();  
	        return file_buff;  
	    }
	    
	    public static void wirteDataToFile(String savePath,byte[] data) throws Exception{
	        FileOutputStream fileOutputStream=null;
	        try {
	            fileOutputStream = new FileOutputStream(savePath);
	            fileOutputStream.write(data);
	            fileOutputStream.flush();
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            try {
	                if (fileOutputStream != null) {
	                    fileOutputStream.close();
	                }
	            } catch (IOException e) {
	            }
	        }
	    }
	    
	    @SuppressWarnings("static-access")
		public  String getPath(){
	        String rootPath=getClass().getResource("/").getFile().toString().replace("WEB-INF/classes/", "");  
	        rootPath+="template/"+this.getUUid()+"/";
	        File dir = new File(rootPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		return rootPath;
	}
	    
	    

		/** 
		 * 删除目录（文件夹）以及目录下的文件 
		 * @param   sPath 被删除目录的文件路径 
		 * @return  目录删除成功返回true，否则返回false 
		 */  
		public static boolean deleteDirectory(String sPath) {  
		    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
		    if (!sPath.endsWith(File.separator)) {  
		        sPath = sPath + File.separator;  
		    }  
		    File dirFile = new File(sPath);  
		    //如果dir对应的文件不存在，或者不是一个目录，则退出  
		    if (!dirFile.exists() || !dirFile.isDirectory()) {  
		        return false;  
		    }  
		    boolean   flag = true;  
		    //删除文件夹下的所有文件(包括子目录)  
		    File[] files = dirFile.listFiles();  
		    for (int i = 0; i < files.length; i++) {  
		        //删除子文件  
		        if (files[i].isFile()) {  
		            flag = deleteFile(files[i].getAbsolutePath());  
		            if (!flag) break;  
		        } //删除子目录  
		        else {  
		            flag = deleteDirectory(files[i].getAbsolutePath());  
		            if (!flag) break;  
		        }  
		    }  
		    if (!flag) return false;  
		    //删除当前目录  
		    if (dirFile.delete()) {  
		        return true;  
		    } else {  
		        return false;  
		    }  
		}  
		
		/** 
		 * 删除单个文件 
		 * @param   sPath    被删除文件的文件名 
		 * @return 单个文件删除成功返回true，否则返回false 
		 */  
		public static boolean deleteFile(String sPath) {  
			boolean  flag = false;  
			File    file = new File(sPath);  
		    // 路径为文件且不为空则进行删除  
		    if (file.isFile() && file.exists()) {  
		        file.delete();  
		        flag = true;  
		    }  
		    return flag;  
		}  

}
