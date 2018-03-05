package com.snail.demoactivemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * p2p
 *
 * @classDesc: 功能描述:(生产者)
 * @author: lipan
 * @createTime: 2018/3/4
 */
public class P2P_Producer {

    // 队列名称
    static String queue = "my-queue";

    static String BROKER_URL = "tcp://127.0.0.1:61616";


    public static void main(String[] args) throws JMSException {
        start ();
    }

    public static void start() throws JMSException {

        System.out.println ("生产者启动...");

        //  创建 ActiveMQConnectionFactory 工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory (BROKER_URL);
        //  创建连接
        Connection connection = activeMQConnectionFactory.createConnection ();
        //  开启连接
        connection.start ();
        //  创建会话 -- 开启事务 , 消费者需要手动签收消息
        Session session = connection.createSession (true, Session.CLIENT_ACKNOWLEDGE);
        //  创建 消息队列实例
        Queue queue = session.createQueue (P2P_Producer.queue);
        //  创建生产者实例
        MessageProducer producer = session.createProducer (queue);
        //  发送消息到队列
        send (producer, session);

        System.out.println ("发送成功...");
        // 关闭连接
        connection.close ();
    }

    private static void send(MessageProducer producer, Session session) throws JMSException {
        for (int i = 0; i < 5; i++) {
            System.out.println ("我生产了消息!" + i);
            TextMessage textMessage = session.createTextMessage ("我生产了消息 " + i);
            producer.send (textMessage);
        }
        // 开启了事务 , 这里需要提交
        session.commit ();
    }
}
