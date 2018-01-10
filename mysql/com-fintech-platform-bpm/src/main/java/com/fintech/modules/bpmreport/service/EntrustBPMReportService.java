package com.fintech.modules.bpmreport.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.bpmreport.dao.EntrustBPMReportDao;
import com.fintech.modules.bpmreport.dao.ProcessReportDao;
import com.fintech.modules.bpmreport.dto.DataBean;
import com.fintech.platform.api.org.UserInfo;
/**
 * entrust 服务层查询
 * @author
 * @date 2016-2-12
 */
@Service("entrustBPMReportService")
public class EntrustBPMReportService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private EntrustBPMReportDao entrustBPMReportDao;
	@Autowired
	private ProcessReportDao processReportDao;
	@Autowired
	private RoleBPMReportService roleBPMReportService;
	/**
	 * 获取 委托概况相关数据
	 * @param userInfo
	 * @return
	 */
	public List<DataBean> getEntrustStateData(UserInfo userInfo) {
		boolean isAdmin=isAdmin(userInfo.getUserId());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isAdmin", isAdmin);
		map.put("orgId", userInfo.getOrgId());
		//mysql
		try {
			map.put("orgIds", roleBPMReportService.getSysOrgChildList(map));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<DataBean> list=entrustBPMReportDao.getEntrustStateData(map);
		return dealData(list);
	}
	/**
	 *数据处理
	 * @param list
	 * @return
	 */
	private List<DataBean> dealData(List<DataBean> list) {
		for(DataBean data:list){
			if("1".equals(data.getName())){
				data.setName("请假委托");
			}else if("2".equals(data.getName())){
				data.setName("离职委托");
			}else if("3".equals(data.getName())){
				data.setName("代理委托");
			}else{
				data.setName("其他");
			}
		}
		return list;
	}
	/**
	 * 是否是管理员
	 * @param userId
	 * @return
	 */
	public  boolean isAdmin(Long userId){
		List<String> list=processReportDao.getRoleCodListByUserId(userId);
		if(list==null||list.isEmpty()){
			return false;
		}
		return true;
	}
	
	
	
	
	

}
