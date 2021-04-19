package solutions.egen.model;
/*
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long addressId;

	@NonNull
	private String addressLine1;

	private String addressLine2;

	@NonNull
	private String city;

	@NonNull
	private String state;

	@NonNull
	private String zipCode;

	@NonNull
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@NonNull
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;
}
*/

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "shipping")
public class Shipping {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderShippingId;

	@NonNull
	private String deliveryMethod;

	@NonNull
	private Double orderShippingCharges;

	@NonNull
	@ManyToOne(targetEntity = Address.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Address orderShippingAddress;

	public Shipping() {
	}

	public Shipping(String deliveryMethod, Double orderShippingCharges, Address orderShippingAddress) {
		this.deliveryMethod = deliveryMethod;
		this.orderShippingCharges = orderShippingCharges;
		this.orderShippingAddress = orderShippingAddress;
	}

	public Double getOrderShippingCharges() {
		return orderShippingCharges;
	}

	public void setOrderShippingCharges(Double orderShippingCharges) {
		this.orderShippingCharges = orderShippingCharges;
	}

	public Long getOrderShippingId() {
		return orderShippingId;
	}

	public void setOrderShippingId(Long orderShippingId) {
		this.orderShippingId = orderShippingId;
	}

	public Address getOrderShippingAddress() {
		return orderShippingAddress;
	}

	public void setOrderShippingAddress(Address orderShippingAddress) {
		this.orderShippingAddress = orderShippingAddress;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
}
