package com.mrl.mq.rabitmq_01.helloworld;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * @author lwqMR
 *	消费者
 */
public class Receive {
	/**
	 *	消息队列名字 
	 */
	private static final String QUEUE_NAME = "helloworld";
	
	public static void main(String[] args) throws Exception {
		//1.连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		//2.给连接工厂设置属性
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setPort(AMQP.PROTOCOL.PORT);
		//3.创建一个连接
		Connection conn = factory.newConnection();
		//4.创建频道
		Channel channel = conn.createChannel();
		//5.声明队列,防止消息接受者先运行此程序，队列中还不存在时创建
		//TODO 参数的具体说明需要去参考API文档
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//6创建队列消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//7.指定消费者队列,参数，队列名字，是否自动ACK，消费者
		channel.basicConsume(QUEUE_NAME, true,consumer);
		//8.循环消费
		while(true){
			//nextDelivery是一个阻塞方法（内部其实是阻塞队列的take方法）
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println("received message:"+message);
		}
	}
}
