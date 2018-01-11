package com.jy.platform.jbpm4.drawJbpmPng;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jy.platform.jbpm4.service.IModifyProInfoService;
import com.jy.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;


public class Test {
	public static void main(String[] args) {
		
		try {
			String str = "Mybatis-Configuration.xml,applicationContext-JBPM.xml,mybatis-context.xml";
			
			//str = str +",applicationContext-quartz.xml,applicationContext-security.xml,core-context.xml,redis-context.xml,servlet-context.xml,ua-context.xml";
			//初始化所有spring 配置文件
	    	String[] strsPath = str.split(",");
	        ApplicationContext context=new ClassPathXmlApplicationContext(strsPath,true);
	        IModifyProInfoService service = (IModifyProInfoService) context.getBean("com.jy.platform.jbpm4.service.impl.ModifyProInfoServiceImpl");
	        String ss = "<process name='exampleProName' key='exampleKey' xmlns='http://jbpm.org/4.4/jpdl' version='1'>  <start g='-2,137,48,48' name='start1'>    <transition g='-21,-30' to='申请人'/>  </start>  <task assignee='leaveUser' g='86,135,92,52' name='申请人'>    <transition g='-23,-29' name='提交申请' to='isManager'/>    <transition name='结束' g='-17,11' to='end 1'/>  </task>  <task assignee='bossUser' g='327,65,107,52' name='老板'>    <transition g='637,89:-46,-6' name='同意' to='end2'/>    <transition g='132,93:79,0' name='返回' to='申请人'/>  </task>  <decision expr='#{isManager==&apos;Y&apos;?&quot;yes&quot;:&quot;no&quot;}' g='223,138,48,48' name='isManager'>    <handler/>    <transition g='-17,-6' name='yes' to='老板'/>    <transition g='-9,2' name='no' to='部门经理'/>  </decision>  <end g='804,114,48,48' name='end2'/>  <task assignee='manageUser' g='329,192,115,52' name='部门经理'>    <transition g='131,216:88,5' name='返回' to='申请人'/>    <transition name='同意' g='-29,6' to='decision 1'/>  </task>  <end g='107,320,48,48' name='end 1'/>  <decision expr='#{day&lt;3?&quot;同意&quot;:&quot;老板审批&quot;}' g='533,198,48,48' name='decision 1'>    <handler/>    <transition g='656,221:-39,-20' to='end2' name='同意'>      <condition expr='#{day&lt;3}'/>    </transition>    <transition name='老板审批' g='-3,-9' to='老板'>      <condition expr='#{day&gt;=3}'/>    </transition>  </decision></process>";
	        Map<String,Object> paramMap = new HashMap<String,Object>();
	        
	        TemporaryJbpm4InfoDTO dto = new TemporaryJbpm4InfoDTO();
	        dto.setXmlContent(ss);
			dto.setProcName("exampleProName");
			dto.setProcVersion("1");
			
			dto.setProcCode("exampleKey");
			dto.setBizFile("temp");
			dto.setRemark("");
			
			
	        paramMap.put("dto", dto);
	        
	        service.updateJbpm4LobTableImmediately(paramMap);
	        System.out.println("-------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
