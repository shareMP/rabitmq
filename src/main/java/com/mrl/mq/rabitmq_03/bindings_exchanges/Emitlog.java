package com.mrl.mq.rabitmq_03.bindings_exchanges;

import java.io.IOException;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author lwqMR 日志发送端
 */
public class Emitlog {
	
	/**
	 *	交换器名字 
	 */
	private static final String EXCHANGE_NAME = "ex_log";
	
	public static void main(String[] args) throws Exception {
		//创建连接和频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel chanel = connection.createChannel();
		//声明转发器和类型,广播
		chanel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String message = new Date().toLocaleString()+" : log something";
		
		for(int i = 0;i<1000;i++){
			//往转发器上发送消息
			chanel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println(" Send :"+message);
		}
		chanel.close();
		connection.close();
	}
}
