/**
 * 
 */
package com.jy.platform.core.common;

/**
 * <p>定义各业务系统的系统编码，供业务系统接入消息服务时使用</p>	
 * @author lin
 * @2014年12月3日 上午11:06:32
 */
public enum SystemFlag {

	PT("平台","001"),DQ("贷前","010"),DH("贷后","011"),LC("理财","100"),HX("核心","101");
	
	private String name;
	private String flag;

	private SystemFlag(String name,String flag){
		this.name = name;
		this.flag = flag;
	}
	
	public String getFlag(){
		return flag;
	}
	
	public String getName(){
		return name;
	}
}
