package com.monkeyzi.rocketmq.mq.demo.orderlymsg;

import com.monkeyzi.rocketmq.constant.MqConstant;
import com.monkeyzi.rocketmq.entity.OrderDO;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author monkeyzi
 * @date 2019/8/21 11:17
 * @description  顺序消息producer
 **/
public class OrderlyMqMsgProducer {

    private static List<OrderDO> orderList;
    static {
       orderList=new ArrayList<>();
       orderList.add(new OrderDO(1111L,"订单创建"));
       orderList.add(new OrderDO(1111L,"订单付款"));
       orderList.add(new OrderDO(1111L,"订单完成"));
       orderList.add(new OrderDO(1111L,"订单通知"));

       orderList.add(new OrderDO(2222L,"订单创建"));
       orderList.add(new OrderDO(2222L,"订单付款"));
       orderList.add(new OrderDO(2222L,"订单完成"));

       orderList.add(new OrderDO(3333L,"订单创建"));
       orderList.add(new OrderDO(3333L,"订单付款"));
       orderList.add(new OrderDO(3333L,"订单完成"));

    }

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer=new DefaultMQProducer("orderly_mq_producer_group");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        producer.start();
        for (OrderDO orderDO:orderList){
            Message message=new Message("orderly_topic","orderly_tag",
                    String.valueOf(orderDO.getOrderId()),orderDO.toString().getBytes());
            SendResult sendResult=producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    //arg就是下面传入的orderId
                    long id= (long) arg;
                    //取模，保证相同orderId的放入同一个队列
                    long index = id % mqs.size();
                    return mqs.get((int) index);
                }
            },orderDO.getOrderId());
            System.out.printf("Product：发送状态=%s, 存储queue=%s ,orderid=%s, type=%s\n", sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(), orderDO.getOrderId(), orderDO.getOrderDesc());
        }

        //关闭生产者
        producer.shutdown();
    }
}
