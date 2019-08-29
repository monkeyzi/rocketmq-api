package com.monkeyzi.rocketmq.mq.demo.scheduled;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
/**
 * @author 高艳国
 * @date 2019/8/16 15:47
 * @description  延时消息 messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
 **/
public class ScheduledMqProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer=new DefaultMQProducer("scheduled_producer_group__name");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        producer.start();
        int totalMessagesToSend = 2;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("topic_sched", "tag_sched",("Hello scheduled message " + i).getBytes());
            // 消息60s后才会被消费
            message.setDelayTimeLevel(5);
            SendResult sendResult = producer.send(message);
            System.out.println("消息发送："+sendResult.getSendStatus());
        }

        producer.shutdown();
    }
}
