package com.mrl.mq.rabitmq_02.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	private static final String QUEUE_NAME = "workqueue";
	public static void main(String[] args) throws Exception {
		//创建连接和频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		
		//声明队列
		//声明持久化队列
		boolean durable = true;
		channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
		//发送10条消息,依次在消息后面加1到10个点
		for (int i = 0; i < 50; i++) {
			String dots = "";
			for (int j = 0; j <= i; j++) {
				dots+=".";
			}
			String message = "helloworld"+dots+dots.length();
			//发送消息 ,默认的交换机
			//设置发送的消息持久化
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println("send message:"+message);
		}
		//关闭
		channel.close();
		conn.close();
	}
	
}
