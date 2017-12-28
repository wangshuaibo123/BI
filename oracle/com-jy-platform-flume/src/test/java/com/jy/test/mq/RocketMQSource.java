package com.jy.test.mq;

import com.alibaba.rocketmq.client.consumer.MQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.consumer.PullStatus;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 *
配置项	必填	默认值	说明
namesrvAddr	必填	null	Name Server地址，遵循RocketMQ配置方式
consumerGroup	可选	DEFAULT_CONSUMER	Consumer分组
topic	必填	null	Topic名称
tags	可选	*	Tag名称，遵循RocketMQ配置方式
messageModel	可选	BROADCASTING	BROADCASTING或CLUSTERING
maxNums	可选	32	一次读取消息数量

agent1.sources.source1.type=com.jy.modules.platform.flume.source.RocketMQSource
agent1.sources.source1.namesrvAddr=rocketmq_namesrv:9876
agent1.sources.source1.consumerGroup=MyConsumerGroup_1
agent1.sources.source1.topic=TopicTest
agent1.sources.source1.tags=*
agent1.sources.source1.messageModel=BROADCASTING
agent1.sources.source1.maxNums=32
agent1.sources.source1.channels=channel1
 * @description:接收消息
 * @author chengang
 * @date: 2016年6月8日 上午11:19:08
 */
public class RocketMQSource extends AbstractSource implements Configurable, PollableSource {

    private static final Logger LOG = LoggerFactory.getLogger(RocketMQSource.class);

    private String topic;
    private String tags;
    private String topicHeaderName;
    private String tagsHeaderName;
    private int maxNums;
    private MQPullConsumer consumer;

    @Override
    public void configure(Context context) {
       
        consumer.registerMessageQueueListener(topic, null);
    }

    @Override
    public Status process() throws EventDeliveryException {
        List<Event> eventList = Lists.newArrayList();
        Map<MessageQueue, Long> offsetMap = Maps.newHashMap();
        Event event;
        Map<String, String> headers;
        
        try {
            Set<MessageQueue> mqs = Preconditions.checkNotNull(consumer.fetchSubscribeMessageQueues(topic));
            for (MessageQueue mq : mqs) {
                // 获取offset
                long offset = this.getMessageQueueOffset(mq);
                PullResult pullResult = consumer.pull(mq, tags, offset, maxNums);
                // 发现新消息写入Event
                if (pullResult.getPullStatus() == PullStatus.FOUND) {
                    for (MessageExt messageExt : pullResult.getMsgFoundList()) {
                        event = new SimpleEvent();
                        headers = new HashMap<String, String>();
                        headers.put(topicHeaderName, messageExt.getTopic());
                        headers.put(tagsHeaderName, messageExt.getTags());
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("MessageQueue={}, Topic={}, Tags={}, Message: {}",new Object[] {
                                    mq, messageExt.getTopic(), messageExt.getTags(), messageExt.getBody()});
                        }
                        event.setBody(messageExt.getBody());
                        event.setHeaders(headers);
                        eventList.add(event);
                    }
                    offsetMap.put(mq, pullResult.getNextBeginOffset());
                }
            }
            // 批量处理事件
            getChannelProcessor().processEventBatch(eventList);
            for (Map.Entry<MessageQueue, Long> entry : offsetMap.entrySet()) {
                // 更新offset
                this.putMessageQueueOffset(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            LOG.error("RocketMQSource consume message exception", e);
            return Status.BACKOFF;
        }
        return Status.READY;
    }

    @Override
    public synchronized void start() {
        try {
            // 启动Consumer
            consumer.start();
        } catch (MQClientException e) {
            LOG.error("RocketMQSource start consumer failed", e);
            Throwables.propagate(e);
        }
        super.start();
    }

    @Override
    public synchronized void stop() {
        // 停止Consumer
        consumer.shutdown();
        super.stop();
    }

    private void putMessageQueueOffset(MessageQueue mq, long offset) throws MQClientException {
        // 存储Offset，客户端每隔5s会定时刷新到Broker或者写入本地缓存文件
        consumer.updateConsumeOffset(mq, offset);
    }

    private long getMessageQueueOffset(MessageQueue mq) throws MQClientException {
        // 从Broker获取Offset
        long offset = consumer.fetchConsumeOffset(mq, false);

        if (offset < 0L) {
            offset = 0L;
        }
        return offset;
    }

}
