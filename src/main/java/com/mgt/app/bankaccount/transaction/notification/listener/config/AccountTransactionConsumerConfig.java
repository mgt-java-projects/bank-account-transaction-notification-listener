package com.mgt.app.bankaccount.transaction.notification.listener.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

/**
 * Account transaction consumer configuration kafka event processing.
 * 
 * @author stami
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bank.account.transaction.event.consumer.topic.config")
public class AccountTransactionConsumerConfig {

	/**
	 * Cluster server url.
	 */
	private String bootstrapServerUrl;

	/**
	 * Consumer group id.
	 */
	private String consumerGroupId;

	/**
	 * Bean instance of {@link ConsumerFactory}
	 * 
	 * @return ConsumerFactory instance.
	 */
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerUrl);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory(props);
	}

	/**
	 * Bean instance of {@link ConcurrentKafkaListenerContainerFactory}
	 * 
	 * @return ConcurrentKafkaListenerContainerFactory instance
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	/**
	 * Define bean instance for {@link ObjectMapper}.
	 * 
	 * @return ObjectMapper
	 */
	@Bean()
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		return new ObjectMapper();
	}
}
