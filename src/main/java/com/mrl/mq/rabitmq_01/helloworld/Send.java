package com.mrl.mq.rabitmq_01.helloworld;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author lwqMR
 *  RabitMQ生产者
 */
public class Send {
	
	/**
	 *	消息队列名字 
	 */
	private static final String QUEUE_NAME = "helloworld";

	public static void main(String[] args) throws IOException {
		//1.创建连接到RabitMQ,连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		//2.设置rabitmq所在主机ip或者主机名
		factory.setHost("127.0.0.1");
		//3.设置用户名
		factory.setUsername("guest");
		//4.设置密码
		factory.setPassword("guest");
		//5.指定端口,默认监听端口，5672
		factory.setPort(AMQP.PROTOCOL.PORT);
		//6.创建一个连接
		Connection connection = factory.newConnection();
		//7.创建一个频道，因为连接是基于tcp的，频繁的创建和销毁浪费资源，而基于连接的频道较好
		Channel channel = connection.createChannel();
		//8.指定队列
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		//9.发送消息
		String message = "Hello,World";
		//10.往队列中发送一条消息
		//发送10000条消息
		for(int i=0;i<10000;i++){
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println("发送消息:"+message);
		}
		
		//11.关闭频道和连接
		channel.close();
		connection.close();
	}
	
}
