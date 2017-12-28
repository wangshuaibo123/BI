package com.jy.modules.platform.bizauth.vmtreeinfo.controller;

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

import com.jy.modules.platform.bizauth.vmtreeinfo.dto.VmtreeInfoDTO;
import com.jy.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService;
import com.jy.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.jy.modules.platform.sysauth.service.SysRoleUserService;
import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: VmtreeInfoController
 * @description: 定义 虚拟树表 控制层
 * @author: chen_gang
 */
@Controller
@Scope("prototype")
@RequestMapping("/vmtreeInfo")
public class VmtreeInfoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(VmtreeInfoController.class);

    @Autowired
    @Qualifier("com.jy.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService")
    private VmtreeInfoService service;

    @Autowired
    @Qualifier("com.jy.modules.platform.sysauth.service.SysRoleUserService")
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
            model.setViewName("platform/bizauth/vmtreeinfo/queryVmtreeInfo");
        } else if ("toAdd".equals(operate)) { // 跳转至 新增页面
            String id = this.getParameterString("pid");
            VmtreeInfoDTO dto = new VmtreeInfoDTO();
            dto.setOrgType(this.getParameterString("orgType"));
            dto.setParentId(Long.parseLong(id));
            model.addObject("dto", dto);
            model.setViewName("platform/bizauth/vmtreeinfo/addVmtreeInfo");
        } else if ("toManage".equals(operate)) {// 跳转至 维护页面
            String orgId = this.getParameterString("orgId");
            String orgType = this.getParameterString("orgType");
            model = this.queryOneDTO(orgId, orgType);
            model.addObject("orgId", orgId);
            model.addObject("orgType", orgType);
            model.setViewName("platform/bizauth/vmtreeinfo/updateVmTree");
        } else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
            String orgId = this.getParameterString("orgId");
            String orgType = this.getParameterString("orgType");
            model = this.queryOneDTO(orgId, orgType);
            model.addObject("orgId", orgId);
            model.setViewName("platform/bizauth/vmtreeinfo/updateVmtreeInfo");
        } else if ("toView".equals(operate)) {// 跳转至 查看页面
            String orgId = this.getParameterString("orgId");
            String orgType = this.getParameterString("orgType");
            model = this.queryOneDTO(orgId, orgType);
            model.setViewName("platform/bizauth/vmtreeinfo/viewVmtreeInfo");
        } else if ("toSelectTree".equals(operate)) {// 跳转至 查看页面
            String fillElementId = this.getParameterString("fillElementId");
            String url = this.getParameterString("url");
            model.addObject("fillElementId", fillElementId);
            model.addObject("url", url);
            model.setViewName("platform/bizauth/vmtreeinfo/selectTree");
        }else if ("toChangeBizPostion".equals(operate)) {// 跳转至 业务虚拟树快速调岗页面
        	 String orgId = this.getParameterString("orgId");
             String orgType = this.getParameterString("orgType");
             model = this.queryOneDTO(orgId, orgType);
             model.addObject("orgId", orgId);
             model.addObject("orgType", orgType);
             model.setViewName("platform/bizauth/vmtreeinfo/changeBizPostion");
        }

        return model;
    }

    /**
     * @author chen_gang
     * @description:查询分页列表
     * @date 2015-01-16 17:14:31
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListVmtreeInfo")
    @ResponseBody
    public DataMsg queryListVmtreeInfo(HttpServletRequest request, VmtreeInfoDTO dto, @ModelAttribute DataMsg dataMsg)
            throws Exception {

        Map<String, Object> searchParams = new HashMap<String, Object>();
        dto.setParentId(0L);
        searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
        params.setPageParameter(pageInfo);

        List<VmtreeInfoDTO> list = service.searchVmtreeInfoByPaging(params.getSearchParams());

        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }

    /**
     * @author chen_gang
     * @description:新增
     * @date 2015-01-16 17:14:31
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertVmtreeInfo")
    @ResponseBody
    public DataMsg insertVmtreeInfo(HttpServletRequest request, VmtreeInfoDTO dto, @ModelAttribute DataMsg dataMsg) {
        try {
            dto = (VmtreeInfoDTO) super.initDto(dto);
            dto.setCreateBy(dto.getOpUserId());
            if ("HR".endsWith(dto.getSourceType())) {
                // 去抓取该orgId在HR的对象
                // dto.setEndFlag(endFlag);
                service.insertVmtreeInfoForHR(dto);
            } else {
                service.insertVmtreeInfo(dto);
            }
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setStatus(DataMsg.STATUS_OK);
            Map<String, String> map = new HashMap<String, String>();
            map.put("ID", dto.getOrgId().toString());
            map.put("NAME", dto.getOrgName());
            map.put("orgType", dto.getOrgType());
            map.put("endFlag", dto.getEndFlag());
            map.put("sourceType", dto.getSourceType());
            map.put("PID", dto.getParentId().toString());
            dataMsg.setData(map);
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            dataMsg.setStatus(DataMsg.STATUS_FAILED);
            logger.error("执行方法insertVmtreeInfo异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author chen_gang
     * @description:编辑
     * @date 2015-01-16 17:14:31
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateVmtreeInfo")
    @ResponseBody
    public DataMsg updateVmtreeInfo(HttpServletRequest request, VmtreeInfoDTO dto, @ModelAttribute DataMsg dataMsg) {
        try {
            dto = (VmtreeInfoDTO) super.initDto(dto);

            service.updateVmtreeInfo(dto);

            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setStatus(DataMsg.STATUS_OK);
            // dataMsg.setData(this.makeJSONData(dto.getId()));
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            dataMsg.setStatus(DataMsg.STATUS_FAILED);
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author chen_gang
     * @description:删除
     * @date 2015-01-16 17:14:31
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteVmtreeInfo")
    @ResponseBody
    public DataMsg deleteVmtreeInfo(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {

        VmtreeInfoDTO dto = new VmtreeInfoDTO();
        String orgType = (String) this.getParameter("orgType");
        dto.setOrgType(orgType);
        String ids = (String) this.getParameter("ids");// 格式: 1,2,3
        dataMsg = super.initDataMsg(dataMsg);
        try {
            service.deleteVmtreeInfoByPrimaryKey(dto, ids);
            dataMsg.setMsg("删除成功");
            dataMsg.setStatus(DataMsg.STATUS_OK);
        }
        catch (Exception e) {
            dataMsg.setStatus(DataMsg.STATUS_FAILED);
            dataMsg.failed(e.getMessage());
            logger.error("执行方法deleteSysResource异常：", e);

        }

        return dataMsg;
    }

    /**
     * @author chen_gang
     * @description:通过主键查询 其明细信息
     * @date 2015-01-16 17:14:31
     * @param orgId
     * @return
     */
    private ModelAndView queryOneDTO(String orgId, String orgType) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try {
            VmtreeInfoDTO dto = service.queryVmtreeInfoByPrimaryKey(orgId, orgType);
            // 将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }
        catch (Exception e) {
            throw new AbaboonException("执行queryOneDTO异常：", e);
        }
        return model;
    }

    /**
     * @title: queryTreeVMOrg
     * @author
     * @description:获取虚拟组织的树
     * @date 2015年1月17日 上午11:16:43
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     * @throws
     */
    @RequestMapping(value = "/queryTreeVMOrg")
    @ResponseBody
    public List<Map<String, String>> queryTreeVMOrg(HttpServletRequest request, VmtreeInfoDTO dto,
            @ModelAttribute DataMsg dataMsg) throws Exception {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        String orgId = request.getParameter("orgId");
        String orgType = request.getParameter("orgType");
        searchParams.put("orgType", orgType);
        searchParams.put("orgId", Long.parseLong(orgId));
        List<VmtreeInfoDTO> list = service.searchVmtreeInfoForTree(searchParams);
        return treeData(list);// 组织树的数据
    }

    /**
     * 
     * 查询组织机构树对外的接口
     * 
     */
    @RequestMapping(value = "/queryVmTreeSysOrg")
    @ResponseBody
    public List<Map<String, String>> queryVmTreeSysOrg(HttpServletRequest request, VmtreeInfoDTO dto,
            @ModelAttribute DataMsg dataMsg) throws Exception {

        String orgType = (String) request.getParameter("orgType");
        List<VmtreeInfoDTO> list = new ArrayList<VmtreeInfoDTO>();
        Map<String, Object> searchParams = new HashMap<String, Object>();
        if ("".equals(orgType) || orgType == null) {} else {

            searchParams.put("orgType", orgType);
        }
        list = service.searchVmtreeInfoForAllTree(searchParams);
        return vmTreeData(list, orgType);// 组织树的数据
    }

    /**
     * @title: queryTreeVMOrgByOrgType
     * @author
     * @description: 根据orgType生成树
     * @date 2015年1月17日 下午5:07:58
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     * @throws
     */
    @RequestMapping(value = "/queryTreeVMOrgByOrgType")
    @ResponseBody
    public List<Map<String, String>> queryTreeVMOrgByOrgType(HttpServletRequest request, VmtreeInfoDTO dto,
            @ModelAttribute DataMsg dataMsg) throws Exception {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        String orgType = request.getParameter("orgType");
        String orgId = request.getParameter("orgId");
        String checkedIds = request.getParameter("checkedIds");

        UserInfo user = sessionAPI.getCurrentUserInfo();

        // 获取当前用户管理的机构
        Map<String, Object> userParam = new HashMap<String, Object>();
        userParam.put("orgId", user.getOrgId());
        userParam.put("userId", user.getUserId());
        SysRoleUserDTO sDto = sysRoleUserService.findSysRoleOrgByCurrentUser(userParam);
        // 获取当前用户管理的机构

        if (sDto != null)
            orgId = sDto.getTargetId().toString();

        dto.setOrgType(orgType);
        if (orgId == null || "".equals(orgId)) {
            dto.setParentId(0L);
        }
        searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
        params.setPageParameter(pageInfo);
        if (orgId == null || "".equals(orgId)) {
            List<VmtreeInfoDTO> reList = service.searchVmtreeInfoByPaging(params.getSearchParams());
            if (reList != null && reList.size() == 1) {
                orgId = reList.get(0).getOrgId().toString();
            }
        }
        List<VmtreeInfoDTO> list = null;
        try {
            searchParams = new HashMap<String, Object>();
            searchParams.put("orgId", Long.parseLong(orgId));
            searchParams.put("orgType", orgType);
            list = service.searchVmtreeInfoForTree(searchParams);
        }
        catch (Exception e) {
            // TODO: handle exception
            dataMsg.setStatus(DataMsg.STATUS_FAILED);
            logger.error("执行方法queryTreeVMOrgByOrgType异常：", e);
        }
        
        if(StringUtils.isNotEmpty(checkedIds)){
            return treeData(list, checkedIds);// 组织树的数据,并选中
        }else{
            return treeData(list);// 组织树的数据
        }
        
    }

    private List<Map<String, String>> treeData(List<VmtreeInfoDTO> data) {
        if (data != null && data.size() > 0) {
            List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
            for (VmtreeInfoDTO dto : data) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("ID", dto.getOrgId().toString());
                map.put("NAME", dto.getOrgName());
                map.put("orgType", dto.getOrgType());
                map.put("endFlag", dto.getEndFlag());
                map.put("sourceType", dto.getSourceType());
                map.put("PID", dto.getParentId().toString());
                // map.put("open", "true");
                maps.add(map);
            }
            return maps;
        } else {
            return null;
        }
    }

    /**
     * @Title: treeData
     * @Description: 列表数据转换为树形，并选中checkedIds中的值
     * @param @param data
     * @param @param checkedIds 格式： 3,4,5
     * @param @return 设定文件
     * @return List<Map<String,String>> 返回类型
     * @throws
     */
    private List<Map<String, String>> treeData(List<VmtreeInfoDTO> data, String checkedIds) {

        if (data != null && data.size() > 0) {
            List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
            for (VmtreeInfoDTO dto : data) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("ID", dto.getOrgId().toString());
                map.put("NAME", dto.getOrgName());
                map.put("orgType", dto.getOrgType());
                map.put("endFlag", dto.getEndFlag());
                map.put("sourceType", dto.getSourceType());
                map.put("PID", dto.getParentId().toString());
                if (checkedIds.equals(dto.getOrgId().toString())
                        || checkedIds.startsWith(dto.getOrgId().toString() + ",")
                        || checkedIds.contains("," + dto.getOrgId().toString() + ",")
                        || checkedIds.endsWith("," + dto.getOrgId().toString())) {
                    map.put("checked", "true");
                }
                // map.put("open", "true");
                maps.add(map);
            }
            return maps;
        } else {
            return null;
        }
    }

    private List<Map<String, String>> vmTreeData(List<VmtreeInfoDTO> data, String orgType) {
        if (data != null && data.size() > 0) {
            List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
            for (VmtreeInfoDTO dto : data) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("ID", dto.getOrgId().toString() + dto.getOrgType());
                map.put("orgId", dto.getOrgId().toString());
                map.put("NAME", dto.getOrgName());
                map.put("orgType", dto.getOrgType());
                map.put("endFlag", dto.getEndFlag());
                map.put("sourceType", dto.getSourceType());
                map.put("PID", dto.getParentId().toString() + dto.getOrgType());
                map.put("parentId", dto.getParentId().toString());
                map.put("open", "false");
                if (StringUtils.isEmpty(orgType)) {
                    maps.add(map);
                } else if (StringUtils.isNotEmpty(orgType) && dto.getParentId() > 0) {
                    // 如何指定某一个虚拟树 则排序parentId是0的数据
                    maps.add(map);
                }
            }
            return maps;
        } else {
            return null;
        }
    }

}
