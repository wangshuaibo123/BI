/**
 *@Copyright:Copyright (c) 2014 - 2015
 *@Company:捷越
 */
package com.fintech.modules.platform.testtable1.dto;

import com.fintech.platform.core.common.BaseDTO;


/**
 *@Title:
 *@Description:测试视图
 *@author
 *@Since:2014年12月22日
 *@Version:1.1.0
 */
public class SysDataPrvDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6278696400577202041L;
    
    /**表名称**/
    private java.lang.String tableName;
    
    /**业务ID*/
    private java.lang.Long bizId;
    
    /**被授权用户**/
    private java.lang.Long createUserId;
    
    /**授权用户**/
    private java.lang.Long authUserId;

    public java.lang.String getTableName() {
        return tableName;
    }

    public void setTableName(java.lang.String tableName) {
        this.tableName = tableName;
    }

    public java.lang.Long getBizId() {
        return bizId;
    }

    public void setBizId(java.lang.Long bizId) {
        this.bizId = bizId;
    }

    public java.lang.Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(java.lang.Long createUserId) {
        this.createUserId = createUserId;
    }

    public java.lang.Long getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(java.lang.Long authUserId) {
        this.authUserId = authUserId;
    }
    
    

}
