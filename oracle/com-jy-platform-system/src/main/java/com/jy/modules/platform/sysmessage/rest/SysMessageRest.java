package com.jy.modules.platform.sysmessage.rest;

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

import com.jy.modules.platform.sysmessage.dto.SysMessageDTO;
import com.jy.modules.platform.sysmessage.service.SysMessageService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysMessageRest
 * @description:定义 SYS_MESSAGE rest服务
 * @author lin
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysMessage")
public class SysMessageRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysMessageRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.sysmessage.service.SysMessageService")
    private SysMessageService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysMessageDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysMessageDTO> responseMsg = new ResponseMsg<SysMessageDTO>();
		SysMessageDTO entity = service.querySysMessageByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysMessageDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysMessageDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysMessageDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysMessage(obj);
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
	public ResponseEntity<ResponseMsg<SysMessageDTO>> add(@RequestBody SysMessageDTO obj) throws Exception{
		ResponseMsg<SysMessageDTO> responseMsg = new ResponseMsg<SysMessageDTO>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysMessage(obj);
		responseMsg.setResponseBody(obj);
		ResponseEntity<ResponseMsg<SysMessageDTO>> responseEntity=new ResponseEntity<ResponseMsg<SysMessageDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 修改一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysMessageDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysMessage(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 修改一个业务对象,并返回对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateRtn/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<SysMessageDTO>> updateRtn(@RequestBody SysMessageDTO obj) throws Exception{
		ResponseMsg<SysMessageDTO> responseMsg = new ResponseMsg<SysMessageDTO>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysMessage(obj);
		responseMsg.setResponseBody(obj);
		ResponseEntity<ResponseMsg<SysMessageDTO>> responseEntity=new ResponseEntity<ResponseMsg<SysMessageDTO>>(responseMsg, HttpStatus.OK);
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
		service.deleteSysMessageByID(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysMessageDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysMessageDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysMessageDTO>>();
	
		List<SysMessageDTO> list = service.searchSysMessageByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysMessageDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysMessageDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysMessageDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysMessageDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysMessageDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysMessageDTO>>();
		List<SysMessageDTO> list = service.searchSysMessage(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysMessageDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysMessageDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysMessageDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	
	/**
	 * 关联查询消息及消息关系数据
	 * @return
	 */
	@RequestMapping(value = "/complexSearch/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysMessageDTO>>> complexSearch(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysMessageDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysMessageDTO>>();
		List<SysMessageDTO> list = service.complexSearch(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysMessageDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysMessageDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysMessageDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}

}
