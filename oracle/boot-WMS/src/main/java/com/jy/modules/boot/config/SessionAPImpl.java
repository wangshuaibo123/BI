package com.jy.modules.boot.config;

import javax.servlet.http.HttpServletRequest;

import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;


public class SessionAPImpl implements SessionAPI {
    
    //@Autowired
    HttpServletRequest request;

    @Override
    public UserInfo getCurrentUserInfo() {
        // TODO Auto-generated method stub
        System.out.println("####获取当前用户" + this + ", " + request);
        return null;
    }

}
