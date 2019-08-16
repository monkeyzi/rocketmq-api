package com.monkeyzi.rocketmq.mq.demo.simple;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class MqConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("tran_consumer_group_name");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.subscribe("topic_tran","*");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                MessageExt msg=list.get(0);
                System.out.println("消费消息："+new String(msg.getBody()));
                return ConsumeOrderlyStatus.SUCCESS;
            }


        });
        consumer.start();
        System.out.println("消费者启动成功！");
    }
}
