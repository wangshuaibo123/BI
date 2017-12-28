package com.jy.modules.platform.leave.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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

import com.jy.modules.platform.leave.dto.LeaveInfoDTO;
import com.jy.modules.platform.leave.service.LeaveInfoService;
import com.jy.modules.platform.sysorg.dao.SysOrgUserDao;
import com.jy.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.jy.modules.platform.sysorg.dto.SysUserDTO;
import com.jy.modules.platform.sysorg.service.SysUserService;
import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;
import com.jy.platform.tools.common.DateUtil;
import com.jy.platform.tools.common.SysConstant;

/**
 * @classname: LeaveInfoController
 * @description: 定义  员工请假表 控制层
 * @author:  JY-IT-D001
 */
@Controller
@Scope("prototype")
@RequestMapping("/system/leaveInfo")
public class LeaveInfoController extends BaseController{
	private static final Logger logger =  LoggerFactory.getLogger(LeaveInfoController.class);

	@Autowired
	@Qualifier("com.jy.modules.platform.leave.service.LeaveInfoService")
	private LeaveInfoService service;

	@Autowired
	private SessionAPI sessionAPI;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysOrgUserDao  sysOrgUserDao;
	
	private static SimpleDateFormat dateMat = new SimpleDateFormat("yyyy-MM-dd");


	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 * @throws Exception 
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(HttpServletRequest request,@PathVariable("operate") String operate) throws Exception {
		ModelAndView model = new ModelAndView();

		if("toQueryPage".equals(operate)){//跳转至 查询页面
			String isAdmin = request.getParameter("isAdmin");
			model.addObject("isAdmin", isAdmin);
			model.setViewName("platform/leaveinfo/queryLeaveInfo");
			
		}else if("toLeave".equals(operate)){ //跳转至 新增页面
			model.setViewName("platform/leaveinfo/toLeaveInfo");
		}else if("toView".equals(operate)){//跳转至 查看页面
			String ids = getParameterString("ids");
			model.addObject("userName",this.getAllLeaveUserName(ids));
			model.addObject("ids",ids);
			model.addObject("leaveType",getParameterString("leaveType"));
			model.setViewName("platform/leaveinfo/viewLeaveInfo");
		}else if("toAddLeave".equals(operate)){
			model.setViewName("platform/leaveinfo/addLeave");
		}else if("toAddAgencyLeave".equals(operate)){
			model.setViewName("platform/leaveinfo/addAgencyLeave");
		}

		return model;
	}

