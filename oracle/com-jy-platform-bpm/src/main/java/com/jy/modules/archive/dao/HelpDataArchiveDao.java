package com.jy.modules.archive.dao;

import java.io.Serializable;

import com.jy.platform.core.mybatis.MyBatisRepository;

/**
 * dao 实现类
 * @author xyz
 *
 *
 */
@MyBatisRepository
public interface HelpDataArchiveDao extends Serializable{
	public void insertActInstanceData(String info)throws Exception;
	public void insertTaskData(String info)throws Exception;
	public void insertConsignedTaskData(String info)throws Exception;
	public void insertVReportData(String info)throws Exception;
	public void insertBizInfoData(String info)throws Exception;
	public void insertBizTabData(String info)throws Exception;
	public void insertProcessData(String info)throws Exception;
	public void deleteConsignedTaskData(String info)throws Exception;
	public void deleteVReportData(String info)throws Exception;
	public void deleteBizInfoData(String info)throws Exception;
	public void deleteBizTabData(String info)throws Exception;
	public void deleteProcessData(String info)throws Exception;
	public void deleteActInstData(String info)throws Exception;
	public void deleteTaskData(String info)throws Exception;
}
