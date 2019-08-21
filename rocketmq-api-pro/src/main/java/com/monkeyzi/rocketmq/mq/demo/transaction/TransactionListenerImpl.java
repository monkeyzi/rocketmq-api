package com.monkeyzi.rocketmq.mq.demo.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author monkeyzi
 * @date 2019/8/21 17:21
 * @description  事务回查监听
 **/
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
        // 这里的o就是发送事务消息的时候，传入的参数
        System.out.println("执行本地事务,业务key="+o);
        int value = transactionIndex.getAndIncrement();
        System.out.println("执行本地事务transactionId："+message.getTransactionId()+"===value:"+value);
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
        Integer status=localTrans.get(messageExt.getTransactionId());
        System.out.println("执行本地事务会查transcationId:"+messageExt.getTransactionId()+"===status:"+status);
        if (null!=status){
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }else {
            return LocalTransactionState.UNKNOW;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
