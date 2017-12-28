package com.jy.modules.platform.schedule2.ptschedlog.rest;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;

import com.jy.modules.platform.schedule2.ptschedlog.dto.PtSchedLogDTO;
import com.jy.modules.platform.schedule2.ptschedlog.service.PtSchedLogService;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: PtSchedLogRest
 * @description:定义 pt_sched_log rest服务
 * @author JY-IT-D001
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/ptSchedLog")
public class PtSchedLogRest extends BaseRest{

	@Autowired
    @Qualifier("com.jy.modules.platform.schedule2.ptschedlog.service.PtSchedLogService")
    private PtSchedLogService service;

	@Autowired
	private Validator validator;

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<PtSchedLogDTO>> create(@RequestBody PtSchedLogDTO obj) throws Exception{
		ResponseMsg<PtSchedLogDTO> responseMsg = new ResponseMsg<PtSchedLogDTO>();
		BeanValidators.validateWithException(validator, obj);
		service.insertPtSchedLog(obj);
		responseMsg.setResponseBody(obj);
		ResponseEntity<ResponseMsg<PtSchedLogDTO>> responseEntity=new ResponseEntity<ResponseMsg<PtSchedLogDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 修改一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody PtSchedLogDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updatePtSchedLog(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}

}