	/**
	 * @author JY-IT-D001
	 * @description:查询分页列表
	 * @date 2014-12-03 13:59:17
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListLeaveInfo")
	@ResponseBody
	public DataMsg queryListLeaveInfo(HttpServletRequest request, LeaveInfoDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		searchParams.put("curUserId", userInfo.getUserId());
		String isAdmin = request.getParameter("isAdmin");
		searchParams.put("isAdmin", isAdmin);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<LeaveInfoDTO> list = service.searchLbTLeaveInfoByPaging(params.getSearchParams());

		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}


	/**
	 * @author JY-IT-D001
	 * @description:新增
	 * @date 2014-12-03 13:59:17
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertLeaveInfo")
	@ResponseBody
	public DataMsg insertLbTLeaveInfo(HttpServletRequest request, LeaveInfoDTO dto,@ModelAttribute DataMsg dataMsg){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		try {
			String startDate = this.getParameterString("startTimeStr");
			String endDate = this.getParameterString("endTimeStr");
			if(StringUtils.isNotBlank(startDate)){
				dto.setStartTime(dateMat.parse(startDate));
			}
			if(StringUtils.isNotBlank(endDate)){
				dto.setEndTime(dateMat.parse(endDate));
			}			
			String leaveType = dto.getLeaveType();
			UserInfo userInfo = sessionAPI.getCurrentUserInfo();
			//本人委托
			if("2".equals(leaveType)){
				
				boolean isExist = isExist(String.valueOf(userInfo.getUserId()));
				if(isExist){
					dataMsg = super.initDataMsg(dataMsg);
					dataMsg.setStatus("failed");
					dataMsg.setMsg("该用户已请假，请先取消请假！");
					return dataMsg;
				}
				dto.setLeaveUserId(String.valueOf(userInfo.getUserId()));
				dto.setStatus(SysConstant.LEAVEING);
				dto.setValidateState("1");
				dto.setUserName(userInfo.getUserName());
				dto.setOrgName(userInfo.getOrgName());
				dto.setOrgId(userInfo.getOrgId());								
				SysUserDTO sysUser = new SysUserDTO();
				sysUser.setId(new Long(dto.getLeaveUserId()));
				searchParams.put("dto", sysUser);
				List<SysUserDTO> sysUsers = sysUserService.searchByParam(searchParams);
				if(sysUsers.size()> 0){					
					List<SysOrgUserDTO>  sysorg = sysUsers.get(0).getSysOrgUserDTOs();
					for (SysOrgUserDTO sysOrgUserDTO : sysorg) {
						//一个人可以有多个机构，但只有一个是主要机构 1代表主要机构
						if("1".equals(sysOrgUserDTO.getIsMainOrg())){
							dto.setUserLevel(sysOrgUserDTO.getPositionName());
						}
					}
				}
			}else if("1".equals(leaveType)){//代理委托
				boolean isExist = isExist(String.valueOf(String.valueOf(dto.getLeaveUserId())));
				if(isExist){
					dataMsg = super.initDataMsg(dataMsg);
					dataMsg.setStatus("failed");
					dataMsg.setMsg("该用户已请假，请先取消请假！");
					return dataMsg;
				}
				dto.setStatus(SysConstant.LEAVEING);
				dto.setValidateState("1");
				SysUserDTO sysUser = new SysUserDTO();
				sysUser.setId(new Long(dto.getLeaveUserId()));
				searchParams.put("dto", sysUser);
				List<SysUserDTO> sysUsers = sysUserService.searchByParam(searchParams);
				if(sysUsers.size()> 0){
					dto.setLeaveUserId(String.valueOf(dto.getLeaveUserId()));
					dto.setUserName(sysUsers.get(0).getUserName());
					List<SysOrgUserDTO>  sysorg = sysUsers.get(0).getSysOrgUserDTOs();
					for (SysOrgUserDTO sysOrgUserDTO : sysorg) {
						//一个人可以有多个机构，但只有一个是主要机构 1代表主要机构
						if("1".equals(sysOrgUserDTO.getIsMainOrg())){
							dto.setOrgName(sysOrgUserDTO.getOrgName());
							dto.setUserLevel(sysOrgUserDTO.getPositionName());
							dto.setOrgId(sysOrgUserDTO.getOrgId());
						}
					}
				}
			}
			
			dto.setCreateBy(userInfo.getUserId());
			service.insertLbTLeaveInfo(dto);
			dataMsg = super.initDataMsg(dataMsg);
			dataMsg.setMsg("新增成功！");
			dataMsg.setData(this.makeJSONData(dto.getId()));
		}catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法insertSysResource异常：", e);
		}
		return dataMsg;
	}
	
	private boolean isExist(String userId) throws Exception{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		LeaveInfoDTO dto = new LeaveInfoDTO();
		dto.setLeaveUserId(userId);
		dto.setStatus("2");
		dto.setValidateState("1");
		searchParams.put("dto", dto);
		List<LeaveInfoDTO> list = service.searchLbTLeaveInfo(searchParams);
		if(null!=list && list.size()>0){
			return true;
		}
		return false;
	}



	@RequestMapping(value = "/batchInsertLeaveInfo")
	@ResponseBody
	public DataMsg batchInsertLbTLeaveInfo(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<LeaveInfoDTO> leaveInfoDTOs = new ArrayList<LeaveInfoDTO>();
		try {
			String id = this.getParameterString("vId");
			String startDate = this.getParameterString("startTime");
			String endDate = this.getParameterString("endTime");
			String leave_type = this.getParameterString("leave_type");
			String[] ids = id.split(",");
			if(ids.length > 0){
				for (String idstr : ids) {
					SysUserDTO sysUser = new SysUserDTO();
					LeaveInfoDTO leaveInfo = new LeaveInfoDTO();
					if(StringUtils.isNotBlank(startDate)){
						leaveInfo.setStartTime(DateUtil.getDateFromString(startDate));
					}
					if(StringUtils.isNotBlank(endDate)){
						leaveInfo.setEndTime(DateUtil.getDateFromString(endDate));
					}
					if(StringUtils.isNotBlank(leave_type)){
						leaveInfo.setLeaveType(leave_type);
					}
					if(StringUtils.isNotBlank(idstr)){
						sysUser.setId(Long.parseLong(idstr));
					}
					leaveInfo.setStatus(SysConstant.LEAVEING);
					searchParams.put("dto", sysUser);
					List<SysUserDTO> sysUsers = sysUserService.searchByParam(searchParams);
					if(sysUsers.size()> 0){
						leaveInfo.setLeaveUserId(String.valueOf(idstr));
						leaveInfo.setUserName(sysUsers.get(0).getUserName());
						leaveInfo.setEmail(sysUsers.get(0).getEmail());
						List<SysOrgUserDTO>  sysorg = sysUsers.get(0).getSysOrgUserDTOs();
						for (SysOrgUserDTO sysOrgUserDTO : sysorg) {
							if("1".equals(sysOrgUserDTO.getIsMainOrg())){
								leaveInfo.setOrgName(sysOrgUserDTO.getOrgName());
								leaveInfo.setUserLevel(sysOrgUserDTO.getPositionName());
								leaveInfo.setOrgId(sysOrgUserDTO.getOrgId());
							}
						}
					}
					leaveInfoDTOs.add(leaveInfo);
				}
				service.batchInsertLbTLeaveInfo(leaveInfoDTOs);
			}
		}catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法batchInsertLbTLeaveInfo异常：", e);
		}
		return dataMsg;
	}



	/**
	 * @author JY-IT-D001
	 * @description:删除
	 * @date 2014-12-03 13:59:17
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteLeaveInfo")
	@ResponseBody
	public DataMsg deleteLbTLeaveInfo(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){

		String ids = (String) this.getParameter("ids");//格式: 1,2,3
		try {
			service.deleteLbTLeaveInfoByIds(ids);
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}
	/**
	 * @author JY-IT-D001
	 * @description:通过主键查询 其明细信息
	 * @date 2014-12-03 13:59:17
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try{
			LeaveInfoDTO dto = service.queryLbTLeaveInfoByPrimaryKey(id);
			//将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		}catch(Exception e){
			throw new AbaboonException("执行queryOneDTO异常：",e);
		}
		return model;
	}


	/***
	 * 获取当前登陆用户的员工请假信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findLeaveInfoByCurrentUser")
	@ResponseBody
	public DataMsg findLbTLeaveInfoByCurrentUser( @ModelAttribute DataMsg dataMsg) throws Exception{
		LeaveInfoDTO leaveInfoDTO = new LeaveInfoDTO();
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		leaveInfoDTO.setLeaveUserId(String.valueOf(userInfo.getUserId()));
		leaveInfoDTO.setUserName(userInfo.getUserName());
		leaveInfoDTO.setOrgName(userInfo.getOrgName());
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("leaveUserId", userInfo.getUserId());
		searchParams.put("orgId", userInfo.getOrgId());
		searchParams.put("status",SysConstant.LEAVEING);
		List<LeaveInfoDTO> leaveInfoDTOs = service.searchLbTLeaveInfoByStatus(searchParams);
		//根据请假状态去查询，如果存在则默认为请假，否则正常
		if(CollectionUtils.isNotEmpty(leaveInfoDTOs)){
			leaveInfoDTO.setStatus(SysConstant.LEAVEING);
			dataMsg.setData(leaveInfoDTOs);
		}else{
			leaveInfoDTO.setStatus(SysConstant.NORMAL);
			leaveInfoDTOs.add(leaveInfoDTO);
			dataMsg.setData(leaveInfoDTOs);
		}
		dataMsg = super.initDataMsg(dataMsg);
		dataMsg.setTotalRows(leaveInfoDTOs.size());
		return dataMsg;
	}


	/***
	 * 获取所有人员的请假信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getAllSysUserLeaveInfo")
	@ResponseBody
	public DataMsg getAllSysUserLeaveInfo( HttpServletRequest request, SysUserDTO dto, @ModelAttribute DataMsg dataMsg ) throws Exception{
		List<LeaveInfoDTO> leaveInfoDTOs = new ArrayList<LeaveInfoDTO>();
		Map<String, Object> result = this.queryListLbTLeave();

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<SysUserDTO> userDTOs = sysUserService.searchSysUserByPaging(params.getSearchParams());


		for(SysUserDTO userDto:  userDTOs){
			//也取出用户机构对应关系
			Map<String, Object> param = new HashMap<String, Object>();
			SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
			sysOrgUserDTO.setUserId(userDto.getId());
			param.put("dto", sysOrgUserDTO);
			List<SysOrgUserDTO> sysOrgUserDTOs  = sysOrgUserDao.searchSysOrgUserInfo(param);
			if(sysOrgUserDTOs!=null && sysOrgUserDTOs.size()>0){
				userDto.setSysOrgUserDTOs(sysOrgUserDTOs);
			}
		}
		for (SysUserDTO sysUserDTO : userDTOs) {
			LeaveInfoDTO leaveInfoDTO = new LeaveInfoDTO();
			if(result.containsKey(String.valueOf(sysUserDTO.getId()))){
				leaveInfoDTO.setStatus(SysConstant.LEAVEING);
			}else{
				leaveInfoDTO.setStatus(SysConstant.NORMAL);
			}
			//获取之前查取的数据对象
			LeaveInfoDTO searchleaveInfo = (LeaveInfoDTO) result.get(sysUserDTO.getId().toString());
			if(searchleaveInfo!=null){
				//如果员工已经请假，则显示请假时间
				leaveInfoDTO.setStartTime(searchleaveInfo.getStartTime());
				leaveInfoDTO.setEndTime(searchleaveInfo.getEndTime());
			}
			leaveInfoDTO.setUserName(sysUserDTO.getUserName());
			leaveInfoDTO.setEmail(sysUserDTO.getEmail());
			leaveInfoDTO.setLeaveUserId(String.valueOf(sysUserDTO.getId()));
			leaveInfoDTO.setId(sysUserDTO.getId());
			//每个用户对应多个机构，但是只有一个是主要的 1代表主要机构
			List<SysOrgUserDTO>  sysorg = sysUserDTO.getSysOrgUserDTOs();
			for (SysOrgUserDTO sysOrgUserDTO : sysorg) {
				if("1".equals(sysOrgUserDTO.getIsMainOrg())){
					leaveInfoDTO.setOrgName(sysOrgUserDTO.getOrgName());
					leaveInfoDTO.setUserLevel(sysOrgUserDTO.getPositionName());
				}
			}
			leaveInfoDTOs.add(leaveInfoDTO);
		}

		dataMsg.setData(leaveInfoDTOs);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}


	/***
	 * 获取请假结果表中的数据，以用户的id和对象形式
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryListLbTLeave() throws Exception {
		Map<String, Object> leaveResult = new HashMap<String, Object>();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", new LeaveInfoDTO());
		List<LeaveInfoDTO> list = service.searchLbTLeaveInfo(searchParams);
		if(CollectionUtils.isNotEmpty(list)){
			for (LeaveInfoDTO lbTLeaveInfoDTO : list) {
				leaveResult.put(lbTLeaveInfoDTO.getLeaveUserId(), lbTLeaveInfoDTO);
			}
		}
		return leaveResult;
	}


	/***
	 * 获取批量请假人员名称
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public String getAllLeaveUserName(String ids) throws Exception{
		StringBuffer sb = new StringBuffer();
		String[] idstr = ids.split(",");
		if(idstr.length > 0){
			for (int i = 0; i < idstr.length; i++) {
				SysUserDTO user = sysUserService.querySysUserByPrimaryKey(idstr[i]);
				if(user!=null){
					if(i<idstr.length-1){
						sb.append(user.getUserName()).append(",");
					}else{
						sb.append(user.getUserName());
					}
				}
			}
		}
		return sb.toString();
	}
}




