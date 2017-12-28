package com.jy.modules.platform.flume.sink;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

/**
Sink配置说明
配置项	必填	默认值	说明
namesrvAddr	必填	null	Name Server地址，遵循RocketMQ配置方式
producerGroup	可选	DEFAULT_PRODUCER	Producer分组
topic	必填	null	Topic名称
tags	可选	空字符串	Tag名称，遵循RocketMQ配置方式

agent1.sinks.sink1.type=com.jy.modules.platform.flume.sink.RocketMQSink
agent1.sinks.sink1.namesrvAddr=172.18.100.169:9876;172.18.100.149:9876
agent1.sinks.sink1.producerGroup=MyProducerGroup_1
agent1.sinks.sink1.topic=FromFlume
agent1.sinks.sink1.tag=Tag1
agent1.sinks.sink1.channel=channel1

 * @description:发送消息
 * @author chengang
 * @date: 2016年6月8日 上午10:30:51
 */
public class RocketMQSink extends AbstractSink implements Configurable {

    private static final Logger LOG = LoggerFactory.getLogger(RocketMQSink.class);

    private String topic;
    private String tag;
    private MQProducer producer;
    private List<HashMap<String, String>> messageList = null;
    private int batchSize= 200;
    
    private String LOGGER_NAME = "flume.client.logback.logger.name";
    private String TIMESTAMP = "timestamp";
    private String LOG_LEVEL = "flume.client.logback.log.level";
    private String MESSAGE_ENCODING = "flume.client.logback.message.encoding";
    private String ERROR_MESSAGE = "flume.client.logback.message";
    private String IP = "flume.client.ip";
    private String SYSTEM_FLAG = "flume.client.system.flag";
    
    @Override
    public void configure(Context context) {
        // 获取配置项
        topic = context.getString(RocketMQSinkUtil.TOPIC_CONFIG, RocketMQSinkUtil.TOPIC_DEFAULT);
        tag = context.getString(RocketMQSinkUtil.TAG_CONFIG, RocketMQSinkUtil.TAG_DEFAULT);
        // 初始化Producer
        producer = Preconditions.checkNotNull(RocketMQSinkUtil.getProducer(context));
        
        this.messageList = new ArrayList<HashMap<String, String>>(this.batchSize);
    }

    @Override
    public Status process() throws EventDeliveryException {
        Channel channel = getChannel();
        Transaction tx = null;
        try {
        	tx = channel.getTransaction();
            tx.begin();
            Event event = null;
            
            long processedEvents = 0L;
            this.messageList.clear();
            for (; processedEvents < this.batchSize; processedEvents += 1L) {
            	event = channel.take();
            	if (event == null || event.getBody() == null || event.getBody().length == 0) {
                    break;
                }
            	
    	        Map<String, String> header = event.getHeaders();
		        if(event.getBody() != null && event.getBody().length > 0){
		        	HashMap<String, String> logMap = new HashMap<String,String>();
		        	logMap.put(LOGGER_NAME, header.get(LOGGER_NAME));
	    			logMap.put(TIMESTAMP, header.get(TIMESTAMP));
	    			logMap.put(IP, header.get(IP));
	    			logMap.put(SYSTEM_FLAG, header.get(SYSTEM_FLAG));
	    			logMap.put(ERROR_MESSAGE, new String(event.getBody(),Charset.forName("UTF8")));
	    			
	    			messageList.add(logMap);
		        }
    		}
            
            if(messageList.size() >0){
            	if (LOG.isDebugEnabled()) {
                    LOG.debug("SendResult={}, Message={}", messageList);
                }
            	// 发送消息
                producer.send(new Message(topic, tag, JSONObject.toJSONBytes(messageList)));
                
            }
            
            
            tx.commit();
            return Status.READY;
        } catch (Exception e) {
            LOG.error("RocketMQSink send message exception", e);
            try {
                if(tx!=null)tx.rollback();
                return Status.BACKOFF;
            } catch (Exception e2) {
                LOG.error("Rollback exception", e2);
            }
            return Status.BACKOFF;
        } finally {
        	if(tx!=null)tx.close();
        }
    }

    @Override
    public synchronized void start() {
        try {
            // 启动Producer
            producer.start();
        } catch (MQClientException e) {
            LOG.error("RocketMQSink start producer failed", e);
            Throwables.propagate(e);
        }
        super.start();
    }

    @Override
    public synchronized void stop() {
        // 停止Producer
        producer.shutdown();
        super.stop();
    }
}
