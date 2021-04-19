package solutions.egen.kafka;

import java.util.List;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import solutions.egen.Exceptions.OrderNotFoundException;
import solutions.egen.mapper.NewOrder;
import solutions.egen.model.Orders;
import solutions.egen.service.OrderService;

@Service
public class KafkaConsumer {

	private final Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private OrderService orderService;

	@KafkaListener(topics = "create_orders", groupId = "group_id", containerFactory = "orderKafkaListenerFactory")
	public void createOrders(NewOrder orders) {
		logger.info("KAFKA: Processing creation order - {}", orders);
		orderService.addBulkOrder(orders);
	}

	@KafkaListener(topics = "cancel_orders", groupId = "group_id")
	public void cancelOrders(List<Long> orderIds) {
		logger.info("KAFKA: Cancelling the following orders - {}", orderIds);
		orderService.cancelBulkOrder(orderIds);
	}
	
	@KafkaListener(topics = "update_orders", groupId = "group_id", containerFactory = "updateKafkaListenerFactory")
	public void createOrders(Orders orders) throws OrderNotFoundException {
		logger.info("KAFKA: Processing update order - {}", orders);
		orderService.updateOrder(orders);
	}

}
