package com.jy.modules.wms.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jy.platform.core.common.BizLoggerUtil;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;

/**
 * 人行PDF解析
 * 
 * @author Administrator
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/api/pdf")
public class DemoRest extends BaseRest {

    private static Logger logger = LoggerFactory.getLogger(DemoRest.class);

    @Value("${biz_app.path}")
    private String bizAppPath;

    /**
     * Pdf解析方法
     * 
     * @param requestBody
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pdfAnalytical/v1", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseMsg<String>> parserPDFByBigFaceless() {
        logger.info("=======start==========");
        
        ResponseMsg<String> responseMsg = new ResponseMsg<String>();
        Map<String,String> tem =  new HashMap<String,String>();
        String str = "";
        try {
            tem.put("name1", "cgtest");
            tem.put("name2", "demo");
            tem.put("name3", "demo1");
            str = JSONObject.toJSONString(tem);
        } catch (Exception e) {
            logger.error("请求人行PDF解析微服务接口异常,pdf附件路径path:{}", e);
        } finally {
            logger.info("接口结束：{}", JSONObject.toJSONString(str));
        }
        //输出业务日志以便后续收集
        BizLoggerUtil.showBizLogInfo(tem, this.getClass());
        
        responseMsg.setResponseBody(str);
        ResponseEntity<ResponseMsg<String>> responseEntity = new ResponseEntity<ResponseMsg<String>>(
                responseMsg, HttpStatus.OK);
        return responseEntity;
    }


}
