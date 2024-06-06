package app.enums;

public enum Org {
	CONTINENTAL("continental");
	Org(String value){
		this.value = value;
	}
	private String value;
	public String getValue() {
		return this.value;
	}
	public static Org getEnum(String value) {
		for(Org org : Org.values()) {
			if(org.getValue().equals(value)) {
				return org;
			}
		}
		return null;
	}
}
