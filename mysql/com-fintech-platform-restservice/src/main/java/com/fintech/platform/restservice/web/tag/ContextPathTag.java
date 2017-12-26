package com.fintech.platform.restservice.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @classname: ContextPathTag
 * @description: TODO(上下文路径标签<app:contextPath>)
 */
public class ContextPathTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    /*
     * (非 Javadoc) <p>title: doStartTag</p> <p>description: </p>
     * 
     * @return
     * 
     * @throws JspException
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        JspWriter writer = this.pageContext.getOut();
        try {
            String contextPath = request.getContextPath();
            if ("/".equals(contextPath)) {
                contextPath = "";
            }
            writer.print(contextPath);
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        }
        return 1;
    }

    public int doEndTag() throws JspException {
        return 6;
    }
}
