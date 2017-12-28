package com.jy.platform.core.message;

import java.io.Serializable;

/**
 * @classname: PaginatedHelper
 * @description: TODO(分页参数读取辅助类)
 */
public class DataMsg implements Serializable {

    public static final int PAGE_SIZE = 20;

    public static final String SORT_TYPE_ASC = "asc";

    public static final String SORT_TYPE_DESC = "desc";

    public static final String STATUS_OK = "ok";

    public static final String STATUS_FAILED = "failed";

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 每页大小
     */
    private int pageSize = PAGE_SIZE;

    /**
     * 当前页码
     */
    private int pageIndex = 1;

    /**
     * 总记录数
     */
    private int totalRows = 0;

    /**
     * 数据集合
     */
    private Object data = null;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 排序方式
     */
    private String sortType = SORT_TYPE_ASC;

    /**
     * 分页开始行数
     */
    private int startIndex;

    /**
     * 分页结束行数
     */
    private int endIndex;

    /**
     * 状态
     */
    private String status = STATUS_OK;

    /**
     * 消息
     */
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public void failed(String message) {
        this.status = this.STATUS_FAILED;
        this.msg = message;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public static String getSortTypeAsc() {
        return SORT_TYPE_ASC;
    }

    public static String getSortTypeDesc() {
        return SORT_TYPE_DESC;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public DataMsg() {
    	this.status = this.STATUS_OK;
    }
}
