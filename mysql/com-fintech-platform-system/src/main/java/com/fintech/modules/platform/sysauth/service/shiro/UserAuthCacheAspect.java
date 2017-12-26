
package com.fintech.modules.platform.sysauth.service.shiro;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.fintech.modules.common.util.ObtainPropertiesInfo;

//@Component
//@Aspect
public class UserAuthCacheAspect extends BaseRedisCacheAspect {

	/**
	 * 采用redis将cache名称修改为key前缀
	 */
    /*public UserAuthCacheAspect() {
        setCacheName("sys-authCache");
    }*/

//    private String rolesKeyPrefix = "roles-";
//    private String stringRolesKeyPrefix = "string-roles-";
//    private String stringPermissionsKeyPrefix = "string-permissions-";


    private String ResourceRoleKeyPrefix = "sys-authCache-"+ObtainPropertiesInfo.getValByKey("app.code")+"-ResourceRole-";
    private String ResourcePermissionKeyPrefix = "sys-authCache-"+ObtainPropertiesInfo.getValByKey("app.code")+"-ResourcePermission-";
    private String ResourceUrlKeyPrefix = "sys-authCache-"+ObtainPropertiesInfo.getValByKey("app.code")+"-ResourceUrl-";

    ////////////////////////////////////////////////////////////////////////////////


    @Pointcut(value = "target(com.fintech.modules.platform.sysauth.service.SysResourceService)")
    private void sysResourceServicePointcut() {
    }

    @Pointcut(value = "execution(* insert**(..)) || execution(* update**(..)) || execution(* delete**(..))")
    private void sysResourceCacheEvictAllPointcut() {
    }

    @Pointcut(value = "execution(* getSysResourceRole(*)) && args(arg)", argNames = "arg")
    private void sysResourceRoleCacheablePointcut(String arg) {
    }
    
    @Pointcut(value = "execution(* getSysResourcePermission(*)) && args(arg)", argNames = "arg")
    private void sysResourcePermissionCacheablePointcut(String arg) {
    }

    @Pointcut(value = "execution(* getSysResourceUrl(*)) && args(arg)", argNames = "arg")
    private void sysResourceUrlCacheablePointcut(String arg) {
    }
    
    @Pointcut(value="execution(* getSysResourceAllUrl(*)) && args(arg)", argNames = "arg")
    private void sysResourceAllUrlCacheablePointcut(String arg){
    }
    
    //////////////////////////////////////////////////////////////////////////
    
    @Pointcut(value = "target(com.fintech.modules.platform.sysauth.service.SysAclService)")
    private void sysAclServicePointcut() {
    }
    
    @Pointcut(value = "execution(* insert**(..)) || execution(* update**(..)) || execution(* delete**(..)) || execution(* save**(..))")
    private void sysAclCacheEvictAllPointcut() {
    }
    
    /////////////////////////////////////////////////////////////////////////////
    
    @Pointcut(value = "target(com.fintech.modules.platform.sysauth.service.SysRoleService)")
    private void sysRoleServicePointcut() {
    }
    
    @Pointcut(value = "execution(* insert**(..)) || execution(* update**(..)) || execution(* delete**(..))")
    private void sysRoleCacheEvictAllPointcut() {
    }
    
    /////////////////////////////////////////////////////////////////////////////
    
    @Pointcut(value = "target(com.fintech.modules.platform.sysauth.service.SysRoleUserService)")
    private void sysRoleUserServicePointcut() {
    }
    
    @Pointcut(value = "execution(* insert**(..)) || execution(* update**(..)) || execution(* delete**(..))")
    private void sysRoleUserCacheEvictAllPointcut() {
    }
    
    /////////////////////////////////////////////////////////////////////////
    
    

    @Before(
            "(sysResourceServicePointcut() && sysResourceCacheEvictAllPointcut()) " +
                    "|| (sysAclServicePointcut() && sysAclCacheEvictAllPointcut()) " +
                    "|| (sysRoleServicePointcut() && sysRoleCacheEvictAllPointcut()) " +
                    "|| (sysRoleUserServicePointcut() && sysRoleUserCacheEvictAllPointcut())"
    )
    public void sysResourceCacheClearAllAdvice() throws Throwable {
        clear("sys-authCache-"+ObtainPropertiesInfo.getValByKey("app.code"));
    }
    
    @Around(value = "sysResourceServicePointcut() && sysResourceUrlCacheablePointcut(arg)", argNames = "pjp,arg")
    public Object findSysResourceUrlCacheableAdvice(ProceedingJoinPoint pjp,String arg) throws Throwable {

        String key = this.ResourceUrlKeyPrefix+arg;
        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findSysResourceUrlCacheableAdvice, hit key:{}", key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findSysResourceUrlCacheableAdvice, miss key:{}", key);

        retVal = pjp.proceed();

        put(key, retVal);

        return retVal;
    }

    
    @Around(value = "sysResourceServicePointcut() && sysResourceRoleCacheablePointcut(arg)", argNames = "pjp,arg")
    public Object findSysResourceRoleCacheableAdvice(ProceedingJoinPoint pjp,String arg) throws Throwable {

        String key = this.ResourceRoleKeyPrefix+arg;
        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findSysResourceRoleCacheableAdvice, hit key:{}", key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findSysResourceRoleCacheableAdvice, miss key:{}", key);

        retVal = pjp.proceed();

        put(key, retVal);

        return retVal;
    }

    @Around(value = "sysResourceServicePointcut() && sysResourcePermissionCacheablePointcut(arg)", argNames = "pjp,arg")
    public Object findSysResourcePermissionCacheableAdvice(ProceedingJoinPoint pjp,String arg) throws Throwable {

        String key = this.ResourcePermissionKeyPrefix+arg;
        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findSysResourcePermissionCacheableAdvice, hit key:{}", key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findSysResourcePermissionCacheableAdvice, miss key:{}", key);

        retVal = pjp.proceed();

        put(key, retVal);

        return retVal;
    }
   
    @Around(value = "sysResourceServicePointcut() && sysResourceAllUrlCacheablePointcut(arg)", argNames = "pjp,arg")
    public Object findSysResourceAllUrlCacheableAdvice(ProceedingJoinPoint pjp,String arg) throws Throwable {

        String key = this.ResourceUrlKeyPrefix+arg;
        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findSysResourceUrlCacheableAdvice, hit key:{}", key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findSysResourceUrlCacheableAdvice, miss key:{}", key);

        retVal = pjp.proceed();

        put(key, retVal);

        return retVal;
    }

}
