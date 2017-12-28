package com.jy.modules.archive.dao;

import java.io.Serializable;
import java.util.List;

import com.jy.modules.archive.dto.ActInstancePo;
import com.jy.platform.core.mybatis.MyBatisRepository;

/**
 * dao 实现类
 * @author xyz
 *
 *
 */
@MyBatisRepository
public interface ActInstanceDataArchiveDao extends Serializable{
	public List<ActInstancePo>  getTabDataList(String str)throws Exception;
	public  void  createTable()throws Exception;
	public int getTableNum()throws Exception;
	public void insertData(String str)throws Exception;
	public void deleteData()throws Exception;
	public void insertListData(String str)throws Exception;
	public void deleteListData(String str)throws Exception;
	public void deleteDataById(Long id)throws Exception;
	public  void  createTMPTable()throws Exception;
	public void insertTMPData(String str)throws Exception;
	public void deleteTMPData()throws Exception;
}
