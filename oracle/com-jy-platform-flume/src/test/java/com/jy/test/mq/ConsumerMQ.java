package com.jy.test.mq;


import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageConst;
import com.alibaba.rocketmq.common.message.MessageExt;
/**
 *
 * @description:MQ测试
 * @author chengang
 * @date: 2016年6月8日 上午11:11:55
 */
public class ConsumerMQ {

	public static void main(String[] args) throws MQClientException {
		/**
		 * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br>
		 * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br>
		 */
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
		consumer.setNamesrvAddr("172.18.100.169:9876;172.18.100.149:9876");
		consumer.setInstanceName("Consumber");
		
		consumer.subscribe("FromFlumeToMQ", "*");
		
		//程序第一次启动从消息队列头取数据  
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);  
		
		/**
		 * 顺序消息消费，带事务方式（应用可控制Offset什么时候提交）
		 */
		consumer.registerMessageListener(new MessageListenerOrderly() {
			
			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
					ConsumeOrderlyContext context) {
				//context.setAutoCommit(true);
				//System.out.println(Thread.currentThread().getName()+ " Receive New Messages: " + msgs.size());

				MessageExt msg = msgs.get(0);//msg+
				long offset = msg.getQueueOffset();
				String maxOffset = msg.getProperty(MessageConst.PROPERTY_MAX_OFFSET);
				long dif = Long.parseLong(maxOffset) - offset;
				System.out.println(dif);
				System.out.println(msg+"====="+new String(msg.getBody()));
				System.exit(0);
				if(true){
					return ConsumeOrderlyStatus.SUCCESS;
				}
				//System.exit(0);
				if(dif > 10000){
					//消息堆积不在处理
					System.out.println("==消息堆积===");
					return ConsumeOrderlyStatus.SUCCESS;
				}
				//正常处理
				System.out.println(msg+"====="+new String(msg.getBody()));
				return ConsumeOrderlyStatus.SUCCESS;
			}
		});

		/**
		 * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		 */
		consumer.start();

		System.out.println("ConsumerStarted...................");
	}

}
