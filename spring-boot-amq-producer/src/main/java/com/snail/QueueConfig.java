
package com.snail;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
	@Value(value = "${queue}")
	private String queue;
	
	@Value(value = "${topic}")
	private String topic;

	@Bean
	public Queue queue() {
		return new ActiveMQQueue(queue);
	}
	
	@Bean
	public Topic topic() {
		return new ActiveMQTopic(topic);
	}

}
