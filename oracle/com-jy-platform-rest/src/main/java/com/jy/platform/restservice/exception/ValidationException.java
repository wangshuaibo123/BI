/*
 * Copyright (C) 2006-2014
 * 版权所有者为北京捷越联合信息咨询有限公司。本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的。
 * @title: ValidateException.java
 * @package com.jy.core.exception
 * @author zph
 * @date 2014-9-17 下午2:17:53
 * @version v1.00
 * @description: 数据校验异常类
 */

package com.jy.platform.restservice.exception;

/**
 * @classname: ValidateException
 * @description: 数据校验异常类
 */

public class ValidationException extends AbaboonException {

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
    public ValidationException() {
        super();
    }

    /**
     * 
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
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * <p>
     * title:
     * </p>
     * <p>
     * description:
     * </p>
     * 
     * @param message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * 
     * <p>
     * title:
     * </p>
     * <p>
     * description:
     * </p>
     * 
     * @param cause
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
