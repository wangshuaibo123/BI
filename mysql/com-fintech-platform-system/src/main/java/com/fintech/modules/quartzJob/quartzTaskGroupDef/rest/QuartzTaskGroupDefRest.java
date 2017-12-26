package com.fintech.modules.quartzJob.quartzTaskGroupDef.rest;

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

import com.fintech.modules.quartzJob.quartzTaskGroupDef.dto.QuartzTaskGroupDefDTO;
import com.fintech.modules.quartzJob.quartzTaskGroupDef.service.QuartzTaskGroupDefService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: QuartzTaskGroupDefRest
 * @description:定义 任务分组定义表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/quartzTaskGroupDef")
public class QuartzTaskGroupDefRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(QuartzTaskGroupDefRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.quartzJob.quartzTaskGroupDef.service.QuartzTaskGroupDefService")
    private QuartzTaskGroupDefService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<QuartzTaskGroupDefDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<QuartzTaskGroupDefDTO> responseMsg = new ResponseMsg<QuartzTaskGroupDefDTO>();
		QuartzTaskGroupDefDTO entity = service.queryQuartzTaskGroupDefByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<QuartzTaskGroupDefDTO>> responseEntity = new ResponseEntity<ResponseMsg<QuartzTaskGroupDefDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody QuartzTaskGroupDefDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertQuartzTaskGroupDef(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody QuartzTaskGroupDefDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateQuartzTaskGroupDef(obj);
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
		service.deleteQuartzTaskGroupDefByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>> responseMsg = new ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>>();
	
		List<QuartzTaskGroupDefDTO> list = service.searchQuartzTaskGroupDefByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<QuartzTaskGroupDefDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>> responseMsg = new ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>>();
		List<QuartzTaskGroupDefDTO> list = service.searchQuartzTaskGroupDef(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<QuartzTaskGroupDefDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupDefDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
