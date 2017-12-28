package com.jy.platform.api.sysarea;

import java.util.List;


import java.util.Map;

public interface SysAreaAPI {
	/**
	 * 根据areaCode查询下级地区信息
     * @author bieshuangping
     * @param code
     * @return
     */
    public List<Map> queryChildAreaByCode(String areaCode);
    
    /**
     * 根据parentId查询下级地区信息
     * @author bieshuangping
     * @param pid
     * @return
     */
    public List<Map> queryChildAreaByPid(String parentId);
    
    /**
     * 根据areaCode查询上级areaCode
     * @author bieshuangping
     * @param pid
     * @return
     */
    public String queryParentCodeByCode(String areaCode);
    
    /**
     * 根据areaCode查询地区名称
     * @author bieshuangping
     * @param pid
     * @return
     */
    public Map getAreaByCode(String areaCode);
    
}
