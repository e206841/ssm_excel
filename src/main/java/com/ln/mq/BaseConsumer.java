package com.ln.mq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.rabbitmq.client.Channel;

/**
 * 
 * @author LiuFeng
 * @since 20170327
 */
public abstract class BaseConsumer implements ChannelAwareMessageListener {
	/**
	 * 消息转换器
	 * 消费者要有响应的set方法
	 */
	protected MessageConverter converter;
	
	public void setConverter(MessageConverter converter) {
        this.converter = converter;
    }

	/**
	 * 提交确认方法
	 * @param deliveryTag
	 * @param channel
	 * @throws IOException
	 */
	protected void commit(long deliveryTag, Channel channel) throws IOException {
		channel.basicAck(deliveryTag, false);
	}

	/**
	 * 回滚方法
	 * @param deliveryTag
	 * @param channel
	 * @throws IOException
	 */
	protected void rollBack(long deliveryTag, Channel channel)
			throws IOException {
		channel.basicNack(deliveryTag, false, true);
	}

	protected abstract void onRecMessage(Object data, long deliveryTag,
			Channel channel) throws Exception;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {

		try {
			Object result = converter.fromMessage(message);
			if (result == null) {
				commit(message.getMessageProperties().getDeliveryTag(), channel);
				return;
			}
			onRecMessage(result, message.getMessageProperties()
					.getDeliveryTag(), channel);
		} catch (MessageConversionException e) {
			commit(message.getMessageProperties().getDeliveryTag(), channel);
			e.printStackTrace();
		}
	}

}



