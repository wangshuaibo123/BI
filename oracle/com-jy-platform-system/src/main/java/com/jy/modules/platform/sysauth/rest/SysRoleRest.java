package com.jy.modules.platform.sysauth.rest;

import java.util.List;
import java.util.Map;

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

import com.jy.modules.platform.sysauth.dto.SysRoleDTO;
import com.jy.modules.platform.sysauth.service.SysRoleService;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.common.JYLoggerUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.core.message.ResponseStatus;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysRoleRest
 * @description:定义 角色表 rest服务
 * @author chen_gang
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/sysRole")
public class SysRoleRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysRoleRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.sysauth.service.SysRoleService")
    private SysRoleService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysRoleDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysRoleDTO> responseMsg = new ResponseMsg<SysRoleDTO>();
		SysRoleDTO entity = service.querySysRoleByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysRoleDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysRoleDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysRoleDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysRole(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysRoleDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysRole(obj);
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
		service.deleteSysRoleByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysRoleDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysRoleDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysRoleDTO>>();
	
		List<SysRoleDTO> list = service.searchSysRoleByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysRoleDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysRoleDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysRoleDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysRoleDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysRoleDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysRoleDTO>>();
		List<SysRoleDTO> list = service.searchSysRole(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysRoleDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysRoleDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysRoleDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	@RequestMapping(value = "/getRoleByUserId/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysRoleDTO>>> getRoleByUserId(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysRoleDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysRoleDTO>>();
		Map<String, Object> search = params.getSearchParams();
		String userId = (String) search.get("userId");
		List<SysRoleDTO> list = service.getRoleByUserId(Long.parseLong(userId));
		responseMsg.setResponseBody(new QueryRespBean<SysRoleDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysRoleDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysRoleDTO>>>(responseMsg, HttpStatus.OK);
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
