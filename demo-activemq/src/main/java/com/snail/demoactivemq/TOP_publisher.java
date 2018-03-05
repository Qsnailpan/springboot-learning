package com.snail.demoactivemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @classDesc: 功能描述:(主题 - 发布者)
 * @author: lipan
 * @createTime: 2018/3/4
 */
public class TOP_publisher {

    // 发布主题
    static String TOPIC = "my-Topic";

    static String BROKER_URL = "tcp://127.0.0.1:61616";


    public static void main(String[] args) throws JMSException {
        start ();
    }

    public static void start() throws JMSException {

        System.out.println ("发布者启动...");

        //  创建 ActiveMQConnectionFactory 工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory (BROKER_URL);
        //  创建连接
        Connection connection = activeMQConnectionFactory.createConnection ();
        //  开启连接
        connection.start ();
        //  创建会话 -- 开启事务 , 消费者需要手动签收消息
        Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
        //  创建生产者实例
        MessageProducer producer = session.createProducer (null);

        //  发送消息到队列
        send (producer, session);

        System.out.println ("发布成功...");
        // 关闭连接
        connection.close ();
    }

    private static void send(MessageProducer producer, Session session) throws JMSException {
        //  创建 消息主题
        Topic topic = session.createTopic (TOPIC);
        for (int i = 0; i < 5; i++) {
            System.out.println ("我发布了消息!" + i);
            TextMessage textMessage = session.createTextMessage ("我发布了消息 " + i);
            producer.send (topic, textMessage);
        }
    }

}
