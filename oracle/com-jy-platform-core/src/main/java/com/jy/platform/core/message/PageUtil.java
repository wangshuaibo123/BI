package com.jy.platform.core.message;

public class PageUtil {

	/**
	 * 把前端的page对象转化成后台的page对象
	 * @param dataMsg
	 * @return
	 */
	public static PageParameter toPageParameter(DataMsg dataMsg){
	   	int currentPage = dataMsg.getPageIndex() ;
    	int pageSize = dataMsg.getPageSize();
    	PageParameter pageInfo = new PageParameter();
    	pageInfo.setCurrentPage(currentPage);
    	pageInfo.setPageSize(pageSize);
    	return pageInfo;
	}
}
