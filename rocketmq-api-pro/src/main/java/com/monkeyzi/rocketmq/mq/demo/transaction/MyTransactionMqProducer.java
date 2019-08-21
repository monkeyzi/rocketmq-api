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
        // 需要自定义事务监听器 用于 事务的二次确认 和 事务回查
        TransactionListenerImpl transactionListener=new TransactionListenerImpl();
        // 设置事务消息生产者组
        TransactionMQProducer producer=new TransactionMQProducer("transaction_producer_group_name");
        // 设置naseSrv
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        // 事务消息需要设置事务回查线程池--官方建议自定义线程 给线程取自定义名称 发现问题更好排查
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
        // 设置本地事务监听
        producer.setTransactionListener(transactionListener);
        // 启动生产者
        producer.start();
        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i=0;i<10;i++){
            try {
                Message msg =
                        new Message("topic_tran", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 发送事务消息
                SendResult sendResult = producer.sendMessageInTransaction(msg, "hello"+i);
                System.out.printf("消息发送状态:%s%n", sendResult.getSendStatus());
            }catch (MQClientException | UnsupportedEncodingException e ){
                e.printStackTrace();
            }
        }
        //休眠  ----确保生产者能进行
        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
}
