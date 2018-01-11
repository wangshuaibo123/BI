package com.fintech.modules.archive.service;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.platform.core.common.JYLoggerUtil;


/**
 * 归档数据处理类
 * @author
 *
 */
@Service("helpDataArchive")
public class HelpDataArchive extends DataArchive {
	private static final Logger logger = LoggerFactory.getLogger(HelpDataArchive.class);
	@Autowired
	private BizTabDataArchive bizTabDataArchive;
	public HelpDataArchive(){
	}
	/**
	 * 归档数据
	 */
	public void doArchiving(String info) throws Exception {
		JYLoggerUtil.logCurrentTime("===doArch===", true, HelpDataArchive.class);
		logger.debug("开始执行。。");
		dealTable();
		logger.debug("开始拷贝。。");
		insertData(info) ;
		logger.debug("开始删除。。");
		deleteData(info);
		JYLoggerUtil.logCurrentTime("===doArch===", false, HelpDataArchive.class);
	}
	/**
	 * 将归档数据 拷贝到目标表中
	 */
	@Override
	public void insertData(String str) throws Exception {
		bizTabDataArchive.insertData(str);
	}
	/**
	 * 将归档数据从原表中删除
	 */
	@Override
	public void deleteData(String str) throws Exception {
		bizTabDataArchive.deleteData(str);
	}
	/**
	 * 获取时间
	 * @param code
	 * @return
	 */
	public String getValue(String code) {
		return bizTabDataArchive.queryValueByCode(code);
	}
	/**
	 * 处理归档相关表，如果不存在，则创建
	 */
	@Override
	public void dealTable() throws Exception {
		bizTabDataArchive.dealTable();
	}
}
