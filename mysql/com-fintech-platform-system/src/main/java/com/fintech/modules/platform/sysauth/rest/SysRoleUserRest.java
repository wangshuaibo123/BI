package com.fintech.modules.platform.sysauth.rest;

import java.util.List;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.fintech.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.fintech.modules.platform.sysauth.service.SysRoleUserService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.common.JYLoggerUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.core.message.ResponseStatus;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysRoleUserRest
 * @description:定义 角色用户表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/sysRoleUser")
public class SysRoleUserRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysRoleUserRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.platform.sysauth.service.SysRoleUserService")
    private SysRoleUserService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysRoleUserDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysRoleUserDTO> responseMsg = new ResponseMsg<SysRoleUserDTO>();
		SysRoleUserDTO entity = service.querySysRoleUserByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysRoleUserDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysRoleUserDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysRoleUserDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysRoleUser(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysRoleUserDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysRoleUser(obj);
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
		service.deleteSysRoleUserByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysRoleUserDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysRoleUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysRoleUserDTO>>();
	
		List<SysRoleUserDTO> list = service.searchSysRoleUserByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysRoleUserDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysRoleUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysRoleUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysRoleUserDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysRoleUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysRoleUserDTO>>();
		List<SysRoleUserDTO> list = service.searchSysRoleUser(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysRoleUserDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysRoleUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysRoleUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * rest服务 异常统一处理机制
	 * @param e
	 * @return {"errorDesc":"自定义异常信息","responseBody":null,"retCode":"500"}
	 */
	@ExceptionHandler
	@ResponseBody
    public ResponseEntity<ResponseMsg<?>> exceptionHandler(Exception e){
        JYLoggerUtil.error(this.getClass(), e.getMessage(),e);
		ResponseMsg<?> responseMsg = new ResponseMsg<BaseDTO>();
		responseMsg.setResponseBody(null);
		responseMsg.setErrorDesc("自定义异常信息:"+e.getMessage());
		responseMsg.setRetCode(ResponseStatus.HTTP_SERVER_ERROR);
		
		ResponseEntity<ResponseMsg<?>> responseEntity = new ResponseEntity<ResponseMsg<?>>(responseMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;  
    }

}
