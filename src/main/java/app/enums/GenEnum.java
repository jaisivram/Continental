//$Id$
package app.enums;

public enum GenEnum {
	DB_TYPE_TEXT("TEXT","VARCHAR"),
	DB_TYPE_INT("INTEGER","INT"),
	DB_TYPE_LONG("BIGSERIAL","BIGINT"),
	DB_TYPE_DOUBLE("NUMERIC","DECIMAL");
	private String pg;
	private String mysql;
	private GenEnum(String pg,String mysql) {
		this.pg = pg;
		this.mysql = mysql;
	}
	public String getDbType(String db) {
		switch(db) {
		case "pg":
			return this.pg;
		case "mysql":
			return this.mysql;
		default:
			return null;
		}	
	}
	
	public static GenEnum getType(String type, String db) {
		if(type.equalsIgnoreCase("int8")) {
			return GenEnum.DB_TYPE_LONG;
		}
		for (GenEnum g : GenEnum.values()) {
			if(g.getDbType(db).equalsIgnoreCase(type)) {
				return g;
			}
		}
		return null;
	}
}
