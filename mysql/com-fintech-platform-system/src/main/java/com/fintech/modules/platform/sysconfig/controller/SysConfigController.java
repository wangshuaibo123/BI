package com.fintech.modules.platform.sysconfig.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.SafeEncoder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.common.util.ObtainPropertiesInfo;
import com.fintech.modules.platform.sysconfig.dto.SysConfigDTO;
import com.fintech.modules.platform.sysdict.dto.SysDictDTO;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.core.message.ResponseStatus;
import com.fintech.platform.core.redis.JedisSentinelPool;
import com.fintech.platform.restclient.http.RestClient;
import com.fintech.platform.restclient.http.RestClientConfig;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;
/**
 * @classname: SysConfigController
 * @description: 定义 系统配置表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysConfig")
public class SysConfigController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysConfigController.class);
	private String jyptAppId = "ptpt"; // rest服务appId
	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);// rest服务地址
	
	//@Resource(name="jedisSentinelPool")
	private JedisSentinelPool sessionJedisPool;
	
	//@Resource(name="businessJedisSentinelPool")
	private JedisSentinelPool bizJedisPool;

	public JedisSentinelPool getSessionJedisPool() {
		if(sessionJedisPool == null){
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			JedisSentinelPool sessionJedisPool = (JedisSentinelPool) context.getBean("jedisSentinelPool");
			
			this.sessionJedisPool = sessionJedisPool;
		}
		
		
		return sessionJedisPool;
	}

	public JedisSentinelPool getbizJedisPool() {
		if(bizJedisPool == null){
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			JedisSentinelPool bizJedisPool = (JedisSentinelPool) context.getBean("businessJedisSentinelPool");
			
			this.bizJedisPool = bizJedisPool;
		}
		
		return bizJedisPool;
	}
	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");

		if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/sysConfig/querySysConfig");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/sysConfig/addSysConfig");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysConfig/updateSysConfig");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysConfig/viewSysConfig");
		}
		else if("toDeleteRedisLock".equals(operate)) {
			model.setViewName("platform/sysConfig/deleteRedisLock");
		}

		return model;
	}

	/**
	 * @author
	 * @description:查询分页列表
	 * @date 2014-10-15 10:27:46
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysConfig")
	@ResponseBody
	public DataMsg queryListSysConfig(HttpServletRequest request,
			SysConfigDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		String url = jyptURL
				+ "/api/platform/sysconfig/SysConfigRest/searchByPageNoCache/v1/";
		ResponseMsg<QueryRespBean<SysConfigDTO>> responseMsg = RestClient
				.doPost(jyptAppId,
						url,
						params,
						new TypeReference<ResponseMsg<QueryRespBean<SysConfigDTO>>>() {
						});
		List<SysConfigDTO> list = responseMsg.getResponseBody().getResult();

		dataMsg.setData(list);
		dataMsg.setTotalRows(responseMsg.getResponseBody().getPageParameter().getTotalCount());
		return dataMsg;
	}

	/**
	 * @author
	 * @description:新增
	 * @date 2014-10-15 10:27:46
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertSysConfig")
	@ResponseBody
	public DataMsg insertSysConfig(HttpServletRequest request,
			SysConfigDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysConfigDTO) super.initDto(dto);
			String url = jyptURL
					+ "/api/platform/sysconfig/SysConfigRest/create/v1";
			ResponseMsg<SysConfigDTO> responseMsg = RestClient.doPost(
					jyptAppId, url, dto,
					new TypeReference<ResponseMsg<SysConfigDTO>>() {
					});
			dataMsg = super.initDataMsg(dataMsg);
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("新增成功");
				dataMsg.setStatus(DataMsg.STATUS_OK);
			}
			else{
				dataMsg.setMsg("新增失败");
				dataMsg.setStatus(DataMsg.STATUS_FAILED);
			}
			dataMsg.setData(this.makeJSONData(dto.getId()));
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法insertSysResource异常：", e);
		}
		return dataMsg;
	}

	/**
	 * @author
	 * @description:编辑
	 * @date 2014-10-15 10:27:46
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysConfig")
	@ResponseBody
	public DataMsg updateSysConfig(HttpServletRequest request,
			SysConfigDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysConfigDTO) super.initDto(dto);

			String url = jyptURL
					+ "/api/platform/sysconfig/SysConfigRest/update/v1";
			ResponseMsg<SysConfigDTO> responseMsg = RestClient.doPost(
					jyptAppId, url, dto,
					new TypeReference<ResponseMsg<SysConfigDTO>>() {
					});
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
				dataMsg.setMsg("修改成功");
				dataMsg.setStatus(DataMsg.STATUS_OK);
			} else {
				dataMsg.setMsg("修改失败");
				dataMsg.setStatus(DataMsg.STATUS_FAILED);
			}
			dataMsg.setData(this.makeJSONData(dto.getId()));
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法updateSysResource异常：", e);
		}
		return dataMsg;
	}
	
	/**
	 * 清理redis锁
	 * @param request
	 * @param dataMsg
	 * @return
	 */
	@RequestMapping(value = "/deleteRedisLock")
	@ResponseBody
	public DataMsg deleteRedisLock(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {
		try{
			String module = (String)request.getParameter("module");
			String bizKey = (String)request.getParameter("bizKey");
			StringBuffer pattern = new StringBuffer("REDIS_LOCK:"+ObtainPropertiesInfo.getValByKey("app.code")+":");
			if((module==null || "".equals(module)) 
					&& (bizKey==null || "".equals(bizKey))){
				pattern.append("*");
			}
			else{
				if(module!=null && !"".equals(module)){
					pattern.append(module).append(":");
				}
				else{
					pattern.append("*:");
				}
				if(bizKey!=null && !"".equals(bizKey)){
					pattern.append(bizKey);
				}
				else{
					pattern.append("*");
				}
			}
			
			if(getSessionJedisPool() != null){
				ShardedJedis jedis= sessionJedisPool.getResource();
			    Collection<Jedis> allJedis = jedis.getAllShards();
			    
			    for(Jedis t : allJedis){
			    	Set<byte[]> keys = t.keys(SafeEncoder.encode(pattern.toString()));
			    	for(byte[] key : keys){
			    		jedis.del(key);
			    		logger.info("remove redis cache " + SafeEncoder.encode(key));
			    	}
			    }
			    sessionJedisPool.returnResource(jedis);
			}
			
			dataMsg.setMsg("redis锁清理成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} 
		catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法deleteRedisLock异常：", e);
		}
		
		return dataMsg;
	}

	/**
	 * @author
	 * @description:删除
	 * @date 2014-10-15 10:27:46
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteSysConfig")
	@ResponseBody
	public DataMsg deleteSysConfig(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			String url = jyptURL
					+ "/api/platform/sysconfig/SysConfigRest/delete/v1/" + ids;
			ResponseMsg<SysConfigDTO> responseMsg = RestClient.doGet(jyptAppId,
					url, new TypeReference<ResponseMsg<SysConfigDTO>>() {
					});
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
				dataMsg.setMsg("删除成功");
				dataMsg.setStatus(DataMsg.STATUS_OK);
			} else {
				dataMsg.setMsg("删除失败");
				dataMsg.setStatus(DataMsg.STATUS_FAILED);
			}
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法deleteSysResource异常：", e);
		}
		return dataMsg;
	}
	/**
	 * @author
	 * @description:删除数据缓存
	 * @date 2014-10-15 10:27:46
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/cleanDataCache")
	@ResponseBody
	public DataMsg deleteDataCache(HttpServletRequest request,@ModelAttribute DataMsg dataMsg) {
		try {
			if(getSessionJedisPool() != null){
				ShardedJedis jedis= sessionJedisPool.getResource();
			    Collection<Jedis> allJedis=jedis.getAllShards();
			    for(Jedis t:allJedis){
			    	Set<byte[]> keys=t.keys(SafeEncoder.encode("*"+ObtainPropertiesInfo.getValByKey("app.code")+":biz:*"));
			    	for(byte[] key:keys){
			    		jedis.del(key);
			    		logger.info("remove redis cache " + SafeEncoder.encode(key));
			    	}
			    }
			    //清空消息
			    for(Jedis t:allJedis){
			    	Set<byte[]> keys=t.keys(SafeEncoder.encode("T_MESSAGE:*:"+ObtainPropertiesInfo.getValByKey("app.code")+":*"));
			    	for(byte[] key:keys){
			    		jedis.del(key);
			    		logger.info("remove redis cache " + SafeEncoder.encode(key));
			    	}
			    }
			    //清空消息
			    for(Jedis t:allJedis){
			    	Set<byte[]> keys=t.keys(SafeEncoder.encode("T_USER_MSG_RELATION:*:"+ObtainPropertiesInfo.getValByKey("app.code")+":*"));
			    	for(byte[] key:keys){
			    		jedis.del(key);
			    		logger.info("remove redis cache " + SafeEncoder.encode(key));
			    	}
			    }
			    sessionJedisPool.returnResource(jedis);
			}
			dataMsg.setMsg("缓存清理成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			logger.error("deleteDataCache失败",e);
			dataMsg.setMsg("缓存清理失败");
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		return dataMsg;
	}
	/**
	 * @author
	 * @description:删除数据缓存
	 * @date 2014-10-15 10:27:46
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/cleanAllDataCache")
	@ResponseBody
	public DataMsg cleanAllDataCache(HttpServletRequest request,@ModelAttribute DataMsg dataMsg) {
		try {
			if(getSessionJedisPool() != null){
				ShardedJedis jedis= sessionJedisPool.getResource();
			    Collection<Jedis> allJedis=jedis.getAllShards();
			    for(Jedis t:allJedis){
			    	logger.info("====flushAll=="+t.dbSize());
			    	t.flushAll();
			    }
			    sessionJedisPool.returnResource(jedis);
			}
			dataMsg.setMsg("缓存清理成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			logger.error("cleanAllDataCache失败",e);
			dataMsg.setMsg("缓存清理失败");
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		return dataMsg;
	}
	
	
    /**
     * 清空系统业务数据缓存
     * @param request
     * @param dataMsg
     * @return
     */
	@RequestMapping(value = "/cleanBusinessDataCache")
	@ResponseBody
	public DataMsg deleteBusinessDataCache(HttpServletRequest request,@ModelAttribute DataMsg dataMsg) {
		try {
			if(getbizJedisPool() != null){
				ShardedJedis jedis= bizJedisPool.getResource();
			    Collection<Jedis> allJedis=jedis.getAllShards();
			    for(Jedis t:allJedis){
			    	Set<byte[]> keys=t.keys(SafeEncoder.encode("*"+ObtainPropertiesInfo.getValByKey("app.code")+"*"));
			    	for(byte[] key:keys){
			    		jedis.del(key);
			    		logger.info("remove redis cache " + SafeEncoder.encode(key));
			    	}
			    }
			    bizJedisPool.returnResource(jedis);
			}
			dataMsg.setMsg("缓存清理成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			logger.error("deleteDataCache失败",e);
			dataMsg.setMsg("缓存清理失败");
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		return dataMsg;
	}
	
	
	/**
	 * 清空所有系统业务数据缓存
	 * @param request
	 * @param dataMsg
	 * @return
	 */
	@RequestMapping(value = "/cleanAllBusinessDataCache")
	@ResponseBody
	public DataMsg cleanAllBusinessDataCache(HttpServletRequest request,@ModelAttribute DataMsg dataMsg) {
		try {
			if(getbizJedisPool() != null){
				ShardedJedis jedis= bizJedisPool.getResource();
			    Collection<Jedis> allJedis=jedis.getAllShards();
			    for(Jedis t:allJedis){
			    	logger.info("====flushAll=="+t.dbSize());
			    	t.flushAll();
			    }
			    bizJedisPool.returnResource(jedis);
			}
			dataMsg.setMsg("缓存清理成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			logger.error("cleanAllDataCache失败",e);
			dataMsg.setMsg("缓存清理失败");
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		return dataMsg;
	}
	
	
	/**
	 * @author
	 * @description:唯一性验证
	 * @date 2014-10-15 10:28:43
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryConfigCodeIsOk")
	@ResponseBody
	public DataMsg queryConfigCodeIsOk(HttpServletRequest request,
			SysDictDTO dto, @ModelAttribute DataMsg dataMsg)
			throws AbaboonException {
		dataMsg = super.initDataMsg(dataMsg);
		try {
			String code = this.getParameterString("code");

			if (code != null && code.length() > 0) {
				String url = jyptURL
						+ "/api/platform/sysconfig/SysConfigRest/queryConfigCodeIsOk/v1/"+code;
				ResponseMsg<String> responseMsg = RestClient.doGet(jyptAppId,
						url, new TypeReference<ResponseMsg<String>>() {
						});
				if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()))
					dataMsg.setData(responseMsg.getResponseBody());
				else
					throw new AbaboonException("执行queryConfigCodeIsOk异常：");
			}else{
				dataMsg.setData(0);
			}
		} catch (Exception e) {
			throw new AbaboonException("执行queryConfigCodeIsOk异常：", e);
		}
		return dataMsg;

	}
	
	/**
	 * @author
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-15 10:27:46
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			String url = jyptURL
					+ "/api/platform/sysconfig/SysConfigRest/get/v1/" + id;
			ResponseMsg<SysConfigDTO> responseMsg = RestClient.doGet(jyptAppId,
					url, new TypeReference<ResponseMsg<SysConfigDTO>>() {
					});
			SysConfigDTO dto = responseMsg.getResponseBody();
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}
}
