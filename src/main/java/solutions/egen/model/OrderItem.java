package solutions.egen.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Order_item")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderedItemId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders order;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = false)
	@JoinColumn(name = "item_id")
	private Item item;

	@NonNull
	private Integer orderItemQuantity;

	public OrderItem() {
	}

	public OrderItem(Orders order, Item item, Integer orderItemQuantity) {
		super();
		this.order = order;
		this.item = item;
		this.orderItemQuantity = orderItemQuantity;
	}

	public Long getOrdereditemId() {
		return orderedItemId;
	}

	public void setOrdereditemId(Long ordereditemId) {
		orderedItemId = ordereditemId;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getOrderItemQuantity() {
		return orderItemQuantity;
	}

	public void setOrderItemQuantity(Integer orderItemQuantity) {
		this.orderItemQuantity = orderItemQuantity;
	}

}
