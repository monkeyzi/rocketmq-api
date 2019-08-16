package com.monkeyzi.rocketmq.mq.demo.transaction;

import com.monkeyzi.rocketmq.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * @author monkeyzi
 * @date 2019/8/16 14:40
 * @description  事务消息发送者
 **/
public class MyTransactionMqProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        TransactionListenerImpl transactionListener=new TransactionListenerImpl();
        TransactionMQProducer producer=new TransactionMQProducer("transaction_producer_group_name");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        ExecutorService executorService=new ThreadPoolExecutor(2,
                5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread=new Thread(r);
                        thread.setName("_mq_transaction_thread");
                        return thread;
                    }
                });
        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        producer.start();
        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i=0;i<10;i++){
            try {
                Message msg =
                        new Message("topic_tran", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, "hello"+i);
                System.out.printf("%s%n", sendResult);
                Thread.sleep(1000);
            }catch (MQClientException | UnsupportedEncodingException e ){
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
}
