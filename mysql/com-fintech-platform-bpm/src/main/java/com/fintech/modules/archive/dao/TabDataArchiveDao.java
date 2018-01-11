package com.fintech.modules.archive.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fintech.modules.archive.dto.JBPM4BizTabPo;
import com.fintech.platform.core.mybatis.MyBatisRepository;

/**
 * dao 实现类
 * @author
 *
 *
 */
@MyBatisRepository
public interface TabDataArchiveDao extends Serializable{
	public List<JBPM4BizTabPo>  getTabDataList(Map<String, Object> map)throws Exception;
	public List<JBPM4BizTabPo>  getTabData100List(String str)throws Exception;
	public  void  createTable()throws Exception;
	public int getTableNum()throws Exception;
	public void insertData(String str)throws Exception;
	public void deleteData()throws Exception;
	public void insertListData(List<JBPM4BizTabPo> list)throws Exception;
	public void deleteListData(List<JBPM4BizTabPo> list)throws Exception;
	public int  getTabData100Total(String str)throws Exception;
	public int hasNotEndNum(String proInstanceId);
	public  void  createTMPTable()throws Exception;
	public void insertTMPData(String str)throws Exception;
	public void deleteTMPData()throws Exception;
	public Map<String, Object> queryValueByCode(String code);
}
