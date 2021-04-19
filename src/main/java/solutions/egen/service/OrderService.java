package solutions.egen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class OrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ItemRepository itemRepository;

	public Orders getOrderById(Long orderId) throws OrderNotFoundException {
		Optional<Orders> order = orderRepository.findById(orderId);
		if (order.isPresent()) {
			logger.info("Fetching order {}", orderId);
			return order.get();
		}
		logger.error("Invalid order ID {}", orderId);
		throw new OrderNotFoundException("Order Not Found");
	}

	public List<Orders> getAllOrders() {
		logger.info("Fetching order history");
		return this.orderRepository.findAll();
	}

	public Orders addOrder(NewOrder order) throws ItemNotFoundException {
		Orders newOrder = new Orders();
		newOrder.setOrderStatus(OrderStatus.CREATED.getDesc());

		newOrder.setOrderCustomerId(order.getOrderCustomerId());

		List<ItemDetails> list = order.getOrderItems();
		List<OrderItem> items = new ArrayList<>(list.size());
		for (int i = 0; i < list.size(); i++) {
			Optional<Item> item = itemRepository.findById(list.get(i).getItemID());
			if (item.isPresent()) {
				Item itm = item.get();
				items.add(new OrderItem(newOrder, itm, list.get(i).getOrderItemQuantity()));
			} else {
				throw new ItemNotFoundException("Item not sold in the store");
			}
		}
		newOrder.setOrderItems(items);
		newOrder.setOrderShippingDetails(this.populateShippingDetails(order));
		newOrder.setOrderSubtotal(order.getOrderSubtotal());
		newOrder.setOrderTax(order.getOrderTax());
		newOrder.setOrderTotal(order.getOrderTotal());
		newOrder.setPayment(populatePaymentDetails(order, newOrder));
		return orderRepository.save(newOrder);
	}

	public Orders updateOrder(Orders update) throws OrderNotFoundException {
		Orders orderToUpdate = getOrderById(update.getOrderId());
		orderToUpdate.setOrderId(update.getOrderId());
		Shipping s = update.getOrderShippingDetails();
		s.setOrderShippingAddress(this.checkDuplicateAddress(s.getOrderShippingAddress()));
		orderToUpdate.setOrderShippingDetails(s);
		orderToUpdate.setOrderStatus(update.getOrderStatus());
		orderToUpdate.setOrderSubtotal(update.getOrderSubtotal());
		orderToUpdate.setOrderTax(update.getOrderTax());
		orderToUpdate.setOrderTotal(update.getOrderTotal());

		List<OrderItem> orderList = update.getOrderItems();
		for (OrderItem o : orderList) {
			o.setOrder(orderToUpdate);
		}
		orderToUpdate.setOrderItems(orderList);
		double paidAmount = 0.0;
		List<Payment> paymentList = update.getPayment();

		for (Payment p : paymentList) {
			paidAmount += p.getOrderPaymentAmount();
			p.setOrderBillingAddress(this.checkDuplicateAddress(p.getOrderBillingAddress()));
			p.setOrder(orderToUpdate);
		}
		if (paidAmount != orderToUpdate.getOrderTotal()) {
			throw new IllegalArgumentException("Please enter the exact billed amount to continue");
		}
		orderToUpdate.setPayment(paymentList);
		return orderRepository.save(orderToUpdate);
	}

	public Address checkDuplicateAddress(Address address) {
		Optional<Address> a = findAddress(address);
		if (a.isPresent()) {
			return a.get();
		} else {
			return addressRepository.save(address);
		}
	}

	public Orders cancelOrder(Long orderId) throws OrderNotFoundException {
		Optional<Orders> order = orderRepository.findById(orderId);
		if (order.isPresent()) {
			Orders cancelOrder = order.get();
			if (!(cancelOrder.getOrderStatus().equalsIgnoreCase(OrderStatus.CANCELLED.getDesc()))
					&& !(cancelOrder.getOrderStatus().equalsIgnoreCase(OrderStatus.DELIVERED.getDesc()))) {
				cancelOrder.setOrderStatus(OrderStatus.CANCELLED.getDesc());
				return orderRepository.save(cancelOrder);
			} else {
				throw new OrderNotFoundException("No Active orders with selected order ID");
			}
		}
		throw new OrderNotFoundException("No Active orders with selected order ID");
	}

	private Optional<Address> findAddress(Address address) {
		String line1 = address.getAddressLine1();
		String line2 = address.getAddressLine2();
		String city = address.getCity();
		String state = address.getState();
		String zip = address.getZip();
		return addressRepository.findFirstByAddressLine1AndAddressLine2AndCityAndStateAndZip(line1, line2, city, state,
				zip);
	}

	private Shipping populateShippingDetails(NewOrder order) {
		Shipping newShipping = order.getOrderShippingDetails();
		newShipping.setOrderShippingAddress(order.getOrderShippingDetails().getOrderShippingAddress());
		return newShipping;
	}

	private List<Payment> populatePaymentDetails(NewOrder order, Orders newOrder) {
		List<Payment> payments = order.getPayments();
		List<Payment> newPayments = new ArrayList<>();
		double paidAmount = 0.0;
		for (Payment p : payments) {
			Optional<Address> address = findAddress(p.getOrderBillingAddress());
			paidAmount += p.getOrderPaymentAmount();
			if (address.isPresent()) {

				newPayments.add(new Payment(p.getOrderPaymentMethod(), p.getOrderPaymentAmount(),
						p.getOrderPaymentDate(), p.getCardNumber(), p.getCvv(), p.getExpiryMonth(), p.getExpiryYear(),
						p.getNameOnCard(), address.get(), newOrder));
			} else {
				Address newAddress = p.getOrderBillingAddress();
				addressRepository.save(newAddress);
				newPayments.add(new Payment(p.getOrderPaymentMethod(), p.getOrderPaymentAmount(),
						p.getOrderPaymentDate(), p.getCardNumber(), p.getCvv(), p.getExpiryMonth(), p.getExpiryYear(),
						p.getNameOnCard(), newAddress, newOrder));
			}

		}
		if (paidAmount != newOrder.getOrderTotal()) {
			throw new IllegalArgumentException("Please enter the exact billed amount to continue");
		}
		return newPayments;
	}

	public void addBulkOrder(NewOrder orders) {
		try {
			addOrder(orders);
		} catch (ItemNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void cancelBulkOrder(List<Long> orderIds) {
		orderIds.stream().forEach((orderId) -> {
			try {
				cancelOrder(orderId);
			} catch (OrderNotFoundException e) {
				e.printStackTrace();
			}
		});
	}
}
