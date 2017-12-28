package com.jy.platform.tuomin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局配置
 * @author zhangyu
 *
 */
public class Configuration {

	private static Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	// 用于存储配置信息的属性对象
	private static Properties prop = new Properties();

	// 默认的配置文件
	private static final String CONF_FILE = "main.conf";

	// 初始化时加载配置
	static {
		try {
			File confFile = new File(CONF_FILE);
			if (confFile.exists()) {
				logger.info("加载外部配置文件{}", confFile.getAbsoluteFile());
				prop.load(new BufferedInputStream(new FileInputStream(confFile)));
			} else {
				logger.info("加载内部配置文件{}", CONF_FILE);
				prop.load(new BufferedInputStream(Configuration.class.getResourceAsStream("/" + CONF_FILE)));
			}
			Iterator<Object> it = prop.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = prop.getProperty(key);
				if (value != null && !value.isEmpty()) {
					prop.setProperty(key, new String(value.getBytes("ISO-8859-1"), "UTF-8"));
				}
			}
		} catch (IOException e) {
			logger.error("加载配置文件出现异常，程序退出", e);
			System.exit(-1);
		}
	}

	/**
	 * 数据库连接配置
	 * @author dell
	 *
	 */
	public static class JdbcConf {
		private String className;
		private String url;
		private String userName;
		private String password;

		private JdbcConf() {

		}

		public String getClassName() {
			return className;
		}

		public String getUrl() {
			return url;
		}

		public String getUserName() {
			return userName;
		}

		public String getPassword() {
			return password;
		}

	}

	/**
	 * 线程配置
	 * @author dell
	 *
	 */
	public static class ThreadConf {
		private String key;
		private String sysname;
		private String tableName;
		private String[] fields;
		private String[] types;

		/**
		 * 属性用“|”分隔，字段用“，”分隔，字段和类型用“:”分隔
		 * @param key
		 * @param confString
		 */
		private ThreadConf(String key, String confString) {
			this.key = key;
			String[] sa = confString.split("\\|");
			this.sysname = sa[0];
			this.tableName = sa[1];

			String[] fs = sa[2].split(",");
			this.fields = new String[fs.length];
			this.types = new String[fs.length];
			for (int i=0; i< fs.length; i++) {
				String[] temp = fs[i].split(":");
				this.fields[i] = temp[0];
				this.types[i] = temp[1];
			}
		}

		/**
		 * 线程配置的键
		 * @return
		 */
		public String getKey() {
			return key;
		}

		/**
		 * 系统名称
		 * @return
		 */
		public String getSysname() {
			return sysname;
		}

		/**
		 * 表名
		 * @return
		 */
		public String getTableName() {
			return tableName;
		}

		/**
		 * 字段
		 * @return
		 */
		public String[] getFields() {
			return fields;
		}

		/**
		 * 脱敏类型
		 * @return
		 */
		public String[] getTypes() {
			return types;
		}

	}

	/**
	 * 获取并行线程个数，默认1个（threadCount=1）
	 * @return
	 */
	public static int getThreadCount() {
		String value = prop.getProperty("threadCount", "1");
		return Integer.parseInt(value);
	}

	/**
	 * 获取指定系统的数据库连接配置
	 * @param sysname 系统名称
	 * @return 指定系统的数据库连接配置
	 */
	public static JdbcConf getJdbcConf(String sysname) {
		JdbcConf conf = new JdbcConf();
		conf.className = prop.getProperty(sysname + ".jdbc.className");
		conf.url = prop.getProperty(sysname + ".jdbc.url");
		conf.userName = prop.getProperty(sysname + ".jdbc.userName");
		conf.password = prop.getProperty(sysname + ".jdbc.password");
		return conf;
	}

	/**
	 * 获取指定线程的配置
	 * @param tName 线程编号（比如：t0.0）
	 * @return 指定线程的配置信息
	 */
	public static ThreadConf getThreadConf(String tName) {
		String value = prop.getProperty(tName);
		if (value == null || value.trim().isEmpty()) {
			return null;
		} else {
			return new ThreadConf(tName, value);
		}
	}

	/**
	 * 获取映射偏移量（offset=1）
	 * @return
	 */
	public static int getOffset() {
		String value = prop.getProperty("offset", "1");
		return Integer.parseInt(value);
	}

	/**
	 * 获取批量更新操作每批的数量（jdbc.batchSize=500）
	 * @return
	 */
	public static int getJdbcBatchSize() {
		String value = prop.getProperty("jdbc.batchSize", "500");
		return Integer.parseInt(value);
	}

	/**
	 * 获取查询结果集（ResultSet）每次从DB获取的记录数（jdbc.fetchSize=500）
	 * @return
	 */
	public static int getJdbcFetchSize() {
		String value = prop.getProperty("jdbc.fetchSize", "500");
		return Integer.parseInt(value);
	}

	/**
	 * 获取存储路径（storePath=/home/jyapp/tuomin）
	 * @return
	 */
	public static String getStorePath() {
		return prop.getProperty("storePath", "/home/jyapp/tuomin");
	}

	public static void main(String[] args) {
		System.out.println(Configuration.class.getResource("."));
		System.out.println(Configuration.class.getResource("/"));
		System.out.println(Configuration.class.getResource(""));
		System.out.println(getStorePath());
		System.out.println(getStorePath());
	}
}
