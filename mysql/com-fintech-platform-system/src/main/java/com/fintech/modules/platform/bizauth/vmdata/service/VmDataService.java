package com.fintech.modules.platform.bizauth.vmdata.service;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @classname: VmDataService
 * @description: 催收数据权限
 */
public interface VmDataService {

    /**
     * @title: searchVmDataPriv
     * @author
     * @description: 获取登录人数据权限
     * @date 2015年1月27日 上午11:05:33
     * @param param
     * @return
     * @throws
     */
    public List<Map> searchVmDataPriv(Map<String, Object> param);

    /**
     * @title: searchVmRegisterInfo
     * @author
     * @description: 获取虚拟树信息得到该系统数据权限表
     * @date 2015年1月27日 下午1:13:01
     * @param param
     * @return
     * @throws
     */
    public List<Map> searchVmRegisterInfo(Map<String, Object> param);

    /**
     * @throws Exception
     * @title: getPrimaryVmData
     * @author
     * @description: 通用获取数据权限条件
     * @date 2015年1月27日 下午1:33:54
     * @param vmtreeCode
     * @param userId
     * @return
     * @throws
     */
    public List<Map> getPrimaryVmData(String vmtreeCode, long userId) throws Exception;

    /**
     * @title: getVmDataCondition
     * @author
     * @description: 通用获取人员权限条件
     * @date 2015年1月27日 下午1:52:38
     * @param vmtreeCode
     * @param userId
     * @return
     * @throws Exception
     * @throws
     */
    public String getVmDataCondition(String vmtreeCode, long userId, String alias) throws Exception;

    /**
     * @title: getGeneralVmDataUserCondition
     * @author
     * @description:通用获取查询人员数据权限条件
     * @date 2015年1月27日 下午1:52:41
     * @param vmtreeCode
     * @param userId
     * @return
     * @throws Exception
     * @throws
     */
    public String getGeneralVmDataUserCondition(String vmtreeCode, long userId, String alias) throws Exception;

    /**
     * @title: getOptimizationVmDataUserCondition
     * @author
     * @description: 用于union优化sql，数据权限的一个维度，人员->人员
     * @date 2015年1月28日 下午12:33:13
     * @param vmtreeCode
     * @param userId
     * @param alias
     * @return
     * @throws Exception
     * @throws
     */
    public String getOptimizationVmDataUserCondition(String vmtreeCode, long userId, String alias) throws Exception;

    /**
     * @title: getOptimizationVmDataOrgCondition
     * @author
     * @description: 用于union优化sql，数据权限的一个维度，人员->组织
     * @date 2015年1月28日 下午12:33:16
     * @param vmtreeCode
     * @param userId
     * @param alias
     * @return
     * @throws Exception
     * @throws
     */
    public String getOptimizationVmDataOrgCondition(String vmtreeCode, long userId, String alias) throws Exception;

    /**
     * @title: getHigherVmDataUserCondition
     * @author
     * @description: 获取数据权限上级人员条件
     * @date 2015年2月6日 上午10:53:34
     * @param vmtreeCode
     * @param userId
     * @param alias
     * @return
     * @throws Exception
     * @throws
     */
    public String getHigherVmDataUserCondition(String vmtreeCode, long userId, String alias) throws Exception;

    /**
     * @title: getHigherVmDataOrgCondition
     * @author
     * @description: 获取数据权限上级人员条件
     * @date 2015年2月6日 上午10:53:38
     * @param vmtreeCode
     * @param userId
     * @param alias
     * @return
     * @throws Exception
     * @throws
     */
    public String getHigherVmDataOrgCondition(String vmtreeCode, long userId, long orgId, String alias)
            throws Exception;
    /**
     * @author
     * @description: 组装 userId最新数据权限信息
     * @date 2015年9月21日 下午1:40:12
     * @param userId如：10006495
     * @param orgType 如：LOAN、 CS
     * @param truncateType 是否需要情况表空间 Y、 N
     * @return
     * @throws Exception
     */
    public boolean makeupLastVmdataPrivPart(String userId,String orgType,String truncateType) throws Exception;

}
