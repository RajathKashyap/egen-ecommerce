package solutions.egen.mapper;

public class ItemDetails {

	private Long itemID;

	private Integer orderItemQuantity;

	public ItemDetails() {
		super();
	}

	public ItemDetails(Long itemID, Integer orderItemQuantity) {
		super();
		this.itemID = itemID;
		this.orderItemQuantity = orderItemQuantity;
	}

	public Long getItemID() {
		return itemID;
	}

	public void setItemID(Long itemID) {
		this.itemID = itemID;
	}

	public Integer getOrderItemQuantity() {
		return orderItemQuantity;
	}

	public void setOrderItemQuantity(Integer orderItemQuantity) {
		this.orderItemQuantity = orderItemQuantity;
	}

}
