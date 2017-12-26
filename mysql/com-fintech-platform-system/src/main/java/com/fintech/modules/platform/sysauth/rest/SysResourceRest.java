package com.fintech.modules.platform.sysauth.rest;

import java.util.List;

import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
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

import com.fintech.modules.platform.sysauth.dto.SysResourceDTO;
import com.fintech.modules.platform.sysauth.service.SysResourceService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysResourceRest
 * @description:定义 资源管理表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysauth/SysResourceRest")
public class SysResourceRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysResourceRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.platform.sysauth.service.SysResourceService")
    private SysResourceService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysResourceDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysResourceDTO> responseMsg = new ResponseMsg<SysResourceDTO>();
		SysResourceDTO entity = service.querySysResourceByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysResourceDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysResourceDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<SysResourceDTO>> create(@RequestBody SysResourceDTO obj) throws Exception{
		ResponseMsg<SysResourceDTO> responseMsg = new ResponseMsg<SysResourceDTO>();
		BeanValidators.validateWithException(validator, obj);
		//将冒号转义 ":"->"&#58;"
    	String resoureUrl = StringUtils.isNotEmpty(obj.getResoureUrl()) == false ? "" : obj.getResoureUrl();
    	//去掉空格
    	if(resoureUrl!=null && !"".equals(resoureUrl)){
    		resoureUrl = resoureUrl.replaceAll(" ", "");
    		obj.setResoureUrl(resoureUrl);
    	}
    	if(resoureUrl.contains("http")&&resoureUrl.contains(":")){
    		resoureUrl = resoureUrl.replaceAll(":","&#58;");
			obj.setResoureUrl(resoureUrl);
		}
		service.insertSysResource(obj);
		responseMsg.setResponseBody(obj);
		ResponseEntity<ResponseMsg<SysResourceDTO>> responseEntity=new ResponseEntity<ResponseMsg<SysResourceDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	

	/**
	 * 修改一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<SysResourceDTO>> update(@RequestBody SysResourceDTO obj) throws Exception{
		ResponseMsg<SysResourceDTO> responseMsg = new ResponseMsg<SysResourceDTO>();
		BeanValidators.validateWithException(validator, obj);
		//将冒号转义 ":"->"&#58;"
    	String resoureUrl = StringUtils.isNotEmpty(obj.getResoureUrl()) == false ? "" : obj.getResoureUrl();
    	//去掉空格
    	if(resoureUrl!=null && !"".equals(resoureUrl)){
    		resoureUrl = resoureUrl.replaceAll(" ", "");
    		obj.setResoureUrl(resoureUrl);
    	}
    	if(resoureUrl.contains("http")&&resoureUrl.contains(":")){
    		resoureUrl = resoureUrl.replaceAll(":","&#58;");
			obj.setResoureUrl(resoureUrl);
		}
		service.updateSysResource(obj);
		responseMsg.setResponseBody(obj);
		ResponseEntity<ResponseMsg<SysResourceDTO>> responseEntity=new ResponseEntity<ResponseMsg<SysResourceDTO>>(responseMsg, HttpStatus.OK);
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
		service.deleteSysResourceByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysResourceDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysResourceDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysResourceDTO>>();
	
		List<SysResourceDTO> list = service.searchSysResourceByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysResourceDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysResourceDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysResourceDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysResourceDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysResourceDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysResourceDTO>>();
		List<SysResourceDTO> list = service.searchSysResource(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysResourceDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysResourceDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysResourceDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
