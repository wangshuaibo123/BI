/**
 * monitor：监控功能
 * tmworker：脱敏工具类
 * templates：页面模板
 * main.conf：全局配置
 * 
 * 0.0.1 初始版本
 * 
 * 0.0.2 2017/04/21 17:20
 * 修改映射卡号长度超限的问题，卡号存在非数字字符，获取映射没有取到，拼接新卡号时按null处理，结果长出1位；
 * 还有一些不能为null的字段，没有取到新值，返回的是“”，JDBC可能是按null处理的，也有报错。
 * 
 * 0.0.3 2017/05/02 17:42
 * 解决线程处理异常后，错误日志被更新结束时间的方法清除的问题
 * 
 * 0.0.4 2017/05/03 09:00
 * 解决某些15位身份证号处理出现异常的问题
 * 
 * 0.0.5 2017/05/03 10:00
 * 修改身份证号处理类，解决身份证号的顺序号认为不含有00的问题
 * 
 * 0.0.6 2017/05/09 18:08
 * 新需求，支持序号不连续
 * 
 * 0.0.7 2017/05/10 15:22
 * 解决身份证号返回‘’的问题
 * 
 * 0.0.8 2017/05/23 17:51
 * 1-操作人员提出，要定时执行脱敏程序，而不是人工交互来启动
 * 2-18位的身份证号码需要计算最后一位校验码
 */
package com.jy.platform;
