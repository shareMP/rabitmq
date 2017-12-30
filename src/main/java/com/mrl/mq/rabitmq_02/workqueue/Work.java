package com.mrl.mq.rabitmq_02.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Work {
	
	private static final String QUEUE_NAME = "workqueue";
	
	public static void main(String[] args) throws Exception{
		//���ֲ�ͬ�������̵����
		int hashCode = Work.class.hashCode();
		//����������Ƶ��
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		
		boolean durable = true;
		
		//��������
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		
//	    //����������ת����Ϣ����   ֻ���������߿��е�ʱ��Ż������Ϣ
        int prefetchCount = 1;  
        channel.basicQos(prefetchCount);
		
		//����������
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//��Ӧ�����
		boolean ack = false;
		
		//ָ�����Ѷ���
		channel.basicConsume(QUEUE_NAME,ack,consumer);
		//����
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(hashCode+" received "+message);
			doWork(message);
			System.out.println(hashCode+" Done");
			
			//����Ӧ��
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
	
	/**
	 * @param task
	 *	ÿ�����ʱ1��
	 * @throws InterruptedException 
	 */
	public static void doWork(String task) throws InterruptedException{
		for(char ch : task.toCharArray()){
			if(ch == '.'){
				Thread.sleep(1000);
			}
		}
	}
}
