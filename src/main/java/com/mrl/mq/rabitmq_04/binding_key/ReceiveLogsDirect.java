package com.mrl.mq.rabitmq_04.binding_key;

import java.util.Random;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author lwqMR ���ն�
 */
public class ReceiveLogsDirect {
	private static final String EXCHANGE_NAME = "ex_log_direct";
	private static final String[] SEVERITIES = { "info", "warning", "error" };

	public static void main(String[] args) throws Exception {
		// �������Ӻ�Ƶ��
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel chanel = connection.createChannel();

		// ����ת����������Ϊdirect
		chanel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		//�������
		String queueName = chanel.queueDeclare().getQueue();
		String severity = getSeverity();
		//ָ���󶨵�bind_key
		chanel.queueBind(queueName, EXCHANGE_NAME, severity);
		System.out.println("Waiting for Message "+severity +"logs");
		QueueingConsumer consumer = new QueueingConsumer(chanel);
		
		chanel.basicConsume(queueName, true,consumer);
		
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println("Received Message "+message);
		}
	}
	
	/** 
     * �������һ����־���� 
     *  
     * @return 
     */  
    private static String getSeverity()  
    {  
        Random random = new Random();  
        int ranVal = random.nextInt(3);  
        return SEVERITIES[ranVal];  
    }  
}
