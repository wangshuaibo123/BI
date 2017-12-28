package com.jy.modules.platform.quartztaskhis.rest;

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

import com.jy.modules.platform.quartztaskhis.dto.QuartzTaskHisDTO;
import com.jy.modules.platform.quartztaskhis.service.QuartzTaskHisService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: QuartzTaskHisRest
 * @description:定义 定时任务执行轨迹表 rest服务
 * @author lei
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/quartzTaskHis")
public class QuartzTaskHisRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(QuartzTaskHisRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.quartztaskhis.service.QuartzTaskHisService")
    private QuartzTaskHisService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<QuartzTaskHisDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<QuartzTaskHisDTO> responseMsg = new ResponseMsg<QuartzTaskHisDTO>();
		QuartzTaskHisDTO entity = service.queryQuartzTaskHisByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<QuartzTaskHisDTO>> responseEntity = new ResponseEntity<ResponseMsg<QuartzTaskHisDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody QuartzTaskHisDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertQuartzTaskHis(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody QuartzTaskHisDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateQuartzTaskHis(obj);
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
		service.deleteQuartzTaskHisByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskHisDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<QuartzTaskHisDTO>> responseMsg = new ResponseMsg<QueryRespBean<QuartzTaskHisDTO>>();
	
		List<QuartzTaskHisDTO> list = service.searchQuartzTaskHisByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<QuartzTaskHisDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskHisDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskHisDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskHisDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<QuartzTaskHisDTO>> responseMsg = new ResponseMsg<QueryRespBean<QuartzTaskHisDTO>>();
		List<QuartzTaskHisDTO> list = service.searchQuartzTaskHis(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<QuartzTaskHisDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskHisDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<QuartzTaskHisDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
