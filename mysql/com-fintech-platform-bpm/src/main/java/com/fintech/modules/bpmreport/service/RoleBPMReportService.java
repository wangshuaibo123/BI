package com.fintech.modules.bpmreport.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.bpmreport.dao.ProcessReportDao;
import com.fintech.modules.bpmreport.dao.RoleBPMReportDao;
import com.fintech.modules.bpmreport.dto.DataBean;
import com.fintech.platform.api.org.UserInfo;
/**
 * user 服务层查询
 * @author
 * @date 2016-2-17
 */
@Service("roleBPMReportService")
public class RoleBPMReportService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private RoleBPMReportDao roleBPMReportDao;
	@Autowired
	private ProcessReportDao processReportDao;
	/**
	 * 获取 积压最多的流程TOP10
	 * @param userInfo
	 * @return
	 * @throws Exception 
	 */
	public List<DataBean> getOverStockMostData(UserInfo userInfo) throws Exception {
		
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		map.put("orgIds", this.getSysOrgChildList(map));
		List<DataBean> list=roleBPMReportDao.getOverStockMostDataList(map);
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
		List<DataBean> list=roleBPMReportDao.getProcessedMostData(map);
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
		List<DataBean> list=roleBPMReportDao.getStartUpMostData(map);
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
		List<DataBean> list=roleBPMReportDao.getAvgEfficientHighestData(map);
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
		List<DataBean> list=roleBPMReportDao.getAvgEfficientLowestData(map);
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
		List<DataBean> list=roleBPMReportDao.getEntrustMostData(map);
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
		List<DataBean> list=roleBPMReportDao.getSupportMostData(map);
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
		List<DataBean> list=roleBPMReportDao.getEntrustStateData(map);
		return list;
	}
	
	public String getSysOrgChildList(Map<String, Object> map) throws Exception{
		String str = "";
		String orgId = String.valueOf(map.get("orgId"));
		if(!StringUtils.isNotEmpty(orgId)){
			return str;
		}
		//Map<String,Object> result=roleBPMReportDao.getSysOrgChildList(map);
		//str = result.get("ORGIDS")==null?"":result.get("ORGIDS").toString();
		List<Map<String,String>> data = this.getOrgChild(orgId);
		if(data != null && data.size() >0 ){
			StringBuffer sb = new StringBuffer();
			for(Map<String,String> temp :data){
				sb.append("'").append(temp.get("ID")).append("'").append(",");
			}
			str =  sb.substring(0, sb.length()-1);
		}
		return str;
		
	}
	
	private List<Map<String,String>> getOrgChild(String orgId){
		List<String> data = new ArrayList<String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("orgId", orgId);
		List<Map<String,String>> result = roleBPMReportDao.getOrgChild(param);
		return result;
	}

}
