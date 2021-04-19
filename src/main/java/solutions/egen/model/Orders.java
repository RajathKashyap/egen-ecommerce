package solutions.egen.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;

	@NonNull
	private String orderStatus;

	@NonNull
	private Long orderCustomerId;

	@NonNull
	private Double orderSubtotal;

	@NonNull
	private Double orderTax;

	@NonNull
	private Double orderTotal;

	@NonNull
	@OneToOne(targetEntity = Shipping.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Shipping orderShippingDetails;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems;

	@NonNull
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Payment> payment;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<Payment> getPayment() {
		return payment;
	}

	public void setPayment(List<Payment> payment) {
		this.payment = payment;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getOrderCustomerId() {
		return orderCustomerId;
	}

	public void setOrderCustomerId(Long orderCustomerId) {
		this.orderCustomerId = orderCustomerId;
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

	public Shipping getOrderShippingDetails() {
		return orderShippingDetails;
	}

	public void setOrderShippingDetails(Shipping orderShippingDetails) {
		this.orderShippingDetails = orderShippingDetails;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", orderStatus=" + orderStatus + ", orderCustomerId=" + orderCustomerId
				+ ", orderSubtotal=" + orderSubtotal + ", orderTax=" + orderTax + ", orderTotal=" + orderTotal
				+ ", orderShippingDetails=" + orderShippingDetails + ", orderItems=" + orderItems + ", payment="
				+ payment + "]";
	}

	
}
