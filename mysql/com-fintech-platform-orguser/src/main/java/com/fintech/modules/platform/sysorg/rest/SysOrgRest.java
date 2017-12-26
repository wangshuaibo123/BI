package com.fintech.modules.platform.sysorg.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
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

import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;
import com.fintech.modules.platform.sysorg.service.SysOrgService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;

/**
 * @classname: SysOrgRest
 * @description:定义 机构部门表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/api/platform/SysOrgRest")
public class SysOrgRest extends BaseRest {

    private static Logger logger = LoggerFactory.getLogger(SysOrgRest.class);

    @Autowired
    private SysOrgService service;

    @Autowired
    private Validator validator;

    /**
     * 取得单个业务对象
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<ResponseMsg<SysOrgDTO>> get(@PathVariable("ID") String ID) throws Exception {
        ResponseMsg<SysOrgDTO> responseMsg = new ResponseMsg<SysOrgDTO>();
        SysOrgDTO entity = service.querySysOrgByPrimaryKey(ID);
        responseMsg.setResponseBody(entity);
        ResponseEntity<ResponseMsg<SysOrgDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysOrgDTO>>(responseMsg,
                HttpStatus.OK);
        return responseEntity;
    }
    
    @ResponseBody
    @RequestMapping(value = "/get/v2/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<ResponseMsg<SysOrgDTO>> get(@PathVariable("ID") Long ID) throws Exception {
        ResponseMsg<SysOrgDTO> responseMsg = new ResponseMsg<SysOrgDTO>();
        SysOrgDTO entity = service.querySysOrgByPrimaryKey(ID.toString());
        responseMsg.setResponseBody(entity);
        if(ID >10){
        	responseMsg.setErrorDesc("自定义信息返回");
        	//responseMsg.setRetCode("500");
        }
        ResponseEntity<ResponseMsg<SysOrgDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysOrgDTO>>(responseMsg,
                HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 插入一个业务对象
     * 
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create/v1", method = RequestMethod.POST)
    public ResponseEntity<ResponseMsg<SysOrgDTO>> create(@RequestBody SysOrgDTO obj) throws Exception {
        ResponseMsg<SysOrgDTO> responseMsg = new ResponseMsg<SysOrgDTO>();
        BeanValidators.validateWithException(validator, obj);
        try {
			service.insertSysOrg(obj);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
        responseMsg.setResponseBody(obj);
        ResponseEntity<ResponseMsg<SysOrgDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysOrgDTO>>(responseMsg,
                HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 修改一个业务对象
     * 
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update/v1", method = RequestMethod.POST)
    public ResponseEntity<ResponseMsg<SysOrgDTO>> update(@RequestBody SysOrgDTO obj) throws Exception {
        ResponseMsg<SysOrgDTO> responseMsg = new ResponseMsg<SysOrgDTO>();
        BeanValidators.validateWithException(validator, obj);
        service.updateSysOrg(obj);
        if(!obj.getParentId().equals(obj.getParentOrgId())){
        	 Map<String, Object> paramMap = new HashMap<String, Object>();
        	 SysOrgDTO p = new SysOrgDTO();
        	 p.setParentId(obj.getId()+"");
        	 paramMap.put("dto", p);
             List<SysOrgDTO> dataList = service.searchSysOrg(paramMap);
             for(SysOrgDTO d:dataList){
            	 service.updateSysOrgParentIds(d);
             }
        }
        responseMsg.setResponseBody(obj);
        ResponseEntity<ResponseMsg<SysOrgDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysOrgDTO>>(responseMsg,
                HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 删除一个业务对象
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<ResponseMsg<Void>> delete(@PathVariable("ID") String ID) throws Exception {
        ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
        service.deleteSysOrgByPrimaryKey(null, ID);
        ResponseEntity<ResponseMsg<Void>> responseEntity = new ResponseEntity<ResponseMsg<Void>>(responseMsg,
                HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 按条件查询并分页
     * 
     * @param page
     * @return
     */
    @RequestMapping(value = "/searchByPage/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>> searchByPage(@RequestBody QueryReqBean params)
            throws Exception {

        ResponseMsg<QueryRespBean<SysOrgDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysOrgDTO>>();

        List<SysOrgDTO> list = service.searchSysOrgByPaging(params.getSearchParams());
        responseMsg.setResponseBody(new QueryRespBean<SysOrgDTO>(params.getPageParameter(), list));
        ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>> responseEntity = new ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>>(
                responseMsg, HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 按条件查询不分页
     * 
     * @param page
     * @return
     */
    @RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>> search(@RequestBody QueryReqBean params)
            throws Exception {
        ResponseMsg<QueryRespBean<SysOrgDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysOrgDTO>>();
        Map<String, Object> searchParams = params.getSearchParams();
        if ("up".equals(searchParams.get("trace"))) {// 如果是向上追溯
            SysOrgDTO sysOrgDTO = service.querySysOrgByPrimaryKey((String) searchParams.get("orgId"));
            String idsString = "-1" + StringUtils.replace(sysOrgDTO.getParentIds(), "/", ",") + "-1";
            searchParams.put("ids", idsString);
        }
        List<SysOrgDTO> list = service.searchSysOrg(params.getSearchParams());
        responseMsg.setResponseBody(new QueryRespBean<SysOrgDTO>(params.getPageParameter(), list));

        ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>> responseEntity = new ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>>(
                responseMsg, HttpStatus.OK);
        return responseEntity;
    }

    /**
     * @author
     * @description:查询列表 SYS_ORG 根据人员id，岗位id查询org的parentIds author : cxt 、cyy
     * @date 2014-12-09 20:29:06
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/searchSysOrgByUserIdAndParentIds/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>> searchSysOrgByUserIdAndParentIds(
            @RequestBody QueryReqBean params) throws Exception {
        ResponseMsg<QueryRespBean<SysOrgDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysOrgDTO>>();
        Map<String, Object> searchParams = params.getSearchParams();

        List<SysOrgDTO> list = service.searchSysOrgByUserIdAndParentIds(params.getSearchParams());
        responseMsg.setResponseBody(new QueryRespBean<SysOrgDTO>(params.getPageParameter(), list));

        ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>> responseEntity = new ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>>(
                responseMsg, HttpStatus.OK);
        return responseEntity;
    }
    
    /**
     * 查询有效的机构，不分页
     * @param page
     * @return
     */
    @RequestMapping(value = "/searchValid/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>> searchValid(@RequestBody QueryReqBean params)
            throws Exception {
        ResponseMsg<QueryRespBean<SysOrgDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysOrgDTO>>();
        Map<String, Object> searchParams = params.getSearchParams();
        SysOrgDTO dto = new SysOrgDTO();
        dto.setValidateState("1");
        searchParams.put("dto", dto);
        if ("up".equals(searchParams.get("trace"))) {// 如果是向上追溯
            SysOrgDTO sysOrgDTO = service.querySysOrgByPrimaryKey((String) searchParams.get("orgId"));
            String idsString = "-1" + StringUtils.replace(sysOrgDTO.getParentIds(), "/", ",") + "-1";
            searchParams.put("ids", idsString);
        }
        List<SysOrgDTO> list = service.searchSysOrg(params.getSearchParams());
        responseMsg.setResponseBody(new QueryRespBean<SysOrgDTO>(params.getPageParameter(), list));

        ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>> responseEntity = new ResponseEntity<ResponseMsg<QueryRespBean<SysOrgDTO>>>(
                responseMsg, HttpStatus.OK);
        return responseEntity;
    }


}
