package com.jy.modules.archive.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.archive.dao.ActInstanceDataArchiveDao;

/**
 * 归档数据处理类
 * @author xyz
 *
 */
@Service("actInstanceDataArchive")
public class ActInstanceDataArchive extends DataArchive {
	@Autowired
	private ActInstanceDataArchiveDao actInstanceDataArchiveDao;//引用
	@Autowired
	private TaskDataArchive taskDataArchive;
	public ActInstanceDataArchive(){
	}
	
	/**
	 *  判断表是否存在，存在返回true,否则false
	 * @return
	 * @throws Exception
	 */
	public boolean isExsitTable() throws Exception{
		int  num=actInstanceDataArchiveDao.getTableNum();
		if(num>=1){
			return true;
		}
		return false;
	}
	/***
	 * 将归档数据插入到目标表中
	 */
	@Override
	public void insertData(String str) throws Exception {
		taskDataArchive.insertData(str);
		actInstanceDataArchiveDao.insertData(str);
		actInstanceDataArchiveDao.insertTMPData(str);
	}
	/***
	 * 将归档数据从表中删除
	 */
	@Override
	public void deleteData(String str) throws Exception {
		actInstanceDataArchiveDao.deleteData();
		actInstanceDataArchiveDao.deleteTMPData();
		taskDataArchive.deleteData(str);
	}
	/**
	 * 处理归档相关表，如果不存在，则创建
	 */
	@Override
	public void dealTable() throws Exception {
		if(!isExsitTable()){//表不存在
			actInstanceDataArchiveDao.createTable();
			actInstanceDataArchiveDao.createTMPTable();
		}
		taskDataArchive.dealTable();
	}
	
}
