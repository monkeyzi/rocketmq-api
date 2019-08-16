package com.monkeyzi.rocketmq.mq.demo.simple;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @author monkeyzi
 * @date 2019/8/15 16:09
 * @description 发送异步消息 异步传输通常用于响应时间敏感的业务场景。
 *              对消息可靠性要求比较高的业务尽量还是使用同步发送消息模式
 **/
public class MqAsyncProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {

        DefaultMQProducer producer=new DefaultMQProducer("async_producer_group_name");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        //异步发送失败，重发次数
        producer.setRetryTimesWhenSendAsyncFailed(3);
        producer.start();
        for (int i=0;i<5;i++){
            Message message=new Message("topic_2","tag2",("java2"+i).getBytes());
            int finalI = i;
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("消息："+ finalI +"发送结果："+sendResult.getSendStatus());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("消息："+ finalI +"发送失败！");
                    e.printStackTrace();
                }
            });
        }
        //异步发送消息的时候，确保消息已经发送完毕，再关闭producer，要不然会发送异常
        TimeUnit.SECONDS.sleep(5);
        producer.shutdown();
    }
}
