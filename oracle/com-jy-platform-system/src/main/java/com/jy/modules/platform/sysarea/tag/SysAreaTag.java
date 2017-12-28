package com.jy.modules.platform.sysarea.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jy.modules.platform.sysarea.dto.SysAreaDTO;
import com.jy.modules.platform.sysarea.service.SysAreaService;
import com.jy.platform.api.sysconfig.SysConfigAPI;

/**
 * 城市标签，根据城市编码返回城市名称
 * 
 * @author zhanglin
 * @date 20141025 17:00
 */
public class SysAreaTag extends TagSupport {

	private static final Log log = LogFactory.getLog(SysAreaTag.class);

	private static final long serialVersionUID = -447565596786340001L;

	/** 返回类型 html,json,text **/
	private String dataType = "text";
	/** 省级下拉列表ID **/
	private String provinceId = "s_provinceId";
	/** 省级下拉列表ID **/
	private String cityId = "s_cityId";
	/** 省级下拉列表ID **/
	private String countryId = "s_countryId";
	/** 联动层次 **/
	private String level = "3";
	/** 根标识 **/
	private long root = 0;
	/** 默认值 **/
	private String defaultVal = "1,2,3";
	/** 选中的ID值 **/
	private String selectId = null;
	/** 城市编码 **/
	private String cityCode = null;
	/** 样式名称 **/
	private String className = null;
	/** 是否有默认文本 **/
	private boolean hasBlank = true;
	/** 不是NULL **/
	private boolean notNull = false;
	/**onchange事件方法名称**/
	private String onChange = null;
	/**所有城市信息**/
    private static List<SysAreaDTO> allDataList = null;
    /**省份信息**/
    private  List<SysAreaDTO> provinceList = new ArrayList<SysAreaDTO>();
	/**市级城市信息**/
    private  List<SysAreaDTO> cityList = new ArrayList<SysAreaDTO>();
    /**区县城市信息**/
    private  List<SysAreaDTO> countryList = new ArrayList<SysAreaDTO>();
    /**某城市父子关系集合**/
    private List<SysAreaDTO> areas = new ArrayList<SysAreaDTO>();
    
    private SysAreaService service = null;
    
    private String [] emptyText ={"请选择省份","请选择城市","请选择区县"};
    
    private final static int PROVINCE = 0;
    private final static int CITY = 1;
    private final static int COUNTRY = 2;
    
