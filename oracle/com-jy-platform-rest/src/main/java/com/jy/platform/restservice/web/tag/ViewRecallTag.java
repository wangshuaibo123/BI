/**
 * Copyright (C) 2006-2014
 * 版权所有者为北京捷越联合信息咨询有限公司。本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ViewRecall.java
 * @package com.jy.core.web.tag
 * @author zph
 * @date 2014-9-20 下午5:04:41
 * @version v1.00
 * @description: 记录当前请求URI
 */

package com.jy.platform.restservice.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

/**
 * @classname: ViewRecall
 * @description: 记录当前URI
 */

public class ViewRecallTag extends TagSupport {

    private static final UrlPathHelper urlPathHelper = new UrlPathHelper();

    private static final Log logger = LogFactory.getLog(ViewRecallTag.class);

    private final static String defaultEncoding = WebUtils.DEFAULT_CHARACTER_ENCODING;

    private static final long serialVersionUID = 1L;



    public ViewRecallTag() {
        super();
    }

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        JspWriter writer = this.pageContext.getOut();
        String requestUri = request.getAttribute(TagDict.VIEW_RECALL_URI)==null?"":(String) request.getAttribute(TagDict.VIEW_RECALL_URI);
        StringBuffer buffer = new StringBuffer();
        try {
            buffer.append(TagDict.TAG_PRE_HIDDEN).append(TagDict.VIEW_RECALL_URI).append(TagDict.TAG_VALUE)
                    .append(requestUri).append(TagDict.TAG_END);
            writer.print(buffer.toString());
            request.setAttribute(TagDict.VIEW_RECALL_URI, requestUri);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return super.doStartTag();
    }

}
