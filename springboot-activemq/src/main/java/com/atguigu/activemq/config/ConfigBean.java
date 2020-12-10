package com.atguigu.activemq.config;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class ConfigBean {

	private String myQueue;
}
