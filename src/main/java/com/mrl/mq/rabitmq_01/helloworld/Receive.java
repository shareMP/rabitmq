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
 *	������
 */
public class Receive {
	/**
	 *	��Ϣ�������� 
	 */
	private static final String QUEUE_NAME = "helloworld";
	
	public static void main(String[] args) throws Exception {
		//1.���ӹ���
		ConnectionFactory factory = new ConnectionFactory();
		//2.�����ӹ�����������
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setPort(AMQP.PROTOCOL.PORT);
		//3.����һ������
		Connection conn = factory.newConnection();
		//4.����Ƶ��
		Channel channel = conn.createChannel();
		//5.��������,��ֹ��Ϣ�����������д˳��򣬶����л�������ʱ����
		//TODO �����ľ���˵����Ҫȥ�ο�API�ĵ�
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//6��������������
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//7.ָ�������߶���,�������������֣��Ƿ��Զ�ACK��������
		channel.basicConsume(QUEUE_NAME, true,consumer);
		//8.ѭ������
		while(true){
			//nextDelivery��һ�������������ڲ���ʵ���������е�take������
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println("received message:"+message);
		}
	}
}
