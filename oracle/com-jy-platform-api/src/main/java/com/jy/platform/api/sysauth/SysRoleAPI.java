package com.jy.platform.api.sysauth;

import java.util.List;
import java.util.Map;

/**
 * 
 * @title:
 * @author fangchao
 * @description:
 * @date 2014年11月11日 下午1:40:45
 * @param
 * @throws
 */
public interface SysRoleAPI {

	/**
	 *  查询系统中所有角色信息（如果平台能区分工作流角色，则仅工作流角色）。param参数中去设置对应SysRoleDTO的过滤条件     同  SysRoleDTO 中的属性名称 如：roleType=‘1’,另外参数  groupIds是按角色组来过滤角色
	 * @param pageSize
	 * @param currentPage
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryRoleList(Integer pageSize , Integer currentPage, Map<String, Object> param);
	
	/**
	 *  查询系统中所有角色 组信息 param参数中去设置对应SysRoleDTO的过滤条件     同  SysRoleGroupDTO 中的属性名称 如：roleGroupName=‘1’
	 * @param pageSize
	 * @param currentPage
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryRoleGroupList(Integer pageSize , Integer currentPage, Map<String, Object> param);
	
	/**
	 * 查询角色下的用户	通过角色信息列表查询其名下的人员信息。 param 参数中去设置对应SysRoleUserDTO的过滤条件     同  SysRoleUserDTO 中的属性名称 如：roleId=‘1’
	 * @param pageSize
	 * @param currentPage
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryUserList(Integer pageSize , Integer currentPage, Map<String, Object> param);
	/**
	 * 查询 用户Id 下所有角色信息
	 * @param pageSize
	 * @param currentPage
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getRoleByUserId(String userId);
	
	/**
	 * 查询用户id下所有角色组信息
	 * 
	 */
	public List<Map<String,Object>> getRoleGroupByUserId(String userId);
	
	
}
