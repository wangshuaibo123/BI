package com.jy.platform.restservice.web.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jy.platform.api.sysconfig.SysConfigAPI;
/**
 * 
 * @description: 控制 table 列表的 column 隐藏标签
 * @author chen_gang
 * @date:2014年12月13日下午7:12:51
 */
public class SysColumnHiddenTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private	String codeType ;//字典表 编码 如：jbpm_person_todo_hidden
	private String var;//设置 变量名称
	private String scope = "page" ;//设置 作用域
   
	public int doEndTag () throws JspException{
		int cScope = PageContext.PAGE_SCOPE ;
        if ( scope==null || scope.equalsIgnoreCase("page") ){
        	cScope = PageContext.PAGE_SCOPE ;
        }else if ( "request".equalsIgnoreCase( scope ) ){
        	cScope = PageContext.REQUEST_SCOPE ;
        }else if ( "session".equalsIgnoreCase( scope ) ){
        	 cScope = PageContext.SESSION_SCOPE ;
        }else{
        	cScope = PageContext.APPLICATION_SCOPE ;
        }

        BodyContent body = this.getBodyContent () ;
        
		if (body != null) body.clearBody () ;

		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest () ;
		
		StringBuilder sb = new StringBuilder();
		try{
			if(StringUtils.isNotEmpty(codeType)){
			    ApplicationContext context = null;
                
                try{
                    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
                    ServletContext servletContext = webApplicationContext.getServletContext();
                    context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                }
                catch(Exception e){
                    context = SysConfigAPI.getApplicationContext();  //spring boot启动时会注入
                }
				
				com.jy.platform.api.sysdict.SysDictAPI sysDictAPI = (com.jy.platform.api.sysdict.SysDictAPI)context.getBean("sysDictAPI");
				
				List<Map> allList = sysDictAPI.getDictByKey(codeType);
				if(allList != null && allList.size()>0){
					for(int j=0;j<allList.size();j++){
						String dicValue = String.valueOf(allList.get(j).get("DICVALUE"));
						if(!"".equals(sb.toString())) sb.append(",");
						sb.append("#").append(dicValue).append("#");
					}
				}
			}
			
			pageContext.setAttribute(getVar(), sb.toString(),cScope);
		}catch (Exception ex){
			throw new JspException (ex.toString(), ex) ;
		}

		return EVAL_PAGE ;
	}
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}

}
