package com.fintech.modules.platform.bizauth.vmdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.bizauth.vmdata.dao.VmDataDao;
import com.fintech.modules.platform.bizauth.vmdata.service.VmDataService;

/**
 * @author
 * @classname: VmDataServiceImpl
 * @description: 数据权限接口实现层
 */
@Service("com.fintech.modules.platform.bizauth.vmdata.service.impl.VmDataServiceImpl")
public class VmDataServiceImpl implements VmDataService {
    @Autowired
    private VmDataDao vmDao;
    private static final Logger logger = LoggerFactory.getLogger(VmDataServiceImpl.class);
    @Override
    public List<Map> searchVmDataPriv(Map<String, Object> param) {
        // TODO Auto-generated method stub
        return vmDao.searchVmDataPriv(param);
    }

    @Override
    public List<Map> searchVmRegisterInfo(Map<String, Object> param) {
        // TODO Auto-generated method stub
        return vmDao.searchVmRegisterInfo(param);
    }

    @Override
    public List<Map> getPrimaryVmData(String vmtreeCode, long userId) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        String tableName = vmtreeCode;
        /**
         * 根据编码获取虚拟树表名称
         * 
         * param.put("vmtreeCode", vmtreeCode); List<Map> list =
         * vmDao.searchVmRegisterInfo(param); if (list.size() > 0) { tableName =
         * (String) list.get(0).get("DATA_TAB_NAME"); }
         */
        /**
         * 根据当前登录人获取数据权限
         */
        param.put("userId", userId);
        param.put("tableName", tableName);
        List<Map> dataList = vmDao.searchVmDataPriv(param);

