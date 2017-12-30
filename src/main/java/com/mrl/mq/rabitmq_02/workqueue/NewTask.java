package com.mrl.mq.rabitmq_02.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	private static final String QUEUE_NAME = "workqueue";
	public static void main(String[] args) throws Exception {
		//�������Ӻ�Ƶ��
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		
		//��������
		//�����־û�����
		boolean durable = true;
		channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
		//����10����Ϣ,��������Ϣ�����1��10����
		for (int i = 0; i < 50; i++) {
			String dots = "";
			for (int j = 0; j <= i; j++) {
				dots+=".";
			}
			String message = "helloworld"+dots+dots.length();
			//������Ϣ ,Ĭ�ϵĽ�����
			//���÷��͵���Ϣ�־û�
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println("send message:"+message);
		}
		//�ر�
		channel.close();
		conn.close();
	}
	
}
