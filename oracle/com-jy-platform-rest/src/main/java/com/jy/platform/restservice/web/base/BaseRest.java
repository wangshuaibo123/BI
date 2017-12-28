package com.jy.platform.restservice.web.base;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jy.platform.core.message.ResponseMsg;
/**
 * Rest 服务异常处理基类
 * 将继承该类的 所有抛出的异常进行封装返回
 * @author lei
 *
 */
public class BaseRest {
	
	@ExceptionHandler(Exception.class)
	@SuppressWarnings("all")
    public ResponseEntity<ResponseMsg<T>> handleInvalidRequestError(Exception ex) {
		String errorMsg = ex.getMessage();
		if(errorMsg == null) errorMsg="null";
		errorMsg.replaceAll("[\\t\\n\\r]", "").replaceAll("[\\\\]", ".").replaceAll("\"", "");
		ResponseMsg responseMsg = new ResponseMsg<T>();
		responseMsg.setErrorDesc(errorMsg);
		responseMsg.setRetCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		ResponseEntity responseEntity=new ResponseEntity<ResponseMsg<T>>(responseMsg, HttpStatus.OK);
        return responseEntity;
    }
}
