package com.jy.modules.platform.sysMenu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jy.modules.platform.sysMenu.dto.SysMenuDTO;
import com.jy.modules.platform.sysMenu.service.SysMenuService;
import com.jy.modules.platform.sysauth.dto.SysResourceDTO;
import com.jy.modules.platform.sysauth.service.SysResourceService;
import com.jy.modules.platform.sysauth.service.shiro.AuthServiceImpl;
import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restclient.http.RestClient;
import com.jy.platform.restclient.http.RestService;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;
/**
 * @classname: SysMenuController
 * @description: 定义  菜单管理表 控制层
 * @author:  Administrator
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysMenuController.class);
	private String jyptAppId = "jypt"; //rest服务appId
	private String jyptURL = RestService.getServiceUrl(jyptAppId);//rest服务地址
    @Autowired
    private SessionAPI sessionAPI;
    
    @Autowired
    @Qualifier("com.jy.modules.platform.sysMenu.service.SysMenuService")
    private SysMenuService service;
    
	@Autowired
    @Qualifier("com.jy.modules.platform.sysauth.service.SysResourceService")
	private SysResourceService sysResourceService;
	
    @Autowired
    @Qualifier("shiroAuthService")
	private AuthServiceImpl authServiceImpl;
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysMenu/querySysMenu");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysMenu/addSysMenu");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysMenu/updateSysMenu");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysMenu/viewSysMenu");
        }else if("toTreePage".equals(operate)){//跳转至 查看页面
        	model.setViewName("platform/sysMenu/treeSysMenu");
        }
        return model;
    }
    /**
     * 首页菜单显示专用，有权限控制
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/querySysMenuForHome")
    @ResponseBody
    public DataMsg querySysMenuForHome(HttpServletRequest request, SysMenuDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	//Map<String, Object> searchParams = new HashMap<String, Object>();
    	//searchParams.put("dto", dto);
       // QueryReqBean params = new QueryReqBean();
       // params.setSearchParams(searchParams);
    	//PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		//params.setPageParameter(pageInfo);
		
        //List<SysMenuDTO> list = service.searchSysMenuByPaging(params.getSearchParams());
		 UserInfo userinfo = sessionAPI.getCurrentUserInfo();
		 if(userinfo ==  null) throw new Exception("当前登录用户为null!!!");
		 List<SysMenuDTO> list = authServiceImpl.findMenusByUserId(userinfo.getUserId());
		 //对菜单链接进行转义处理,例如冒号
		 for (SysMenuDTO sysMenuDTO : list) {
			 String menuUrl = StringUtils.isNotEmpty(sysMenuDTO.getMenuUrl()) == false ? "" : sysMenuDTO.getMenuUrl();
			 if(menuUrl.contains("http")&&menuUrl.contains("&#58;")){
				 menuUrl = menuUrl.replaceAll("&#58;", ":");
				 sysMenuDTO.setMenuUrl(menuUrl);
			 }
		}
        dataMsg.setData(list);
        //dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    /**
     * @author Administrator
     * @description:查询分页列表
     * @date 2014-10-14 20:53:04
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysMenu")
    @ResponseBody
    public DataMsg queryListSysMenu(HttpServletRequest request, SysMenuDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        //List<SysMenuDTO> list = service.searchSysMenuByPaging(params.getSearchParams());
		List<SysMenuDTO> list = service.searchAllSysMenu(searchParams);
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    
    /**Description: 获得树形的菜单数据
     * Create Date: 2014年10月25日上午10:25:54<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryTreeSysMenu")
    @ResponseBody
    public List<Map<String, String>> queryTreeSysMenu(HttpServletRequest request, SysMenuDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	dto.setValidateState("1");
    	searchParams.put("dto", dto);
    	//调整成 按order_by 排序的
    	List<SysMenuDTO> list = service.searchSysMenuForHome(searchParams);//service.searchSysMenu(searchParams);
        return treeData(list);//组织树的数据
    }

    /**
     * @author Administrator
     * @description:新增
     * @date 2014-10-14 20:53:04
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysMenu")
    @ResponseBody
    public DataMsg insertSysMenu(HttpServletRequest request, SysMenuDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysMenuDTO)super.initDto(dto);
        	//设置版本号
        	dto.setVersion(SysMenuDTO.generatorVersion());
        	//将冒号转义 ":"->"&#58;"
        	String menuUrl = StringUtils.isNotEmpty(dto.getMenuUrl()) == false ? "" : dto.getMenuUrl();
        	 if(menuUrl.contains("http")&&menuUrl.contains(":")){
				 menuUrl = menuUrl.replaceAll(":","&#58;");
				 dto.setMenuUrl(menuUrl);
			 }
            service.insertSysMenu(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(treeNode(dto));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author Administrator
     * @description:编辑
     * @date 2014-10-14 20:53:04
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysMenu")
    @ResponseBody
    public DataMsg updateSysMenu(HttpServletRequest request, SysMenuDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysMenuDTO)super.initDto(dto);
        	//将冒号转义  ":"->"&#58;"
        	String menuUrl = StringUtils.isNotEmpty(dto.getMenuUrl()) == false ? "" : dto.getMenuUrl();
        	 if(menuUrl.contains("http")&&menuUrl.contains(":")){
				 menuUrl = menuUrl.replaceAll(":","&#58;");
				 dto.setMenuUrl(menuUrl);
			 }
            service.updateSysMenu(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(treeNode(dto));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author Administrator
     * @description:删除
     * @date 2014-10-14 20:53:04
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysMenu")
    @ResponseBody
    public DataMsg deleteSysMenu(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysMenuByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    
    /**Description: 唯一性校验相关字段
     * Create Date: 2014年10月20日下午5:24:08<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param request
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/checkUnique")
    @ResponseBody
    public DataMsg checkUnique(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
        try{
        	String id = (String)this.getParameter("id");
        	String menuCode = (String)this.getParameter("menuCode");
        	
        	Map<String, Object> searchParams = new HashMap<String, Object>();
        	SysMenuDTO dto = new SysMenuDTO();
        	if(menuCode!=null && !"".equals(menuCode)){
        		dto.setMenuCode(menuCode);
        	}
        	
        	if(menuCode!=null && !"".equals(menuCode)){
        		searchParams.put("dto", dto);
        		QueryReqBean params = new QueryReqBean();
        		params.setSearchParams(searchParams);
        		String url= jyptURL+"/api/platform/SysMenuRest/searchNoCache/v1";
        		ResponseMsg<QueryRespBean<SysMenuDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params,new TypeReference<ResponseMsg<QueryRespBean<SysMenuDTO>>>(){});
        		List<SysMenuDTO> list = responseMsg.getResponseBody().getResult();
        		int resultInt = list.size();
        		if(resultInt==0 ){
        			
        		} else if ( id != null && id.equals(list.get(0).getId())){
        			
        		} else{
        			dataMsg.failed("数据唯一校验失败！");
        		}
        	}
			 
        }catch(Exception e){
        	logger.error(e.getMessage());
			dataMsg.failed(e.getMessage());
        }
        return dataMsg;
    }
    
    
    /**
     * @author Administrator
     * @description:通过主键查询 其明细信息
     * @date 2014-10-14 20:53:04
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysMenuDTO dto = service.querySysMenuByPrimaryKey(id);
        	//同时也取出resource
        	if(dto.getResourceId()!=null){
        		SysResourceDTO resource = sysResourceService.querySysResourceByPrimaryKey(""+dto.getResourceId());
        		model.addObject("resource",resource);
        	}
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
	/**Description: 转换成树节点集合
	 * Create Date: 2014年10月12日下午9:47:06<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param data
	 * @return
	 */
	private List<Map<String, String>> treeData(List<SysMenuDTO> data) {
		if(data!=null && data.size()>0){
			List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
			for(SysMenuDTO sysMenu: data){
				Map<String, String>  map = new HashMap<String, String>();
				map.put("ID", sysMenu.getId().toString());//设置menu id
				map.put("NAME", sysMenu.getMenuName());//设置menu 名称
				map.put("PID", sysMenu.getParentId().trim());//设置menu父id
				maps.add(map);
			}
			return maps;
		}else{
			return null;
		}
	}
	
	/**Description: 转换成树节点
	 * Create Date: 2014年10月12日下午9:47:06<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param data
	 * @return
	 */
	private Map<String, String> treeNode(SysMenuDTO sysMenu){
		Map<String, String>  map = new HashMap<String, String>();
		map.put("ID", sysMenu.getId().toString());
		map.put("NAME", sysMenu.getMenuName());
		map.put("PID", sysMenu.getParentId().trim());
		return map;
	}
}
