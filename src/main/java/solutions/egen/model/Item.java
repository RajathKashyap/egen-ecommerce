package solutions.egen.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "items")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long itemId;

	@NonNull
	private String itemName;

	@NonNull
	private Double itemPrice;

	@JsonIgnore
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems;

	
	
	public Item() {
		super();
	}

	public Item(Long itemId, String itemName, Double itemPrice) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}

	public Long getOrderItemId() {
		return itemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.itemId = orderItemId;
	}

	public String getOrderItemName() {
		return itemName;
	}

	public void setOrderItemName(String orderItemName) {
		this.itemName = orderItemName;
	}

	public Double getOrderItemPrice() {
		return itemPrice;
	}

	public void setOrderItemPrice(Double orderItemPrice) {
		this.itemPrice = orderItemPrice;
	}

}
