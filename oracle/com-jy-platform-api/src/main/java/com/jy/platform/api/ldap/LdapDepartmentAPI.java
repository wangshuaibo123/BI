package com.jy.platform.api.ldap;


/**
 * 提供新建,修改,删除openldap接口
 * @author luoyr
 *
 */
public interface LdapDepartmentAPI {
	
	/**
	 * 创建机构
	 * eg:
	 * LdapDepartmentVo vo = new LdapDepartmentVo();
			vo.setOrgId("100002");
			vo.setOrgName("机构");
			vo.setAddress("朝阳门内大街");
			vo.setPhone("01058012328"); 
			vo.setParentId("10000");
			ldapDepartmentAPI.createOrg(vo);
	 * @param vo parentId为根节点则为null
	 * @throws Exception
	 */
	public void createOrg(LdapDepartmentVo vo) throws Exception;
	/**
	 * 修改机构信息 ,不允许修改所属上级机构信息,若修改所属上级需要调用迁移机构接口
	 * @param vo 
	 * @throws Exception
	 */
	public void updateOrg(LdapDepartmentVo vo) throws Exception;
	/**
	 * 删除叶子节点的机构数据,机构下有用户或机构下仍然有子机构则删除失败
	 * @param orgId 非空
	 * @throws Exception
	 */
	public void deleteOrg(String orgId) throws Exception;
	/**
	 * 迁移机构
	 * <pre>
	 * eg1 叶子机构迁移,即将迁移机构无子节点(包括子机构和用户)
	 * ldapDepartmentAPI.trasferOrg("100003", "20000");
	 * eg2 叶子机构,但此机构下有用户,进行编程式事务控制(多个用户则循环调用)
	 * 
		* 机构下有用户,先删除用户,再转移机构,最后添加用户
		* 原用户信息 
		* LdapUserVo uvo = new LdapUserVo();
				uvo.setUserId("8002");
				uvo.setName("员工");
				uvo.setLoginname("example");
				uvo.setPassword("123456");
				uvo.setCode("8002");
				uvo.setOrgId("100002");
		* 
	    TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			//删除用户
			userAPIImpl.deleteUserById("8002");
			//转移机构
			ldapDepartmentAPI.trasferOrg("100002", "10000");
			//fail("exception");
			//添加用户
			LdapUserVo uvo = new LdapUserVo();
			uvo.setUserId("8002");
			uvo.setName("员工");
			uvo.setLoginname("example");
			uvo.setPassword("123456");
			uvo.setCode("8002");
			uvo.setOrgId("100002");
			userAPIImpl.createUser(uvo);
			transactionManager.commit(status);
	    } catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
		}
		eg3 非叶子机构,机构节点下有子机构,各节点机构又有用户数据		
		删除机构下所有用户,删除机构下所有子机构,并且根据从下往上的顺序删除,然后根据上往下顺序重新添加,不调用迁移接口
		<pre>
	 * @param orgId 非空
	 * @param destinationParentOrgId 可以为null,则表示迁移到根节点
	 * @throws Exception
	 */
	public void trasferOrg(String orgId,String destinationParentOrgId) throws Exception;
	
	

}
