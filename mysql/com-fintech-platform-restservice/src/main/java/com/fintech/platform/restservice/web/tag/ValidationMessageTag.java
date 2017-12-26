/**
 * 
 * @title: ValidationMessageTag.java
 * @package com.pt.core.web.tag
 * @author
 * @date 2014-9-20 下午6:24:36
 * @version v1.00
 * @description: 回显异常信息
 */

package com.fintech.platform.restservice.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @classname: ValidationMessageTag
 * @description: 回显异常信息
 */

public class ValidationMessageTag extends TagSupport {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */

    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * title:
     * </p>
     * <p>
     * description:
     * </p>
     */

    public ValidationMessageTag() {
        super();
    }

    /* (非 Javadoc)
    * <p>title: doStartTag</p>
    * <p>description: </p>
    * @return
    * @throws JspException
    * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
    */

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        String message = request.getAttribute(TagDict.VALIDATION_MESSAGE) == null ? "" : (String) request
                .getAttribute(TagDict.VALIDATION_MESSAGE);
        JspWriter writer = this.pageContext.getOut();
        try {
            writer.write(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

}
