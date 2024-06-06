//$Id$
package app.utils;
import java.io.FileReader;
import java.util.Properties;
///import java.util.Hashtable;

import app.exceptions.Xception;
public class PropLoader {
	private static Properties props;
	public static String getProp(String key) throws Xception{
		if(props!=null) {
			return props.getProperty(key);
		}
		else {
			try {
				FileReader reader = new FileReader(System.getenv("tomcat_root")+"/conf/Continental.conf");
				props = new Properties();
				props.load(reader);
				return props.getProperty(key);
			}
			catch(Exception e) {
				throw new Xception("property loading failed");
			}
			
		}
	}
}


