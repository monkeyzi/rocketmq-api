package com.monkeyzi.rocketmq.mq.demo.retry;

import com.monkeyzi.rocketmq.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消息重试 测试
 */
@Slf4j
public class MqRetryConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("retry_mq_consumer_group");
        //设置nameSrvAddr
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        //订阅主题
        consumer.subscribe("topic_info","*");
        //设置从队列的尾部开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt msg=msgs.get(0);
                try {
                    //获取消费次数
                    int  consumeTimes=msg.getReconsumeTimes();
                    //消费者获取消息
                    String body = new String(msg.getBody(), "utf-8");
                    System.out.println("消息="+body+"-------的消费次数为:"+consumeTimes);
                    //消费次数大于3次就不再消费，向mq返回消费成功，此时需要将消息记录起来，方便人工兜底
                    if (consumeTimes>=2){
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    //在这里抛出异常，模拟异常重试
                    throw new RuntimeException("消费者消费异常");
                    //return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }catch (Exception e){
                    e.printStackTrace();
                    // 异常的时候，稍后重试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

            }
        });
        consumer.start();
        System.out.println("消费者启动成功！");
    }
}
