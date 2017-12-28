package com.jy.platform.api.ldap;

/**
 * 提供添加,修改与删除接口与openldap同步
 * @author luoyr
 */
public interface LdapUserAPI {
	/**
	 * 创建用户
	 * @param vo orgId所属机构ID为null,则表达所属根节点,登录用户名全局唯一 
	 * @throws Exception
	 */
	public void createUser(LdapUserVo vo) throws Exception;
	/**
	 * 修改用户,用户所属机构修改调用用户迁移接口
	 * @param vo orgId所属机构ID为null,不需要设值
	 * @throws Exception
	 */
	public void updateUser(LdapUserVo vo) throws Exception;
	/**
	 * 创建或修改用户,当openldap根据用户id查询无传入用户信息时,会创建该用户信息;反之则会修改
	 * @param vo
	 * @throws Exception
	 */
	public void createOrUpdateUser(LdapUserVo vo)throws Exception;
	
	/**
	 * 删除用户
	 * @param userid 用户id
	 * @throws Exception
	 */
	public void deleteUserById(String userid) throws Exception;
	
	/**
	 * 迁移用户
	 * @param userid 用户id 非空
	 * @param destinationOrgId 为null表示迁移到根节点
	 * @throws Exception
	 */
	public void trasferUser(String userid,String destinationOrgId) throws Exception;
	/**
	 * 修改密码 返回false修改密码失败
	 * 4 其它修改失败异常
	 * 2 密码不能为空
	 * 1 密码与历史密码相同
	 * 0 成功
	 * @param loginName 登录用户名全局唯一 
	 * @param newPwd
	 * @throws Exception
	 */
	public int changePwd(String loginName,String newPwd);
	
}
