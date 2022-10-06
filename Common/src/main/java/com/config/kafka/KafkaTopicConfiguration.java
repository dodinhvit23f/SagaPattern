package com.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import com.common.KafkaTopic;

public class KafkaTopicConfiguration {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServer;
	
	@Bean
	public KafkaAdmin KafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		return new KafkaAdmin(configs);
	}
	
	@Bean
	public NewTopic orderTopic() {
		return TopicBuilder
				.name(KafkaTopic.ORDER)
				.build();
	}
	
	@Bean
	public NewTopic paymentTopic() {
		return TopicBuilder
				.name(KafkaTopic.PAYMENT)
				.build();
	}
	
	@Bean
	public NewTopic customerTopic() {
		return TopicBuilder
				.name(KafkaTopic.CUSTOMER)
				.build();
	}
}
