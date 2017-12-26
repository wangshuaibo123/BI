package com.fintech.modules.genCode.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @classname: ProductCodeDTO
 * @description: TODO(这里用一句话描述这个类的作用)
 */
public class ProductCodeDTO implements Serializable {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1004077695301677258L;
    //定义rest 
    private String restClassName;
    public String getRestClassName() {
		return restClassName;
	}

	public void setRestClassName(String restClassName) {
		this.restClassName = restClassName;
	}

	public String getRestPackageName() {
		return restPackageName;
	}

	public void setRestPackageName(String restPackageName) {
		this.restPackageName = restPackageName;
	}

	private String restPackageName;

    // 是否是子表，如果是子表的话则 需要设置与主表的关联关系
    private String tableIsSub;

    // 关联关系中的主表名称
    private String tableMainName;

    // 关联关系中 主表的管理 字段 属性
    private String columnOfMainTable;

    // 关联关系中 子表的 字段 属性 CUST_ID
    private String columnOfSubTable;

    // 关联关系中 子表dto的 字段 属性 custId
    private String proOfSubTable;

    // 如果表属性是主表时 ，用来控制是否关联子表
    private String relationSub;

    private String addSubJspName;

    private String updateSubJspName;

    // 如:USERINFO
    private String tableName;

    // 如：RoleDTO
    private String dtoClassName;

    // 如：com.platform.um.role.dto
    private String dtoPackageName;

    // 如：RoleServiceImpl
    private String serviceClassName;

    // 如：com.platform.um.role.service.impl
    private String servicePackageName;

    private String mapperClassName;

    private String mapperPackageName;

    // 如：IRoleService
    private String iServiceClassName;

    // 如：com.platform.um.role.service
    private String iServicePackageName;

    // 如：RoleAction
    private String actionClassName;

    // 如：com.platform.um.role.action
    private String actionPackageName;

    // 如：RoleProvider
    private String providerClassName;

    // 如：com.platform.um.role.provider
    private String providerPackageName;

    // 表字段属性信息列表
    private List columnsList;

    // 生成的文件存储位置：
    private String targetDir;

    // 作者
    private String author;

    // 表的备注信息 如果“角色信息”
    private String tableComments;

    // jsp 存放的目录
    private String jspTargetDir;
    
    private String jsTargetDir;

    public String getJsTargetDir() {
		return jsTargetDir;
	}

	public void setJsTargetDir(String jsTargetDir) {
		this.jsTargetDir = jsTargetDir;
	}

	// jsp 的目录前缀，如：biz
    private String jspPrefix;

    // jsp 修改页面的名称 如：updateRole
    private String updateJspName;

    // jsp 新增页面的名称 如：addRole
    private String addJspName;

    // jsp 查询页面的名称 如：queryRole
    private String queryJspName;

    private String viewJspName;

    private String dwrName;

    private String jsName;

    private String jsTargetPath;

    // 生成代码的当前时间
    private String curtDate;

    // 格式化后的名称 RoleInfo
    private String formated_tab_name;

    // 格式化后的名称 role_info
    private String lower_tab_name;

    // 实体包前缀如：com.platform.um
    private String packageNamePrefix;

    // ibatis sql 文件名称如：role_sql
    private String sqlFileName;

    // 模块名称 如 userinfo roleInfo
    private String modelName;
    
    private String templatePath;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDtoClassName() {
        return dtoClassName;
    }

    public void setDtoClassName(String dtoClassName) {
        this.dtoClassName = dtoClassName;
    }

    public String getDtoPackageName() {
        return dtoPackageName;
    }

