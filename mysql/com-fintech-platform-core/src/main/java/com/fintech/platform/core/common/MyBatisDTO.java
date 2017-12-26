package com.fintech.platform.core.common;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/**
 * 自定义 sql分页 控制信息
 * @author
 *
 */
public class MyBatisDTO extends BaseDTO{
	private static final long serialVersionUID = -4206353810709428124L;
	public static final int DEFAULT_PAGE_SIZE = 20;
	private int pageSize;
    private int currentPage;
    private int totalPage;
    private int totalCount;

    public MyBatisDTO() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }
    public MyBatisDTO(int currentPage, int pageSize) {
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
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

	/**
	 * 分页查询 返回 总个数
	 * @param list
	 * @return
	 */
	public static int getTotalCountOfListByPage(List dataList){
		int totalNum = 0;
		if(dataList != null && dataList.size() >0){
			Object obj  = dataList.get(0);
			if(obj instanceof Map){
				Map temp = (Map) dataList.get(0);
				totalNum = Integer.parseInt(String.valueOf((BigDecimal)temp.get("TOTALCOUNT")));
			}/*else if(obj instanceof MyBatisPageInfoExt){
				MyBatisPageInfoExt tempDto =  (MyBatisPageInfoExt) dataList.get(0);
				totalNum = tempDto.getTotalCount();
			}*/else{
				MyBatisDTO tempDto =  (MyBatisDTO) dataList.get(0);
				totalNum = tempDto.getTotalCount();
			}
			
		}
		return totalNum;
	}
    
}
