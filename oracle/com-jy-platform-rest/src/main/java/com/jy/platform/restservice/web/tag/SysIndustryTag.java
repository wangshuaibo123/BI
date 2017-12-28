/**
 * 
 */
package com.jy.platform.restservice.web.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jy.platform.api.sysconfig.SysConfigAPI;
import com.jy.platform.api.sysindustry.SysIndustryAPI;

/**
 * <p>行业数据标签</p>
 * <p>只实现了返回html dom结构的数据</p>		
 * @author lin
 * @2014年12月4日 下午2:23:55
 */
public class SysIndustryTag extends TagSupport{
	private static final long serialVersionUID = 4896064816204542695L;

	private static final Logger logger = LoggerFactory.getLogger(SysIndustryTag.class);
	
	private String industryDomId = "s_industry_id"; 
	private String positionDomId = "s_position_id";
	private String defaultIndustry = "";
	private String defaultPosition = "";
	private String displayFormat = "html";//显示格式，默认html,还有text,json
	private String emptyText = "请选择";
	private String onchange;
	private String className= null;
	private int level = 2;
	private boolean hasBlank = true;
	private boolean hasLable = false;//是否显示label标签
	private String industryLableName = "行业";
	private String positionLableName = "职位";
	private boolean single = false;
	private String extendProperty= null;;//扩展属性


	public String getExtendProperty() {
		return extendProperty;
	}

