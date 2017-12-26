package com.fintech.platform.core.message;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
/**
 * 定义请求HeaderBean信息
 * @author Administrator
 *
 */
@Validated
public class HeaderBean implements Serializable{
	private static final long serialVersionUID = 1L;
    @NotEmpty(message="调用方系统标识globalID不能为空")
	private String globalID;
    @NotEmpty(message="类别代码categoryCode不能为空")
   	private String categoryCode;
    @NotEmpty(message="请求时间frontTransTime不能为空")
   	private String requestTime;
    @NotEmpty(message="接口名称interfaceName不能为空")
	private String interfaceName;
    //扩展字段1
    private String BAK1;
    //扩展字段2
    private String BAK2;
    
    public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

    public String getGlobalID() {
		return globalID;
	}
	public void setGlobalID(String globalID) {
		this.globalID = globalID;
	}
	
	public String getBAK1() {
		return BAK1;
	}
	public void setBAK1(String bAK1) {
		BAK1 = bAK1;
	}
	public String getBAK2() {
		return BAK2;
	}
	public void setBAK2(String bAK2) {
		BAK2 = bAK2;
	}
	
	public HeaderBean() {
    	this.requestTime =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
	}
	
   	public String toString() {
   		StringBuilder builder = new StringBuilder();
   		builder.append("headerBean [globalID=");
   		builder.append(getGlobalID());
   		builder.append(", categoryCode=");
   		builder.append(getCategoryCode());
   		builder.append(",interfaceName=");
   		builder.append(getInterfaceName());
   		builder.append(", requestTime=");
   		builder.append(getRequestTime());
   		builder.append(", BAK1=");
   		builder.append(getBAK1());
   		builder.append(", BAK2=");
   		builder.append(getBAK2());
   		builder.append("]");
   		return builder.toString();
   	}
    

}
