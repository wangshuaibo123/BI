package com.jy.modules.platform.jbpm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.MediaTypes;

import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.jbpm4.service.IModifyProInfoService;
import com.jy.platform.restservice.web.base.BaseRest;

@Controller
@RequestMapping(value = "/api/jbpm/repositoryCache")
public class RepositoryCacheRest extends BaseRest{
	
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.ModifyProInfoServiceImpl")
	private IModifyProInfoService modifyProService;
	
	/**
	 * 删除一个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<Void>> delete(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		modifyProService.deleteRepositoryCache(ID);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}

}
