package solutions.egen.kafka;

import java.util.HashMap;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import solutions.egen.mapper.NewOrder;
import solutions.egen.model.Orders;

@EnableKafka
@Configuration
public class KafkaConfiguration {

	private static final String KAFKA_SERVER = "127.0.0.1:9092";

	@Bean
	public ProducerFactory<String, NewOrder> createProducerFactory() {
		HashMap<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, NewOrder> createKafkaTemplate() {
		return new KafkaTemplate<>(createProducerFactory());
	}
	
	@Bean
	public ProducerFactory<String, Orders> updateProducerFactory() {
		HashMap<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, Orders> updateKafkaTemplate() {
		return new KafkaTemplate<>(updateProducerFactory());
	}

	@Bean
	public ProducerFactory<String, List<Long>> cancelProducerFactory() {
		HashMap<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, List<Long>> cancelKafkaTemplate() {
		return new KafkaTemplate<>(cancelProducerFactory());
	}

	@Bean
	public ConsumerFactory<String, NewOrder> createConsumerFactory() {
		HashMap<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(NewOrder.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, NewOrder> orderKafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, NewOrder> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(createConsumerFactory());
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, Orders> updateConsumerFactory() {
		HashMap<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(Orders.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Orders> updateKafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Orders> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(updateConsumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, List<Long>> cancelConsumerFactory() {
		HashMap<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Long.class);
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<List<Long>>(javaType, objectMapper, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, List<Long>> cancelKafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, List<Long>> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(cancelConsumerFactory());
		return factory;
	}

}
