package com.monkeyzi.rocketmq.mq.demo.simple;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 同步发送消息
 */
public class MqSyncProducer {

    public static void main(String[] args) throws MQClientException, RemotingException,
            InterruptedException, MQBrokerException, UnsupportedEncodingException {
        DefaultMQProducer producer=new DefaultMQProducer("sync_producer_group_name");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        //发送失败的重试次数
        producer.setRetryTimesWhenSendFailed(3);
        producer.start();
        for (int i=0;i<5;i++){
            Message message=new Message("topic_1","tag1",("hello_world"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult result=producer.send(message);
            /**    SendResult的发送结果有四种状态
             *     SEND_OK,  成功
             *     FLUSH_DISK_TIMEOUT, 消息发送成功，但是服务器刷盘超时，消息已经进入服务器队列，
             *                         只有此时服务器宕机，消息才会丢失；
             *     FLUSH_SLAVE_TIMEOUT,消息发送成功，但是服务器同步到slave时超时，消息已经进入服务器队列，
             *                         只有此次服务器宕机，消息才会丢失；
             *     SLAVE_NOT_AVAILABLE;消息发送成功，但是此时slave不可用，消息已经进入服务器队列，
             *                         只有此时服务器宕机，消息才会丢失；
             */
            System.out.println("消息发送结果："+result.getSendStatus());
        }
        producer.shutdown();
    }
}
