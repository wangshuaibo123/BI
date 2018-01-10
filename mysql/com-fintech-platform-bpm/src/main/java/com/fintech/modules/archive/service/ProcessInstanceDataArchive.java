package com.fintech.modules.archive.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.archive.dao.ProcessInstanceDataArchiveDao;

/**
 * 归档数据处理类
 * @author
 *
 */
@Service("processInstanceDataArchive")
public class ProcessInstanceDataArchive extends DataArchive {
	private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceDataArchive.class);
	@Autowired
	private ProcessInstanceDataArchiveDao processInstanceDataArchiveDao;//引用
	@Autowired
	private ActInstanceDataArchive actInstanceDataArchive;//引用
	public ProcessInstanceDataArchive(){
	}
	/**
	 *  判断表是否存在，存在返回true,否则false
	 * @return
	 * @throws Exception
	 */
	public boolean isExsitTable() throws Exception{
		int  num=processInstanceDataArchiveDao.getTableNum();
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
		actInstanceDataArchive.insertData(str);
		processInstanceDataArchiveDao.insertData(str);
		processInstanceDataArchiveDao.insertTMPData(str);
	}
	/***
	 * 将归档数据从表中删除
	 */
	@Override
	public void deleteData(String str) throws Exception {
		actInstanceDataArchive.deleteData(str);
		processInstanceDataArchiveDao.deleteData();
		processInstanceDataArchiveDao.deleteTMPData();
	}
	/**
	 * 处理归档相关表，如果不存在，则创建
	 */
	@Override
	public void dealTable() throws Exception {
		logger.debug("处理表");
		if(!isExsitTable()){//表不存在
			processInstanceDataArchiveDao.createTable();
			processInstanceDataArchiveDao.createTMPTable();
		}
		actInstanceDataArchive.dealTable();
	}
}
