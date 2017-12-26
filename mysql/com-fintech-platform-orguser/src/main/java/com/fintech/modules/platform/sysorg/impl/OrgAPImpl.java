package com.fintech.modules.platform.sysorg.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.OrgInfo;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restclient.http.RestClientConfig;
import com.fintech.platform.tools.common.BeanUtil;

@SuppressWarnings("all")
public class OrgAPImpl implements OrgAPI{
	
	private static final Logger logger = LoggerFactory.getLogger(OrgAPImpl.class);
	private String jyptAppId = "ptpt"; // rest服务appId
	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);// rest服务地址
	
	//@Autowired
	private static OrgAPImplHandler handler;
	
	public OrgAPImplHandler instandHandler(){
		if(this.handler==null){
			this.handler = new OrgAPImplHandler();
		}
		return this.handler;
	}

	@Override
	public UserInfo getUserInfoByLoginName(String loginName) {
		UserInfo user = new UserInfo();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		SysUserDTO dtoparam = new SysUserDTO();
		dtoparam.setLoginName(loginName);// 设置查询参数
		searchParams.put("dto", dtoparam);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		try {
			handler = this.instandHandler();
			user = handler.getUserInfoByLoginName(loginName);
		} catch (Exception e) {
			logger.error("执行方法queryListSysUser异常：", e);
		}
		return user;
	}

	/**
	 * Description: 通过orgids取用户列表 Create Date: 2014年10月21日下午5:36:04<br/>
	 * Author : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By : <br/>
	 * 
	 * @param orgId
	 *            机构id
	 * @return List<String> 用户Id的集合
	 */
	@Override
	public List<String> getUsersByOrgId(String... orgIds) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("orgIds",
				org.apache.commons.lang3.StringUtils.join(orgIds, ","));
		searchParams.put("dto", new SysUserDTO());
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		List<String> result = null;
		try {
			handler = this.instandHandler();
			result = handler.getUsersByOrgId(orgIds);
		} catch (Exception e) {
			logger.error("执行方法queryListSysUser异常：", e);
		}
		return result;
	}

	/**
	 * Description: 员工查询接口，能够返回所有财富中心员工的姓名、性别、EMAIL、员工编号、部门信息、职位等 Create Date:
	 * 2014年10月22日上午10:07:51<br/>
	 * Author : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By : <br/>
	 * 
	 * @param userId
	 */
	@Override
	public UserInfo getUserInfoDetail(String userId) {
		UserInfo userInfo = null;
		try {
			handler = this.instandHandler();
			userInfo = handler.getUserInfoDetail(userId);
		} catch (Exception e) {
			logger.error("执行queryOneDTO异常：", e);
		}
		return userInfo;
	}

	/**
	 * Description: 查询单个机构信息 Create Date: 2014年10月30日下午5:24:25<br/>
	 * Author : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By : <br/>
	 * 
	 * @param orgId
	 * @return
	 */
	@Override
	public OrgInfo getOrgInfo(String orgId) {
		OrgInfo orgInfo = null;
		try {
			/*String url = jyptURL + "/api/platform/SysOrgRest/get/v1/" + orgId;
			ResponseMsg<SysOrgDTO> responseMsg = RestClient.doGet(jyptAppId,
					url, new TypeReference<ResponseMsg<SysOrgDTO>>() {
					});
			SysOrgDTO dto = responseMsg.getResponseBody();
			if (dto != null) {
				orgInfo = new OrgInfo();
				BeanUtils.copyProperties(orgInfo, dto);
			}*/
			handler = this.instandHandler();
			orgInfo = handler.getOrgInfo(orgId);
		} catch (Exception e) {
			logger.error("执行queryOneDTO异常：", e);
		}
		return orgInfo;
	}

	/**Description: 输入参数为机构和角色的组合查询条件，输出为用户列表,查询分页信息
	 * Create Date: 2015年04月03日上午15:20:15<br/>
	 * Author     : za <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param pageSize
	 * @param currentPage
	 * @param orgId,roleId  
	 * @return List<Map<String, Object>> 返回的是数据的map形式的集合List，list中每个map对象的key与   SysUserDTO属性名称对应（），且map中还包含两个特殊属性totalCount ，totalPage  作分页之用
	 * {id=8, userName=8, userNo=8, loginName=8, password=8, salt=8, mobile=8, email=8, userImage=8, sex=1, sexCN=男, 
	 * birthday=null, nationality=null, education=null, job=null, homeAddress=null, homeZipcode=null, homeTel=null, 
	 * officeTel=null, officeAddress=null, orderBy=1, validateState=1, validateStateCN=有效, isLocked=null, 
	 * isLockedCN=null, version=null, sysOrgUserDTOs=[], 
	 * totalCount=8,
	 * totalPage=1}
	 * 
	 */
	@Override
	public List<Map<String,Object>> queryUserByRolePage(Integer pageSize,
			Integer currentPage, Map<String, Object> param){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try{
			Map<String, Object> searchParams = new HashMap<String, Object>();
			// 组织机构Id
			searchParams.put("orgId", param.get("orgId"));
			// 角色Id
			searchParams.put("roleId", param.get("roleId"));
			String url = null;
			QueryReqBean params = new QueryReqBean();
			//url = jyptURL + "/api/platform/SysUserRest/searchUserByRolePage/v1";
			PageParameter pageInfo = new PageParameter();
			pageInfo.setPageSize(pageSize);
			pageInfo.setCurrentPage(currentPage);
			params.setPageParameter(pageInfo);
			params.setSearchParams(searchParams);
			
			/*ResponseMsg<QueryRespBean<SysUserDTO>> responseMsg = RestClient
					.doPost(jyptAppId,
							url,
							params,
							new TypeReference<ResponseMsg<QueryRespBean<SysUserDTO>>>() {
							});
			List<SysUserDTO> list = responseMsg.getResponseBody().getResult();*/
			handler = this.instandHandler();
			List<SysUserDTO> list = handler.queryUserByRolePage(params);
			for (SysUserDTO sysUserDTO : list) {
				Map<String, Object> dtoMap = BeanUtil.transBean2Map(sysUserDTO);
				result.add(dtoMap);
				if (!(pageSize==null || pageSize == 0)) {
					/*dtoMap.put("totalCount", responseMsg.getResponseBody()
							.getPageParameter().getTotalCount());
					dtoMap.put("totalPage", responseMsg.getResponseBody()
							.getPageParameter().getTotalPage());
					dtoMap.put("totalRows", responseMsg.getResponseBody()
							.getPageParameter().getTotalPage());*/
					dtoMap.put("totalCount", pageInfo.getTotalCount());
					dtoMap.put("totalPage",pageInfo.getTotalPage());
					dtoMap.put("totalRows", pageInfo.getTotalPage());
				}
			}
		 }catch (Exception e) {
			logger.error("执行方法queryUserByRoleIdPage异常：", e);
		}
		 return result;
	}
	@Override
	public List<Map<String, Object>> queryUserListPage(Integer pageSize,
			Integer currentPage, Map<String, Object> param) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> searchParams = new HashMap<String, Object>();

			// orgId
			searchParams.put("orgId", param.get("orgId"));
			SysUserDTO dtoParam = new SysUserDTO();// 可能将param中的参数扩展到 dtoParam中
			BeanUtils.populate(dtoParam, param);//
			dtoParam.setValidateState("1");//默认设置查询有效的
			
			//rolegroupIds
			searchParams.put("groupIds", param.get("groupIds"));
			searchParams.put("groupCodes", param.get("groupCodes"));

			String url = null;
			PageParameter pageInfo = null;
			QueryReqBean params = new QueryReqBean();
			searchParams.put("dto", dtoParam);// 参数
			params.setSearchParams(searchParams);
			handler = this.instandHandler();
			List<SysUserDTO> list = null;
			if (pageSize==null || pageSize == 0) {
				//url = jyptURL + "/api/platform/SysUserRest/search/v1";
				list = handler.queryUserList(params);
			} else{
				//url = jyptURL + "/api/platform/SysUserRest/searchByPage/v1";
				list = handler.queryUserListPage(params);
				pageInfo = new PageParameter();
				pageInfo.setPageSize(pageSize);
				pageInfo.setCurrentPage(currentPage);
				params.setPageParameter(pageInfo);
			}
			
			/*ResponseMsg<QueryRespBean<SysUserDTO>> responseMsg = RestClient
					.doPost(jyptAppId,
							url,
							params,
							new TypeReference<ResponseMsg<QueryRespBean<SysUserDTO>>>() {
							});*/
			//List<SysUserDTO> list = responseMsg.getResponseBody().getResult();
			for (SysUserDTO sysUserDTO : list) {
				Map<String, Object> dtoMap = BeanUtil.transBean2Map(sysUserDTO);
				result.add(dtoMap);
				if (!(pageSize==null || pageSize == 0)) {
					/*dtoMap.put("totalCount", responseMsg.getResponseBody()
							.getPageParameter().getTotalCount());
					dtoMap.put("totalPage", responseMsg.getResponseBody()
							.getPageParameter().getTotalPage());
					dtoMap.put("totalRows", responseMsg.getResponseBody()
							.getPageParameter().getTotalPage());*/
					dtoMap.put("totalCount", pageInfo.getTotalCount());
					dtoMap.put("totalPage",pageInfo.getTotalPage());
					dtoMap.put("totalRows", pageInfo.getTotalPage());
				}
			}
		} catch (Exception e) {
			logger.error("执行方法queryListSysUser异常：", e);
		}
		return result;
	}

	@Override
	public <T> List<T> mappingOrgUser(List<T> datas,
			Map<String, String> userInfo, Map<String, String> orgInfo) {
		if (isNotNull(userInfo) || isNotNull(orgInfo)) {
			Method getMethod = null;
			Method setMethod = null;
			for (T t : datas) {
				if (isNotNull(userInfo)) {
					Set<String> userKeys = userInfo.keySet();
					for (String str : userKeys) {
						try {
							getMethod = t.getClass().getDeclaredMethod(
									toMethodName(str, 1));
							Object id = getMethod.invoke(t);
							if(id==null)continue;
							setMethod = getDeclaredMethod(t,
									toMethodName(userInfo.get(str), 2),
									String.class);
							setMethod.invoke(t,
									getUserInfoDetail(id.toString())
											.getUserName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if (isNotNull(orgInfo)) {
					Set<String> orgKeys = orgInfo.keySet();
					for (String str : orgKeys) {
						try {
							getMethod = t.getClass().getDeclaredMethod(
									toMethodName(str, 1));
							Object id = getMethod.invoke(t);
							if(id==null)continue;
							setMethod = getDeclaredMethod(t,
									toMethodName(orgInfo.get(str), 2),
									String.class);
							setMethod.invoke(t, getOrgInfo(id.toString())
									.getOrgName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return datas;
	}

	private boolean isNotNull(Map<String, String> userInfo) {
		if (userInfo != null && userInfo.size() > 0)
			return true;
		return false;
	}

	private String toMethodName(String name, int type) {
		StringBuilder sb = new StringBuilder();
		if (type == 1) {
			sb.append("get");
		} else if (type == 2) {
			sb.append("set");
		}
		return sb.append(Character.toUpperCase(name.charAt(0)))
				.append(name.substring(1)).toString();
	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredMethod
	 * 
	 * @param object
	 *            : 子类对象
	 * @param methodName
	 *            : 父类中的方法名
	 * @param parameterTypes
	 *            : 父类中的方法参数类型
	 * @return 父类中的方法对象
	 */

	public static Method getDeclaredMethod(Object object, String methodName,
			Class<?>... parameterTypes) {
		Method method = null;

		for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
				return method;
			} catch (Exception e) {
				// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会执行clazz =
				// clazz.getSuperclass(),最后就不会进入到父类中了
			}
		}
		return null;
	}
	
	/**
	 * 通过用户id查询用户所属的营业部或部门
	 * @param userId
	 * @return
	 */
	public String searchOrgByUserId(String userId) {
		instandHandler();
		String orgId =null ;
		try {
		    orgId = handler.queryDepartmentByUserId(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return orgId;
		}
	}
}
