package com.atguigu.activemq.topic;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProduce_topic {
	
	public static final String ACTIVEMQ_URL="tcp://192.168.8.138:61616";
	public static final String TOPIC="topic_01";
	
	public static void main(String[] args) throws JMSException {
		
		//创建连接工厂
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		//获取连接
		Connection  connection =activeMQConnectionFactory.createConnection();
		connection.start();
		
		//第一个参数:是否开启事务；第二个参数:是否自动确认消息
		Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建TOPIC
		Topic  tpic =session.createTopic(TOPIC);
		
		//创建消息生产者
		MessageProducer messageProducer =session.createProducer(tpic);
		
		messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
		//发送消息
		for (int i = 1; i <= 3; i++) {
			TextMessage  textMessage = session.createTextMessage("topic massage---"+i);
			messageProducer.send(textMessage);
		}
		
		
		messageProducer.close();
		session.close();
		connection.close();
		
		System.out.println("===========消息发送成功===============");
	}

}
