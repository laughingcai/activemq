package com.atguigu.com.activemq;

import org.apache.activemq.broker.BrokerService;

public class EmbedBroker {

	public static void main(String[] args) throws Exception {
		//activemq也支持再vm中通信基于嵌入式的broker,相当于在本地启动一个activemq
		BrokerService brokerService = new BrokerService();
		brokerService.setUseJmx(true);
		brokerService.addConnector("tcp://localhost:61616");
		brokerService.start();
	}
}
