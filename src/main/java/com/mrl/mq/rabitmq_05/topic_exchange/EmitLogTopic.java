package com.mrl.mq.rabitmq_05.topic_exchange;

import java.util.UUID;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {
	private static final String EXCHANGE_NAME = "topic_logs";

	public static void main(String[] args) throws Exception {
		// 创建连接和频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel chanel = connection.createChannel();

		// 设置主题转发器
		chanel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String[] routings_keys = new String[] { "kernal.info", "cron.warning", "auth.info", "kernal.critical" };
		
		for (String routing_key : routings_keys) {
			 String msg = UUID.randomUUID().toString();
			 chanel.basicPublish(EXCHANGE_NAME, routing_key, null, msg.getBytes());
			 System.out.println("Send Message,Routing Key is "+routing_key+",msg is "+msg);
		}

		chanel.close();  
        connection.close();  
	}
}
