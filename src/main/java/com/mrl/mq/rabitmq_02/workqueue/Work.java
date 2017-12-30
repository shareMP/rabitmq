package com.mrl.mq.rabitmq_02.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Work {
	
	private static final String QUEUE_NAME = "workqueue";
	
	public static void main(String[] args) throws Exception{
		//区分不同工作进程的输出
		int hashCode = Work.class.hashCode();
		//创建和连接频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		
		boolean durable = true;
		
		//声明队列
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		
//	    //设置最大服务转发消息数量   只有在消费者空闲的时候才会接收消息
        int prefetchCount = 1;  
        channel.basicQos(prefetchCount);
		
		//创建消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//打开应答机制
		boolean ack = false;
		
		//指定消费队列
		channel.basicConsume(QUEUE_NAME,ack,consumer);
		//消费
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(hashCode+" received "+message);
			doWork(message);
			System.out.println(hashCode+" Done");
			
			//发送应答
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
	
	/**
	 * @param task
	 *	每个点耗时1秒
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
