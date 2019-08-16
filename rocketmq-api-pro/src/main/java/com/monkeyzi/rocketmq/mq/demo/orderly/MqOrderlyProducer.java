package com.monkeyzi.rocketmq.mq.demo.orderly;

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
 * @date 2019/8/16 11:26
 * @description  顺序消息生产者
 **/
public class MqOrderlyProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer=new DefaultMQProducer("orderly_producer_group_name");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        producer.start();
        String[] tags = new String[] { "TagA", "TagB", "TagC" };
        List<OrderDO> orderDOList=new MqOrderlyProducer().builderMsgList();
        for (int i=0;i<10;i++){
             String body="消息"+orderDOList.get(i);
             Message message=new Message("topic_order",tags[i % tags.length],orderDOList.get(i).getOrderId().toString(),body.getBytes());
             SendResult sendResult=producer.send(message, new MessageQueueSelector() {
                 @Override
                 public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                     long id= (long) o;
                     long index = id % list.size();
                     return list.get((int) index);
                 }
             },orderDOList.get(i).getOrderId());
             System.out.println(sendResult.getSendStatus() + ", body:" + body);
        }
        //关闭
        producer.shutdown();
    }


    private List<OrderDO> builderMsgList(){
        List<OrderDO> orderDOList=new ArrayList<>();
        OrderDO orderDO=new OrderDO();
        orderDO.setOrderId(1111L);
        orderDO.setOrderDesc("创建");
        orderDOList.add(orderDO);

        orderDO=new OrderDO();
        orderDO.setOrderId(2222L);
        orderDO.setOrderDesc("创建");
        orderDOList.add(orderDO);

        orderDO=new OrderDO();
        orderDO.setOrderId(1111L);
        orderDO.setOrderDesc("付款");
        orderDOList.add(orderDO);

        orderDO=new OrderDO();
        orderDO.setOrderId(3333L);
        orderDO.setOrderDesc("创建");
        orderDOList.add(orderDO);

        orderDO=new OrderDO();
        orderDO.setOrderId(2222L);
        orderDO.setOrderDesc("付款");
        orderDOList.add(orderDO);

        orderDO=new OrderDO();
        orderDO.setOrderId(2222L);
        orderDO.setOrderDesc("完成");
        orderDOList.add(orderDO);

        orderDO=new OrderDO();
        orderDO.setOrderId(3333L);
        orderDO.setOrderDesc("付款");
        orderDOList.add(orderDO);

        orderDO=new OrderDO();
        orderDO.setOrderId(1111L);
        orderDO.setOrderDesc("推送");
        orderDOList.add(orderDO);

        orderDO=new OrderDO();
        orderDO.setOrderId(1111L);
        orderDO.setOrderDesc("完成");
        orderDOList.add(orderDO);

        orderDO=new OrderDO();
        orderDO.setOrderId(3333L);
        orderDO.setOrderDesc("完成");
        orderDOList.add(orderDO);
        return orderDOList;
    }
}
