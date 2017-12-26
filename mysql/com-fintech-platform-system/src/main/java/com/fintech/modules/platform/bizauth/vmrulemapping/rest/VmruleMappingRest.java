package com.fintech.modules.platform.bizauth.vmrulemapping.rest;

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

import com.fintech.modules.platform.bizauth.vmrulemapping.dto.VmruleMappingDTO;
import com.fintech.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: VmruleMappingRest
 * @description:定义 映射表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/bizauth/vmruleMapping")
public class VmruleMappingRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(VmruleMappingRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService")
    private VmruleMappingService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<VmruleMappingDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<VmruleMappingDTO> responseMsg = new ResponseMsg<VmruleMappingDTO>();
		VmruleMappingDTO entity = service.queryVmruleMappingByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<VmruleMappingDTO>> responseEntity = new ResponseEntity<ResponseMsg<VmruleMappingDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody VmruleMappingDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertVmruleMapping(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody VmruleMappingDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateVmruleMapping(obj);
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
		service.deleteVmruleMappingByPrimaryKey(null,ID,null);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<VmruleMappingDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<VmruleMappingDTO>> responseMsg = new ResponseMsg<QueryRespBean<VmruleMappingDTO>>();
	
		List<VmruleMappingDTO> list = service.searchVmruleMappingByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<VmruleMappingDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<VmruleMappingDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<VmruleMappingDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<VmruleMappingDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<VmruleMappingDTO>> responseMsg = new ResponseMsg<QueryRespBean<VmruleMappingDTO>>();
		List<VmruleMappingDTO> list = service.searchVmruleMapping(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<VmruleMappingDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<VmruleMappingDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<VmruleMappingDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
