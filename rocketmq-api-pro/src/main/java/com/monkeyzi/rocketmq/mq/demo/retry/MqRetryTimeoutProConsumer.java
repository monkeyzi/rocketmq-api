package com.monkeyzi.rocketmq.mq.demo.retry;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MqRetryTimeoutProConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("retry_timeout_consumer_group");
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.subscribe("topic_info","*");
        //设置消费消息超时时间为1分钟
        consumer.setConsumeTimeout(1);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt=msgs.get(0);
                String body=new String(messageExt.getBody());
                System.out.println("收到的消息为："+body+"消费次数为:"+messageExt.getReconsumeTimes());
                try {
                    //模拟耗时操作2分钟，大于设置的消费超时时间
                    TimeUnit.MINUTES.sleep(2);
                    System.out.println("休眠一分钟 msg="+body);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("消息者启动成功！");
    }
}