	public void setExtendProperty(String extendProperty) {
		this.extendProperty = extendProperty;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int doStartTag() throws JspException {
		SysIndustryAPI api = getInstance();
		StringBuilder result = new StringBuilder();
		List<Map> industries = new ArrayList<Map>();
		try {
			industries = api.getIndustries("");
			if ("html".equalsIgnoreCase(displayFormat)) {
				result.append(createIndustryAndPositionDom(api, industries));
			} else if("json".equalsIgnoreCase(displayFormat)){
				result.append(createIndustryAndPositionJson(api, industries));
			}else{
				throw new RuntimeException("输出格式为:[" + displayFormat + "]的不支持");
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("###################行业自定义标签##############################");
				logger.debug("请求的数据格式为：" + displayFormat + " 返回数据为：" + result.toString());
				logger.debug("###################行业自定义标签##############################");
			}
			pageContext.getOut().write(result.toString());
		} catch (Exception e) {
			logger.error("加载行业字典数据异常...", e);
		}
		return 0;
	}
	
	/**
	 * 返回json格式的数据
	 * @param api
	 * @param industries
	 * @return
	 */
	private String createIndustryAndPositionJson(SysIndustryAPI api,
			List<Map> industries) throws Exception {
		StringBuilder result = new StringBuilder();
		result.append("[");
		if(industries != null && industries.size()>0){
			for(int i=0;i < industries.size();i++){
			   boolean isExistDefaultValue = defaultIndustry!=null 
						&& !"".equals(defaultIndustry) 
						&& defaultIndustry.equals(industries.get(i).get("INDUSTRYCODE").toString());
			   if(isExistDefaultValue){
				    result.append("{value:\"");
				    String positionValue =getPositonByIndustry(api);
				    if( positionValue !=null ){
				    	result.append(positionValue.split("#")[0]);//存在职位时，显示职位的CODE
				    }else{
				    	result.append(industries.get(i).get("INDUSTRYCODE"));
				    }
					result.append("\",text:\"");
					 if( positionValue !=null ){
					    	result.append(industries.get(i).get("INDUSTRYNAME") + positionValue.split("#")[1]);
					    }else{
					    	result.append(industries.get(i).get("INDUSTRYNAME"));
					    }
					result.append("\"}");
			   }else{
				   result.append("{value:\"");
				   result.append(industries.get(i).get("INDUSTRYCODE"));
				   result.append("\",text:\"");
				   result.append(industries.get(i).get("INDUSTRYNAME"));
				   if(i!=industries.size()-1){
					   result.append("\"},");
				   }else{
					   result.append("\"}");
				   }
				  
			   }
			}
		}
		result.append("]");
		return result.toString();
	}
	
	private String getPositonByIndustry(SysIndustryAPI api) throws Exception{
		String result = null;
		 if(defaultIndustry!=null && !"".equals(defaultIndustry)){
			 List<Map> positions = api.getPositionsByIndustry(defaultIndustry);
				for (int i = 0,len = positions.size(); i <len ; i++) {
					 boolean isExistDefaultPositionValue = defaultPosition!=null 
								&& !"".equals(defaultPosition) 
								&& defaultPosition.equals(positions.get(i).get("INDUSTRYCODE").toString());
					 if(isExistDefaultPositionValue){
						 result = positions.get(i).get("INDUSTRYCODE")+"#"+ positions.get(i).get("INDUSTRYNAME");
					 }
				}
		 }
		return result;
	}
	


	/**
	 * 加载api接口实例
	 * @return
	 */
	private SysIndustryAPI getInstance(){
	    ApplicationContext context = null;
        try{
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
            ServletContext servletContext = webApplicationContext.getServletContext();
            context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        }
        catch(Exception e){
            context = SysConfigAPI.getApplicationContext();  //spring boot启动时会注入
        }
		return (SysIndustryAPI)context.getBean("sysIndustryAPI");
	}
	

	/**
	 * 创建行业和职位的DOM结构
	 * @param industries
	 * @return
	 */
	private String createIndustryAndPositionDom(SysIndustryAPI api,List<Map> industries){
		StringBuilder result = new StringBuilder();
		if (hasLable) {
			result.append("<lable>"+industryLableName+"：</lable>");
		}
		result.append("<select id='"+industryDomId+"' name='"+industryDomId+"'");
		if (className !=null) {
			result.append("class='" + className + "'");
		}
		
		if(extendProperty!=null){
			result.append(" "+extendProperty+" ");
		}
		
		result.append(" onchange='loadPosition(this);'>");
		if (hasBlank) {
			result.append("<option value=''>" + emptyText + "<option>");
		}
		if (industries != null && industries.size() > 0) {
			for (int i = 0,len = industries.size(); i <len ; i++) {
				result.append("<option value='"+industries.get(i).get("INDUSTRYCODE")+"'");
				final boolean isExistDefaultValue = defaultIndustry!=null 
										&& !"".equals(defaultIndustry) 
										&& defaultIndustry.equals(industries.get(i).get("INDUSTRYCODE").toString());
				if (isExistDefaultValue) {
					result.append(" selected ='selected' ");
				}
				result.append(">" + industries.get(i).get("INDUSTRYNAME") + "</option>");
			}
		}
		result.append("</select>");
		if (level >= 2) {
			if (hasLable) {
				result.append("&nbsp;&nbsp;&nbsp;");
				result.append("<lable>"+positionLableName+"：</lable>");
			}
			result.append(createPositionDom(api));
		}
		//只显示position
		if (single) {
			result = new StringBuilder();
			result.append(createPositionDom(api));
		}
		
		return result.toString();
	}
	
	/**
	 * 创建职位dom结构
	 * @param api
	 * @return
	 */
	private String createPositionDom(SysIndustryAPI api){
		StringBuilder result = new StringBuilder();
		result.append("<select id='"+positionDomId+"' name='"+positionDomId+"'");
		if (className !=null) {
			result.append("class='" + className + "'");
		}
		result.append(">");
		if (hasBlank) {
			result.append("<option value=''>" + emptyText + "<option>");
		}
		if (defaultPosition != null && !"".equals(defaultPosition)) {
			try {
				List<Map> positions = api.getPositionsByIndustry(defaultIndustry);
				for (int i = 0,len = positions.size(); i <len ; i++) {
					result.append("<option value='"+positions.get(i).get("INDUSTRYCODE")+"'");
					if (positions.get(i).get("INDUSTRYCODE").toString().equals(defaultPosition)) {
						result.append(" selected='selected'");
					}
					result.append(">");
					result.append(positions.get(i).get("INDUSTRYNAME")+"</option>");
				}
			} catch (Exception e) {
				logger.error("加载职位字典数据异常...",e);
			}
		}
		result.append("</select>");
		return result.toString();
		
	}
	
	
	public String getIndustryDomId() {
		return industryDomId;
	}



	public void setIndustryDomId(String industryDomId) {
		this.industryDomId = industryDomId;
	}



	public String getPositionDomId() {
		return positionDomId;
	}



	public void setPositionDomId(String positionDomId) {
		this.positionDomId = positionDomId;
	}



	public String getDefaultIndustry() {
		return defaultIndustry;
	}



	public void setDefaultIndustry(String defaultIndustry) {
		this.defaultIndustry = defaultIndustry;
	}



	public String getDefaultPosition() {
		return defaultPosition;
	}



	public void setDefaultPosition(String defaultPosition) {
		this.defaultPosition = defaultPosition;
	}



	public String getDisplayFormat() {
		return displayFormat;
	}



	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}



	public String getEmptyText() {
		return emptyText;
	}



	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}



	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public boolean isHasBlank() {
		return hasBlank;
	}


	public void setHasBlank(boolean hasBlank) {
		this.hasBlank = hasBlank;
	}
	
	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean isHasLable() {
		return hasLable;
	}


	public void setHasLable(boolean hasLable) {
		this.hasLable = hasLable;
	}
	
	public String getIndustryLableName() {
		return industryLableName;
	}


	public void setIndustryLableName(String industryLableName) {
		this.industryLableName = industryLableName;
	}


	public String getPositionLableName() {
		return positionLableName;
	}


	public void setPositionLableName(String positionLableName) {
		this.positionLableName = positionLableName;
	}
	
	public boolean isSingle() {
		return single;
	}


	public void setSingle(boolean single) {
		this.single = single;
	}
}
