package com.ln.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;

import com.rabbitmq.client.Channel;

/**
 * 消费者
 *
 */
public class Customer implements ChannelAwareMessageListener{
	protected MessageConverter converter;

	public void setConverter(MessageConverter converter) {
		this.converter = converter;
	}
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		Object fromMessage = converter.fromMessage(message);
		System.out.println("***********消费者********");
		System.out.println("***********接收到的Message:"+fromMessage.toString());
	}

}
