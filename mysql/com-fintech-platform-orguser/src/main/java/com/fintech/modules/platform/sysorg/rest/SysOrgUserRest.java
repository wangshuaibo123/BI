package com.fintech.modules.platform.sysorg.rest;

import java.util.List;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.modules.platform.sysorg.service.SysOrgUserService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysOrgUserRest
 * @description:定义 SYS_ORG_USER rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/api/platform/SysOrgUserRest")
public class SysOrgUserRest extends BaseRest {

	private static Logger logger = LoggerFactory.getLogger(SysOrgUserRest.class);

	@Autowired
    private SysOrgUserService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysOrgUserDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysOrgUserDTO> responseMsg = new ResponseMsg<SysOrgUserDTO>();
		SysOrgUserDTO entity = service.querySysOrgUserByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysOrgUserDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysOrgUserDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysOrgUserDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysOrgUser(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysOrgUserDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysOrgUser(obj);
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
		service.deleteSysOrgUserByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysOrgUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysOrgUserDTO>>();
	
		List<SysOrgUserDTO> list = service.searchSysOrgUserByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysOrgUserDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**Description: 分页查询 SYS_ORG_USER列表	模糊查询
	 * 	parentIds 		父节点id
	 *  orgName 		机构名称
	 *  userName 		用户名称
	 *  positionName	岗位名称
	 * Create Date: 2014年12月3日下午3:26:10<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchFuzzyByPage/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>> searchFuzzyByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysOrgUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysOrgUserDTO>>();
	
		List<SysOrgUserDTO> list = service.searchFuzzySysOrgUserByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysOrgUserDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**Description: 分页查询 SYS_ORG_USER列表	模糊查询
	 * 	parentIds 		父节点id
	 *  orgName 		机构名称
	 *  userName 		用户名称
	 *  positionName	岗位名称
	 * Create Date: 2014年12月3日下午3:26:10<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchFuzzy/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>> searchFuzzy(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysOrgUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysOrgUserDTO>>();
	
		List<SysOrgUserDTO> list = service.searchFuzzySysOrgUser(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysOrgUserDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysOrgUserDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysOrgUserDTO>>();
		List<SysOrgUserDTO> list = service.searchSysOrgUser(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysOrgUserDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysOrgUserDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
