package com.jy.modules.archive.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.archive.dao.BizInfoDataArchiveDao;

/**
 * 归档数据处理类
 * @author xyz
 *
 */
@Service("bizInfoDataArchive")
public class BizInfoDataArchive extends DataArchive {
	@Autowired
	private BizInfoDataArchiveDao bizInfoDataArchiveDao;//引用
	public BizInfoDataArchive(){
	}
	/**
	 *  判断表是否存在，存在返回true,否则false
	 * @return
	 * @throws Exception
	 */
	public boolean isExsitTable() throws Exception{
		int  num=bizInfoDataArchiveDao.getTableNum();
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
		bizInfoDataArchiveDao.insertData(str);
		bizInfoDataArchiveDao.insertTMPData(str);
	}
	/***
	 * 将归档数据从表中删除
	 */
	@Override
	public void deleteData(String str) throws Exception {
		bizInfoDataArchiveDao.deleteData();
		bizInfoDataArchiveDao.deleteTMPData();
	}
	/**
	 * 处理归档相关表，如果不存在，则创建
	 */
	@Override
	public void dealTable() throws Exception {
		if(!isExsitTable()){//表不存在
			bizInfoDataArchiveDao.createTable();
			bizInfoDataArchiveDao.createTMPTable();
		}
	}
}
