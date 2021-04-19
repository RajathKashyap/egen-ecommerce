package solutions.egen.kafka;

import java.util.List;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import solutions.egen.mapper.NewOrder;
import solutions.egen.model.Orders;

@Service
public class KafkaProducer {

	private final Logger logger = LoggerFactory.getLogger(Producer.class);

	private static final String CREATE_ORDERS_TOPIC = "create_orders";

	private static final String CANCEL_ORDERS_TOPIC = "cancel_orders";
	
	private static final String UPDATE_ORDERS_TOPIC = "update_orders";

	@Autowired
	private KafkaTemplate<String, NewOrder> createKafkaTemplate;

	@Autowired
	private KafkaTemplate<String, Orders> updateKafkaTemplate;

	@Autowired
	private KafkaTemplate<String, List<Long>> cancelKafkaTemplate;

	public void createOrders(NewOrder orders) {
		logger.info("KAFKA: Queuing creation order - {}", orders);
		this.createKafkaTemplate.send(CREATE_ORDERS_TOPIC, orders);
	}

	public void cancelOrders(List<Long> orderIds) {
		logger.info("KAFKA: Queing cancellation order - {}", orderIds);
		this.cancelKafkaTemplate.send(CANCEL_ORDERS_TOPIC, orderIds);
	}

	public void updateOrders(Orders orders) {
		logger.info("KAFKA: Queuing updation order - {}", orders);
		this.updateKafkaTemplate.send(UPDATE_ORDERS_TOPIC, orders);
	}

}
