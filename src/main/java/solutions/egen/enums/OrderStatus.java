package solutions.egen.enums;

public enum OrderStatus {
	CREATED("created"), PENDING("pending"), SHIPPED("shipped"), DELIVERED("delivered"), CANCELLED("cancelled"),
	READY("ready for pickup");

	private String desc;

	public String getDesc() {
		return desc;
	}

	OrderStatus(String desc) {
		this.desc = desc;
	}
}
