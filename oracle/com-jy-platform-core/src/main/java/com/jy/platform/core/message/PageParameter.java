package com.jy.platform.core.message;

import java.io.Serializable;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 分页参数类
 * 
 */
public class PageParameter implements Serializable{
	private static final Logger logger =  LoggerFactory.getLogger(PageParameter.class);
    public static final int DEFAULT_PAGE_SIZE = 20;
	private int pageSize;
    private int currentPage;
    private int totalPage;
    private int totalCount;
    
    private Future futureResult;

    public Future getFutureResult() {
		return futureResult;
	}

	public void setFutureResult(Future futureResult) {
		this.futureResult = futureResult;
	}

	public PageParameter() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * 
     * @param currentPage
     * @param pageSize
     */
    public PageParameter(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
    	try{
			if(futureResult!=null && "get".equals((String)futureResult.get())){
				futureResult = null;
				return totalPage;
			}
		} catch(Exception e){
			logger.error("page.totalPage",e);
		}
    	
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
    	try {
			if(futureResult!=null && "get".equals((String)futureResult.get())){
				futureResult = null;
				return totalCount;
			}
		} catch(Exception e){
    		logger.error("page.totalCount",e);
		}
    	
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageParameter [pageSize=");
		builder.append(pageSize);
		builder.append(", currentPage=");
		builder.append(currentPage);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append(", totalCount=");
		builder.append(totalCount);
		builder.append("]");
		return builder.toString();
	}

}
