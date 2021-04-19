package solutions.egen.mapper;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import solutions.egen.model.Payment;
import solutions.egen.model.Shipping;

@Component
public class NewOrder {

	@NonNull
	private Long orderCustomerId;

	@NonNull
	private Double orderSubtotal;

	@NonNull
	private Double orderTax;

	@NonNull
	private Double orderTotal;

	@NonNull
	private Shipping orderShippingDetails;

	@NonNull
	private List<ItemDetails> orderItems;

	@NonNull
	private List<Payment> payments;

	public Long getOrderCustomerId() {
		return orderCustomerId;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public void setOrderCustomerId(Long orderCustomerId) {
		this.orderCustomerId = orderCustomerId;
	}

	public Shipping getOrderShippingDetails() {
		return orderShippingDetails;
	}

	public void setOrderShippingDetails(Shipping orderShippingDetails) {
		this.orderShippingDetails = orderShippingDetails;
	}

	public List<ItemDetails> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<ItemDetails> orderItems) {
		this.orderItems = orderItems;
	}

	public Double getOrderSubtotal() {
		return orderSubtotal;
	}

	public void setOrderSubtotal(Double orderSubtotal) {
		this.orderSubtotal = orderSubtotal;
	}

	public Double getOrderTax() {
		return orderTax;
	}

	public void setOrderTax(Double orderTax) {
		this.orderTax = orderTax;
	}

	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public NewOrder() {
	}

	public NewOrder(Long orderCustomerId, Double orderSubtotal, Double orderTax, Double orderTotal,
			Shipping orderShippingDetails, List<ItemDetails> orderItems, List<Payment> payments) {
		super();
		this.orderCustomerId = orderCustomerId;
		this.orderSubtotal = orderSubtotal;
		this.orderTax = orderTax;
		this.orderTotal = orderTotal;
		this.orderShippingDetails = orderShippingDetails;
		this.orderItems = orderItems;
		this.payments = payments;
	}

}
