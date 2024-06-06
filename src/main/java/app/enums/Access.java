package app.enums;

public enum Access {
	ADMIN("admin"),
	EMPLOYEE("employee");
	private String value;
	Access(String value){
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
	public static Access getEnum(String value) {
		for(Access access : Access.values()) {
			if(access.getValue().equals(value)) {
				return access;
			}
		}
		return null;
	}
}
