/*package solutions.egen.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import lombok.Data;

@Entity
@Data
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long paymentConfirmationId;

	@NonNull
	private Long orderId;

	@NonNull
	private Long billingAddressId;

	@NonNull
	private String paymentMethod;

	@NonNull
	private Double paymentAmount;

	@ManyToOne
	@JoinColumn(name = "billingAddressId", nullable = false, insertable = false, updatable = false)
	private Address billingAddress;
	
	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false)
	private Orders orderEntity;

	@NonNull
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_payment_date", nullable = false)
	private Date order_payment_date;
}
*/

package solutions.egen.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderPaymentConfirmationNumber;

	@NonNull
	private String orderPaymentMethod;

	@NonNull
	private Double orderPaymentAmount;

	@NonNull
	private Date orderPaymentDate;

	private String cardNumber;
	
	@JsonIgnore
	private int cvv;

	private int expiryMonth;

	private int expiryYear;

	private String nameOnCard;

	@NonNull
	@OneToOne(targetEntity = Address.class, cascade = CascadeType.MERGE)
	private Address orderBillingAddress;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER,  cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "order_id")
	private Orders order;

	public Payment() {

	}

	public Payment(String orderPaymentMethod, Double orderPaymentAmount, Date orderPaymentDate, String cardNumber,
			int cvv, int expiryMonth, int expiryYear, String nameOnCard, Address orderBillingAddress, Orders order) {
		this.orderPaymentMethod = orderPaymentMethod;
		this.orderPaymentAmount = orderPaymentAmount;
		this.orderPaymentDate = orderPaymentDate;
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.nameOnCard = nameOnCard;
		this.orderBillingAddress = orderBillingAddress;
		this.order = order;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public Long getOrderPaymentConfirmationNumber() {
		return orderPaymentConfirmationNumber;
	}

	public void setOrderPaymentConfirmationNumber(Long orderPaymentConfirmationNumber) {
		this.orderPaymentConfirmationNumber = orderPaymentConfirmationNumber;
	}

	public String getOrderPaymentMethod() {
		return orderPaymentMethod;
	}

	public void setOrderPaymentMethod(String orderPaymentMethod) {
		this.orderPaymentMethod = orderPaymentMethod;
	}

	public Double getOrderPaymentAmount() {
		return orderPaymentAmount;
	}

	public void setOrderPaymentAmount(Double orderPaymentAmount) {
		this.orderPaymentAmount = orderPaymentAmount;
	}

	public Date getOrderPaymentDate() {
		return orderPaymentDate;
	}

	public void setOrderPaymentDate(Date orderPaymentDate) {
		this.orderPaymentDate = orderPaymentDate;
	}

	public Address getOrderBillingAddress() {
		return orderBillingAddress;
	}

	public void setOrderBillingAddress(Address orderBillingAddress) {
		this.orderBillingAddress = orderBillingAddress;
	}
}
