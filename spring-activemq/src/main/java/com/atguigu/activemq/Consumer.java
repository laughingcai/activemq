package com.atguigu.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	
	@Autowired
    private JmsTemplate jmsTemplate;
	
	public static void main(String[] args) {
		 ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		 Consumer consumer = (Consumer) applicationContext.getBean("consumer");
		 String message = (String) consumer.jmsTemplate.receiveAndConvert();
		 
		 System.out.println("========消费者接收到的消息为========="+message);
	}

}
