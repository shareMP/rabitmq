package com.mrl.mq.rabitmq_04.binding_key;

import java.util.Random;
import java.util.UUID;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {
	private static final String EXCHANGE_NAME = "ex_log_direct";
	private static final String[] SEVERITIES = { "info", "warning", "error" };

	public static void main(String[] args) throws Exception {
		// 创建连接和频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel chanel = connection.createChannel();
		
		//声明转发器的类型为direct
		chanel.exchangeDeclare(EXCHANGE_NAME, "direct");
		//发送6条消息
		for(int i= 0;i<6;i++){
			 String severity = getSeverity();  
			 String message = severity+"_log:"+UUID.randomUUID().toString();
			 chanel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
			 System.out.println("Send Message:"+message);
		}
		
	}

	private static String getSeverity() {
	    Random random = new Random();  
        int ranVal = random.nextInt(3);  
        return SEVERITIES[ranVal];  
	}
}
