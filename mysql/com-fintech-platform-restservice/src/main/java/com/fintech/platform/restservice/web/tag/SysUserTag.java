package com.fintech.platform.restservice.web.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysauth.SysRoleAPI;
import com.fintech.platform.api.sysconfig.SysConfigAPI;

public class SysUserTag extends BodyTagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String userId=""; //id
	private UserInfo userInfo = null;
	private String type;
	private boolean hasBlank = true;
	private String emptyText;
	private String roleCodes;
	private String scope = "page" ;//设置 作用域
	private String var;//设置 变量名称

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		try {
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			//如果角色列表有数据则查询角色code下的所有用户信息
			if(StringUtils.isNotEmpty(roleCodes)){
				SysRoleAPI sysRoleAPI = (SysRoleAPI) context.getBean("sysRoleAPI");
				StringBuilder select = new StringBuilder();
				Map<String, Object> searchParams = new HashMap<String, Object>();
	    		searchParams.put("roleCodes", roleCodes);
	    		
				List<Map<String, Object>> list = sysRoleAPI.queryUserList(0, 1, searchParams);
				if("json".equals(type)){
					select.append("[");
					if(list != null && list.size()>0){
						if(hasBlank){
							select.append("{value:'',text:'");
							select.append(emptyText);
							select.append("'},");
						}
						for(int i=0;i<list.size();i++){
							select.append("{value:'");
							select.append(list.get(i).get("userId"));
							select.append("',text:'");
							select.append(list.get(i).get("userName")).append("[").append(list.get(i).get("userNo")).append("]");
							if(i==(list.size()-1)){
								select.append("'}");
							}else{
								select.append("'},");
							}
						}
					}else{
						select.append("{}");
					}
					select.append("]");
				}
				pageContext.getOut().write(select.toString());
			}else{
				OrgAPI orgAPI = (OrgAPI)context.getBean("orgAPI");
				if(StringUtils.isEmpty(userId) || "".equals(userId)){
					SessionAPI sessionAPI = (SessionAPI) context.getBean("sessionAPI");
					userInfo = sessionAPI.getCurrentUserInfo();//获取当前登录人
				}else{
					userInfo = orgAPI.getUserInfoDetail(userId);
					// 输出到页面
					if(userInfo != null && StringUtils.isNotEmpty(userInfo.getUserName())) {
						pageContext.getOut().write(userInfo.getUserName());//写入名称
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
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
		try{
			pageContext.setAttribute(getVar(), userInfo,cScope);
		}catch (Exception ex){
			throw new JspException (ex.toString(), ex) ;
		}
		return EVAL_PAGE ;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getVar() {
		return ( var!=null && !"".equals(var))  ? var:"user"  ;  //默认为user
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public boolean isHasBlank() {
		return hasBlank;
	}

	public void setHasBlank(boolean hasBlank) {
		this.hasBlank = hasBlank;
	}
	public String getRoleCodes() {
		return roleCodes;
	}
	public void setRoleCodes(String roleCodes) {
		this.roleCodes = roleCodes;
	}
	public String getEmptyText() {
		return emptyText;
	}

	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}
}
