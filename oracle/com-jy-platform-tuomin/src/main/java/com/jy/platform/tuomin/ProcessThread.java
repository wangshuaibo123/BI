package com.jy.platform.tuomin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.platform.tuomin.Configuration.ThreadConf;
import com.jy.platform.tuomin.monitor.MonitorService;

public class ProcessThread implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(ProcessThread.class);
	
	/**
	 * 一个线程要处理的最大的表数量
	 */
	private static final int MAX_NUM = 1024;
	
	/**
	 * 线程序号
	 */
	private String threadNo;
	
	public ProcessThread(String threadNo) {
		this.threadNo = threadNo;
	}

	/**
	 * 线程逻辑：遍历处理配置的本线程所有的处理项
	 */
	public void run() {
		Thread.currentThread().setName("process-" + threadNo);

		// 从序号0到MAX_NUM，遍历本线程要处理的所有配置，到第一个为空的配置时结束
		for (int idx = 0; idx < MAX_NUM; idx++) {
			// 获取要处理的配置信息（t0.0~t0.n）
			ThreadConf threadConf = Configuration.getThreadConf(threadNo + "." + idx);
			if (threadConf == null) {
				//logger.info("线程{}的第{}步不存在，整个线程处理完毕", threadNo, idx);
				//break;
				// 新需求，处理顺序号可能不连续
				continue;
			}
			logger.info("将要处理{}.{}, "+threadConf.getSysname()+", "+threadConf.getTableName(), threadNo, idx);
			
			MonitorService.setBeginTime(threadConf.getKey());
			// 根据配置信息进行处理
			process(threadConf);
			MonitorService.setEndTime(threadConf.getKey());
		}
		
		logger.info("线程{}全部处理完毕", threadNo);
	}

	/**
	 * 根据配置进行处理
	 * @param threadConf 
	 */
	private void process(ThreadConf threadConf) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String[] fields = threadConf.getFields();
		String[] types = threadConf.getTypes();
		
		// 先构造sql语句
		String[] sql = buildSQL(threadConf); 
		try {
			// 根据配置的系统名称获取对应的数据库连接
			conn = DBManager.getConnection(threadConf.getSysname());
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql[0]);
			rs.setFetchSize(Configuration.getJdbcFetchSize());
			pstmt = conn.prepareStatement(sql[1]);
			int batchCount = 0;
			int count = 0;
			
			// 遍历数据集，同时进行更新操作
			while (rs.next()) {
				int paramIdx = 1;
				// 对所有字段进行脱敏处理
				for (int i=0; i<fields.length; i++) {
					pstmt.setObject(paramIdx++, TMFactory.getNewValue(rs.getString(fields[i]), types[i]));
				}
				pstmt.setObject(paramIdx, rs.getObject("rowid"));
				pstmt.addBatch();
				
				batchCount ++;
				// 到了一定的数量后，批量提交
				if (batchCount == Configuration.getJdbcBatchSize()) {
					pstmt.executeBatch();
					conn.commit();
					//pstmt.clearBatch();
					batchCount = 0;
				}
				
				if (++count % 10000 == 0) {
					MonitorService.setStatus(threadConf.getKey(), "已处理数据" + count + "行");
					logger.info("{}已处理数据{}", threadConf.getKey(), count);
				}
			}
			// 批量提交未提交的数据
			if (batchCount != 0) {
				pstmt.executeBatch();
				conn.commit();
				//pstmt.clearBatch();
			}
		} catch (Exception e) {
			MonitorService.setError(threadConf.getKey(), "数据库处理出现异常：" + e.getMessage());
			logger.error("线程" + threadConf.getKey() + "数据库处理出现异常：", e);
		} finally {
			DBManager.release(rs);
			DBManager.release(stmt);
			DBManager.release(conn);
		}
		
	}

	/**
	 * 根据配置信息构造sql语句
	 * @param conf
	 * @return 数组{“查询的sql”，“更新的sql”}
	 */
	private String[] buildSQL(ThreadConf conf) {
		
		// 构建查询语句
		String querySql = "select rowid";
		for (int i=0; i<conf.getFields().length; i++) {
			// 如果是要清空的字段，不需要查询出来
			if (conf.getTypes()[i] != "''") {
				querySql += ", " + conf.getFields()[i];
			}
		}
		querySql += " from " + conf.getTableName();
		logger.info("{}的查询sql为{}", conf.getKey(), querySql);
		
		// 构建更新语句
		String updateSql = "update " + conf.getTableName() + " set ";
		for (int i=0; i<conf.getFields().length; i++) {
			if (i != 0) {
				updateSql += ", ";
			}
			updateSql += conf.getFields()[i] + " = ?";
		}
		updateSql += " where rowid = ?";
		logger.info("{}的更新sql为{}", conf.getKey(), updateSql);
		
		return new String[] {querySql, updateSql};
	}

}
