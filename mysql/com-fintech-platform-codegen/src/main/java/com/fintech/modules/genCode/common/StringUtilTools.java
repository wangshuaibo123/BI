package com.fintech.modules.genCode.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
/**
 * 
 * @Description: 字符串处理工具
 * @author
 * @version 1.0, 
 * @date 2013-3-17 上午11:22:39
 */
public class StringUtilTools {
	//定义sql 格式的字符长度为id              ,
	//					  rolename        ,
	private static int sqlFormatSize = 30;
	//定义 数据库属性与java 类型转换	
	private static Map<String,String> dataTypeMap = new HashMap<String,String>();
	
	static{
		
		dataTypeMap.put("char", "java.lang.String");
		dataTypeMap.put("varchar", "java.lang.String");
		dataTypeMap.put("varchar2", "java.lang.String");
		//dataTypeMap.put("int", "java.lang.Integer");//调整 为Long
		//dataTypeMap.put("float", "java.lang.Long");//调整为Long
		dataTypeMap.put("int", "java.lang.Integer");
		dataTypeMap.put("float", "java.lang.float");
		dataTypeMap.put("double", "java.lang.Double");
		dataTypeMap.put("number", "java.lang.Long");
		dataTypeMap.put("decimal", "java.lang.Long");
		dataTypeMap.put("date", "java.util.Date");
		dataTypeMap.put("time", "java.sql.Timestamp");
//		dataTypeMap.put("TIMESTAMP(6)", "java.sql.Timestamp");
//		dataTypeMap.put("timestamp(6)", "java.sql.Timestamp");
		dataTypeMap.put("clob", "java.sql.Clob");
		
		//mysql新增
		dataTypeMap.put("bigint", "java.lang.Long");
		//dataTypeMap.put("datetime", "java.util.Date");
		//dataTypeMap.put("TIMESTAMP", "java.util.Date");
		//dataTypeMap.put("timestamp", "java.util.Date");
		dataTypeMap.put("datetime", "java.sql.Timestamp");
		dataTypeMap.put("TIMESTAMP", "java.sql.Timestamp");
		dataTypeMap.put("timestamp", "java.sql.Timestamp");
		dataTypeMap.put("longtext", "java.lang.String");
	}
	
	/**
	 * 将表名或字段的名称进行转换<br>
	 * 例如 表名 wORK_Flow 转化为 WorkFlow 字段名 WORK_Flow_NaME 转化为 workFlowName
	 *
	 * @param aName
	 * @param isColumn 是不是列(列首字母小写,实体首字母大写)
	 * @return
	 */
	public static String convertName(String aName, boolean isColumn) {
		String name = WordUtils.capitalizeFully(aName, new char[] {'_'});
		//属性的首字母小写,实体首字母大写
		if(isColumn)
			name = StringUtils.uncapitalize(name);//首字母变小写
		return StringUtils.remove(name, '_');
	}
	/**
	 * 格式化 数据库属性为java 类型
	 * @param dataType
	 * @return
	 */
	public static String formatDataType(String dataType){
		String  type = "";
		if(StringUtils.isNotEmpty(dataType)) dataType = dataType.toLowerCase();
		type = dataTypeMap.get(dataType);
		if(!StringUtils.isNotEmpty(type) ){
			type = "java.lang.Object";
		}
		return type;
	}

    
	/**
	* @title: formatDBDataType
	* @author
	* @description:
	* @date 2014-9-12 下午12:02:44
	* @param dataType
	* @return
	* @throws 
	*/ 
    public static String formatDBDataType(String dataType){
    	dataType=dataType.toUpperCase();

        if("VARCHAR2".equals(dataType)||"CLOB".equals(dataType)||"BLOB".equals(dataType)){
            dataType = "VARCHAR";
        }else if("NUMBER".equals(dataType) || "INT".equals(dataType) || "BIGINT".equals(dataType)){
            dataType = "DECIMAL";
        }else if(dataType.contains("TIMESTAMP")){
            dataType = "TIMESTAMP";
        }else if(dataType.contains("DATETIME")){
            dataType = "DATE";
        }
        return dataType;
    }
	
