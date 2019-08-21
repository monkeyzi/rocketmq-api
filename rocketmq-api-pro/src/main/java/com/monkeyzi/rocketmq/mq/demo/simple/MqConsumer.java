package com.monkeyzi.rocketmq.mq.demo.simple;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
/**
 * @author 高艳国
 * @date 2019/8/21 17:22
 * @description 消费端消费消息核心代码。消费端和普通消费一样。
 **/
public class MqConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("trans_consumer_group_name");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.subscribe("topic_tran","*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    MessageExt msg=msgs.get(0);
                    System.out.println("消费消息："+new String(msg.getBody()));
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }catch (Exception e){
                    e.printStackTrace();
                    //消费失败，设置稍后重试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

            }
        });
        consumer.start();
        System.out.println("消费者启动成功！");
    }
}
