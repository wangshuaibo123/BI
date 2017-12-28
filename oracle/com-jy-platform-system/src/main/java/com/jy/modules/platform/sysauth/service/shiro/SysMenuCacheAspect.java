
package com.jy.modules.platform.sysauth.service.shiro;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.jy.modules.common.util.ObtainPropertiesInfo;

/**
 * 菜单缓存
 */
//@Component
//@Aspect
public class SysMenuCacheAspect extends BaseRedisCacheAspect {

	/**
	 * 采用redis将cache名字改成key的前缀
	 */
//    public SysMenuCacheAspect() {
//        setCacheName("sys-menuCache");
//    }

    private String menusKeyPrefix = "sys-menuCache-"+ObtainPropertiesInfo.getValByKey("app.code")+"-menus-";
   // platform/testtable1/queryTestTable1:delete

    @Pointcut(value = "target(com.jy.modules.platform.sysMenu.service.SysMenuService)")
    private void sysMenuServicePointcut() {
    }

    @Pointcut(value = "execution(* insert**(..)) || execution(* update**(..)) || execution(* delete**(..))")
    private void sysMenuCacheEvictAllPointcut() {
    }

    @Pointcut(value = "execution(* searchSysMenuForHome(*))")
    private void sysMenuCacheablePointcut() {
    }

    @Pointcut(value = "target(com.jy.modules.platform.sysauth.service.SysResourceService)")
    private void resourceServicePointcut() {
    }

    @Pointcut(value = "execution(* insert**(..)) || execution(* update**(..)) || execution(* delete**(..))")
    private void resourceCacheEvictAllPointcut() {
    }
    
    
    @Before(
            "(resourceServicePointcut() && resourceCacheEvictAllPointcut()) " +
               "|| (sysMenuServicePointcut() && sysMenuCacheEvictAllPointcut())"
    )
    public void sysMenuCacheClearAllAdvice() throws Throwable {
        clear("sys-menuCache-"+ObtainPropertiesInfo.getValByKey("app.code"));
    }

//    @Before(value = "resourceServicePointcut() && resourceCacheEvictAllPointcut()")
//    public void sysMenuCacheClearAllAdvice() throws Throwable {
//        clear();
//    }
    
    @Around(value = "sysMenuServicePointcut() && sysMenuCacheablePointcut()", argNames = "pjp")
    public Object findMenusCacheableAdvice(ProceedingJoinPoint pjp) throws Throwable {

        String key = this.menusKeyPrefix+"all";
        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findMenusCacheableAdvice, hit key:{}", key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findMenusCacheableAdvice, miss key:{}", key);

        retVal = pjp.proceed();

        put(key, retVal);

        return retVal;
    }

}
