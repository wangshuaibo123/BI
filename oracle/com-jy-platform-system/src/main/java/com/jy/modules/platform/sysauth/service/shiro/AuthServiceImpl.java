package com.jy.modules.platform.sysauth.service.shiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.jy.modules.platform.sysMenu.dto.SysMenuDTO;
import com.jy.modules.platform.sysMenu.service.SysMenuService;
import com.jy.modules.platform.sysauth.dto.SysResourceRoleDTO;
import com.jy.modules.platform.sysauth.dto.SysRoleDTO;
import com.jy.modules.platform.sysauth.service.SysResourceService;
import com.jy.modules.platform.sysauth.service.SysRoleService;
import com.jy.platform.api.sysconfig.SysConfigAPI;

@Service("shiroAuthService")
public class AuthServiceImpl implements IAuthService {
    
	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    
    //注意/r/n前不能有空格
    private static final String CRLF= "\r\n";
    //private static final String LAST_AUTH_STR= "/** =authc,forceLogout\r\n";
    private static final String LAST_AUTH_STR= "/** =authc\r\n";
    private static final String SUPER_ADMIN= "super_admin";
    
	@Autowired
	@Lazy
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    @Autowired
    @Lazy
	private SysResourceService sysResourceService;
	@Autowired
	@Lazy
	private SysRoleService sysRoleService;
	@Autowired
	@Lazy
	private SysMenuService sysMenuService;
    @Autowired
    @Lazy
    private SysConfigAPI sysConfigAPI;
    
    @Value("${app.datasource:YES}")
    private String appDatasource;

    
    @Override
    public String loadFilterChainDefinitions() {

       StringBuffer sb = new StringBuffer("");
       sb.append(getFixedAuthRule())
       .append(getDynaAuthRule())
       .append(getRestfulOperationAuthRule())
       .append(CRLF);
       log.debug("auth:"+sb.toString());
       return sb.toString();
    }
    
    //生成restful风格功能权限规则
    private String getRestfulOperationAuthRule() {
//       
//       List<Operation> operations = dao.queryEntitys("from Operation o", newObject[]{});
//       
//       Set<String> restfulUrls = newHashSet<String>();
//       for(Operation op : operations) {
//           restfulUrls.add(op.getUrl());
//       }
//       StringBuffer sb  = newStringBuffer("");
//       for(Iterator<String> urls =  restfulUrls.iterator(); urls.hasNext(); ) {
//           String url = urls.next();
//           if(! url.startsWith("/")) {
//              url = "/"+ url ;
//           }
//           sb.append(url).append("=").append("authc, rest[").append(url).append("]").append(CRLF);
//       }
//       returnsb.toString();
       return "";
    }
    
    
    //根据角色，得到动态权限规则
    private String getDynaAuthRule() {
        if("NO".equalsIgnoreCase(appDatasource)) return "";
        
        StringBuffer sb = new StringBuffer("");
       
        //key放url,value放角色code
        Map<String, Set<String>> rules = new HashMap<String,Set<String>>();
//       Set<String> roleCodeSet = new HashSet<String>();
//       roleCodeSet.add("admin");//admin角色
//       rules.put("/sysPrvRole/prepareExecute/toQueryPage/**", roleCodeSet);
       
        //初始化角色资源到缓冲
        try {
            List<SysResourceRoleDTO> resourceList = sysResourceService.getSysResourceRole("url");
          
            for(SysResourceRoleDTO sysResource : resourceList) {
             
               if(sysResource!=null&&sysResource.getResoureType()!=null&&sysResource.getResoureType().equals("url")){
                   String url = sysResource.getResoureUrl();
            	   if(url==null||url.equals("")){
            	       continue;  
            	   }
            	   //去除空格
            	   url = url.replaceAll(" ", "");
            	   if(url!=null && url.indexOf("http")!=-1){//基于http开头的不过滤
            	       continue;  
            	   }
                   if(url.indexOf("?")!=-1) {
                       url = url.substring(0,url.indexOf("?"))+"/**";
                   }
                   if(!url.startsWith("/")) {
                       url = "/"+ url;
                   }
          
                   if(!rules.containsKey(url)) {
                       rules.put(url, new HashSet<String>());
                   }
                   rules.get(url).add((sysResource.getRoleCode()));
               }
           }
           
           for(Map.Entry<String, Set<String>> entry :rules.entrySet()) {
               sb.append(entry.getKey()).append("=").append("authc,roleOrFilter").append(entry.getValue()).append(CRLF);
           }
        }
        catch (Exception e) {
            log.error("getDynaAuthRule() error: ", e);
        }
        
        return sb.toString();
    }
    
   
    private String getFixedAuthRule() {
       
       StringBuffer sb = new StringBuffer("");
       
       ClassPathResource cp = new ClassPathResource("auth-shiro.properties");
       Properties properties = new OrderedProperties();
       try{
           properties.load(cp.getInputStream());
       } catch(IOException e) {
           log.error("auth-shiro.properties error!", e);
           throw new RuntimeException("load auth-shiro.properties error!");
       }
       for(Iterator its = properties.keySet().iterator();its.hasNext();) {
           String key = (String)its.next();
           sb.append(key).append(" = ").append(properties.getProperty(key).trim()).append(CRLF);
           
       }      
       return sb.toString();
       
    }
    
