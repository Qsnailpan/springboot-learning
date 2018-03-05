package com.snail.demoactivemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @classDesc: 功能描述:(主题 - 订阅者)
 * @author: lipan
 * @createTime: 2018/3/4
 */
public class TOP_Subscriber {


    // 订阅主题
    static String TOPIC = "my-Topic";

    static String BROKER_URL = "tcp://127.0.0.1:61616";


    public static void start() throws JMSException {
        System.out.println ("订阅者启动...");

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory (BROKER_URL);

        Connection connection = activeMQConnectionFactory.createConnection ();

        // connection.setClientID ("ccc"); // 持久订阅需要
        connection.start ();

        // 不开启事务 , 自动签收
        Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic (TOPIC);

        // 普通订阅
        MessageConsumer consumer = session.createConsumer (topic);
        // 持久订阅
        //  MessageConsumer consumer = session.createDurableSubscriber (topic,"ccc");

        while (true) {

            TextMessage message = (TextMessage) consumer.receive ();
            if (message != null) {
                System.out.println ("收到订阅消息:" + message.getText ());
            } else break;
        }
    }

    public static void main(String[] args) throws JMSException {
        start ();
    }


}
