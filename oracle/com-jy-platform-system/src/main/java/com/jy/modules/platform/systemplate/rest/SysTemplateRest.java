package com.jy.modules.platform.systemplate.rest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
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

import com.jy.modules.platform.systemplate.dto.SysTemplateDTO;
import com.jy.modules.platform.systemplate.service.SysTemplateService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysTemplateRest
 * @description:定义 模板 rest服务
 * @author yuchengyang-pc
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/systemplate/SysTemplateRest")
public class SysTemplateRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysTemplateRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.systemplate.service.SysTemplateService")
    private SysTemplateService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysTemplateDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysTemplateDTO> responseMsg = new ResponseMsg<SysTemplateDTO>();
		SysTemplateDTO entity = service.querySysTemplateByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysTemplateDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysTemplateDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysTemplateDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysTemplate(obj, null);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysTemplateDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysTemplate(obj , null);
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
		service.deleteSysTemplateByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysTemplateDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysTemplateDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysTemplateDTO>>();
	
		List<SysTemplateDTO> list = service.searchSysTemplateByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysTemplateDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysTemplateDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysTemplateDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysTemplateDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysTemplateDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysTemplateDTO>>();
		List<SysTemplateDTO> list = service.searchSysTemplate(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysTemplateDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysTemplateDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysTemplateDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	
	/**Description: 
	 * Create Date: 2014年10月29日下午3:20:58<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTemplateContentByNo/v1", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseMsg<String>> getTemplateContentByNo(@RequestBody QueryReqBean params) throws Exception{
		ResponseMsg<String> responseMsg = new ResponseMsg<String>();
		ByteArrayOutputStream baos  = null;
		try {
			List<SysTemplateDTO> list = service.searchSysTemplate(params.getSearchParams());
			Blob content = (Blob) list.get(0).getTemplateContent();
			InputStream  ins = content.getBinaryStream();
			baos = new ByteArrayOutputStream();
			int i = -1;
			while ((i= ins.read()) != -1) {
				baos.write(i);
			}
			responseMsg.setResponseBody( baos.toString("UTF-8"));
		} catch (Exception e) {
			logger.error("searchNoJson：exception" + e.getStackTrace());
		} finally{
			baos.close();//关闭
		}
		ResponseEntity<ResponseMsg<String>> responseEntity=new ResponseEntity<ResponseMsg<String>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
}
