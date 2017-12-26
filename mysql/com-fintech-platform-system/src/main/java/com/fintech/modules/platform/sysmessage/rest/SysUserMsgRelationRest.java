package com.fintech.modules.platform.sysmessage.rest;

import java.util.List;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.fintech.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.fintech.modules.platform.sysmessage.service.SysUserMsgRelationService;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysUserMsgRelationRest
 * @description:定义 SYS_USER_MSG_RELATION rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysUserMsgRelation")
public class SysUserMsgRelationRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysUserMsgRelationRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.platform.sysmessage.service.SysUserMsgRelationService")
    private SysUserMsgRelationService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysUserMsgRelationDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysUserMsgRelationDTO> responseMsg = new ResponseMsg<SysUserMsgRelationDTO>();
		SysUserMsgRelationDTO entity = service.querySysUserMsgRelationByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysUserMsgRelationDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysUserMsgRelationDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysUserMsgRelationDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysUserMsgRelation(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	
	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<SysUserMsgRelationDTO>> add(@RequestBody SysUserMsgRelationDTO obj) throws Exception{
		ResponseMsg<SysUserMsgRelationDTO> responseMsg = new ResponseMsg<SysUserMsgRelationDTO>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysUserMsgRelation(obj);
		responseMsg.setResponseBody(obj);
		ResponseEntity<ResponseMsg<SysUserMsgRelationDTO>> responseEntity=new ResponseEntity<ResponseMsg<SysUserMsgRelationDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	

	/**
	 * 修改一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysUserMsgRelationDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysUserMsgRelation(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 修改一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateRtn/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<SysUserMsgRelationDTO>> updateRtn(@RequestBody SysUserMsgRelationDTO obj) throws Exception{
		ResponseMsg<SysUserMsgRelationDTO> responseMsg = new ResponseMsg<SysUserMsgRelationDTO>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysUserMsgRelation(obj);
		responseMsg.setResponseBody(obj);
		ResponseEntity<ResponseMsg<SysUserMsgRelationDTO>> responseEntity=new ResponseEntity<ResponseMsg<SysUserMsgRelationDTO>>(responseMsg, HttpStatus.OK);
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
		service.deleteSysUserMsgRelationByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>>();
	
		List<SysUserMsgRelationDTO> list = service.searchSysUserMsgRelationByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysUserMsgRelationDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>>();
		List<SysUserMsgRelationDTO> list = service.searchSysUserMsgRelation(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysUserMsgRelationDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	/**
     * @description: 获取所有可用的（且已分配角色）用户编号列表 
     * @date 2015-12-03 09:37:58
     * @return
     */
	@RequestMapping(value = "/queryAllUsefulSysUser/v1")
	@ResponseBody
	public ResponseEntity<ResponseMsg<List<SysUserDTO>>> queryAllUsefulSysUser() throws Exception{
		
		ResponseMsg<List<SysUserDTO>> responseMsg = new ResponseMsg<List<SysUserDTO>>();
		List<SysUserDTO> list = service.queryAllUsefulSysUser();
		responseMsg.setResponseBody(list);
		
		ResponseEntity<ResponseMsg<List<SysUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<List<SysUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
}
