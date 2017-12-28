package com.jy.platform.bootbase.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

/**
 * Servlet Filter implementation class HelloFilter
 */
public class HttpAuthFilter implements Filter {
    
    private Logger logger = LoggerFactory.getLogger(HttpAuthFilter.class);

    /**
     * Default constructor.
     */
    public HttpAuthFilter() {

    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        
        if (logger.isTraceEnabled()) {
            printParams(req);
        }

        // 从header中获取客户端id和password
        String authorization = req.getHeader("authorization");
        String clientId = "";
        String clientPwd = "";
        if (authorization != null && !"".equals(authorization)) {
            byte[] bytes = Base64Utils.decodeFromString(authorization);
            String str = new String(bytes);
            if (str.indexOf(":") != -1) {
                clientId = str.split(":")[0];
                clientPwd = str.split(":")[1];
            }
        }

        // 校验通过
        if (checkAuth(clientId, clientPwd)) {
            chain.doFilter(request, response);
        }
        else {
            logger.warn("clientId或clientPwd不正确，拒绝访问:{},{},{},{}", clientId, clientPwd, req.getRequestURI(), req.getRemoteAddr());
            HttpServletResponse resp = (HttpServletResponse) response;
            PrintWriter writer = null;
            try {
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json; charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                writer = resp.getWriter();
                StringBuilder sb = new StringBuilder("clientId或clientPwd不正确，拒绝访问。");
                writer.append(sb);
                writer.flush();
            }
            catch (Exception e) {
                throw new ServletException(e);
            }
            finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }

    }

    /**
     * 检测客户端访问的合法性
     * 
     * @param clientId
     * @param clientPwd
     * @return
     */
    private boolean checkAuth(String clientId, String clientPwd) {
        boolean isAuth = false;
        if (clientId == null || clientId.equals("") || clientPwd == null || clientPwd.equals("")) {
            return false;
        }

        // TODO
        isAuth = true;
        return isAuth;
    }

    private void printParams(HttpServletRequest req) {
        System.out.println("-----------------------------------------");
        Map<String, String[]> pMap = req.getParameterMap();
        if (pMap != null && !pMap.isEmpty()) {
            Iterator<Entry<String, String[]>> it = pMap.entrySet().iterator();
            Entry<String, String[]> param;
            while (it.hasNext()) {
                param = it.next();
                if (param.getValue().length == 1) {
                    System.out.println(param.getKey() + "=" + param.getValue()[0]);
                } else {
                    for (int i = 0; i < param.getValue().length; i++) {
                        System.out.println(param.getKey() + "=" + param.getValue()[i]);
                    }
                }
            }
        }
        System.out.println("-----------------------------------------");
    }
    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {

    }

}
