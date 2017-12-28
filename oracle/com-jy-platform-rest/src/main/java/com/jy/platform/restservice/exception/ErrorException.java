/**
 * Copyright (C) 2006-2014
 * 版权所有者为北京捷越联合信息咨询有限公司。本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ErrorException.java
 * @package com.jy.core.exception
 * @author
 * @date 2014-9-17 下午2:25:39
 * @version v1.00
 * @description: 逻辑异常类
 */

package com.jy.platform.restservice.exception;

/**
 * @classname: ErrorException
 * @description: 业务逻辑处理异常统一抛出此异常
 */

public class ErrorException extends AbaboonException {

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

    public ErrorException() {
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

    public ErrorException(String message) {
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

    public ErrorException(Throwable cause) {
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

    public ErrorException(String message, Throwable cause) {
        super(message, cause);
    }

}
