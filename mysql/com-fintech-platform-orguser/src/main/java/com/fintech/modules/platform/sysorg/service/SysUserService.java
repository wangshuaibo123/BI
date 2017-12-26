package com.fintech.modules.platform.sysorg.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.sysorg.dao.SysOrgUserDao;
import com.fintech.modules.platform.sysorg.dao.SysUserDao;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.tools.pwdhash.AesPasswordHashMatcher;
import com.fintech.platform.tools.pwdhash.BasePasswordHashMatcher;
import com.fintech.platform.tools.pwdhash.Md5PasswordHashMatcher;
import com.fintech.platform.tools.pwdhash.Sha1PasswordHashMatcher;
import com.fintech.platform.tools.pwdhash.util.HashUtils;

/**
 * @classname: SysUserService
 * @description: 定义  系统用户表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysorg.service.SysUserService")
public class SysUserService implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static Md5PasswordHashMatcher passwordHashMatcher = null;
    
	@Autowired
	private SysUserDao dao;
	
	@Autowired
	private SysOrgUserDao sysOrgUserDao;
	
	 
	/**
     * @author
     * @description: 分页查询 系统用户表列表
     * @date 2014-10-15 10:25:49
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysUserDTO> searchSysUserByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysUserDTO> dataList =  dao.searchSysUserByPaging(searchParams);
		//取出用户机构岗位对应关系
		for(SysUserDTO d:dataList){
			Map<String, Object> params = new HashMap<String, Object>();
			SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
			sysOrgUserDTO.setUserId(d.getId());
			params.put("dto", sysOrgUserDTO);
			List<SysOrgUserDTO> sysOrgUserDTOs  = sysOrgUserDao.searchSysOrgUserInfo(params);
			if(sysOrgUserDTOs!=null && sysOrgUserDTOs.size()>0){
				d.setSysOrgUserDTOs(sysOrgUserDTOs);
				//设置中文名称攻查询页面显示
				String positionCN="",orgCN="";
				for(SysOrgUserDTO ou:sysOrgUserDTOs){
					positionCN += ou.getPositionName() + " ";
					orgCN += ou.getOrgName() + " ";
				}
				d.setPositionCN(positionCN);
				d.setOrgCN(orgCN);
			}
		}
		return dataList;
	}
	
	/**
     * @author
     * @description: 按条件查询系统用户表列表
     * @date 2014-10-15 10:25:49
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysUserDTO> searchSysUser(Map<String,Object> searchParams) throws Exception {
	    List<SysUserDTO> dataList = dao.searchSysUser(searchParams);
	    //取出用户机构岗位对应关系
	    for(SysUserDTO d:dataList){
			Map<String, Object> params = new HashMap<String, Object>();
			SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
			sysOrgUserDTO.setUserId(d.getId());
			params.put("dto", sysOrgUserDTO);
			List<SysOrgUserDTO> sysOrgUserDTOs  = sysOrgUserDao.searchSysOrgUserInfo(params);
			if(sysOrgUserDTOs!=null && sysOrgUserDTOs.size()>0){
				d.setSysOrgUserDTOs(sysOrgUserDTOs);
			}
		}
        return dataList;
    }
	/**
     * @author
     * @description: 查询系统用户表对象
     * @date 2014-10-15 10:25:49
     * @param id
     * @return
     * @throws
     */ 
	public SysUserDTO querySysUserByPrimaryKey(String id) throws Exception {
		
		SysUserDTO dto = dao.findSysUserByPrimaryKey(id);
		
		if(dto == null) dto = new SysUserDTO();
		
		return dto;
		
	}
	
	/**Description: 查询系统用户表对象(包含用户机构对应关系)
	 * Create Date: 2014年10月18日下午12:15:34<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysUserDTO querySysUserFullByPrimaryKey(String id) throws Exception {
		SysUserDTO dto = dao.findSysUserByPrimaryKey(id);
		if(dto == null){
			dto = new SysUserDTO();
		}else{
			//也取出用户机构对应关系
			Map<String, Object> searchParams = new HashMap<String, Object>();
			SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
			sysOrgUserDTO.setUserId(Long.parseLong(id));
			searchParams.put("dto", sysOrgUserDTO);
			List<SysOrgUserDTO> sysOrgUserDTOs  = sysOrgUserDao.searchSysOrgUserInfo(searchParams);
			if(sysOrgUserDTOs!=null && sysOrgUserDTOs.size()>0){
				dto.setSysOrgUserDTOs(sysOrgUserDTOs);
			}
			for(SysOrgUserDTO sysOrgUser:sysOrgUserDTOs){
				if("1".equals (sysOrgUserDTO.getIsMainOrg())){//是主机构
					dto.setUserOrgId(sysOrgUser.getOrgId());
					dto.setOrgCN(sysOrgUser.getOrgName());
					dto.setPositionCN(sysOrgUser.getPositionName());
					break;
				}
			}
		}
		return dto;
	}

	/**
     * @title: insertSysUser
     * @author
     * @description: 新增 系统用户表对象
     * @date 2014-10-15 10:25:49
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysUser(SysUserDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		byte[] passWordhash = initPassWordHashMatcher().hash(dto.getPassword().getBytes());
		String hashPwd = HashUtils.base64EncodeStr(passWordhash);
		dto.setPassword(hashPwd);
		if(dto.getValidateState()==null || "".equals(dto.getValidateState()))
		{
			dto.setValidateState("1");
		}
		paramMap.put("dto", dto);
		int count = dao.insertSysUser(paramMap);
		SysUserDTO resultDto = (SysUserDTO) paramMap.get("dto");
		Long keyId = Long.parseLong(resultDto.getUserNo());
		
		//同时也新增岗位信息
		for(SysOrgUserDTO sysOrgUserDTO : dto.getSysOrgUserDTOs()){
			Map<String, Object> map = new HashMap<String, Object>();
			sysOrgUserDTO.setUserId(keyId);
			map.put("dto", sysOrgUserDTO);
			sysOrgUserDao.insertSysOrgUser(map);
		}
		return keyId;
	}
	/**
     * @title: updateSysUser
     * @author
     * @description: 修改 系统用户表对象
     * @date 2014-10-15 10:25:49
     * @param paramMap
     * @return
     * @throws
     */
	@Transactional(rollbackFor={Exception.class})
	public void updateSysUser(SysUserDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if("1".equals(dto.getIsChange())){
			byte[] passWordhash = initPassWordHashMatcher().hash(dto.getPassword().getBytes());
			String hashPwd = HashUtils.base64EncodeStr(passWordhash);
			dto.setPassword(hashPwd);
		}
		paramMap.put("dto", dto);
		Map<String, Object> pmap = new HashMap<String, Object>();
		String str = "";
		//同时也维护岗位信息
		for(SysOrgUserDTO sysOrgUserDTO : dto.getSysOrgUserDTOs()){
			Map<String, Object> map = new HashMap<String, Object>();
			/*if(dto.getValidateState().equals("1")){
				sysOrgUserDTO.setValidateState("1");
			}else{
				sysOrgUserDTO.setValidateState("0");
			}*/
			sysOrgUserDTO.setValidateState("1");
			map.put("dto", sysOrgUserDTO);
			sysOrgUserDTO.setUserId(dto.getId());
			if(sysOrgUserDTO.getId()!=null){
				str += sysOrgUserDTO.getId()+",";
				sysOrgUserDao.updateSysOrgUser(map);
			}else{
			    sysOrgUserDao.insertSysOrgUser(map);
			    str += sysOrgUserDTO.getId()+",";
			}
		}
		if(StringUtils.isNotBlank(str)){
			str = str.substring(0, str.length() - 1);
			pmap.put("userId",dto.getId());
			pmap.put("ids", str);
			sysOrgUserDao.deleteSysOrgUserByUserId(pmap);
		}
		
		dao.updateSysUser(paramMap);
		
	}
	
	/**
	 * 
	 * 修改用户密码
	 * @param dto
	 * @throws Exception
	 */
	@Transactional(rollbackFor={Exception.class})
	public void modifySysUserPassWord(SysUserDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		dao.updateSysUser(paramMap);
	}
	
	/**
     * @title: deleteSysUserByPrimaryKey
     * @author
     * @description: 删除 系统用户表,按主键
     * @date 2014-10-15 10:25:49
     * @param paramMap
     * @throws
     */ 
	public void deleteSysUserByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysUserByPrimaryKey(paramMap);
		//假删除关联关系
		//sysOrgUserDao.updateValidateSysOrgByUserIds(paramMap);
	}

	
	/**Description: 查找user by 精确的查询条件，例如userloginname
	 * Create Date: 2014年10月23日下午4:36:28<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 */
	public List<SysUserDTO> searchByParam(Map<String, Object> searchParams) {
		List<SysUserDTO>  sysUserDTOList = dao.searchSysUser(searchParams);
		for(SysUserDTO dto:  sysUserDTOList){
			//也取出用户机构对应关系
			Map<String, Object> param = new HashMap<String, Object>();
			SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
			sysOrgUserDTO.setUserId(dto.getId());
			param.put("dto", sysOrgUserDTO);
			List<SysOrgUserDTO> sysOrgUserDTOs  = sysOrgUserDao.searchSysOrgUserInfo(param);
			if(sysOrgUserDTOs!=null && sysOrgUserDTOs.size()>0){
				dto.setSysOrgUserDTOs(sysOrgUserDTOs);
			}
		}
		return sysUserDTOList;
	}
	
	public List<SysUserDTO> getUserInfoByLoginName(String loginName) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("loginName", loginName);
		
		List<SysUserDTO>  sysUserDTOList = dao.searchSysUserByLoginName(param);
		if(sysUserDTOList.size() ==0){
			param.clear();
			param.put("email", loginName);
			sysUserDTOList = dao.searchSysUserByLoginName(param);
		}
		if(sysUserDTOList.size() ==0){
			param.clear();
			param.put("mobile", loginName);
			sysUserDTOList = dao.searchSysUserByLoginName(param);
		}
			
		for(SysUserDTO dto:  sysUserDTOList){
			//也取出用户机构对应关系
			Map<String, Object> param2 = new HashMap<String, Object>();
			SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
			sysOrgUserDTO.setUserId(dto.getId());
			param2.put("dto", sysOrgUserDTO);
			List<SysOrgUserDTO> sysOrgUserDTOs  = sysOrgUserDao.searchSysOrgUserInfo(param2);
			if(sysOrgUserDTOs!=null && sysOrgUserDTOs.size()>0){
				dto.setSysOrgUserDTOs(sysOrgUserDTOs);
			}
		}
		return sysUserDTOList;
	}
	/**
     * @author 
     * @description: 分页查询 系统用户表列表
     * @date 2015-4-03 10:25:49
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysUserDTO> searchUserRoleByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysUserDTO> dataList =  dao.searchUserRoleByPaging(searchParams);
		//也取出用户机构对应关系
		for(SysUserDTO dto:  dataList){
			Map<String, Object> param = new HashMap<String, Object>();
			SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
			sysOrgUserDTO.setUserId(dto.getId());
			param.put("dto", sysOrgUserDTO);
			List<SysOrgUserDTO> sysOrgUserDTOs  = sysOrgUserDao.searchSysOrgUserInfo(param);
			if(sysOrgUserDTOs!=null && sysOrgUserDTOs.size()>0){
				dto.setSysOrgUserDTOs(sysOrgUserDTOs);
			}
		}
		return dataList;
	}
	
	
	/**
	 * 
	 * 初始化加密方法
	 * @return
	 */
	public static Md5PasswordHashMatcher initPassWordHashMatcher(){
		if(passwordHashMatcher==null)
		{
			BasePasswordHashMatcher bphm = new BasePasswordHashMatcher();
			Sha1PasswordHashMatcher sphm = new Sha1PasswordHashMatcher(bphm);
			AesPasswordHashMatcher aphm = new AesPasswordHashMatcher(sphm);
			aphm.setPkey("qhcjr01234567890");
			passwordHashMatcher = new Md5PasswordHashMatcher(aphm);
		}
		return passwordHashMatcher;
	}
	
	public List<Map<String,Object>> getUserRoleByTargetId(String userId){
		Map<String, Object> searchParams = new HashMap<String,Object>();
		searchParams.put("targetId", userId);
		List<Map<String,Object>> result = dao.getUserRoleByTargetId(searchParams);
		
		return result;
	}
}

