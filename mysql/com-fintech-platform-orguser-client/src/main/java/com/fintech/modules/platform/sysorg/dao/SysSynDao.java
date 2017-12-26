package com.fintech.modules.platform.sysorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysorg.dto.SysSynDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
@MyBatisRepository
public interface SysSynDao {
    
    /**Description: 
     * Create Date: 2015年1月26日下午7:28:26<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param searchParams
     * @return
     */
    public List<SysSynDTO> searchSysSynByPaging(Map<String, Object> searchParams) ;
    
    
    /**
     * 
     * 
     * 查询不分页的所有版本号
     * @param searchParams
     * @return
     */
    public List<SysSynDTO> searchAutoSysSyn() ;
    
    
    /**Description: 
     * Create Date: 2015年1月30日下午3:38:34<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param searchParams
     * @return
     */
    public List<SysSynDTO> searchSysSyn(Map<String, Object> searchParams) ;
    
    
    /**Description: 
     * Create Date: 2015年1月30日下午6:52:18<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     */
    public void updateSynType(Map<String, Object> paramMap);
    
    
    public void updateUserSyn(Map<String, Object> paramMap);
    
    public void updateOrgUserSyn(Map<String, Object> paramMap);
    
    public void updatePositionSyn(Map<String, Object> paramMap);
    
    public void updateOrgSyn(Map<String, Object> paramMap);
    
}
