package com.fintech.platform.jbpm4.tool;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import bsh.EvalError;
import bsh.Interpreter;
/**
 * 
 * @description:定义工作流执行的工具类
 * @author
 * @date:2014年10月29日下午8:13:37
 */
public class JbpmTools {
	public static final JbpmTools  tools = new JbpmTools();
	private JbpmTools(){
		
	}
	public static JbpmTools getInstance(){
		return tools;
	}
	
	/**
     * 将 字符串 代码 转换成 可执行的代码
     * @param otherParam
     * @return
     * @throws EvalError
     */
    @SuppressWarnings("all")
    public Map<String,Object> makeupDynamicMap(String otherParam) throws EvalError{
		Map<String,Object> variables = new HashMap<String,Object>(); 
		if(StringUtils.isEmpty(otherParam)) return variables;
		//通过 页面传输代码 来控制流程
		if(StringUtils.isNotEmpty(otherParam)){
			Interpreter it=new Interpreter();
			Object obj = null;
			//将传递过来的双引号去掉
			obj = it.eval(otherParam);
			variables.putAll((Map)obj);
		}
		//定义 子流程之间的跨流程处理 outcome="#{result}" 主流程中子流程连接为to 
		//if(variables.get("result") == null)
		//variables.put("result", "to");
		return variables;
	}

}
