package com.mrl.mq.rabitmq_06.spring_01;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.AMQP;

/**
 * @author lwqMR
 *
 */
@Configuration
public class AnnotationConfiguration {
	// ָ���������� routingkey������Ĭ��ΪQueue�����ƣ�ʹ��Exchange����ΪDirectExchange
	protected String springQueueDemo = "spring-queue-demo";

	// ��������
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		connectionFactory.setPort(AMQP.PROTOCOL.PORT);
		return connectionFactory;
	}

	// ����rabbitAdmin ������
	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}
	
	 //����rabbitTemplate ��Ϣģ����  
    @Bean  
    public RabbitTemplate rabbitTemplate() {  
        RabbitTemplate template = new RabbitTemplate(connectionFactory());  
        //The routing key is set to the name of the queue by the broker for the default exchange.  
        template.setRoutingKey(this.springQueueDemo);  
        //Where we will synchronously receive messages from  
        template.setQueue(this.springQueueDemo);  
        return template;  
    }  
    
    // Every queue is bound to the default direct exchange  
    public Queue helloWorldQueue() {  
        return new Queue(this.springQueueDemo);  
    }  
    
}
