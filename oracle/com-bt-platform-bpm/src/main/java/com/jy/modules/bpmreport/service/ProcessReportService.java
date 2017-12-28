package com.jy.modules.bpmreport.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.bpmreport.dao.ProcessReportDao;
import com.jy.modules.bpmreport.dto.DataBean;
import com.jy.modules.bpmreport.dto.SysDictDetailDTO;
import com.jy.platform.api.org.UserInfo;
/**
 * 
 * @author xyz
 *
 */
@Service("processReportService")
public class ProcessReportService implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String PROCESS_DEF_DICT_CODE="WORKFLOW_BIZ_TYPE";
	@Autowired
	private ProcessReportDao processReportDao;

	/**
	 * 获取 积压最多的流程TOP10
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getOverStockMoreData(UserInfo userInfo) {
		
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=processReportDao.getOverStockMoreDataList(map);
		return dealListName(list);
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
	public List<DataBean> getProcessedMoreData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=processReportDao.getProcessedMoreData(map);
		return dealListName(list);
	}
	/**
	 * 获取发起最多TOP10的数据
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getStartUpMoreData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=processReportDao.getStartUpMoreData(map);
		return dealListName(list);
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
		List<DataBean> list=processReportDao.getAvgEfficientHighestData(map);
		return dealData(list);
	}
	/**
	 * 处理数据
	 * @param list
	 * @return
	 */
	public List<DataBean> dealData(List<DataBean> list){
		for(DataBean data:list){
			long num=data.getNum()/60000;
			if(num==0){
				num=1;
			}
			data.setNum(num);
		}
		return dealListName(list);
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
		List<DataBean> list=processReportDao.getAvgEfficientLowestData(map);
		return dealData(list);
	}
	/**
	 * 平均人工速率最高TOP10
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getAvgHumanEfficientHighestData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=processReportDao.getAvgHumanEfficientHighestData(map);
		return dealData(list);
	}
	/**
	 * 处理数据名称
	 * @param list
	 * @return
	 */
	public List<DataBean> dealListName(List<DataBean>  list){
		
		List<SysDictDetailDTO> listDetail=processReportDao.getSysDictDetailList(PROCESS_DEF_DICT_CODE);
		if(listDetail!=null&&listDetail.size()>0){
			return setListName(listDetail,list);
		}
		return list;
	}
	/**
	 * 具体处理数据名称
	 * @param listDetail
	 * @param list
	 * @return
	 */
	private  List<DataBean> setListName(List<SysDictDetailDTO> listDetail,List<DataBean>  list){
		for(DataBean data:list){
			String name=data.getName();
			data.setName(getName(listDetail,name));
		}
		return list;
	}
	/**
	 * 获取相应 数据名称
	 * @param list
	 * @param name
	 * @return
	 */
	private String getName(List<SysDictDetailDTO> list, String name) {
		for(SysDictDetailDTO data:list){
			if(name.equals(data.getDictDetailValue())){
				return data.getDictDetailName();
			}
		}
		return name;
	}
	/**
	 * 平均人工速率最低TOP10
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getAvgHumanEfficientLowestData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		List<DataBean> list=processReportDao.getAvgHumanEfficientLowestData(map);
		return dealData(list);
	}
}
