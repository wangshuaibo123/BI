package com.jy.modules.logmonitor.sysapperrorinfo.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.logmonitor.sysapperrorinfo.dao.SysAppErrorInfoDao;
import com.jy.modules.logmonitor.sysapperrorinfo.dto.SysAppErrorInfoDTO;
import com.jy.modules.logmonitor.sysapperrorinfo.dto.SysAppErrorLevelDetailDTO;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: SysAppErrorInfoService
 * @description: 定义  业务系统节点错误日志 实现类
 * @author:  lei
 */
@Service("com.jy.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService")
public class SysAppErrorInfoService implements Serializable {
	

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysAppErrorInfoDao dao;
	/**
     * @author luoyr
     * @description: 统计日志级别
     * @date 2015-04-03 10:07:07
     * @param searchParams 条件
     * @param type 类型 
     * SysAppErrorInfoDTO.TYPE_COUNT_DAY 按照天/月统计;
     * SysAppErrorInfoDTO.TYPE_COUNT_HOUR 按照小时/天统计
     * @return
     * @throws
     */ 
	public List<SysAppErrorInfoDTO> countSysAppErrorInfo(Map<String,Object> searchParams,int type) throws Exception {
		if(type == SysAppErrorInfoDTO.TYPE_COUNT_DAY){
			return dao.countSysAppErrorByDay(searchParams);
		}
		
		if(type == SysAppErrorInfoDTO.TYPE_COUNT_HOUR){
			return dao.countSysAppErrorByHour(searchParams);
		}
		
		return null;
	}
	/**
     * @author luoyr
     * @description: 统计日志级别
     * @date 2015-04-03 10:07:07
     * @param searchParams 条件
     * @param type 类型 
     * SysAppErrorInfoDTO.TYPE_COUNT_DAY 按照天/月统计;
     * SysAppErrorInfoDTO.TYPE_COUNT_HOUR 按照小时/天统计
     * @return
     * @throws
     */ 
	public List<SysAppErrorInfoDTO> percentSysAppErrorInfo(Map<String,Object> searchParams,int type) throws Exception {
		if(type == SysAppErrorInfoDTO.TYPE_COUNT_DAY){
			return dao.percentSysAppErrorByDay(searchParams);
		}
		
		if(type == SysAppErrorInfoDTO.TYPE_COUNT_HOUR){
			return dao.percentSysAppErrorByHour(searchParams);
		}
		
		return null;
	}
	/**
     * @author lei
     * @description: 分页查询 业务系统节点错误日志列表
     * @date 2015-04-03 10:07:07
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysAppErrorInfoDTO> searchSysAppErrorInfoByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysAppErrorInfoDTO> dataList =  dao.searchSysAppErrorInfoByPaging(searchParams);
		return dataList;
	}
	/**
     * @author lei
     * @description: 按条件查询业务系统节点错误日志列表
     * @date 2015-04-03 10:07:07
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysAppErrorInfoDTO> searchSysAppErrorInfo(Map<String,Object> searchParams) throws Exception {
	    List<SysAppErrorInfoDTO> dataList = dao.searchSysAppErrorInfo(searchParams);
        return dataList;
    }
	/**
     * @author lei
     * @description: 查询业务系统节点错误日志对象
     * @date 2015-04-03 10:07:07
     * @param id
     * @return
     * @throws
     */ 
	public SysAppErrorInfoDTO querySysAppErrorInfoByPrimaryKey(String id) throws Exception {
		
		SysAppErrorInfoDTO dto = dao.findSysAppErrorInfoByPrimaryKey(id);
		
		if(dto == null) dto = new SysAppErrorInfoDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysAppErrorInfo
     * @author lei
     * @description: 新增 业务系统节点错误日志对象
     * @date 2015-04-03 10:07:07
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public void insertSysAppErrorInfo(File file, String nodeName, String appFlag) throws Exception {
		List<SysAppErrorInfoDTO> list=new ArrayList<SysAppErrorInfoDTO>();
		SysAppErrorInfoDTO searDto=new SysAppErrorInfoDTO();
		searDto.setNodeName(nodeName);
		searDto.setAppFlag(appFlag);
		searDto.setFileName(file.getName());
		Map<String, Object> searchParam=new HashMap<String, Object>();
		searchParam.put("dto", searDto);
		List<SysAppErrorInfoDTO> endDtos=searchSysAppErrorInfo(searchParam);
		String endTime=null;
		if(endDtos.size()>0){
			endTime=endDtos.get(0).getCreateTime();
		}
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		try {
			String buf=null;
			boolean start=false;
			int count=0;
			while((buf=reader.readLine())!=null){
				if(endTime!=null&&!endTime.equals("")){
					if(buf.contains(endTime)){
						start=true;
						continue;
					}
					if(!start)
						continue;
				}else{
					start=true;
				}
				if(start){
					if(buf.startsWith("[")){
						SysAppErrorInfoDTO temp=new SysAppErrorInfoDTO();
						temp.setCreateTime(buf.substring(1,buf.indexOf("]")));
						temp.setConcent(buf.substring(buf.lastIndexOf("]")+1));
						if(temp.getConcent().length()>1000){
							temp.setConcent(temp.getConcent().substring(0, 1500));
						}
						temp.setNodeName(nodeName);
						temp.setAppFlag(appFlag);
						temp.setFileName(file.getName());
						list.add(temp);
						count=0;
					}else{
						count++;
						if(count==1){
							list.get(list.size()-1).setConcent(list.get(list.size()-1).getConcent()+"\r\n"+buf);
						}
					}
				}
				if(list.size() == 50){
					dao.insertSysAppErrorInfo(list);
					list.clear();
				}
			}
			if(list.size()>0){
				dao.insertSysAppErrorInfo(list);
			}
		} catch (Exception e) {
		}finally{
			reader.close();
		}
		
	}
	/**
     * @title: updateSysAppErrorInfo
     * @author lei
     * @description: 修改 业务系统节点错误日志对象
     * @date 2015-04-03 10:07:07
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysAppErrorInfo(SysAppErrorInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysAppErrorInfo(paramMap);
	}
	/**
     * @title: deleteSysAppErrorInfoByPrimaryKey
     * @author lei
     * @description: 删除 业务系统节点错误日志,按主键
     * @date 2015-04-03 10:07:07
     * @param paramMap
     * @throws
     */ 
	public void deleteSysAppErrorInfoByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysAppErrorInfoByPrimaryKey(paramMap);
	}

	/**
	 * @tile: updateSysAppErrorInfoLevel
	 * @author yongliangguo
	 * @deprecated: 根据关键字更新日志的级别
	 * @date 2015-06-17
	 * @param keyWord
	 * @param level
	 */
	public void updateSysAppErrorInfoLevel(String appFlag, String keyWord, String level, long levelSetupId){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("appFlag", appFlag);
		paramMap.put("keyWord", keyWord);
		paramMap.put("level", level);
		paramMap.put("levelSetupId", levelSetupId);
		dao.updateSysAppErrorInfoLevel(paramMap);
	}
	
	public void updateSysAppErrorMatched(){
		dao.updateSysAppErrorMatched();
	}
	
	/**
	 * 根据时间的形式获取当前区域时间中产生的错误数
	 * @param rateUnit 1：分钟；2：小时
	 * @param time 分钟/时间
	 * @return
	 */
	public List<SysAppErrorLevelDetailDTO> getErrorForLevelCount(String rateUnit, int time, int maxRate){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("rateUnit", rateUnit);
		paramMap.put("time", time);
		paramMap.put("maxRate", maxRate);
		return dao.getErrorForLevelCount(paramMap);
	}
}

