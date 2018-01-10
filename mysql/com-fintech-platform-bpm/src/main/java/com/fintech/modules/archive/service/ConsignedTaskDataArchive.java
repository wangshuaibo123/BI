package com.fintech.modules.archive.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.archive.dao.ConsignedTaskDataArchiveDao;

/**
 * 归档数据处理类
 * @author
 *
 */
@Service("consignedTaskDataArchive")
public class ConsignedTaskDataArchive extends DataArchive {
	@Autowired
	private ConsignedTaskDataArchiveDao consignedTaskDataArchiveDao;//引用
	public ConsignedTaskDataArchive(){
	}
	/**
	 *  判断表是否存在，存在返回true,否则false
	 * @return
	 * @throws Exception
	 */
	public boolean isExsitTable() throws Exception{
		int  num=consignedTaskDataArchiveDao.getTableNum();
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
		consignedTaskDataArchiveDao.insertData(str);
		consignedTaskDataArchiveDao.insertTMPData(str);
	}
	/***
	 * 将归档数据从表中删除
	 */
	@Override
	public void deleteData(String str) throws Exception {
		consignedTaskDataArchiveDao.deleteData();
		consignedTaskDataArchiveDao.deleteTMPData();
	}
	/**
	 * 处理归档相关表，如果不存在，则创建
	 */
	@Override
	public void dealTable() throws Exception {
		if(!isExsitTable()){//表不存在
			consignedTaskDataArchiveDao.createTable();
			consignedTaskDataArchiveDao.createTMPTable();
		}
	}
}
