package com.monkeyzi.rocketmq.mq.demo.orderlymsg;

import com.monkeyzi.rocketmq.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author monkeyzi
 * @date 2019/8/21 13:45
 * @description  顺序消息消息消费者
 **/
@Slf4j
public class OrderlyMqMsgConsumer {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("orderly_mq_consumer_group");
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.subscribe("orderly_topic","orderly_tag");
        consumer.setMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                MessageExt msg=msgs.get(0);
                System.out.println("收到消息:"+new String(msg.getBody()));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("顺序消息消费者启动ok");
    }
}
