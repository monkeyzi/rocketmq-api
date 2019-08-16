package com.monkeyzi.rocketmq.mq.demo.orderly;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author monkeyzi
 * @date 2019/8/16 11:24
 * @description  顺序消息消费者
 **/
public class MqOrderlyConsumer {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("orderly_consumer_group_name");
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("topic_order","TagA || TagB || TagC");
        Random random=new Random();
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                for (MessageExt msg:list){
                    System.out.println(msg+",content:"+new String(msg.getBody()));
                }
                try {
                    //模拟业务耗时
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("顺序消费者启动成功！");

    }
}
