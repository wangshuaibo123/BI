package com.fintech.platform.restservice.web.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.dataprv.DataPrvAPI;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysconfig.SysConfigAPI;

/**
 * 虚拟组织树 虚线管理 自定义标签
 * 
 * @author
 *
 */
public class RuleMappingTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String onchange;
	private String type = "select";// 默认类型为select，可以支持select、json二种
	private String extendProperty;
	private String emptyText = "请选择";
	private String userId;
	private String orgType;
	private boolean hasBlank = true;
	private String className;
	private String defaultValue;
	private boolean isOrder = true;
	private boolean currentUser = true;
	private String validateState;

	public boolean isOrder() {
		return isOrder;
	}

	public void setOrder(boolean isOrder) {
		this.isOrder = isOrder;
	}

	public boolean isCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(boolean currentUser) {
		this.currentUser = currentUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		StringBuffer select = new StringBuffer();
		// options
		try {
			Map<String, String> maps = null;
			if (userId != null && orgType != null && !"".equals(userId)
					&& !"".equals(orgType)) {
				ApplicationContext context = null;
				try{
					WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
					ServletContext servletContext = webApplicationContext.getServletContext();
					context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				}catch(Exception e){
					context = SysConfigAPI.getApplicationContext();
				}
				DataPrvAPI dataPrvAPI = (DataPrvAPI) context
						.getBean("dataPrvAPI");

				OrgAPI orgAPI = (OrgAPI) context.getBean("orgAPI");

				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("vmMappingtableName", orgType + "_"
						+ "VMRULE_MAPPING");
				paramMap.put("userId", userId);
				paramMap.put("orgType", orgType);
				paramMap.put("isOrder", isOrder);
				paramMap.put("validateState", validateState);
				maps = dataPrvAPI.findVmruleMappingByUserId(paramMap);
				if (defaultValue != null && !"".equals(defaultValue)) {
					UserInfo userInfo = orgAPI.getUserInfoDetail(defaultValue);
					if (userInfo != null && !"".equals(userInfo)) {
						maps.put(userInfo.getUserId().toString(),
								userInfo.getUserName());
					}
				}

			}
			if (type.equals("select")) {
				select.append("<select ");
				select = getAllProp(select);
				select.append(">");
				// 默认有空白项
				if (hasBlank) {
					select.append("<option value=''>");
					select.append(emptyText);
					select.append("</option>");
				}
				if (maps != null && maps.size() > 0) {
					for (Map.Entry<String, String> entry : maps.entrySet()) {
						if (currentUser) {
							// 得到当前登录人
							Subject currentSubject = SecurityUtils.getSubject();
							UserInfo currentUser = (UserInfo) currentSubject
									.getSession().getAttribute("userInfo");
							if (currentUser.getUserId().toString()
									.equals(entry.getKey()))
								continue;
						}
						select.append("<option value='");
						select.append(entry.getKey());
						select.append("'");
						if (entry.getKey().equals(defaultValue)) {
							select.append(" selected='selected'");
						}
						select.append(">");
						select.append(entry.getValue());
						select.append("</option>");
					}
				}
				select.append("</select>");
			}

			if (type.equals("json")) {
				select.append("[");
				select.append("{value:'");
				select.append("");
				select.append("',text:'");
				select.append("请选择'},");
				if (maps != null && maps.size() > 0) {
					int i = 0;
					for (Map.Entry<String, String> entry : maps.entrySet()) {
					
						
						// [{"value": "", "text": "全部"},{"value": "1", "text":
						// "人对人"},{"value": "2", "text": "人对机构"},{"value": "3",
						// "text": "机构对机构"}]
						select.append("{value:'");
						select.append(entry.getKey());
						select.append("',text:'");
						select.append(entry.getValue());
						if (i == (maps.size() - 1)) {
							select.append("'}");
						} else {
							select.append("'},");
						}
						i++;

					}
				}
				select.append("]");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 输出到页面
		try {
			pageContext.getOut().write(select.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 设置标签的属性
	 * 
	 * @param StringBuffer
	 *            results 传入标签属性StringBuffer
	 * @return StringBuffer 返回加入标签属性后的StringBuffer
	 */
	private StringBuffer getAllProp(StringBuffer results) throws JspException {

		prepareAttribute(results, "id", getId());
		prepareAttribute(results, "name", getName());
		prepareAttribute(results, "onchange", getOnchange());
		prepareAttribute(results, "className", getClassName());
		results.append(" " + getExtendProperty());
		return results;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Prepares an attribute if the value is not null, appending it to the the
	 * given StringBuffer.
	 * 
	 * @param handlers
	 *            The StringBuffer that output will be appended to.
	 */
	protected void prepareAttribute(StringBuffer handlers, String name,
			Object value) {
		if (value != null) {
			handlers.append(" ");
			handlers.append(name);
			handlers.append("=\"");
			handlers.append(value);
			handlers.append("\"");
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the onchange
	 */
	public String getOnchange() {
		return onchange;
	}

	/**
	 * @param onchange
	 *            the onchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (null == type) {
			this.type = "select";
		} else {
			this.type = type;
		}
	}

	public String getExtendProperty() {
		return extendProperty;
	}

	public void setExtendProperty(String extendProperty) {
		this.extendProperty = extendProperty;
	}

	public String getEmptyText() {
		return emptyText;
	}

	public void setEmptyText(String emptyText) {
		if (null == emptyText) {
			this.emptyText = "请选择";
		} else {
			this.emptyText = emptyText;
		}

	}

	public boolean isHasBlank() {
		return hasBlank;
	}

	public void setHasBlank(boolean hasBlank) {
		this.hasBlank = hasBlank;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getValidateState() {
		return validateState;
	}

	public void setValidateState(String validateState) {
		this.validateState = validateState;
	}
}
