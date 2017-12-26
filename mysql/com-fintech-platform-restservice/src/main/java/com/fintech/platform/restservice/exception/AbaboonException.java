/**
 * 
 * @title: AbaboonException.java
 * @package com.pt.core.exception
 * @author
 * @date 2014-9-17 下午2:31:16
 * @version v1.00
 * @description: 平台核心异常类
 */

package com.fintech.platform.restservice.exception;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @classname: AbaboonException
 * @description: 核心异常类
 */

public class AbaboonException extends Exception {

    private Locale local;

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

    public AbaboonException() {
        super();
    }

    /**
     * <p>
     * title:
     * </p>
     * <p>
     * description:
     * </p>
     * 
     * @param message
     */

    public AbaboonException(String message) {
        super(message);
    }

    /**
     * <p>
     * title:
     * </p>
     * <p>
     * description:
     * </p>
     * 
     * @param cause
     */

    public AbaboonException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * title:
     * </p>
     * <p>
     * description:
     * </p>
     * 
     * @param message
     * @param cause
     */

    public AbaboonException(String message, Throwable cause) {
        super(message, cause);
    }

    public final static void main(String[] sarg) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:servlet-context.xml");
        ctx.getMessage("", new String[] { "1", "2" }, new Locale("zh"));
    }
}
