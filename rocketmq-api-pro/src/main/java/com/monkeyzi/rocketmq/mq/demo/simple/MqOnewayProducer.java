package com.monkeyzi.rocketmq.mq.demo.simple;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
/**
 * @author monkeyzi
 * @date 2019/8/16 11:22
 * @description  单向消息发送，使用场景是要求耗时比较短，对消息一致性要求不高，允许丢部分消息的场景，日志系统
 **/
public class MqOnewayProducer {

    public static void main(String[] args) throws RemotingException, MQClientException, InterruptedException {

        DefaultMQProducer producer=new DefaultMQProducer("oneway_producer_group_name");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        producer.start();
        for (int i=0;i<5;i++){
            Message message=new Message("topic_3","tag3",("java3"+i).getBytes());
            producer.sendOneway(message);
        }
        producer.shutdown();
    }
}