        System.out.println(dataList.get(0).get("OWNER_ID") + "," + dataList.get(0).get("ORG_ID"));
        return dataList;

    }

    @Override
    public String getVmDataCondition(String vmtreeCode, long userId, String alias) throws Exception {
        String tableName = vmtreeCode;
        /**
         * 根据编码获取虚拟树表名称
         * 
         * param.put("vmtreeCode", vmtreeCode); List<Map> list =
         * vmDao.searchVmRegisterInfo(param); if (list.size() > 0) { tableName =
         * (String) list.get(0).get("DATA_TAB_NAME"); }
         */
        String tableAlias = "t.";
        if (alias != null && !alias.equals("")) {
            tableAlias = alias + ".";
        }
        String condition = " and exists (select 1 from " + tableName + " vmdata where 1 = 1 and vmdata.USER_ID = "
                + userId + " and (vmdata.OWNER_ID = " + tableAlias + "OWNER_ID or vmdata.ORG_ID = " + tableAlias
                + "ORG_ID)) ";

        System.out.println(condition);
        return condition;
    }

    @Override
    public String getGeneralVmDataUserCondition(String vmtreeCode, long userId, String alias) throws Exception {
        String tableName = vmtreeCode;
        /**
         * 根据编码获取虚拟树表名称
         * 
         * param.put("vmtreeCode", vmtreeCode); List<Map> list =
         * vmDao.searchVmRegisterInfo(param); if (list.size() > 0) { tableName =
         * (String) list.get(0).get("DATA_TAB_NAME"); }
         */
        String tableAlias = "t.";
        if (alias != null && !alias.equals("")) {
            tableAlias = alias + ".";
        }
        String condition = " and exists (select 1 from " + tableName + " vmdata1 where 1 = 1 and vmdata1.USER_ID = "
                + userId + " and vmdata1.OWNER_ID = " + tableAlias + "USER_ID ) ";

        // String condition = " AND " + tableAlias +
        // "USER_ID IN (SELECT NVL(vmdata1.OWNER_ID, vmdata1.OWNER_ID) "
        // + "FROM " + tableName + " vmdata1 " + " WHERE vmdata1.USER_ID = " +
        // userId + ")";

        System.out.println(condition);
        return condition;
    }

    @Override
    public String getOptimizationVmDataUserCondition(String vmtreeCode, long userId, String alias) throws Exception {
        String tableName = vmtreeCode;
        /**
         * 根据编码获取虚拟树表名称
         * 
         * param.put("vmtreeCode", vmtreeCode); List<Map> list =
         * vmDao.searchVmRegisterInfo(param); if (list.size() > 0) { tableName =
         * (String) list.get(0).get("DATA_TAB_NAME"); }
         */
        String tableAlias = "t.";
        if (alias != null && !alias.equals("")) {
            tableAlias = alias + ".";
        }
        String condition = " and exists (select 1 from " + tableName + " vmdata1 where 1 = 1 and vmdata1.USER_ID = "
                + userId + " and vmdata1.OWNER_ID = " + tableAlias + "OWNER_ID ) ";

        // String condition = " AND " + tableAlias +
        // "OWNER_ID IN (SELECT NVL(vmdata1.OWNER_ID, vmdata1.OWNER_ID) "
        // + "FROM " + tableName + " vmdata1 " + " WHERE vmdata1.USER_ID = " +
        // userId + ")";

        System.out.println(condition);
        return condition;
    }

    @Override
    public String getOptimizationVmDataOrgCondition(String vmtreeCode, long userId, String alias) throws Exception {
        String tableName = vmtreeCode;
        /**
         * 根据编码获取虚拟树表名称
         * 
         * param.put("vmtreeCode", vmtreeCode); List<Map> list =
         * vmDao.searchVmRegisterInfo(param); if (list.size() > 0) { tableName =
         * (String) list.get(0).get("DATA_TAB_NAME"); }
         */
        String tableAlias = "t.";
        if (alias != null && !alias.equals("")) {
            tableAlias = alias + ".";
        }
        String condition = " and exists (select 1 from " + tableName + " vmdata2 where 1 = 1 and vmdata2.USER_ID = "
                + userId + " and  vmdata2.ORG_ID = " + tableAlias + "ORG_ID) ";

        // String condition = " AND " + tableAlias +
        // "ORG_ID IN (SELECT NVL(vmdata2.ORG_ID, vmdata2.ORG_ID) " + "FROM  "
        // + tableName + " vmdata2 " + " WHERE vmdata2.USER_ID = " + userId +
        // ")";

        System.out.println(condition);
        return condition;
    }

    @Override
    public String getHigherVmDataUserCondition(String vmtreeCode, long userId, String alias) throws Exception {
        String tableName = vmtreeCode;
        /**
         * 根据编码获取虚拟树表名称
         * 
         * param.put("vmtreeCode", vmtreeCode); List<Map> list =
         * vmDao.searchVmRegisterInfo(param); if (list.size() > 0) { tableName =
         * (String) list.get(0).get("DATA_TAB_NAME"); }
         */
        String tableAlias = "t.";
        if (alias != null && !alias.equals("")) {
            tableAlias = alias + ".";
        }
        String condition = " and " + tableAlias + "USER_ID != " + userId + " and exists (select 1 from " + tableName
                + " vmdata1 where 1 = 1 and vmdata1.OWNER_ID = " + userId + " and vmdata1.USER_ID = " + tableAlias
                + "USER_ID ) ";

        return condition;
    }

    @Override
    public String getHigherVmDataOrgCondition(String vmtreeCode, long userId, long orgId, String alias)
            throws Exception {
        String tableName = vmtreeCode;
        /**
         * 根据编码获取虚拟树表名称
         * 
         * param.put("vmtreeCode", vmtreeCode); List<Map> list =
         * vmDao.searchVmRegisterInfo(param); if (list.size() > 0) { tableName =
         * (String) list.get(0).get("DATA_TAB_NAME"); }
         */
        String tableAlias = "t.";
        if (alias != null && !alias.equals("")) {
            tableAlias = alias + ".";
        }
        String condition = " and " + tableAlias + "USER_ID != " + userId + " and exists (select 1 from " + tableName
                + " vmdata2 where 1 = 1 and vmdata2.ORG_ID = " + orgId + " and  vmdata2.USER_ID = " + tableAlias
                + "USER_ID) ";

        return condition;
    }
    
	public boolean makeupLastVmdataPrivPart(String userId,String orgType,String truncateType) throws Exception {
		boolean flag = true;
		Map<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("orgType", orgType);
		param.put("tabName", "VMDATA_PRIV_PART");
		if(StringUtils.isEmpty(truncateType)){
			truncateType = "N";
		}
		param.put("truncateType", truncateType);
				
		//判断userId的表分区是否存在select * from vmdata_priv_part partition(D2) ;
		//不存在 则创建alter table vmdata_priv_part add partition D1 values('1') ;
		//判断userId是否是最新的数据权限
		//如果不是 则按表分区 alter table vmdata_priv_part truncate partition D1;
		//insert 最新数据权限
		vmDao.updateVmdataPrivPart(param);
		return flag;
	}

}
