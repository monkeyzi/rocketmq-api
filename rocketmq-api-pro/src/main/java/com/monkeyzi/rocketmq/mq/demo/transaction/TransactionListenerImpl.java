package com.monkeyzi.rocketmq.mq.demo.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionListenerImpl  implements TransactionListener {

    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * 处理本地事务
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("执行本地事务:"+o);
        int value = transactionIndex.getAndIncrement();
        System.out.println("value="+value);
        int status=value % 3;
        localTrans.put(message.getTransactionId(), status);
        return LocalTransactionState.UNKNOW;
    }

    /**
     * 本地事务回查
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("本地事务会查:");
        Integer status=localTrans.get(messageExt.getTransactionId());
        if (null!=status){
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