    public void setDtoPackageName(String dtoPackageName) {
        this.dtoPackageName = dtoPackageName;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public String getIServiceClassName() {
        return iServiceClassName;
    }

    public void setIServiceClassName(String serviceClassName) {
        iServiceClassName = serviceClassName;
    }

    public String getIServicePackageName() {
        return iServicePackageName;
    }

    public void setIServicePackageName(String servicePackageName) {
        iServicePackageName = servicePackageName;
    }

    public String getActionClassName() {
        return actionClassName;
    }

    public void setActionClassName(String actionClassName) {
        this.actionClassName = actionClassName;
    }

    public String getActionPackageName() {
        return actionPackageName;
    }

    public void setActionPackageName(String actionPackageName) {
        this.actionPackageName = actionPackageName;
    }

    public String getProviderClassName() {
        return providerClassName;
    }

    public void setProviderClassName(String providerClassName) {
        this.providerClassName = providerClassName;
    }

    public String getProviderPackageName() {
        return providerPackageName;
    }

    public void setProviderPackageName(String providerPackageName) {
        this.providerPackageName = providerPackageName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List columnsList) {
        this.columnsList = columnsList;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public String getTableComments() {
        return tableComments;
    }

    public void setTableComments(String tableComments) {
        this.tableComments = tableComments;
    }

    public String getJspTargetDir() {
        return jspTargetDir;
    }

    public void setJspTargetDir(String jspTargetDir) {
        this.jspTargetDir = jspTargetDir;
    }

    public String getCurtDate() {
        return curtDate;
    }

    public void setCurtDate(String curtDate) {
        this.curtDate = curtDate;
    }

    public String getFormated_tab_name() {
        return formated_tab_name;
    }

    public void setFormated_tab_name(String formated_tab_name) {
        this.formated_tab_name = formated_tab_name;
    }

    public String getLower_tab_name() {
        return lower_tab_name;
    }

    public void setLower_tab_name(String lower_tab_name) {
        this.lower_tab_name = lower_tab_name;
    }

    public String getPackageNamePrefix() {
        return packageNamePrefix;
    }

    public void setPackageNamePrefix(String packageNamePrefix) {
        this.packageNamePrefix = packageNamePrefix;
    }

    public String getSqlFileName() {
        return sqlFileName;
    }

    public void setSqlFileName(String sqlFileName) {
        this.sqlFileName = sqlFileName;
    }

    public String getJspPrefix() {
        return jspPrefix;
    }

    public void setJspPrefix(String jspPrefix) {
        this.jspPrefix = jspPrefix;
    }

    public String getUpdateJspName() {
        return updateJspName;
    }

    public void setUpdateJspName(String updateJspName) {
        this.updateJspName = updateJspName;
    }

    public String getAddJspName() {
        return addJspName;
    }

    public void setAddJspName(String addJspName) {
        this.addJspName = addJspName;
    }

    public String getQueryJspName() {
        return queryJspName;
    }

    public void setQueryJspName(String queryJspName) {
        this.queryJspName = queryJspName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getViewJspName() {
        return viewJspName;
    }

    public void setViewJspName(String viewJspName) {
        this.viewJspName = viewJspName;
    }

    public String getDwrName() {
        return dwrName;
    }

    public void setDwrName(String dwrName) {
        this.dwrName = dwrName;
    }

    public String getJsName() {
        return jsName;
    }

    public void setJsName(String jsName) {
        this.jsName = jsName;
    }

    public String getJsTargetPath() {
        return jsTargetPath;
    }

    public void setJsTargetPath(String jsTargetPath) {
        this.jsTargetPath = jsTargetPath;
    }

    public String getTableIsSub() {
        return tableIsSub;
    }

    public void setTableIsSub(String tableIsSub) {
        this.tableIsSub = tableIsSub;
    }

    public String getTableMainName() {
        return tableMainName;
    }

    public void setTableMainName(String tableMainName) {
        this.tableMainName = tableMainName;
    }

    public String getColumnOfMainTable() {
        return columnOfMainTable;
    }

    public void setColumnOfMainTable(String columnOfMainTable) {
        this.columnOfMainTable = columnOfMainTable;
    }

    public String getColumnOfSubTable() {
        return columnOfSubTable;
    }

    public void setColumnOfSubTable(String columnOfSubTable) {
        this.columnOfSubTable = columnOfSubTable;
    }

    public String getRelationSub() {
        return relationSub;
    }

    public void setRelationSub(String relationSub) {
        this.relationSub = relationSub;
    }

    public String getAddSubJspName() {
        return addSubJspName;
    }

    public void setAddSubJspName(String addSubJspName) {
        this.addSubJspName = addSubJspName;
    }

    public String getUpdateSubJspName() {
        return updateSubJspName;
    }

    public void setUpdateSubJspName(String updateSubJspName) {
        this.updateSubJspName = updateSubJspName;
    }

    public String getProOfSubTable() {
        return proOfSubTable;
    }

    public void setProOfSubTable(String proOfSubTable) {
        this.proOfSubTable = proOfSubTable;
    }

    public String getMapperClassName() {
        return mapperClassName;
    }

    public void setMapperClassName(String mapperClassName) {
        this.mapperClassName = mapperClassName;
    }

    public String getMapperPackageName() {
        return mapperPackageName;
    }

    public void setMapperPackageName(String mapperPackageName) {
        this.mapperPackageName = mapperPackageName;
    }

    public String getiServiceClassName() {
        return iServiceClassName;
    }

    public void setiServiceClassName(String iServiceClassName) {
        this.iServiceClassName = iServiceClassName;
    }

    public String getiServicePackageName() {
        return iServicePackageName;
    }

    public void setiServicePackageName(String iServicePackageName) {
        this.iServicePackageName = iServicePackageName;
    }
    
    public String getTemplatePath() {
        return templatePath;
    }
    
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

}
