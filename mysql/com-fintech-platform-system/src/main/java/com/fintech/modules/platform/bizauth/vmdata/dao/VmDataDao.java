package com.fintech.modules.platform.bizauth.vmdata.dao;

import java.util.List;
import java.util.Map;

import com.fintech.platform.core.mybatis.MyBatisRepository;

/**
 * @author
 * @classname: VmDataDao
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@MyBatisRepository
public interface VmDataDao {

    /**
     * @title: searchVmDataPriv
     * @author
     * @description:
     * @date 2015年1月27日 下午12:48:25
     * @param param
     * @return
     * @throws
     */
    public List<Map> searchVmDataPriv(Map<String, Object> param);

    /**
     * @title: searchVmRegisterInfo
     * @author
     * @description:
     * @date 2015年1月27日 下午1:12:40
     * @param param
     * @return
     * @throws
     */
    public List<Map> searchVmRegisterInfo(Map<String, Object> param);

	public void updateVmdataPrivPart(Map<String, String> param);

}
