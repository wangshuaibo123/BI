package com.jy.modules.platform.flume.sink;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleJdbcSink extends AbstractSink implements Configurable{
	private Logger log = LoggerFactory.getLogger(OracleJdbcSink.class);

	//RPC消息头常量
    private String LOGGER_NAME = "flume.client.logback.logger.name";
    private String TIMESTAMP = "timestamp";
    private String LOG_LEVEL = "flume.client.logback.log.level";
    private String MESSAGE_ENCODING = "flume.client.logback.message.encoding";
    private String ERROR_MESSAGE = "flume.client.logback.message";
    private String IP = "flume.client.ip";
    private String SYSTEM_FLAG = "flume.client.system.flag";
	
	private String url;//数据库URL
	private String user;
	private String password;
	
	private BasicDataSource dataSource;//数据库连接池
	
	private int batchSize = 10;//执行一次commit的批次
	private AtomicInteger ai = new AtomicInteger(0);
	private int maxRetries = 5;//失败最大重试次数
	
	/**
	 * 读取配置
	 */
	@Override
	public void configure(Context content){
		this.url = content.getString("url");
		this.user = content.getString("user");
		this.password = content.getString("password");
	}
	
	
	/**
	 * Sink start
	 * 初始化数据库连接
	 */
	@Override
	public void start(){
		super.start();
		
		//初始化datasource
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setInitialSize(1);//初始连接数
		dataSource.setMaxIdle(1);//最大空闲连接数
		dataSource.setMaxActive(2);//最大活动连接数
	}
	
	
	@Override
	public Status process() throws EventDeliveryException{
		Status status = Status.READY;
		Event event = null;
		Map<String,String> header = null;
		Map<String,String> logMap = null;
		List<Map<String,String>> logList = new ArrayList<Map<String,String>>();
		Connection conn = null;
		PreparedStatement ps = null;

	    //Start transaction
	    Channel ch = getChannel();
	    Transaction txn = ch.getTransaction();
	    txn.begin();
	    try{
	    	for(int i=0; i<batchSize; i++){
	    		event = ch.take();
	    		
	    		if(event != null){
	    			logMap = new HashMap<String,String>();
	    			header = event.getHeaders();
	    			
			        if(event.getBody()!=null && event.getBody().length>0){
			        	logMap.put(LOGGER_NAME, header.get(LOGGER_NAME));
		    			logMap.put(TIMESTAMP, header.get(TIMESTAMP));
		    			logMap.put(IP, header.get(IP));
		    			logMap.put(SYSTEM_FLAG, header.get(SYSTEM_FLAG));
		    			logMap.put(ERROR_MESSAGE, new String(event.getBody(),Charset.forName("UTF8")));
		    			
		    			logList.add(logMap);
			        }
                } 
	    		//当获取不到事件时，跳出循环
	    		else{
	    			if(logList==null || logList.size()==0){
	    				status = Status.BACKOFF;
	    			}
                    break;
                }
	    	}
	    	
	    	conn = dataSource.getConnection();
	    	//创建一个PreparedStatement对象
            ps = conn.prepareStatement("insert into SYS_APP_ERROR_INFO (id, node_name, app_flag, create_time, concent, MATCHED_FLAG) "
            		+ " values (seq_SYS_APP_ERROR_INFO.nextval,?,?,to_char(sysdate,'yyyy-MM-dd'),?,?)");
	    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	Timestamp ts = null;
	        if(logList!=null && logList.size()>0){
	        	ps.clearBatch();
                for(int i=0; i<logList.size(); i++){
                	logMap = logList.get(i);
                	
//                	ps.setLong(1, getNextId(conn));
                    ps.setString(1, logMap.get(IP)!=null ? (String)logMap.get(IP) : null);
                    ps.setString(2, logMap.get(SYSTEM_FLAG)!=null ? (String)logMap.get(SYSTEM_FLAG) : null);
//                    if(logMap.get(TIMESTAMP) != null){
//                    	try{
//                    		ts = new Timestamp(Long.valueOf((String)logMap.get(TIMESTAMP)));
//                            ps.setString(4, df.format(ts));
//                    	}
//                    	catch(Exception e){
//                    		e.printStackTrace();
//                    		
//                    		ps.setString(4, "");
//                    	}
//                    }
//                    else{
//                    	ps.setString(4, "");
//                    }
                    
                    if(logMap.get(ERROR_MESSAGE)!=null && ((String)logMap.get(ERROR_MESSAGE)).length()>3500){
                    	ps.setString(3, (((String)logMap.get(ERROR_MESSAGE)).substring(0, 3500)).trim());
                    }
                    else{
                    	ps.setString(3, ((String)logMap.get(ERROR_MESSAGE)).trim());
                    }
                    ps.setString(4, "N");
                    
                    ps.addBatch();
                }
                
                ps.executeBatch();
                
                conn.commit();
	        }
	        
	        txn.commit();
	    } 
	    catch(Throwable t){
	    	t.printStackTrace();
	    	
	    	if(conn != null){
	    		try{
					conn.rollback();
				} 
	    		catch(SQLException e){
					e.printStackTrace();
				}
	    	}
	    	
	    	try{
	    		//如果已达最大重试次数
	    		if(ai.get() == maxRetries){
	    			log.info("达到最大重试次数，ai=="+ai.get());
	    			ai.set(0);//重新计数
	    			log.info("达到最大重试次数，ai=="+ai.get());
	    			
	    			txn.commit();//提交，跳过异常数据
	    		}
	    		else{
	    			log.info("出现异常，统计+1，准备回滚，ai=="+ai.get());
	    			ai.incrementAndGet();
	    			
	    			txn.rollback();
	    		}
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    		log.error(e.getMessage());
	    	}

	    	status = Status.BACKOFF;
	    	
	    	if(t instanceof Error){
	    	    throw (Error)t;
	    	}
	    }
	    finally{
	    	if(ps != null){
	    		try{
					ps.close();
					ps = null;
				} 
	    		catch(SQLException e){
					e.printStackTrace();
				}
	    	}
	    	
	    	if(conn != null){
	            try{
	            	conn.close();//归还连接
	            } 
	            catch(SQLException e){
	                e.printStackTrace();
	            }
	        }
	    	
	    	txn.close();
	    	
	    	logList = null;
	    	header = null;
	    	logMap = null;
	    }
	    
	    return status;
	}

	
	/**
	 * Sink stop
	 */
	@Override
	public void stop(){
		super.stop();
		
        if(dataSource != null){
            try{
            	dataSource.close();
            } 
            catch(SQLException e){
                e.printStackTrace();
            }
        }
	}
	
	
	/**
	 * 获取id
	 * @return
	 * @throws Exception
	 */
//	private int getNextId(Connection conn) throws Exception{
//		int id = 0;
//		Statement st = null;
//		ResultSet rs = null;
//		
//		try{
//	    	st = conn.createStatement(); 
//	    	rs = st.executeQuery("select seq_SYS_APP_ERROR_INFO.nextval as id from dual");
//	    	rs.next();
//	    	
//	    	id = rs.getInt(1);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		finally{
//			if(rs != null){
//				try{
//					rs.close();
//					rs = null;
//				}catch(Exception e){e.printStackTrace();}
//			}
//			
//			if(st != null){
//				try{
//					st.close();
//					st = null;
//				}catch(Exception e){e.printStackTrace();}
//			}
//		}
//		
//    	return id;
//	}
	
}
