package app.enums;

public enum Validity {
	VALID("valid"),
	EXPIRED("expired"),
	BLOCKED("blocked");
	private String value;
	Validity(String value){
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
	public static Validity getEnum(String value) {
		for(Validity validity : Validity.values()) {
			if(validity.getValue().equals(value)) {
				return validity;
			}
		}
		return null;
	}
}
