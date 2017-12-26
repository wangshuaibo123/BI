package com.fintech.modules.platform.bizauth.vmuservmorgmap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.URLDecoder;

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

import com.fintech.modules.platform.bizauth.vmdatapriv.service.VmdataPrivService;
import com.fintech.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService;
import com.fintech.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService;
import com.fintech.modules.platform.bizauth.vmuservmorgmap.dto.VmuserVmorgMapDTO;
import com.fintech.modules.platform.bizauth.vmuservmorgmap.service.VmuserVmorgMapService;
import com.fintech.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.fintech.modules.platform.sysauth.service.SysRoleUserService;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: VmuserVmorgMapController
 * @description: 定义 员工虚拟组织关系表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/vmuserVmorgMap")
public class VmuserVmorgMapController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(VmuserVmorgMapController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.bizauth.vmuservmorgmap.service.VmuserVmorgMapService")
    private VmuserVmorgMapService service;

    @Autowired
    @Qualifier("com.fintech.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService")
    private VmtreeInfoService vmtreeservice;

    @Autowired
    @Qualifier("com.fintech.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService")
    private VmruleMappingService vmrulemapservice;
    
    
    @Autowired
    @Qualifier("com.fintech.modules.platform.bizauth.vmdatapriv.service.VmdataPrivService")
    private VmdataPrivService vmdataservice;
    
    @Autowired
    @Qualifier("com.fintech.modules.platform.sysauth.service.SysRoleUserService")
    private SysRoleUserService sysRoleUserService;

    @Autowired
    private SessionAPI sessionAPI;

    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
            String orgId = (String) this.getParameter("orgId");
            String orgType = (String) this.getParameter("orgType");
            String orgName = (String) this.getParameter("orgName");
            orgName = URLDecoder.decode(orgName, "utf-8");
            model.addObject("orgId", orgId);
            model.addObject("orgType", orgType);
            model.addObject("orgName", orgName);
            model.setViewName("platform/bizauth/vmuservmorgmap/queryVmSysUser");
        } else if ("toAdd".equals(operate)) { // 跳转至 新增页面
            String orgId = (String) this.getParameter("orgId");
            String orgType = (String) this.getParameter("orgType");
            String orgName = (String) this.getParameter("orgName");
            UserInfo user = sessionAPI.getCurrentUserInfo();
            orgName = URLDecoder.decode(orgName, "utf-8");
            model.addObject("orgId", orgId);
            model.addObject("orgType", orgType);
            model.addObject("orgName", orgName);
            model.addObject("userInfo", user);
            model.setViewName("platform/bizauth/vmuservmorgmap/addVmSysUser");
        } else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
            String id = this.getParameterString("id");
            model = this.queryOneDTO(id);
            model.setViewName("platform/bizauth/vmuservmorgmap/updateVmuserVmorgMap");
        } else if ("toView".equals(operate)) {// 跳转至 查看页面
            String id = this.getParameterString("id");
            model = this.queryOneDTO(id);
            model.setViewName("platform/bizauth/vmuservmorgmap/viewVmuserVmorgMap");
        } else if ("toManagePage".equals(operate)) {// 跳转至 虚拟树管理页面
            String orgType = (String) this.getParameter("orgType");
            model.addObject("orgType", orgType);
            
            model.setViewName("platform/bizauth/vmuservmorgmap/manageVmSysUser");
        }

        return model;
    }

    /**
     * @author
     * @description:查询分页列表
     * @date 2015-01-16 17:15:01
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListVmuserVmorgMap")
    @ResponseBody
    public DataMsg queryListVmuserVmorgMap(HttpServletRequest request, VmuserVmorgMapDTO dto,
            @ModelAttribute DataMsg dataMsg) throws Exception {

        Map<String, Object> searchParams = new HashMap<String, Object>();

        String orgId = request.getParameter("orgId");
        String orgType = request.getParameter("orgType");
        String userName = request.getParameter("userName");
        String orgName = request.getParameter("orgName");
        
        if(orgId==null || orgId.equals("") || orgId.equals("0")){//初始时候，将用户的org获取
            UserInfo user = sessionAPI.getCurrentUserInfo();

            // 获取当前用户管理的机构
            Map<String, Object> userParam = new HashMap<String, Object>();
            userParam.put("orgId", user.getOrgId());
            userParam.put("userId", user.getUserId());
            SysRoleUserDTO sDto = sysRoleUserService.findSysRoleOrgByCurrentUser(userParam);
            // 获取当前用户管理的机构

            if (sDto != null)
                orgId = sDto.getTargetId().toString();
        }
        
        if (orgId != null && !"".equals(orgId)) {
            searchParams.put("orgId", Long.parseLong(orgId));
        }
        if (orgType != null && !"".equals(orgType)) {
            searchParams.put("orgType", orgType);
        }
        
        if (userName != null && !"".equals(userName)) {
            searchParams.put("userName", userName);
        }
        
        if (orgName != null && !"".equals(orgName)) {
            searchParams.put("orgName", orgName);
        }
        
        searchParams.put("dto", dto);
        searchParams.put("orgIds", service.findVmuserVmorgMapTreeByOrgId(searchParams));
        // searchParams.put("orgId", Long.parseLong(orgId));

        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
        params.setPageParameter(pageInfo);

        List<VmuserVmorgMapDTO> list = service.searchVmuserVmorgMapByPaging(params.getSearchParams());

        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }

    /**
     * @author
     * @description:查询分页列表
     * @date 2015-01-16 17:15:01
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListVmuserMap")
    @ResponseBody
    public DataMsg queryListVmuserMap(HttpServletRequest request, SysUserDTO dto, @ModelAttribute DataMsg dataMsg)
            throws Exception {

        String orgId = this.getParameterString("orgId");
        String showLowerUser = this.getParameterString("showLowerUser");
        String orgType = this.getParameterString("orgType");
        
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("dto", dto);
        searchParams.put("orgId", orgId);
        searchParams.put("showLowerUser", showLowerUser);
        searchParams.put("orgType", orgType);
        searchParams.put("orgIds", service.findVmuserVmorgMapTreeByOrgId(searchParams));
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
        params.setPageParameter(pageInfo);

        List<SysUserDTO> list = service.searchVmuserByPaging(params.getSearchParams());

        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
        
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2015-01-16 17:15:01
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListVmuser")
    @ResponseBody
    public DataMsg queryListVmuser(HttpServletRequest request, SysUserDTO dto, @ModelAttribute DataMsg dataMsg)
            throws Exception {

        String orgId = this.getParameterString("orgId");
        String orgType=this.getParameterString("orgType");
        
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("dto", dto);
        searchParams.put("orgId", orgId);
        searchParams.put("orgType", orgType);
        searchParams.put("orgIds", service.findVmuserVmorgMapTreeByOrgId(searchParams));
        //是否包含 orgId 下级的user
        searchParams.put("showLowerUser", this.getParameterString("showLowerUser"));
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
        params.setPageParameter(pageInfo);
        
        List<SysUserDTO> list = service.searchVmuserByPaging(params.getSearchParams());

        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    
    
    
    

    /**
     * @author
     * @description:新增
     * @date 2015-01-16 17:15:01
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertVmuserVmorgMap")
    @ResponseBody
    public DataMsg insertVmuserVmorgMap(HttpServletRequest request, VmuserVmorgMapDTO dto,
            @ModelAttribute DataMsg dataMsg) {
        try {
            dto = (VmuserVmorgMapDTO) super.initDto(dto);
            // dto.setCreateBy(dto.getOpUserId());
            String useridstr = request.getParameter("userIds");
            if (useridstr.indexOf(",") > 0) {
                String userids[] = useridstr.split(",");
                if (userids.length > 0) {
                    for (String userid : userids) {
                        dto.setUserId(Long.parseLong(userid));
                        service.insertVmuserVmorgMap(dto,"add");
                    }
                }
            } else {
                dto.setUserId(Long.parseLong(useridstr));
                service.insertVmuserVmorgMap(dto,"add");
            }

            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:编辑
     * @date 2015-01-16 17:15:01
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateVmuserVmorgMap")
    @ResponseBody
    public DataMsg updateVmuserVmorgMap(HttpServletRequest request, VmuserVmorgMapDTO dto,
            @ModelAttribute DataMsg dataMsg) {
        try {
            dto = (VmuserVmorgMapDTO) super.initDto(dto);

            service.updateVmuserVmorgMap(dto);

            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:删除
     * @date 2015-01-16 17:15:01
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteVmuserVmorgMap")
    @ResponseBody
    public DataMsg deleteVmuserVmorgMap(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {

        BaseDTO dto = super.initDto(null);
        String ids = (String) this.getParameter("ids");// 格式: 1,2,3
        String userId = request.getParameter("userid");
        String orgType = request.getParameter("orgType");

        dataMsg = super.initDataMsg(dataMsg);
        try {
            if (userId.indexOf(",") > 0) {
                String[] resStr = userId.split(",");
                for (String str : resStr) {
                    // 删除数据权限和MAPPING 参数：(人员ID 删除类型：机构|人员 ,树类型)
                    vmrulemapservice.deletePrivApi(str, "1", orgType);
                    service.deleteVmuserVmorgMapByUserId(str, orgType);
                }
            } else {
                vmrulemapservice.deletePrivApi(userId, "1", orgType);
                service.deleteVmuserVmorgMapByUserId(userId, orgType);
            }
            // service.deleteVmuserVmorgMapByPrimaryKey(dto,ids);
            dataMsg.setMsg("删除成功");
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法deleteSysResource异常：", e);

        }

        return dataMsg;
    }
    
    
    /**
     * 根据userId和orgId删除“员工虚拟组织关系表”数据，同时删除“数据权限表”和“映射表”数据
     * 逻辑：
     * 1、只删除“员工虚拟组织关系表VMUSER_VMORG_MAP”下，当前用户，当前组织的数据，如果当前用户还在别的组织下，那条数据不删除
     * 2、由于“数据权限表”和“映射表”是以userId维度去管理的，所以，只有当“员工虚拟组织关系表VMUSER_VMORG_MAP”下没有此用户数据后，才删除“数据权限表”和“映射表”数据
     * @param request
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/deleteVmuserVmorgMapByUserIdsAndOrgIds")
    @ResponseBody
    public DataMsg deleteVmuserVmorgMapByUserIdsAndOrgIds(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {

        String userIdsAndOrgIdsStr = request.getParameter("userIdsAndOrgIdsStr");
        String orgType = request.getParameter("orgType");

        dataMsg = super.initDataMsg(dataMsg);
        try{
        	service.deleteVmuserVmorgMapAndPrivByUserIdAndOrgId(userIdsAndOrgIdsStr, orgType);
            
            dataMsg.setMsg("删除成功");
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法deleteSysResource异常：", e);
        }

        return dataMsg;
    }
    
    
    /**
     * @author
     * @description:刷新数据权限功能
     * @date 2015-01-26 17:15:01
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/refreshVmuserVmorgMap")
    @ResponseBody
    public DataMsg refreshVmuserVmorgMap(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {

        BaseDTO dto = super.initDto(null);
        String ids = (String) this.getParameter("ids");// 格式: 1,2,3
        String userId = request.getParameter("userid");
        String orgId = request.getParameter("orgId");
        String orgType = request.getParameter("orgType");
        //初始化DTO
        VmuserVmorgMapDTO vmuserorgdto =new VmuserVmorgMapDTO();
        vmuserorgdto.setOrgId(Long.parseLong(orgId));
        vmuserorgdto.setOrgType(orgType);
        
        dataMsg = super.initDataMsg(dataMsg);
        try {
            if (userId.indexOf(",") > 0) {
                String[] resStr = userId.split(",");
                for (String str : resStr) {
                    // 删除数据权限参数：(人员ID 删除类型：机构|人员 ,树类型)
                	vmdataservice.deletePrivByuserId(str, "1", orgType);
                    //先判断是组织还是人员？
                    vmuserorgdto.setUserId(Long.parseLong(str));
                    service.insertVmuserVmorgMap(vmuserorgdto,"ref");
                    
                }
            } else {
            	vmdataservice.deletePrivByuserId(userId, "1", orgType);
                //先判断是组织还是人员？
                vmuserorgdto.setUserId(Long.parseLong(userId));
                service.insertVmuserVmorgMap(vmuserorgdto,"ref");
            }
            dataMsg.setMsg("刷新成功");
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法deleteSysResource异常：", e);

        }

        return dataMsg;
    }
    
    
    

    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2015-01-16 17:15:01
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try {
            VmuserVmorgMapDTO dto = service.queryVmuserVmorgMapByPrimaryKey(id);
            // 将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }
        catch (Exception e) {
            throw new AbaboonException("执行queryOneDTO异常：", e);
        }
        return model;
    }
    
    //
    /**
     * @author
     * @description:校验对象是否重复
     * @date 2015-01-16 17:15:01
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/validateVmuserVmorgMap")
    @ResponseBody
    public DataMsg validateVmuserVmorgMap(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {
        try {
            dataMsg = super.initDataMsg(dataMsg);
            String userIds = this.getParameterString("userId");
        	String orgId = this.getParameterString("orgId");
        	String orgType = this.getParameterString("orgType");
        	
        	if (userIds.indexOf(",") > 0) {
        	String struserId[]=userIds.split(",");
        	for(String userId:struserId){
        		service.validateVmuserVmorgMap(userId,orgId,orgType);
        	}
        	}else{
        		service.validateVmuserVmorgMap(userIds,orgId,orgType);
        	}
        
            dataMsg.setMsg("");
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法validateVmuserVmorgMap异常：", e);
        }
        return dataMsg;
    }
    
    /**
     * @author
     * @description:刷新数据权限功能
     * @date 2015-07-10 17:15:01
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/cleanVmuserVmorgMap")
    @ResponseBody
    public DataMsg cleanVmuserVmorgMap(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {

        BaseDTO dto = super.initDto(null);
        String orgId = request.getParameter("orgId");
        String orgType = request.getParameter("orgType");
        //初始化DTO
        VmuserVmorgMapDTO vmuserorgdto =new VmuserVmorgMapDTO();
        vmuserorgdto.setOrgId(Long.parseLong(orgId));
        vmuserorgdto.setOrgType(orgType);
        
        dataMsg = super.initDataMsg(dataMsg);
        try {
        	if(orgId==null || orgId.equals("") || orgId.equals("0")){//初始时候，将用户的org获取
        		UserInfo user = sessionAPI.getCurrentUserInfo();

        		// 获取当前用户管理的机构
        		Map<String, Object> userParam = new HashMap<String, Object>();
        		userParam.put("orgId", user.getOrgId());
        		userParam.put("userId", user.getUserId());
        		SysRoleUserDTO sDto = sysRoleUserService.findSysRoleOrgByCurrentUser(userParam);
        		// 获取当前用户管理的机构

        		if (sDto != null)
        			orgId = sDto.getTargetId().toString();
        		else 
        			orgId = user.getOrgId().toString();
        	}
        	service.modifyCleanVmorgMap(orgId, orgType);
        	dataMsg.setStatus(DataMsg.STATUS_OK);
        	dataMsg.setMsg("离职人员业务用户配置清理成功");
        }
        catch (Exception e) {
        	dataMsg.setStatus(DataMsg.STATUS_FAILED);
            dataMsg.failed(e.getMessage());
            logger.error("执行方法deleteSysResource异常：", e);

        }

        return dataMsg;
    }
}
