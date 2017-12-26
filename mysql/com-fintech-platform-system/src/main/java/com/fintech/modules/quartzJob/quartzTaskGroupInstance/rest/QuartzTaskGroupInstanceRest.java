package com.fintech.modules.quartzJob.quartzTaskGroupInstance.rest;

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

import com.fintech.modules.quartzJob.quartzTaskGroupInstance.dto.QuartzTaskGroupInstanceDTO;
import com.fintech.modules.quartzJob.quartzTaskGroupInstance.service.QuartzTaskGroupInstanceService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: QuartzTaskGroupInstanceRest
 * @description:定义 分组任务实例表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/quartzTaskGroupInstance")
public class QuartzTaskGroupInstanceRest extends BaseRest {

	private static Logger logger = LoggerFactory.getLogger(QuartzTaskGroupInstanceRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.quartzJob.quartzTaskGroupInstance.service.QuartzTaskGroupInstanceService")
    private QuartzTaskGroupInstanceService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<QuartzTaskGroupInstanceDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<QuartzTaskGroupInstanceDTO> responseMsg = new ResponseMsg<QuartzTaskGroupInstanceDTO>();
		QuartzTaskGroupInstanceDTO entity = service.queryQuartzTaskGroupInstanceByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<QuartzTaskGroupInstanceDTO>> responseEntity = new ResponseEntity<ResponseMsg<QuartzTaskGroupInstanceDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody QuartzTaskGroupInstanceDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertQuartzTaskGroupInstance(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody QuartzTaskGroupInstanceDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateQuartzTaskGroupInstance(obj);
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
		service.deleteQuartzTaskGroupInstanceByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>> responseMsg = new ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>>();
	
		List<QuartzTaskGroupInstanceDTO> list = service.searchQuartzTaskGroupInstanceByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<QuartzTaskGroupInstanceDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>> responseMsg = new ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>>();
		List<QuartzTaskGroupInstanceDTO> list = service.searchQuartzTaskGroupInstance(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<QuartzTaskGroupInstanceDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskGroupInstanceDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	@ResponseBody
	@RequestMapping(value = "/updateAutoByGroup/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<Void>> updateByGroup(@RequestBody QueryReqBean params) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		service.updateAutoExceGroupTaskByGroup(params.getSearchParams());
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/stopByGroup/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<Void>> stopByGroup(@RequestBody QueryReqBean params) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		service.updateGroupStop(params.getSearchParams());
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	
	@ResponseBody
	@RequestMapping(value = "/updateAutoByTaskClass/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<Void>> updateByTaskClass(@RequestBody QueryReqBean params) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		service.updateAutoExceByTaskClass(params.getSearchParams());
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
}
