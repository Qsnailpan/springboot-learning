
package com.snail;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 
 * @author sky
 *
 */
@Component
public class Consumer {

	@JmsListener(destination = "${queue}")
	public void queueReceive(String msg) {
		System.out.println("收到msg:" + msg);
	}
/*
	@JmsListener(destination = "${topic}")
	public void topicReceive(String msg) {
		System.out.println("收到订阅msg:" + msg);
	}
*/
}
