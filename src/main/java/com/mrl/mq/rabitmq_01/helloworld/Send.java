package com.mrl.mq.rabitmq_01.helloworld;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author lwqMR
 *  RabitMQ������
 */
public class Send {
	
	/**
	 *	��Ϣ�������� 
	 */
	private static final String QUEUE_NAME = "helloworld";

	public static void main(String[] args) throws IOException {
		//1.�������ӵ�RabitMQ,���ӹ���
		ConnectionFactory factory = new ConnectionFactory();
		//2.����rabitmq��������ip����������
		factory.setHost("127.0.0.1");
		//3.�����û���
		factory.setUsername("guest");
		//4.��������
		factory.setPassword("guest");
		//5.ָ���˿�,Ĭ�ϼ����˿ڣ�5672
		factory.setPort(AMQP.PROTOCOL.PORT);
		//6.����һ������
		Connection connection = factory.newConnection();
		//7.����һ��Ƶ������Ϊ�����ǻ���tcp�ģ�Ƶ���Ĵ����������˷���Դ�����������ӵ�Ƶ���Ϻ�
		Channel channel = connection.createChannel();
		//8.ָ������
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		//9.������Ϣ
		String message = "Hello,World";
		//10.�������з���һ����Ϣ
		//����10000����Ϣ
		for(int i=0;i<10000;i++){
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println("������Ϣ:"+message);
		}
		
		//11.�ر�Ƶ��������
		channel.close();
		connection.close();
	}
	
}
