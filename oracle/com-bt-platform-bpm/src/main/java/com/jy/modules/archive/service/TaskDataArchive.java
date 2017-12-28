package com.jy.modules.archive.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.archive.dao.TaskDataArchiveDao;
/**
 * 归档数据处理类
 * @author xyz
 *
 */
@Service("taskDataArchive")
public class TaskDataArchive  extends DataArchive {
	@Autowired
	private TaskDataArchiveDao taskDataArchiveDao;//引用
	@Autowired
	private ConsignedTaskDataArchive consignedTaskDataArchive;//引用
	public TaskDataArchive(){
	}
	/**
	 *  判断表是否存在，存在返回true,否则false
	 * @return
	 * @throws Exception
	 */
	public boolean isExsitTable() throws Exception{
		int  num=taskDataArchiveDao.getTableNum();
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
		consignedTaskDataArchive.insertData(str);
		taskDataArchiveDao.insertData(str);
		taskDataArchiveDao.insertTMPData(str);
	}
	/***
	 * 将归档数据从表中删除
	 */
	@Override
	public void deleteData(String str) throws Exception {
		consignedTaskDataArchive.deleteData(str);
		taskDataArchiveDao.deleteData();
		taskDataArchiveDao.deleteTMPData();
	}
	/**
	 * 处理归档相关表，如果不存在，则创建
	 */
	@Override
	public void dealTable() throws Exception {
		if(!isExsitTable()){//表不存在
			taskDataArchiveDao.createTable();
			taskDataArchiveDao.createTMPTable();
		}
		consignedTaskDataArchive.dealTable();
	}
}

