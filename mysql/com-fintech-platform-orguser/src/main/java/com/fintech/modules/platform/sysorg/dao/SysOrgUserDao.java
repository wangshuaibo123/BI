package com.fintech.modules.platform.sysorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysOrgUserDao
 * @description: 定义  SYS_ORG_USER 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysOrgUserDao {
    
    /**
     * @author
     * @description: 分页查询SYS_ORG_USER
     * @date 2014-10-15 10:26:28
     * @param searchParams
     * @return
     */
    public List<SysOrgUserDTO> searchSysOrgUserByPaging(Map<String, Object> searchParams) ;
    
	/**Description: 分页查询 SYS_ORG_USER列表	模糊查询
	 * 	parentIds 		父节点id
	 *  orgName 		机构名称
	 *  userName 		用户名称
	 *  positionName	岗位名称
	 * Create Date: 2014年12月3日下午3:26:10<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
    public List<SysOrgUserDTO> searchFuzzySysOrgUserByPaging(Map<String, Object> searchParams) ;
    
	/**Description: 查询 SYS_ORG_USER列表	模糊查询	（不分页）
	 * 	parentIds 		父节点id
	 *  orgName 		机构名称
	 *  userName 		用户名称
	 *  positionName	岗位名称
	 * Create Date: 2014年12月3日下午3:26:10<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgUserDTO> searchFuzzySysOrgUser( Map<String, Object> searchParams);
    
    /**
     * @author
     * @description:查询对象SYS_ORG_USER
     * @date 2014-10-15 10:26:28
     * @param searchParams
     * @return
     */
    public List<SysOrgUserDTO> searchSysOrgUser(Map<String,Object> searchParams);
    

    
    /**Description: 查询对象SYS_ORG_USER 包含冗余信息
     * Create Date: 2014年10月18日下午12:37:25<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param searchParams
     * @return
     */
    public List<SysOrgUserDTO> searchSysOrgUserInfo(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象SYS_ORG_USER
     * @date 2014-10-15 10:26:28
     * @param id
     * @return
     */
    public SysOrgUserDTO findSysOrgUserByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象SYS_ORG_USER
     * @date 2014-10-15 10:26:28
     * @param paramMap
     * @return
     */
    public int insertSysOrgUser(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象SYS_ORG_USER
     * @date 2014-10-15 10:26:28
     * @param paramMap
     */
    public void updateSysOrgUser(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除SYS_ORG_USER
     * @date 2014-10-15 10:26:28
     * @param ids
     * @return
     */ 
    public void deleteSysOrgUserByPrimaryKey(Map<String, Object> paramMap);

	
	/**Description: 按orgids删除机构与用户关联关系
	 * Create Date: 2014年10月18日下午3:40:50<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param paramMap
	 */
	public void deleteSysOrgByOrgIds(Map<String, Object> paramMap);
	
	/**Description: 按userids删除机构与用户关联关系
	 * Create Date: 2014年10月18日下午3:40:50<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param paramMap
	 */
	public void deleteSysOrgByUserIds(Map<String, Object> paramMap);
	
	/**Description: 按userid更新机构与用户关联表（假删除）
	 * Create Date: 2015年2月3日下午2:01:34<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param paramMap
	 */
	public void updateValidateSysOrgByUserIds(Map<String, Object> paramMap);
    
    
	/**Description: 按positionids删除机构与用户关联关系
	 * Create Date: 2014年10月18日下午3:40:50<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param paramMap
	 */
	public void deleteSysOrgByPositionIds(Map<String, Object> paramMap);
	/**Description: 按userid更新机构与用户关联表（真删除）
	 * Create Date: 2016年06月26日下午2:01:34<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param paramMap
	 */
	public void deleteSysOrgUserByUserId(Map<String, Object> paramMap);
	

	/**Description: 查询 SYS_ORG_USER列表	系统间同步用户机构关系使用模糊查询	（不分页）
	 * 	parentIds 		父节点id
	 *  orgName 		机构名称
	 *  userName 		用户名称
	 *  positionName	岗位名称
	 * Create Date: 2015年07月01日上午11:17:10<br/>
	 * Author     : xianwu.jiang <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgUserDTO> searchFuzzySysOrgUserSyn( Map<String, Object> searchParams);

	/**Description: 按userids删除机构与用户关联关系
	 * Create Date: 2014年10月18日下午3:40:50<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param paramMap
	 */
	public void deleteSysOrgUserByUserIdAndOrgId(Map<String, Object> paramMap);
}
