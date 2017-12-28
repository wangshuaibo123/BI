package com.jy.platform.api.org;

import java.util.List;
import java.util.Map;

public interface OrgAPI {
	
	/**Description: 员工查询接口，能够返回所有财富中心员工的姓名、性别、员工编号、部门信息、职位等
	 * Create Date: 2014年10月22日上午10:07:51<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param userId
	 */
//	public UserInfo getCurrentUserInfo();
	
	public UserInfo getUserInfoByLoginName(String loginName);
	
	/**Description: 通过orgid取用户列表
	 * Create Date: 2014年10月21日下午5:36:04<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param orgId 机构id
	 * @return List<String>   用户Id的集合
	 */
	public List<String> getUsersByOrgId(String... orgids);
	
	/**Description: 员工查询接口，能够返回所有财富中心员工的姓名、性别、员工编号、部门信息、职位等
	 * Create Date: 2014年10月22日上午10:07:51<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param userId
	 */
	public UserInfo getUserInfoDetail(String userId);
	
	/**Description: 查询单个机构信息
	 * Create Date: 2014年10月30日下午5:24:25<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param orgId
	 * @return
	 */
	public OrgInfo getOrgInfo(String orgId);
	
	/**Description: 查询分页信息
	 * Create Date: 2014年11月4日上午10:28:42<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param pageSize
	 * @param currentPage
	 * @return List<Map<String, Object>> 返回的是数据的map形式的集合List，list中每个map对象的key与   SysUserDTO属性名称对应（），且map中还包含两个特殊属性totalCount ，totalPage  作分页之用
	 * {id=8, userName=8, userNo=8, loginName=8, password=8, salt=8, mobile=8, email=8, userImage=8, sex=1, sexCN=男, 
	 * birthday=null, nationality=null, education=null, job=null, homeAddress=null, homeZipcode=null, homeTel=null, 
	 * officeTel=null, officeAddress=null, orderBy=1, validateState=1, validateStateCN=有效, isLocked=null, 
	 * isLockedCN=null, version=null, sysOrgUserDTOs=[], 
	 * totalCount=8,
	 * totalPage=1}
	 * 
	 * 新增角色组 查询人员信息  参数在param中   groupIds 
	 * 新增角色组 查询人员信息  参数在param中   groupCodes 
	 * 新增机构 查询人员信息  参数在param中   orgId
	 * 
	 */
	public List<Map<String, Object>> queryUserListPage(Integer pageSize , Integer currentPage, Map<String, Object> param );
	
	/**
	 * 此方法要对业务的数据List进行补充，将数据中的UserId、orgId等用户、组织机构的ID转换成NAME返回去
	 * @param datas	：	需要扩充的集合
	 * @param userInfo	：  	需要转换的用户字段，Map的key为Id，value为转换为的Name的字段	如：("userId","userName")
	 * @param orgInfo	：  	需要转换的组织机构字段，Map的key为Id，value为转换为的Name的字段	如：("orgId","orgName")
	 * @return
	 */
	public <T> List<T> mappingOrgUser(List<T> datas,Map<String,String> userInfo,Map<String,String> orgInfo);
	
	public List<Map<String,Object>> queryUserByRolePage(Integer pageSize,
			Integer currentPage, Map<String, Object> param);
	/**
	 * 
	 * 通过用户ID查询用户所在的营业部ID
	 */

	public String searchOrgByUserId(String userId);
}
