package com.monkeyzi.rocketmq.mq.demo.scheduled;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public class ScheduledMqConsumer {

    public static void main(String[] args) throws MQClientException{

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("scheduled_consumer_group_name");
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        // 订阅主题
        consumer.subscribe("topic_sched", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                for (MessageExt message : messages) {

                    System.out.println("收到消息[msg=" + new String(message.getBody()) + "] 在"
                            + (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms 之后");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动consumer
        consumer.start();
        System.out.println("消费者启动成功");
    }
}
