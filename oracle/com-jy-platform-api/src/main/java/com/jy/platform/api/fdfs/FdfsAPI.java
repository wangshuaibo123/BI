package com.jy.platform.api.fdfs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 提供Fdfs 多文件打成ZIP包 操作
 * @author plx
 *
 */

public interface FdfsAPI {
	
	/**
	 * 
	 * fileIds  文件ID   例如  group1/M00/00/00/rBJk1VSs3PSAAPtZADkUGMzE578760.jpg
	 * 
	 * @param fileIds
	 * @param tempZipDirPath
	 * @return
	 */
	
	
	public void pack(String[] fileIds,HttpServletRequest request,HttpServletResponse response);
	
	
	
	
}
