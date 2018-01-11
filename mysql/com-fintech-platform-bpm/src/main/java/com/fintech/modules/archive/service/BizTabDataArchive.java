package com.fintech.modules.archive.service;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.archive.dao.TabDataArchiveDao;

/**
 * 归档数据处理类
 * @author
 *
 */
@Service("bizTabDataArchive")
public class BizTabDataArchive extends DataArchive {
	private static final Logger logger = LoggerFactory.getLogger(BizTabDataArchive.class);
	@Autowired
	private TabDataArchiveDao tabDataArchiveDao;//引用
	@Autowired
	private ProcessInstanceDataArchive processInstanceDataArchive;
	@Autowired
	private BizInfoDataArchive bizInfoDataArchive;
	public BizTabDataArchive(){
	}
	/**
	 *  判断表是否存在，存在返回true,否则false
	 * @return
	 * @throws Exception
	 */
	public boolean isExsitTable() throws Exception{
		int  num=tabDataArchiveDao.getTableNum();
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
		logger.debug("归档数据");
		tabDataArchiveDao.insertData(str);
		tabDataArchiveDao.insertTMPData(str);
		processInstanceDataArchive.insertData(str);
		bizInfoDataArchive.insertData(str);
	}
	/***
	 * 将归档数据从表中删除
	 */
	@Override
	public void deleteData(String str) throws Exception {
		processInstanceDataArchive.deleteData(str);
		bizInfoDataArchive.deleteData(str);
		tabDataArchiveDao.deleteData();
		tabDataArchiveDao.deleteTMPData();
	}
	/**
	 * 获取时间
	 * @param code
	 * @return
	 */
	public String queryValueByCode(String code) {
		
		Map<String, Object> map = tabDataArchiveDao.queryValueByCode(code);
		if (map != null && map.size() > 0) {
			return map.get("VALUE").toString();
		}
		return null;
	}
	/**
	 * 处理归档相关表，如果不存在，则创建
	 */
	@Override
	public void dealTable() throws Exception {
		if(!isExsitTable()){//表不存在
			tabDataArchiveDao.createTable();
			tabDataArchiveDao.createTMPTable();
		}
		processInstanceDataArchive.dealTable();
		bizInfoDataArchive.dealTable();
	}
}
