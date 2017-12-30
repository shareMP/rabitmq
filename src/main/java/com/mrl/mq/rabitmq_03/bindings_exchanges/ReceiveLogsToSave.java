package com.mrl.mq.rabitmq_03.bindings_exchanges;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author lwqMR ������ �洢��־
 */
public class ReceiveLogsToSave {
	/**
	 * ����������
	 */
	private static final String EXCHANGE_NAME = "ex_log";

	public static void main(String[] args) throws Exception {
		// �������Ӻ�Ƶ��
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel chanel = connection.createChannel();
		
		chanel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		//����һ���ǳ־û���,Ψһ�ģ����Զ�ɾ���Ķ���
		String queueName = chanel.queueDeclare().getQueue();
		//Ϊת����ָ������,����binding
		chanel.queueBind(queueName, EXCHANGE_NAME, "");
		System.out.println("Wait for message...");
		QueueingConsumer consumer = new QueueingConsumer(chanel);
		//ָ�������ߣ��ڶ�������Ϊ�Զ�Ӧ��
		chanel.basicConsume(queueName,false,consumer);
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			print2File(message);
		}
	}

	private static void print2File(String message) {
		String dir = ReceiveLogsToSave.class.getClassLoader().getResource("").getPath();
		String logName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		File file = new File("c:/cs",logName+".txt");
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file, true);
			fos.write((message+"\r\n").getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
