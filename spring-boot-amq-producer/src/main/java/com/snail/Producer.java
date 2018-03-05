
package com.snail;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Producer {
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private Queue queue;
	
	@Autowired
	private Topic topic;

	@Scheduled(fixedDelay = 3000)
	public void send() {
		
		String str = "发布消息队列" + System.currentTimeMillis();
		//String str = "发布主题消息队列" + System.currentTimeMillis();
		
		System.out.println(str);
		
		jmsMessagingTemplate.convertAndSend(queue, str);
		//jmsMessagingTemplate.convertAndSend(topic, str);
	}
}
