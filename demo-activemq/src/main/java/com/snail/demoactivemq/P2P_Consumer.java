package com.snail.demoactivemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @classDesc: 功能描述:(消费者)
 * @author: lipan
 * @createTime: 2018/3/4
 */

public class P2P_Consumer {

    // 队列名称
    static String queue = "my-queue";

    static String BROKER_URL = "tcp://127.0.0.1:61616";


    public static void start() throws JMSException {
        System.out.println ("消费者启动...");

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory (BROKER_URL);

        Connection connection = activeMQConnectionFactory.createConnection ();

        connection.start ();


        Session session = connection.createSession (true, Session.CLIENT_ACKNOWLEDGE);

        Queue queue = session.createQueue (P2P_Consumer.queue);

        MessageConsumer consumer = session.createConsumer (queue);

        while (true) {
            TextMessage message = (TextMessage) consumer.receive ();
            if (message != null) {
                System.out.println ("接收到消息:" + message.getText ());
                // 手动签收
                message.acknowledge ();
                session.commit ();
            } else break;
        }
        connection.close ();
    }

    public static void main(String[] args) throws JMSException {
        start ();
    }


}