    /**
     * 初始化数据源
     */
    private void initContext(){
    	try {
    	    ApplicationContext context = null;
            try{
                WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
                ServletContext servletContext = webApplicationContext.getServletContext();
                context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            }
            catch(Exception e){
                context = SysConfigAPI.getApplicationContext();  //spring boot启动时会注入
            }
			service = (SysAreaService) context.getBean("com.jy.modules.platform.sysarea.service.SysAreaService");
			//初始化数据
			if(allDataList == null){
				Map<String, Object> searchParams = new HashMap<String, Object>();
				searchParams.put("dto", new SysAreaDTO());
				try {
					allDataList = service.searchSysArea(searchParams);
					/*if(allDataList!=null && allDataList.size()>0){
						for(int i=0,len = allDataList.size();i<len;i++){
				    		SysAreaDTO sysAreaDTO = (SysAreaDTO)allDataList.get(i);
				    		if(sysAreaDTO.getParentId()==Long.parseLong(root)){
				    				provinceList.add(sysAreaDTO);
				    		}
						}	
					}*/
				} catch (Exception e) {
					if (log.isErrorEnabled()) {
						log.error("加载城市数据异常...",e);
					}
				}
			}
			
			if (!StringUtils.isEmpty(cityCode)) {
				Map<String, Object> searchParams = new HashMap<String, Object>();
				SysAreaDTO dto = new SysAreaDTO();
				dto.setAreaCode(cityCode);
				searchParams.put("dto", dto);
				try {
					areas = service.searchSysAreaByAreaCode(searchParams);
				} catch (Exception e) {
					if (log.isErrorEnabled()) {
						log.error("根据城市编码查询城市信息错误", e);
					}
				}
			}
		} catch (BeansException e) {
			if(log.isErrorEnabled()){
				log.error("城市标签加载service接口异常...",e);
			}
		}
    }
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		initContext();
		StringBuffer result = new StringBuffer();
		if (!StringUtils.isEmpty(areas) && areas.size() > 0) {
			if ("json".equalsIgnoreCase(dataType)) {
				result.append(buildJson(areas));
			} else if ("html".equalsIgnoreCase(dataType)) {
				result.append(buildHtml(areas));
			} else if ("text".equalsIgnoreCase(dataType)) {
				for (int i = 0,len = areas.size(); i < len; i++) {
					if (isMunicipality(areas.get(i).getAreaName())) {
						continue;
					}
					result.append(areas.get(i).getAreaName());
				}
			}
		}
		try {
			pageContext.getOut().write(result.toString());
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("根据城市编码查询城市信息标签错误", e);
			}
		}
		return 0;
	}
	
	/**
	 * 构建JSON数据
	 * @param result
	 * @return
	 * @throws JspException
	 */
	private String buildJson(List<SysAreaDTO> areas) throws JspException{
		StringBuffer result = new StringBuffer();
		result.append("[");
		for (int i = 0,len = areas.size(); i < len; i++) {
			if (isMunicipality(areas.get(i).getAreaName())) {
				continue;
			}
			result.append("{areaCode:'"+areas.get(i).getAreaCode()+"',areaName:'"+areas.get(i).getAreaName());
			if (i == (areas.size() - 1)) {
				result.append("'}");
			} else {
				result.append("'},");
			}
		}
		result.append("]");
		return result.toString();
	}
	
	/**
	 * 是否是直辖市的情况
	 * @param areaName
	 * @return
	 */
	private boolean isMunicipality(String areaName){
		boolean result = false;
		if ("市辖区".equals(areaName)||"市".equals(areaName)||"县".equals(areaName)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 构建HTML格式数据
	 * @param result 
	 * @return
	 * @throws JspException 
	 */
	private String buildHtml(List<SysAreaDTO> areas) throws JspException{
		StringBuffer result = new StringBuffer();
		result.append(buildOptionHtml(provinceId, PROVINCE, buildOptionData(areas,PROVINCE)));
		result.append(buildOptionHtml(cityId, CITY, buildOptionData(areas,CITY)));
		if("3".equals(level))result.append(buildOptionHtml(countryId, COUNTRY, buildOptionData(areas,COUNTRY)));
		return result.toString();
	}
	
	/**
	 * 构建option html
	 * @param id
	 * @param type
	 * @param data
	 * @return
	 * @throws JspException 
	 */
	private String buildOptionHtml(String id,int type, String data) throws JspException{
		StringBuffer result = new StringBuffer();
		result.append("<select ");
		result = getAllProp(result,id);
		result.append(">");
		if(hasBlank)result.append("<option value=\"\">"+emptyText[type]+"</option>");
		result.append(data);
		result.append("</select>&nbsp;&nbsp;");
		return result.toString();
	}
	
	
	
	
	
	/**
	 * 构建option中的数据项
	 * todo 待优化结构
	 * @return
	 */
	private String buildOptionData(List<SysAreaDTO> areas,int type) {
		StringBuffer returnData = new StringBuffer();
		if(areas!=null && areas.size()>0){
			if(areas.size()==3){
				if(type==PROVINCE){//说明输入的编码为省级编码
					returnData.append(handlerData(areas.get(0).getId(),root));
				}else if (type==CITY){//说明输入的编码为市级编码
					returnData.append(handlerData(areas.get(1).getId(),areas.get(0).getId()));
				}else if (type==COUNTRY){//说明输入的编码为县级编码
					returnData.append(handlerData(areas.get(2).getId(),areas.get(1).getId()));
				}
			}else if(areas.size()==2){
				if(type==PROVINCE){
					returnData.append(handlerData(areas.get(0).getId(),root));
				}else if (type==CITY){
					returnData.append(handlerData(areas.get(1).getId(),areas.get(0).getId()));
				}else if (type==COUNTRY){
					returnData.append(handlerData(-1,areas.get(1).getId()));
				}
			}else if (areas.size()==1) {
				if(type==PROVINCE){
					returnData.append(handlerData(areas.get(0).getId(),root));
				}else if (type==CITY){
					returnData.append(handlerData(-1,areas.get(0).getId()));
				}
			}
		}
		return returnData.toString();
	}
	
	/**
	 * 处理循环数据
	 * @param data
	 * @return
	 */
	private String handlerData(long id,long parent){
		StringBuffer result = new StringBuffer();
			for (Iterator<SysAreaDTO> iterator = allDataList.iterator(); iterator.hasNext();) {
				SysAreaDTO sysAreaDTO = (SysAreaDTO) iterator.next();
				boolean isChecked = false;
				if(id==sysAreaDTO.getId()){
					isChecked =true;
				}
				if(sysAreaDTO.getParentId()==parent){
					if (isChecked) {
						result.append("<option selected id=\""+sysAreaDTO.getId()+"\" value=\""+sysAreaDTO.getAreaCode()+"\">"+sysAreaDTO.getAreaName()+"</option>");
					}else{
						result.append("<option  id=\""+sysAreaDTO.getId()+"\" value=\""+sysAreaDTO.getAreaCode()+"\">"+sysAreaDTO.getAreaName()+"</option>");
					}
				}
				
				
			}
		return result.toString();
	}

	/**
	 * 设置标签的属性
	 * 
	 * @param StringBuffer
	 *            results 传入标签属性StringBuffer
	 * @return StringBuffer 返回加入标签属性后的StringBuffer
	 */
	private StringBuffer getAllProp(StringBuffer results,String id) throws JspException {

		prepareAttribute(results, "id",id);
		prepareAttribute(results, "name", id);
		if (!StringUtils.isEmpty(onChange)) {
			prepareAttribute(results, "onchange", getOnChange());
		}
		prepareAttribute(results, "className",getClassName());
		prepareAttribute(results, "notNull",isNotNull());
		//results.append(" "+getExtendProperty());
		return results;
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

	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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


	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
	
	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	

}
