package com.atguigu.activemq.topic;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer_topic {
	
	public static final String ACTIVEMQ_URL="tcp://192.168.8.138:61616";
	public static final String TOPIC="topic_01";
	
	public static void main(String[] args) throws JMSException, IOException {
		
		System.out.println("=============2号消费者==============");
		
		//创建连接工厂
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		//获取连接
		Connection  connection =activeMQConnectionFactory.createConnection();
		connection.start();
		
		//第一个参数:是否开启事务；第二个参数:是否自动确认消息
		Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建TOPIC
		Topic  tpic =session.createTopic(TOPIC);
		
		MessageConsumer messageConsumer=	session.createConsumer(tpic);
		
		/**
		 * 使用receive阻塞式的接受消息，加timeout参数则多久没有收到消息后自动断开
		 */
		
//		while (true) {
//			TextMessage message =(TextMessage) messageConsumer.receive(4000l);
//			if(null != message) {
//				
//				System.out.println("消费到消息====》"+message.getText());
//			}else {
//				break;
//			}
//			
//			
//		}
		
		/**
		 * 以监听的方式接收消息
		 */
		
		messageConsumer.setMessageListener((message) -> {
			if (null != message && message instanceof TextMessage) {
				TextMessage textMassage = (TextMessage) message;
				try {
					System.out.println("消费到消息====》" + textMassage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

		});
		System.in.read();//程序不退出
		
		messageConsumer.close();
		session.close();
		connection.close();
		
		
		System.out.println("===========消息消费完成===============");
	}

}
