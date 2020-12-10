package com.atguigu.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class Produce {

	 @Autowired
	    private JmsTemplate jmsTemplate;

	    public static void main(String[] args) {
	        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	        Produce produce = (Produce) applicationContext.getBean("produce");
	        produce.jmsTemplate.send(
	                new MessageCreator() {
	                    public Message createMessage(Session session) throws JMSException {
	                        return session.createTextMessage("***Spring和ActiveMQ的整合case111.....");
	                    }
	                }
	        );
	        System.out.println("********send task over");
	    }

}
