package com.jy.modules.archive.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jy.modules.archive.dto.ProcessInstancePo;
import com.jy.platform.core.mybatis.MyBatisRepository;

/**
 * dao 实现类
 * @author xyz
 *
 *
 */
@MyBatisRepository
public interface ProcessInstanceDataArchiveDao extends Serializable{
	public List<ProcessInstancePo>  getTabDataList(Map<String, Object> map)throws Exception;
	public List<ProcessInstancePo>  getDataList(String str)throws Exception;
	public  void  createTable()throws Exception;
	public int getTableNum()throws Exception;
	public void insertData(String str)throws Exception;
	public void deleteData()throws Exception;
	public void insertListData(String str)throws Exception;
	public void deleteListData(String str)throws Exception;
	public  void  createTMPTable()throws Exception;
	public void insertTMPData(String str)throws Exception;
	public void deleteTMPData()throws Exception;
}
