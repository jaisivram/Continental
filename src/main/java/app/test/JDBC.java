//$Id$
package app.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class JDBC {
	public static void main(String... args) {
	    try {
	        Class.forName("org.postgresql.Driver"); 
	        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Continental","postgres","panama");
	        Statement state = con.createStatement();
	        ResultSet res = state.executeQuery("select * from userdata");
	        while(res.next()) {
	        	int columnLen = res.getMetaData().getColumnCount();
	        	for(int i=1;i<=columnLen;i++) {
	        		System.out.println(res.getString(i));
	        	}
	        }
	        res.close();
	        state.close();
	        con.close();
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
}
