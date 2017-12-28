package com.jy.platform.tuomin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.platform.tuomin.Configuration.JdbcConf;

/**
 * 数据库工具类
 * @author zhangyu
 *
 */
public class DBManager {
	
	private static Logger logger = LoggerFactory.getLogger(DBManager.class);
	
	/**
	 * 数据库驱动缓存，避免多次加载
	 */
	private static Set<String> driverCache = new HashSet<String>();
	
	/**
	 * 获取指定系统的数据库连接
	 * @param sysname 系统名称
	 * @return 数据库连接Connection
	 * @throws Exception
	 */
	public static Connection getConnection(String sysname) throws Exception {
		JdbcConf conf = Configuration.getJdbcConf(sysname);
		loadDriver(conf.getClassName());
		Connection conn = DriverManager.getConnection(conf.getUrl(), conf.getUserName(), conf.getPassword());
		return conn;
	}
	
	/**
	 * 释放ResultSet
	 * @param rs
	 */
	public static void release(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("ResultSet关闭异常", e);
			}
		}
	}

	/**
	 * 释放Statement
	 * @param stmt
	 */
	public static void release(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Statement关闭异常", e);
			}
		}
	}
	
	/**
	 * 释放Connection
	 * @param conn
	 */
	public static void release(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Connection关闭异常", e);
			}
		}
	}
	
	/**
	 * 加载数据库驱动
	 * @param className 驱动名称
	 * @throws ClassNotFoundException 驱动包不存在异常
	 */
	private static synchronized void loadDriver(String className) throws ClassNotFoundException {
		if (!driverCache.contains(className)) {
			logger.debug("加载数据库驱动{}", className);
			Class.forName(className);
		}
	}

}
