
package com.jy.modules.platform.flume.source;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.MQPullConsumer;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.google.common.base.Preconditions;
import org.apache.flume.Context;

/**
 * @description:Source工具类
 * @author chengang
 * @date: 2016年6月8日 上午11:18:56
 */
public class RocketMQSourceUtil {

    /**
     * Topic配置项，如：a1.sources.r1.topic=TestTopic
     */
    public static final String TOPIC_CONFIG = "topic";
    /**
     * Tags配置项，如：a1.sources.r1.tags=Tag1,Tag2
     */
    public static final String TAGS_CONFIG = "tags";
    public static final String TAGS_DEFAULT = "*";
    /**
     * Tags header name configuration, eg: a1.sources.r1.tagsHeaderName=name
     */
    public static final String TAGS_HEADER_NAME_CONFIG = "tagsHeaderName";
    public static final String TAGS_HEADER_NAME_DEFAULT = "tags";
    /**
     * Topic header name configuration, eg: a1.sources.r1.topicHeaderName=name
     */
    public static final String TOPIC_HEADER_NAME_CONFIG = "topicHeaderName";
    public static final String TOPIC_HEADER_NAME_DEFAULT = "topic";
    /**
     * 一次最多拉取条数配置项，如：a1.sources.r1.maxNums=150
     */
    public static final String MAXNUMS_CONFIG = "maxNums";
    public static final int MAXNUMS_DEFAULT = 32;
    /**
     * Consumer分组配置项，如：a1.sources.r1.consumerGroup=please_rename_unique_group_name
     */
    public static final String CONSUMER_GROUP_CONFIG = "consumerGroup";
    public static final String CONSUMER_GROUP_DEFAULT = "DEFAULT_CONSUMER";
    /**
     * Namesrv地址配置项，如：a1.sinks.s1.namesrvAddr=localhost:9876
     */
    public static final String NAMESRV_ADDR_CONFIG = "namesrvAddr";
    /**
     * 订阅方式配置项，如：a1.sources.r1.messageModel=BROADCASTING
     */
    public static final String MESSAGEMODEL_CONFIG = "messageModel";
    public static final String MESSAGEMODEL_DEFAULT = "BROADCASTING";

    public static MQPullConsumer getConsumer(Context context) {
        final String consumerGroup = context.getString(CONSUMER_GROUP_CONFIG, CONSUMER_GROUP_DEFAULT);
        final String namesrvAddr = Preconditions.checkNotNull(context.getString(NAMESRV_ADDR_CONFIG), "RocketMQ namesrvAddr must be specified. For example: a1.sources.r1.namesrvAddr=127.0.0.1:9876");

        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setMessageModel(MessageModel.valueOf(context.getString(MESSAGEMODEL_CONFIG, MESSAGEMODEL_DEFAULT)));
        return consumer;
    }

}
