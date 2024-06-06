package app.utils;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerProvider {
    public static String path = System.getenv("tomcat_root") + "/logs/";
    public static Logger logger = null;
    public static Logger getLogger() {
        if(logger!=null){
            return logger;
        }
        try {
        	FileHandler fileHandler = new FileHandler(path + "/" + "app.log", true);
            Formatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger = Logger.getLogger("logger");
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }
}