    @Override
    public synchronized void reCreateFilterChains() {
       
       AbstractShiroFilter shiroFilter = null;
       try{
           shiroFilter = (AbstractShiroFilter)shiroFilterFactoryBean.getObject();
       } catch(Exception e) {
           log.error("getShiroFilter from shiroFilterFactoryBean error!", e);
           throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
       }
       
       PathMatchingFilterChainResolver filterChainResolver =(PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
       DefaultFilterChainManager manager =(DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
 
       //清空老的权限控制
       manager.getFilterChains().clear();
       
       shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
       shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
       //重新构建生成
       Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        for(Map.Entry<String, String> entry :chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition =entry.getValue().trim().replace(" ", "");
            manager.createChain(url,chainDefinition);
        }
      
    }
 
    /**
	 * 通过用户ID获取该用户的菜单权限
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<SysMenuDTO> findMenusByUserId(Long userId) throws Exception{
		List<SysMenuDTO> list = new ArrayList<SysMenuDTO>();
		Map<Long,String> urlAccessMap = findUrlByUserId(userId);
	   	Map<String, Object> searchParams = new HashMap<String, Object>();
	   	SysMenuDTO dto = new SysMenuDTO();
    	searchParams.put("dto", dto);
	   	List<SysMenuDTO> menuAllList = sysMenuService.searchSysMenuForHome(searchParams);
/*	    String superAdmin = sysConfigAPI.getValue(SUPER_ADMIN);
	   	if("yes".equalsIgnoreCase(superAdmin)){ //菜单不做权限控制
	   		return menuAllList;
	   	}
*/	   	if(urlAccessMap!=null&&urlAccessMap.size()>0){
		   	if(menuAllList!=null){
		   		String menuUrl = "";
		   		String menuCode = "";
		   		Map<String,String> aclMenuCodeMap = new HashMap<String,String>();
		   		//构造权限菜单
		   		for(SysMenuDTO sysMenu : menuAllList){
		   			 menuUrl = sysMenu.getMenuUrl();
		   			 if(urlAccessMap.containsValue(menuUrl)){
		   				Map<String,String> parentMenuCodeMap = getParentMenu(sysMenu);//getParentMenuCode(sysMenu.getMenuCode());
		   				if(parentMenuCodeMap.size()>0){
			   				aclMenuCodeMap.putAll(parentMenuCodeMap);
		   				}
		   				aclMenuCodeMap.put(String.valueOf(sysMenu.getId()), String.valueOf(sysMenu.getId()));
		   			 }
		   		}
		   		for(SysMenuDTO sysMenu : menuAllList){
		   			menuCode = sysMenu.getMenuCode();
		   			String menuId = String.valueOf(sysMenu.getId());
		   			if(aclMenuCodeMap.containsKey(menuId)){
		   				list.add(sysMenu);
		   			}
		   		}
		   		return list;
		   	}
	   	}
		return list;
	}
	/**
	 * 获取当前菜单的父菜单
	 * @param menuCode
	 * @return
	 */
	private Map<String,String> getParentMenuCode(String menuCode){
		Map<String,String> parentMenuCode = new HashMap<String,String>();
		 if(menuCode!=null&&menuCode.indexOf("-")!=-1){
			 String parentMenuArray[] = menuCode.split("-");
			 if(parentMenuArray!=null&&parentMenuArray.length==2){
				 parentMenuCode.put(parentMenuArray[0],parentMenuArray[0]);
			 }else if(parentMenuArray!=null&&parentMenuArray.length==3){
				 parentMenuCode.put(parentMenuArray[0],parentMenuArray[0]);
				 parentMenuCode.put(parentMenuArray[0]+"-"+parentMenuArray[1],parentMenuArray[0]+"-"+parentMenuArray[1]);
   			}
		 }
		 return parentMenuCode;
	}
	/**
	 * 获取当前菜单的父菜单
	 * @param sysMenu
	 * @return
	 */
	private Map<String,String> getParentMenu(SysMenuDTO sysMenu){
		Map<String,String> parentMenuCode = new HashMap<String,String>();
		 if(sysMenu!=null && !"0".equals(sysMenu.getParentId())){//并且其父节id 不是0
			 Long menuLevel = sysMenu.getMenuLevel();
			 String key = sysMenu.getParentId();
			 String value = sysMenu.getParentId();
			 //放入二级parentId
			 parentMenuCode.put(key,value);
			 if(menuLevel ==3){
				//放入一级parentId
				 parentMenuCode.put(sysMenu.getParentId1(),sysMenu.getParentId1());
			 }
		 }
		 return parentMenuCode;
	}
	/**
	 * 根据用户ID获取该用户url资源权限
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private Map<Long,String> findUrlByUserId(Long userId) throws Exception{
		Set<String> roles = findRoles(userId);
		Map<Long,String> urlAccessMap = new HashMap<Long,String>();
		if(roles!=null&&roles.size()>0){
			Iterator<String> it = roles.iterator();
			while(it.hasNext()){
				List<SysResourceRoleDTO> urlList = sysResourceService.getSysResourceUrl(it.next());
				if(urlList!=null){
					for(SysResourceRoleDTO sysResourceRoleDTO : urlList){
						urlAccessMap.put(sysResourceRoleDTO.getResoureId(),sysResourceRoleDTO.getResoureUrl());
					}
				}
			}			
		}
		//补充变更用户基本资源的
        String addBaseRole = "ADD_BASE_"+userId;
        List<SysResourceRoleDTO> addUrl = sysResourceService.getSysResourceUrl(addBaseRole);
        for(SysResourceRoleDTO sysResourceRoleDTO : addUrl){
            urlAccessMap.put(sysResourceRoleDTO.getResoureId(),sysResourceRoleDTO.getResoureUrl());
        }
        //减去多余的
        String subBaseRole = "SUB_BASE_"+userId;
        List<SysResourceRoleDTO> subUrl = sysResourceService.getSysResourceUrl(subBaseRole);
        for(SysResourceRoleDTO sysResourceRoleDTO : subUrl){
            urlAccessMap.remove(sysResourceRoleDTO.getResoureId());
            
        }
		return urlAccessMap;
	}
	
	/**
	 * 根据用户ID获取该用户所属角色
	 * @param userId
	 * @return
	 */
	public Set<String> findRoles(Long userId) {
		Set<String> roles = new HashSet<String>();
		List<SysRoleDTO> roleList = sysRoleService.getRoleByUserId(userId);
		if(roleList!=null){
			for(SysRoleDTO sysRole : roleList){
				roles.add(sysRole.getRoleCode());
			}
		}
		return roles;
	}
	
	/**
	 * 根据用户ID获取该用户所拥有的按钮权限
	 * @param userId
	 * @return
	 */
	public Set<String> findPermissions(Long userId) {
		Set<String> permissions = new HashSet<String>();
		List<SysRoleDTO> roleList = sysRoleService.getRoleByUserId(userId);
		if(roleList!=null){
			for(SysRoleDTO sysRole : roleList){
			    List<SysResourceRoleDTO> resourceList = sysResourceService.getSysResourcePermission(sysRole.getRoleCode());
			    for(SysResourceRoleDTO sysResource : resourceList) {
			    	if(sysResource.getPermission()!=null&&!sysResource.getPermission().equals("")){
				          permissions.add(sysResource.getPermission());
			    	}
			    }
			}
		}
		
//		permissions.add("platform/dataprv/sysprvrole/querySysPrvRole:add");
		return permissions;
	}
}
