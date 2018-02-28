package com.ln.mq;


import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;

import com.rabbitmq.client.Channel;

/**
 * 漏扫任务创建消费者
 * @author linan
 *
 */
public class StartTaskCustomer extends BaseConsumer{
	
	@Override
	protected void onRecMessage(Object data, long deliveryTag, Channel channel)
			throws Exception {
		System.out.println("***********消费者********");
		
		System.out.println("***********接收到的Message:"+data);		
//		commit(deliveryTag,channel);
	}


}
