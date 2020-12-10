package com.atguigu.activemq.queue;

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

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer_ACK {
	
	public static final String ACTIVEMQ_URL="tcp://192.168.8.138:61616";
	public static final String ACTIVEMQ_QUEUE="ack_queue_01";
	
	public static void main(String[] args) throws JMSException, IOException {
		
		//创建连接工厂
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		//获取连接
		Connection  connection =activeMQConnectionFactory.createConnection();
		connection.start();
		
		//第一个参数:是否开启事务；第二个参数:是否自动确认消息
		Session session= connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		
		//创建队列
		Queue queue =session.createQueue(ACTIVEMQ_QUEUE);
		
		MessageConsumer messageConsumer=	session.createConsumer(queue);
		
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
		messageConsumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				if(null !=message&&message instanceof TextMessage) {
					TextMessage textMassage = (TextMessage) message;
					try {
						System.out.println("消费到消息====》"+textMassage.getText());
						//1.消费者设置手动确认消息机制后，必须要执行下面的代码，否则下次还会重复消费
						//2.如果开启了事务，并且有session.commit()，就算设置了手动确认消息机制，即使不写下面这行代码，消息会自动确认，且不会重复消费
						message.acknowledge();
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
	
		
		System.in.read();//程序不退出，press any key exit
		
		messageConsumer.close();
		//session.commit();
		session.close();
		connection.close();
		
		
		System.out.println("===========消息消费完成===============");
	}

}
