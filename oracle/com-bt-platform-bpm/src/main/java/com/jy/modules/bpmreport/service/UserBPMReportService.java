package com.jy.modules.bpmreport.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.bpmreport.dao.ProcessReportDao;
import com.jy.modules.bpmreport.dao.UserBPMReportDao;
import com.jy.modules.bpmreport.dto.DataBean;
import com.jy.platform.api.org.UserInfo;
/**
 * user 服务层查询
 * @author xyz
 * @date 2016-2-17
 */
@Service("userBPMReportService")
public class UserBPMReportService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UserBPMReportDao userBPMReportDao;
	@Autowired
	private ProcessReportDao processReportDao;
	/**
	 * 获取 积压最多的流程TOP10
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getOverStockMostData(UserInfo userInfo) {
		
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=userBPMReportDao.getOverStockMostDataList(map);
		return list;
	}
	public  boolean isAdmin(Long userId){
		List<String> list=processReportDao.getRoleCodListByUserId(userId);
		if(list==null||list.isEmpty()){
			return false;
		}
		return true;
	}
	/**
	 * 获取已办最多TOP10的数据
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getProcessedMostData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=userBPMReportDao.getProcessedMostData(map);
		return list;
	}
	/**
	 * 获取发起最多TOP10的数据
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getStartUpMostData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=userBPMReportDao.getStartUpMostData(map);
		return list;
	}
	/**
	 * 平均速率最高TOP10
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getAvgEfficientHighestData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=userBPMReportDao.getAvgEfficientHighestData(map);
		return dealData(list);
	}
	public List<DataBean> dealData(List<DataBean> list){
		for(DataBean data:list){
			long num=data.getNum()/60000;
			if(num==0){
				num=1;
			}
			data.setNum(num);
		}
		return list;
	}
	/**
	 * 平均速率最低TOP10
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getAvgEfficientLowestData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=userBPMReportDao.getAvgEfficientLowestData(map);
		return dealData(list);
	}
	/**
	 * 获取 委托 最多 TOP10 数据
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getEntrustMostData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=userBPMReportDao.getEntrustMostData(map);
		return list;
	}
	/**
	 * 获取乐于助人最多TOP10 数据
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getSupportMostData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=userBPMReportDao.getSupportMostData(map);
		return list;
	}
	/**
	 * 获取委托状况 数据
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getEntrustStateData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=userBPMReportDao.getEntrustStateData(map);
		return list;
	}

}
