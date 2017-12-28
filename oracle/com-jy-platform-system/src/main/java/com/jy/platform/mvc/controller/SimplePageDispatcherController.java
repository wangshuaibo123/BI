package com.jy.platform.mvc.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;


//import org.apache.http.message.BasicNameValuePair;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 基本的页面跳转控制器。只做页面跳转，可以传递http请求参数，不做其他处理。<br>
 * 非自动扫描，如果想使用，在spring-mvc.xml中配置即可<br>
 * <bean class="com.jy.platform.web.controller.SimpleDispatcherController"></bean> 
 * 
 * @author zhangyu
 * 
 */
//@Controller
@RequestMapping("/view")
public class SimplePageDispatcherController {

    /**
     * 页面跳转(/view/platform/sysConfig/querySysConfig跳转到WEB-INF/jsp/platform/sysConfig/querySysConfig.jsp)
     * @param s1 系统模块名称(例：平台为platform)
     * @param s2 系统功能名称(例：系统配置为sysConfig)
     * @param s3 页面名称(例：查询页面为querySysConfig)
     * @param request HttpServletRequest
     * @return ModelAndView
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{s1}/{s2}/{s3}")
    public ModelAndView execute(@PathVariable("s1") String s1, @PathVariable("s2") String s2,
            @PathVariable("s3") String s3, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.setViewName("/" + s1 + "/" + s2 + "/" + s3);

        // 获取参数
        // model.addAllObjects(request.getParameterMap());
        Map<String, String[]> pMap = request.getParameterMap();
        if (pMap != null && !pMap.isEmpty()) {
            Iterator<Entry<String, String[]>> it = pMap.entrySet().iterator();
            Entry<String, String[]> param;
            while (it.hasNext()) {
                param = it.next();
                if (param.getValue().length == 1) {
                    model.addObject(param.getKey(), param.getValue()[0]);
                } else {
                    for (int i = 0; i < param.getValue().length; i++) {
                        model.addObject(param.getKey(), param.getValue());
                    }
                }
            }
        }

        return model;
    }

}
