package com.fintech.modules.platform.sysorg.rest;

import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.fintech.modules.platform.sysorg.dto.SysPositionDTO;
import com.fintech.modules.platform.sysorg.service.SysPositionService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysPositionRest
 * @description:定义 岗位定义表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/api/platform/sysorg/SysPositionRest")
public class SysPositionRest extends BaseRest {

	private static Logger logger = LoggerFactory.getLogger(SysPositionRest.class);

	@Autowired
	@Qualifier("com.fintech.modules.platform.sysorg.service.SysPositionService")
    private SysPositionService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysPositionDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysPositionDTO> responseMsg = new ResponseMsg<SysPositionDTO>();
		SysPositionDTO entity = service.querySysPositionByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysPositionDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysPositionDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysPositionDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysPosition(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysPositionDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysPosition(obj);
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
		service.deleteSysPositionByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysPositionDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysPositionDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysPositionDTO>>();
	
		List<SysPositionDTO> list = service.searchSysPositionByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysPositionDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysPositionDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysPositionDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysPositionDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysPositionDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysPositionDTO>>();
		List<SysPositionDTO> list = service.searchSysPosition(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysPositionDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysPositionDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysPositionDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 查询有效的岗位，不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/searchValid/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysPositionDTO>>> searchValid(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysPositionDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysPositionDTO>>();
		Map<String, Object> searchParams = params.getSearchParams();
		SysPositionDTO dto = new SysPositionDTO();
		dto.setValidateState("1");
        searchParams.put("dto", dto);
		List<SysPositionDTO> list = service.searchSysPosition(searchParams);
		responseMsg.setResponseBody(new QueryRespBean<SysPositionDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysPositionDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysPositionDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
