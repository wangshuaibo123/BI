package com.fintech.modules.platform.sysorg.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysUserRest
 * @description:定义 系统用户表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/api/platform/SysUserRest")
public class SysUserRest extends BaseRest {

	private static Logger logger = LoggerFactory.getLogger(SysUserRest.class);

	@Autowired
    private SysUserService service;

	@Autowired
	private Validator validator;
	
	//private LdapUserAPI ldapUserAPImpl;
	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysUserDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysUserDTO> responseMsg = new ResponseMsg<SysUserDTO>();
		SysUserDTO entity = service.querySysUserByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysUserDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysUserDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**Description: 取得完整业务对象(包含用户机构岗位对应关系)
	 * Create Date: 2014年10月18日下午12:18:18<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getUser/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysUserDTO>> getUser(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysUserDTO> responseMsg = new ResponseMsg<SysUserDTO>();
		SysUserDTO entity = service.querySysUserFullByPrimaryKey(ID);
		List<SysOrgUserDTO> list = entity.getSysOrgUserDTOs();
		//如果没有归属机构岗位 则默认显示一个
		if(list == null || list.size() ==0){
			List<SysOrgUserDTO> ss  = new ArrayList<SysOrgUserDTO>();
			SysOrgUserDTO dtoOrg = new SysOrgUserDTO();
			dtoOrg.setIsMainOrg("1");
			ss.add(dtoOrg);
			entity.setSysOrgUserDTOs(ss);
		}
		
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysUserDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysUserDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysUserDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysUser(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	

	/**
	 * 修改一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysUserDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysUser(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 删除一个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<Void>> delete(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		service.deleteSysUserByPrimaryKey(null,ID);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 按条件查询并分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/searchByPage/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysUserDTO>>();
	
		List<SysUserDTO> list = service.searchSysUserByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysUserDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysUserDTO>>();
		
		String orgIds = (String)params.getSearchParams().get("orgIds");//
		String orgId = (String)params.getSearchParams().get("orgId");
		String ids = orgIds!=null?orgIds:orgId;
		
		params.getSearchParams().put("orgId", ids);
  
		List<SysUserDTO> list = service.searchSysUser(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysUserDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**Description: 查找user by 精确的查询条件，例如userloginname
	 * Create Date: 2014年10月23日下午4:36:28<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 */
	@RequestMapping(value = "/searchByParam/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>> searchByParam(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysUserDTO>>();
		List<SysUserDTO> list = service.searchByParam(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysUserDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询并分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/searchUserByRolePage/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>> searchUserByRolePage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysUserDTO>>();
	
		List<SysUserDTO> list = service.searchUserRoleByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysUserDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
//	public LdapUserAPI getLdapUserAPImpl() {
//		return ldapUserAPImpl;
//	}
//	public void setLdapUserAPImpl(LdapUserAPI ldapUserAPImpl) {
//		this.ldapUserAPImpl = ldapUserAPImpl;
//	}
	


}
