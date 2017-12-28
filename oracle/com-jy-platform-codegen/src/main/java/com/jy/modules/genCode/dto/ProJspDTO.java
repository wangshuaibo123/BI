package com.jy.modules.genCode.dto;

import java.io.Serializable;

/**
 * @classname: ProJspDTO
 * @description: 定义 生成代码页面 设置的参数信息
 */
public class ProJspDTO implements Serializable {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */

    private static final long serialVersionUID = -2984380948400790392L;

    // 关联关系中 子表的 字段 属性
    private String columnOfSubTable;

    private String columnName;

    private String columnComments;

    private String dataType;

    private String dataLength;

    // 页面是否现在控制
    private String myDisplay;

    // 页面展示方式控制
    private String myView;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComments() {
        return columnComments;
    }

    public void setColumnComments(String columnComments) {
        this.columnComments = columnComments;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getMyDisplay() {
        return myDisplay;
    }

    public void setMyDisplay(String myDisplay) {
        this.myDisplay = myDisplay;
    }

    public String getMyView() {
        return myView;
    }

    public void setMyView(String myView) {
        this.myView = myView;
    }

    public String getColumnOfSubTable() {
        return columnOfSubTable;
    }

    public void setColumnOfSubTable(String columnOfSubTable) {
        this.columnOfSubTable = columnOfSubTable;
    }

}
