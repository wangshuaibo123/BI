/**
 * 2016/11/15 by zhangyu
 * 核心组提出要给“任务查询”功能添加执行日志，
 * 因此采用自定义JobFactory的方式进行功能扩展。
 */

// 配置方式，quartz-spring.xml中
// 添加customJobFactory的bean配置
// <bean id="customJobFactory" class="com.jy.modules.quartzJob.qrtzextlog.CustomJobFactory">
//     <property name="logService" ref="com.jy.modules.quartzJob.qrtzextlog.service.QrtzExtLogService"></property>
// </bean>
// 
// startQuertz中添加jobFactory的属性配置
// <bean id="startQuertz" lazy-init="false" autowire="no" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
//     <property name="jobFactory" ref="customJobFactory" />
package com.jy.modules.quartzJob.qrtzextlog;