package com.jy.platform.schedule;

/**
 * 作业类接口，为了向前兼容，包名还是原来的包名
 * 
 * @author zhangyu
 * 
 */
public interface Job {

	/**
	 * 执行作业处理的逻辑
	 * 
	 * @throws Exception
	 */
	void execute() throws Exception;

	/**
	 * 进行已处理作业回退操作
	 * 
	 * @throws Exception
	 */
	void rollback() throws Exception;

	/**
	 * 暂停正在执行的作业处理
	 * 
	 * @throws Exception
	 */
	void pause() throws Exception;

	/**
	 * 获取作业处理的状态（进度），对于耗时较长的作业比较有用
	 * 
	 * @return 状态描述（进度描述）
	 */
	String getState();

}
