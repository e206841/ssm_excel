package com.ln.mq;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.ln.web.controller.TestBase;

/**
 * 生产者
 *
 */
public class Producer extends TestBase{
	
	private static final String MY_FITST_QUEUE="my_first_queue";
	
	@Resource
	private AmqpTemplate amqpTemplate;
	
	@Test
	public void sendMessage(){
		System.out.println("*******生产者********");
		String message="hello my first queue";
		amqpTemplate.convertAndSend(MY_FITST_QUEUE, message);
	}
	
}
