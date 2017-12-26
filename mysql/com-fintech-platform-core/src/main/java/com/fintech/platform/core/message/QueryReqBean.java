package com.fintech.platform.core.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class QueryReqBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PageParameter pageParameter = null; //分页参数

	private Map<String, Object> searchParams = null;//查询条件
	
	private String sortExp = null;// 排序表达式,比如 a desc,b asc
	
	private HeaderBean headerBean = null;
	
	
	public PageParameter getPageParameter() {
		if(pageParameter==null){
			pageParameter = new PageParameter();
		}else{
			if(pageParameter.getFutureResult() != null){
				pageParameter.getTotalCount();//等待获取总数
				pageParameter.setFutureResult(null);
			}
			
		}
		return pageParameter;
	}
	public void setPageParameter(PageParameter pageParameter) {
		this.pageParameter = pageParameter;
	}
	
	public Map<String, Object> getSearchParams() {
		if(searchParams==null){
			searchParams = new HashMap<String, Object>();
		}
		if(pageParameter!=null){
			searchParams.put("page", pageParameter);
		}
		if(sortExp!=null){
			searchParams.put("sortExp", sortExp);
		}
		return searchParams;
	}
	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}


	public String getSortExp() {
		return sortExp;
	}
	public void setSortExp(String sortExp) {
		this.sortExp = sortExp;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryReqBean [pageParameter=");
		builder.append(pageParameter);
		builder.append(", searchParams=");
		builder.append(searchParams);
		builder.append(", sortExp=");
		builder.append(sortExp);
		builder.append("]");
		return builder.toString();
	}
    public HeaderBean getHeaderBean() {
        return headerBean;
    }
    public void setHeaderBean(HeaderBean headerBean) {
        this.headerBean = headerBean;
    }
	
}
