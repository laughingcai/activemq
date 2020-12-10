package com.atguigu.activemq.topic;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 发布订阅模式，消费持久化消息
 * 1.一定要先运行消费者，等于向MQ注册，类似于我订阅了这个主题
 * 2.然后再运行生产这发送消息
 * 3.无论消费者是否在线，都会接收到消息，不在线的话，下次连接的时候，会把没有收到的消息都接受过来
 * @author ThinkPad
 *
 */
public class JmsConsumer_topic_persistent {
	

	
	public static final String ACTIVEMQ_URL="tcp://192.168.8.138:61616";
	public static final String TOPIC="topic_persistent";
	
	public static void main(String[] args) throws JMSException, IOException {
		
		System.out.println("=============user1消费者==============");
		
		//创建连接工厂
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		//获取连接
		Connection  connection =activeMQConnectionFactory.createConnection();
		
		connection.setClientID("user1");
		
		//第一个参数:是否开启事务；第二个参数:是否自动确认消息
		Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建TOPIC
		Topic  topic =session.createTopic(TOPIC);
		
		//创建一个持久化的topic订阅者
		TopicSubscriber topicSubscriber =session.createDurableSubscriber(topic, "备注");
		
		connection.start();
		
		Message  message  =topicSubscriber.receive();
		
		while(null !=message) {
			TextMessage textMessage= (TextMessage) message;
			
			System.out.println("=======消费者收到的消息为========"+textMessage.getText());
			message=topicSubscriber.receive();
		}
		
		session.close();
		connection.close();
		
		
		System.out.println("===========消息消费完成===============");
	}



	
}
