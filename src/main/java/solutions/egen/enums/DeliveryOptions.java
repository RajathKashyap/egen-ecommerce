package solutions.egen.enums;

public enum DeliveryOptions {
	HOMEDELIVERY("home delivery"), INSTOREPICKUP("in-store pickup"), CURBSIDEDELIVERY(
			"curbside delivery"), THIRDPARTYDELIVERY("3rd party delivery");

	private String desc;

	public String getDesc() {
		return desc;
	}

	private DeliveryOptions(String desc) {
		this.desc = desc;
	}

}
