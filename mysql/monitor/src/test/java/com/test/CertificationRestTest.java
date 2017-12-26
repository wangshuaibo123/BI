package com.test;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.fintech.modules.boot.Application;

/**
 * xqAppServer业务系统监控类
 * Created by dell on 2017/8/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CertificationRestTest {
    private static final Logger logger = LoggerFactory.getLogger(CertificationRestTest.class);

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void certificationRestTest() throws Exception {
        long startTime = System.currentTimeMillis();
        //请求地址
        String url = "http://127.0.0.1:10008/fintech-face/api/authentication/1000/v1";

        long endTime = System.currentTimeMillis();
        logger.info("本次测试共计执行时间为: {}毫秒", endTime-startTime);
    }

    private JSONObject buildRequest() throws Exception {
        //组装业务报文
        JSONObject reqJsonObj = new JSONObject();
        reqJsonObj.put("identityNumber", "220323198204214827");
        reqJsonObj.put("personName", "刘敏");
        reqJsonObj.put("sysSource", "S001");
        return reqJsonObj;
    }

    @Test
    public void printReqMsg() throws Exception {
        buildRequest();
    }
}
