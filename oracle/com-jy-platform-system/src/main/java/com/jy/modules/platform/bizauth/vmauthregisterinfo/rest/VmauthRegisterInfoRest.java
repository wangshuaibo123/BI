package com.jy.modules.platform.bizauth.vmauthregisterinfo.rest;

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

import com.jy.modules.platform.bizauth.vmauthregisterinfo.dto.VmauthRegisterInfoDTO;
import com.jy.modules.platform.bizauth.vmauthregisterinfo.service.VmauthRegisterInfoService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: VmauthRegisterInfoRest
 * @description:定义 权限注册表 rest服务
 * @author chen_gang
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/bizauth/vmauthRegisterInfo")
public class VmauthRegisterInfoRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(VmauthRegisterInfoRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.bizauth.vmauthregisterinfo.service.VmauthRegisterInfoService")
    private VmauthRegisterInfoService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<VmauthRegisterInfoDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<VmauthRegisterInfoDTO> responseMsg = new ResponseMsg<VmauthRegisterInfoDTO>();
		VmauthRegisterInfoDTO entity = service.queryVmauthRegisterInfoByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<VmauthRegisterInfoDTO>> responseEntity = new ResponseEntity<ResponseMsg<VmauthRegisterInfoDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody VmauthRegisterInfoDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertVmauthRegisterInfo(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody VmauthRegisterInfoDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateVmauthRegisterInfo(obj);
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
		service.deleteVmauthRegisterInfoByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>> responseMsg = new ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>>();
	
		List<VmauthRegisterInfoDTO> list = service.searchVmauthRegisterInfoByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<VmauthRegisterInfoDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>> responseMsg = new ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>>();
		List<VmauthRegisterInfoDTO> list = service.searchVmauthRegisterInfo(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<VmauthRegisterInfoDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<VmauthRegisterInfoDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
