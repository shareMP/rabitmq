package com.mrl.mq.rabitmq_05.topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsTopicForKernel {
	private static final String EXCHANGE_NAME = "topic_logs";

	public static void main(String[] args) throws Exception {
		// 创建连接和频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel chanel = connection.createChannel();
		
		chanel.exchangeDeclare(EXCHANGE_NAME,"topic");
		//随机生成一个队列
		String queueName = chanel.queueDeclare().getQueue();
		//接收所有与kernel相关的消息
		chanel.queueBind(queueName, EXCHANGE_NAME, "kernal.*");
		System.out.println("waiting for message...");
		QueueingConsumer consumer = new QueueingConsumer(chanel);
		chanel.basicConsume(queueName, true,consumer);
		while(true){
		    QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();
            System.out.println("Received routingKey :"+routingKey+",msg="+message);
		}
	}
}
