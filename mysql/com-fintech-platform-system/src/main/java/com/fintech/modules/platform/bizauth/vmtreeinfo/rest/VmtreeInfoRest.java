package com.fintech.modules.platform.bizauth.vmtreeinfo.rest;

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

import com.fintech.modules.platform.bizauth.vmtreeinfo.dto.VmtreeInfoDTO;
import com.fintech.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: VmtreeInfoRest
 * @description:定义 虚拟树表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/bizauth/vmtreeInfo")
public class VmtreeInfoRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(VmtreeInfoRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService")
    private VmtreeInfoService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<VmtreeInfoDTO>> get(@PathVariable("ID") String ID,@PathVariable("orgType") String orgType) throws Exception{
		ResponseMsg<VmtreeInfoDTO> responseMsg = new ResponseMsg<VmtreeInfoDTO>();
		VmtreeInfoDTO entity = service.queryVmtreeInfoByPrimaryKey(ID,orgType);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<VmtreeInfoDTO>> responseEntity = new ResponseEntity<ResponseMsg<VmtreeInfoDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody VmtreeInfoDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertVmtreeInfo(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody VmtreeInfoDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateVmtreeInfo(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 删除一个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<Void>> delete(@RequestBody VmtreeInfoDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		service.deleteVmtreeInfoByPrimaryKey(obj,obj.getOrgId().toString());
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
	public ResponseEntity<ResponseMsg<QueryRespBean<VmtreeInfoDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<VmtreeInfoDTO>> responseMsg = new ResponseMsg<QueryRespBean<VmtreeInfoDTO>>();
	
		List<VmtreeInfoDTO> list = service.searchVmtreeInfoByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<VmtreeInfoDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<VmtreeInfoDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<VmtreeInfoDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<VmtreeInfoDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<VmtreeInfoDTO>> responseMsg = new ResponseMsg<QueryRespBean<VmtreeInfoDTO>>();
		List<VmtreeInfoDTO> list = service.searchVmtreeInfo(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<VmtreeInfoDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<VmtreeInfoDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<VmtreeInfoDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
