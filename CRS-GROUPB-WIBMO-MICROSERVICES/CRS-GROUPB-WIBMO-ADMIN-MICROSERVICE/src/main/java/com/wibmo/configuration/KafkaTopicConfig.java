/**
 * 
 */
package com.wibmo.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * 
 */
@Configuration
public class KafkaTopicConfig {

	@Bean
	public NewTopic NotifictionTopic() {
		return TopicBuilder.name("NotificationTopic").build();
	}
}
