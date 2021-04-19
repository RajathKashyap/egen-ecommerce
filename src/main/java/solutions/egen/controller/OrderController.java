package solutions.egen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import solutions.egen.Exceptions.ItemNotFoundException;
import solutions.egen.Exceptions.OrderNotFoundException;
import solutions.egen.kafka.KafkaProducer;
import solutions.egen.mapper.NewOrder;
import solutions.egen.model.Orders;
import solutions.egen.service.OrderService;

@RestController
@RequestMapping("ecommerce/api/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private KafkaProducer kafkaProducer;

	@GetMapping("order")
	@ApiOperation(value = "View list of all orders")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of all orders"), })
	public List<Orders> getAllOrders() {
		return this.orderService.getAllOrders();
	}

	@GetMapping("order/{id}")
	@ApiOperation(value = "View the order by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the order"),
			@ApiResponse(code = 400, message = "Cannot find the order with the id"), })
	public ResponseEntity<Orders> getOrderById(@PathVariable(value = "id") Long orderId) {
		try {
			return ResponseEntity.ok().body(orderService.getOrderById(orderId));
		} catch (OrderNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Not Found", ex);
		}
	}

	@PostMapping(value = "order")
	@ApiOperation(value = "Create new order.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Order successfully created."),
			@ApiResponse(code = 400, message = "Order details not found") })
	public ResponseEntity<Object> createOrder(@RequestBody NewOrder order) {
		try {
			Orders response = orderService.addOrder(order);
			return ResponseEntity.ok(response);
		} catch (ItemNotFoundException itemNotFoundException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order details");
		}
	}

	@PutMapping("order/{id}")
	@ApiOperation(value = "Cancel the order by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully cancelled the order"),
			@ApiResponse(code = 400, message = "Cannot cancel the order, as order with the id doesn't exist"), })
	public ResponseEntity<Object> cancelOrder(@PathVariable(value = "id") Long orderId) {
		try {
			return ResponseEntity.ok(this.orderService.cancelOrder(orderId));
		} catch (OrderNotFoundException exception) {
			return ResponseEntity.badRequest().body(exception.getLocalizedMessage());
		}
	}

	@PostMapping("order/bulk")
	@ApiOperation(value = "Create orders in bulk")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully queued orders for processing"), })
	public ResponseEntity<Object> createBulkOrders(@RequestBody List<NewOrder> orders) {
		try{
			for (NewOrder order : orders) {
				kafkaProducer.createOrders(order);
		}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong, try again in sometime");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Orders successfully queued");
	}
	
	@PutMapping("order/bulk")
	@ApiOperation(value = "Update orders in bulk")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully queued orders for processing"), })
	public ResponseEntity<Object> updateBulkOrders(@RequestBody List<Orders> orders) {
		try{
			for (Orders order : orders) {
				kafkaProducer.updateOrders(order);
		}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong, try again in sometime");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Orders successfully queued");
	}

	@DeleteMapping("order/bulk/{ids}")
	@ApiOperation(value = "Cancel orders in bulk")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully queued orders for cancellation"), })
	public void cancelOrderBulk(@PathVariable List<Long> ids) {
		kafkaProducer.cancelOrders(ids);

	}
}
