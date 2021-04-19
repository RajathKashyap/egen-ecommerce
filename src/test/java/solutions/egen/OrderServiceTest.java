package solutions.egen;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import solutions.egen.Exceptions.ItemNotFoundException;
import solutions.egen.Exceptions.OrderNotFoundException;
import solutions.egen.enums.OrderStatus;
import solutions.egen.mapper.ItemDetails;
import solutions.egen.mapper.NewOrder;
import solutions.egen.model.Address;
import solutions.egen.model.Item;
import solutions.egen.model.OrderItem;
import solutions.egen.model.Orders;
import solutions.egen.model.Payment;
import solutions.egen.model.Shipping;
import solutions.egen.repository.AddressRepository;
import solutions.egen.repository.ItemRepository;
import solutions.egen.repository.OrderRepository;
import solutions.egen.repository.PaymentRepository;
import solutions.egen.repository.ShippingRepository;
import solutions.egen.service.OrderService;

//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

	@Mock
	OrderRepository orderRepo;

	@Mock
	AddressRepository addressRepo;

	@Mock
	PaymentRepository paymentRepo;

	@Mock
	ShippingRepository shippingRepo;

	@Mock
	ItemRepository itemRepo;

	@InjectMocks
	OrderService orderService;

	private Orders orders;
	private NewOrder newOrder;

	@Before
	public void init() throws Exception {
		orders = new Orders();
		newOrder = new NewOrder();
		orders.setOrderId(122353L);
		orders.setOrderStatus(OrderStatus.PENDING.getDesc());
		orders.setOrderTotal(499.99);
		orders.setOrderItems(createOrderItems(orders));
		orders.setOrderShippingDetails(createOrderShippingDetails(orders));
		orders.setPayment(savePaymentDetails(orders));
		orders.setCreateDate(new Date());
		orders.setOrderCustomerId(101L);
		orders.setModifyDate(new Date());
		orders.setOrderSubtotal(490.0);
		orders.setOrderTax(9.99);

		newOrder.setOrderSubtotal(490.0);
		newOrder.setOrderTotal(499.99);
		newOrder.setOrderItems(getItemDetails());
		newOrder.setOrderShippingDetails(createOrderShippingDetails(orders));
		newOrder.setPayments(savePaymentDetails(orders));
		newOrder.setOrderCustomerId(101L);
		newOrder.setOrderTax(9.99);

	}

	@Test
	public void testFindOrderById() throws Exception {

		when(orderRepo.findById(any(Long.class))).thenReturn(Optional.of(orders));
		assertNotNull(orderService.getOrderById(1L));
		assertEquals(3, orderService.getOrderById(1L).getOrderItems().size());
		assertEquals(orders.getOrderTotal(), orderService.getOrderById(1L).getOrderTotal());
	}

	@Test
	public void testCreateOrder() throws OrderNotFoundException, ItemNotFoundException {

		Item item1 = new Item(1L, "Test Item 1", 100.0);
		when(itemRepo.findById(any(Long.class))).thenReturn(Optional.of(item1));
		when(orderRepo.save(any(Orders.class))).thenReturn(orders);
		Orders createdOrder = orderService.addOrder(newOrder);
		assertNotNull(createdOrder);
		assertEquals(orders, createdOrder);
		assertEquals(orders.getOrderItems().size(), createdOrder.getOrderItems().size());
		assertEquals(orders.getOrderTotal(), createdOrder.getOrderTotal());
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateOrderPaymentException() throws OrderNotFoundException, ItemNotFoundException {

		Item item1 = new Item(1L, "Test Item 1", 100.0);
		newOrder.setOrderTotal(100.32);
		when(itemRepo.findById(any(Long.class))).thenReturn(Optional.of(item1));
		orderService.addOrder(newOrder);
	}

	@Test(expected = ItemNotFoundException.class)
	public void testCreateOrderItemException() throws OrderNotFoundException, ItemNotFoundException {
		orderService.addOrder(newOrder);
	}
	
	@Test(expected = OrderNotFoundException.class)
	public void testCreateOrderException() throws OrderNotFoundException, ItemNotFoundException {
		orderService.getOrderById(2L);
	}
	
	private Shipping createOrderShippingDetails(Orders orders) {
		return new Shipping("Home Delivery", 8.0, createAddress());
	}

	private List<ItemDetails> getItemDetails() {
		return new ArrayList<ItemDetails>(
				Arrays.asList(new ItemDetails(1L, 1), new ItemDetails(2L, 1), new ItemDetails(3L, 2)));
	}

	private List<OrderItem> createOrderItems(Orders orders) {
		Item item1 = new Item(1L, "Test Item 1", 100.0);
		Item item2 = new Item(2L, "Test Item 2", 490.0);
		Item item3 = new Item(3L, "Test Item 3", 28.0);
		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(new OrderItem(orders, item1, 1));
		orderItems.add(new OrderItem(orders, item2, 1));
		orderItems.add(new OrderItem(orders, item3, 3));
		return orderItems;
	}

	private Address createAddress() {
		return new Address("1100 west prien lake road", "room 103", "Lake Charles", "LA", "70601");
	}

	private List<Payment> savePaymentDetails(Orders orders) {
		List<Payment> details = new ArrayList<>();
		details.add(new Payment("Credit Card", 499.99, new Date(), "3216547896", 1234, 2024, 12, "Rich Tester",
				createAddress(), orders));
		return details;
	}
}