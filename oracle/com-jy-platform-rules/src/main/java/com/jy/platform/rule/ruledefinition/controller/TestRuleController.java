package com.jy.platform.rule.ruledefinition.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.platform.core.message.DataMsg;
import com.jy.platform.restservice.web.base.BaseController;
import com.jy.platform.rule.InputParam;
import com.jy.platform.rule.RulesEngine;

/**
 * @classname: RuleDefinitionController
 * @description: 定义  RULE_DEFINITION 控制层
 * @author:  zhangyu
 */
@Controller
@RequestMapping("/testRule")
public class TestRuleController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(TestRuleController.class);

    @Autowired
    @Qualifier("rulesEngine")
    private RulesEngine engine;

    // TODO ceshi
    @RequestMapping(value = "/dotest")
    @ResponseBody
    public DataMsg testRule(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {
    	//RulesEngine engine = new RulesEngine();
    	//engine.setRuleDefService(service);
   	 	String rules = (String) this.getParameter("rules");
   	 	dataMsg = super.initDataMsg(dataMsg);
   	 	
   	 	String dt = new java.sql.Timestamp(System.currentTimeMillis()).toString();
   	 	List<Object> input = new ArrayList<Object>();
   	 	input.add("aaa-" + dt);
   	 	input.add("bbb-" + dt);
   	 	input.add("ccc-" + dt);
   	 	Map<String, Object> inMap = new HashMap<String, Object>();
   	 	inMap.put("k1", "v1");
   	 	inMap.put("k2", "v2");
   	 	input.add(inMap);
   	    InputParam param = new InputParam();
   	 	param.put("k1", "v1-222");
   	 	param.put("k2", 2);
   	 	param.put("k3", new java.util.Date());
   	 	param.put("k4", 1.23);
   	 
   	 	input.add(param);
//   	 	Map<String, Object> inMap2 = new HashMap<String, Object>();
//   	 	inMap2.put("k1", "v1-222");
//   	 	inMap2.put("k2", 2);
//   	 	inMap2.put("k3", new java.util.Date());
//   	 	inMap2.put("k4", 1.23);
//   	 	input.add(inMap2);
   	 	
   	 	Map<String, Object> global = new HashMap<String, Object>();
   	 	global.put("key1", "value1-" + dt);
   	 	global.put("key2", "value2-" + dt);
   	 	global.put("msg", new Message());
   	 	
   	 	
		try {
			long t1 = System.currentTimeMillis();
			for (int i = 0; i< 1; i++) {
			    //engine.execute(rules, input, global);
				engine.execute(rules, param, global);
			}
			long t2 = System.currentTimeMillis();
			System.out.println("执行10000次耗时" + (t2 - t1));
			System.out.println("key1 = " + global.get("key1"));
			System.out.println("key2 = " + global.get("key2"));
			System.out.println("msg = " + global.get("msg"));
			dataMsg.setMsg("规则执行成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行规则异常：", e);
		}
        return dataMsg;
    }
    
    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message = "hello";

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String toString() {
        	return "Message:" + message;
        }
    }
}