	/**
	 * rolename        ,
	 * @param column
	 * @return
	 */
	public static String formatSQLColumns(String column){
		StringBuffer sb = new StringBuffer(column);
		while(sb.length()-1 < sqlFormatSize){
			sb.append(" ");
		}
		sb.append(",");
		
		return sb.toString();
	}
	
	
	/** 
     * 判断str1中包含str2的个数 
      * @param sourceStr
     * @param myStr
     * @return counter 
     */  
    public static int countStr(String sourceStr, String myStr) {  
    	if(!StringUtils.isNotEmpty(sourceStr) || !StringUtils.isNotEmpty(myStr)){
    		return 0 ;
    	}

        int count = 0;
        //不区分大小写
        int myIndex = 0;
        while((myIndex = sourceStr.indexOf(myStr) )!=-1 ){
        	count++;
        	sourceStr = sourceStr.substring(myIndex + myStr.length());
        	
        }
        return count ;
    }
    
    
	/**
	 * 验证特殊字符是否存在
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	private   static   boolean StringFilter(String   str)   throws   PatternSyntaxException   {      
		 //过滤掉.. WEB_INF目录 *.xml < > \t
		 String regEx="\\.\\.|WEB-INF|\\w.xml|<|>|\t";
		 Pattern   p   =   Pattern.compile(regEx);      
		 Matcher   m   =   p.matcher(str);
		 
		 return   m.find();  
		 
		 
    } 
	/**
	 * 将特殊字符转化成相关指定字符
	 * @param str
	 * @return
	 */
	private static String replaceAllSpecialStr(String str){
		if(!StringUtils.isNotEmpty(str)) return "";
		String value = "";
		value = str.replaceAll("\\.\\.", "。");// 将两个紧邻的..转义成。
		value = value.replaceAll("\\\\u2e\\\\u2e", "。");
		
		value = value.replaceAll("WEB-INF", "戆");//gang
		value = value.replaceAll("\\\\u2d", "戆");
		
		value = value.replaceAll("<", "←");
		value = value.replaceAll("\\\\u3c", "←");
		
		value = value.replaceAll(">", "→");
		value = value.replaceAll("\\\\u3e", "→");
		
		value = value.replaceAll("\\\\", "燮");//xie 替换反斜杠
		value = value.replaceAll("\\\\u5c", "燮");//xie
		
		
		return value;
	}
	
	/**
	 * 将自定义的字符换原成特殊字符
	 * @param str
	 * @return
	 */
	public static String repMyStrToSpecial(String str){
		if(!StringUtils.isNotEmpty(str)) return "";
		String value = "";
		value = str.replaceAll("。", "..");// 
		
		value = value.replaceAll("戆", "WEB-INF");//gang
		
		value = value.replaceAll("←", "<");
		
		value = value.replaceAll("→", ">");
		
		value = value.replaceAll("燮", "\\\\");//xie 替换斜杠
		
		
		return value;
	}
	
	/**
	 * 将 文本按html 格式转换
	 * @param inputStr
	 * @return
	 */
	public static final String escapeNewHTMLTag(String inputStr) {
		if (inputStr == null || inputStr.length() == 0) {
			return "&nbsp;";
		}
		int strLen = inputStr.length();
		StringBuffer buf = new StringBuffer(strLen);
		char ch;
		for (int i = 0; i < inputStr.length(); i++) {
			ch = inputStr.charAt(i);
			switch (ch) {
			
			case ' ':
			{
				if (i > 2 ){
				 if('<' == inputStr.charAt(i - 2) && 'a' == inputStr.charAt(i - 1)){
					 buf.append(ch);
					break; 
				 }
				}
				buf.append("&nbsp;");
				break;
			}
			
			case '\r':
				if (i != inputStr.length()) {
					if (inputStr.charAt(i + 1) == '\n') {
						buf.append("<BR>");
						i++;
					}
				}
				break;
			case '\n':
				buf.append("<BR>");
				break;
			default:
				buf.append(ch);
				break;
			}
		}
		return buf.toString();
	}
	
}

