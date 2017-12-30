package com.mrl.mq.rabitmq_03.bindings_exchanges;

import java.io.IOException;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author lwqMR ��־���Ͷ�
 */
public class Emitlog {
	
	/**
	 *	���������� 
	 */
	private static final String EXCHANGE_NAME = "ex_log";
	
	public static void main(String[] args) throws Exception {
		//�������Ӻ�Ƶ��
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel chanel = connection.createChannel();
		//����ת����������,�㲥
		chanel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String message = new Date().toLocaleString()+" : log something";
		
		for(int i = 0;i<1000;i++){
			//��ת�����Ϸ�����Ϣ
			chanel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println(" Send :"+message);
		}
		chanel.close();
		connection.close();
	}
}
