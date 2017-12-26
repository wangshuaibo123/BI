package com.fintech.modules.platform.syslog.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.syslog.dao.SysLogDao;
import com.fintech.modules.platform.syslog.dto.SysLogDTO;
import com.fintech.platform.tools.common.DateUtil;
/**
 *@Description:拦截日志
 *@author
 *@version 1.0,
 *@date 2014-12-15
 */
@Service("com.fintech.modules.platform.syslog.service.SysLogService")
public class SysLogService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SysLogDao dao;

	/**
	 * @title: insertSysLog
	 * @author
	 * @description: 日志插入数据表
	 * @date 2014-12-15
	 * @param File 日志文件
	 * @throws
	 */
	@SuppressWarnings("all")
	public void insertSysLog(File file)
			throws Exception {
		List<SysLogDTO> list=new ArrayList<SysLogDTO>();
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		try {
			String buf=null;
			while((buf=reader.readLine())!=null){
				if(buf!=null&&!"".equals(buf.trim())&&buf.indexOf("[INFO]")!=-1){
					buf=buf.substring(buf.indexOf("null"));
					String []bufs=buf.split("\t");
					if(bufs.length==9){
						SysLogDTO dto=new SysLogDTO();
						dto.setType(bufs[1]);
						dto.setUserId(bufs[2]);
						dto.setModuleName(bufs[3]);
						dto.setClassName(bufs[4]);
						dto.setMethodName(bufs[5]);
						dto.setParamInfo(bufs[6]);
						dto.setUri(bufs[7]);
						dto.setCreated(bufs[8]);
						//定义工作流处理的方式，工作流参数长不进行数据日志的保存
						if(dto.getParamInfo()!=null){
							if(dto.getParamInfo().length()<1000){
								list.add(dto);
							}
						}else{
							list.add(dto);
						}
					}
					if(list.size() == 50){
						dao.insertSysLog(list);
						list.clear();
					}
				}
			}
			if(list.size()>0){
				dao.insertSysLog(list);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
	}
	
	public List<SysLogDTO> searchSysLogByPaging(Map<String, Object> searchParams){
		List<SysLogDTO> dataList = dao.searchSysLogByPaging(searchParams);
		return dataList;
	}
	
	public SysLogDTO querySysLogByPrimaryKey(String id)
			throws Exception {

		SysLogDTO dto = dao.findSysLogByPrimaryKey(id);

		if (dto == null)
			dto = new SysLogDTO();

		return dto;
	}
	
	/**
	 * 将日志文件打印到文件中
	 * @param dto
	 * @param path
	 */
	public void writeLogFile(SysLogDTO dto, String path){
		String preFile=null;
		FileWriter writer=null;
		try {
			preFile=File.separator+InetAddress.getLocalHost().getHostName()+DateUtil.format(Calendar.getInstance().getTime(), "yyyy-MM-dd")+"_log.log";
			File f=new File(path);
			if(!f.exists()){
				f.mkdirs();
			}
			if(preFile!=null){
				writer=new FileWriter(path+preFile, true);
				writer.write(dto.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
