package com.monkeyzi.rocketmq.mq.demo.retry;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * mq消息重试demo
 */
public class MqRetryProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer  producer=new DefaultMQProducer("retry_mq_producer_group");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        //设置失败三次重试
        producer.setRetryTimesWhenSendFailed(3);
        producer.start();
        Message message=new Message("topic_info","tag_info","测试RMQ重试".getBytes());
        //设置发送消息3ms超时
        SendResult sendResult = producer.send(message);
        System.out.println("消息发送状态为："+sendResult.getSendStatus());
        //关闭生产者
        producer.shutdown();
    }
}
