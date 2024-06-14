package app.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.enums.GenEnum;
import app.exceptions.Xception;
import app.utils.LoggerProvider;
import app.utils.PropLoader;
public class SQLClient {
	public Long userId;
	private static Logger logger = LoggerProvider.getLogger();
    private static final ThreadLocal<SQLClient> threadLocalInstance = new ThreadLocal<SQLClient>();
    private List<String> batch;
    private String db = PropLoader.getProp("db");
    private String url = PropLoader.getProp(db+"_url");
    private String user = PropLoader.getProp(db+"_user");
    private String password = PropLoader.getProp(db+"_password");
    private SQLClient() throws Xception {
        try {
            Class.forName(PropLoader.getProp(db+"_driver_class")); //com.mysql.cj.jdbc.Driver
        }
        catch(Exception e) {
        	logger.log(Level.SEVERE,e.getMessage(),e);
        	throw new Xception("mysql error");
        }
    }

    public static void destroyClient() {
        logger.info(" msg ::: " + (threadLocalInstance.get()==null));
        logger.info("destroy client called null..");
        threadLocalInstance.set(null);
    }

    public static SQLClient getInstance() throws Xception {
        try {
            logger.info("getInstance is called...");
            SQLClient sqlClient = threadLocalInstance.get();
            if (sqlClient == null) {
                logger.info("new local sqlclient created");
                sqlClient = new SQLClient();
                threadLocalInstance.set(sqlClient);
            } else {
                logger.info("sqlclient created already available");
            }
            return sqlClient;
        }
        catch(RuntimeException e) {
        	logger.log(Level.SEVERE,e.getMessage(),e);
        	throw new Xception("mysql error");
        }
    }

    public List<Map<String,Object>> executeQuery(String query) throws Xception { 
        List<Map<String,Object>> records = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url,user,password)){
            try (Statement state = con.createStatement()){
                if(db.equals("pg")){
                    String schema = PropLoader.getProp("schema");
                    state.execute("SET search_path to "+schema+";");
                }
                try (ResultSet rs = state.executeQuery(query)){
                    int columnLen = rs.getMetaData().getColumnCount();
                    while(rs.next()) {
                        Map<String,Object> record = new HashMap<>();
                        for(int i=1;i<=columnLen;i++) {
                            String column = rs.getMetaData().getColumnName(i);
                            String type = rs.getMetaData().getColumnTypeName(i);
                            GenEnum g = GenEnum.getType(type, db);
                            switch(g) {
                            case DB_TYPE_TEXT:
                                String str = rs.getString(i);
                                record.put(column, str);
                                break;
                            case DB_TYPE_LONG:
                                Long lng = rs.getLong(i);
                                record.put(column, lng);
                                break;
                            case DB_TYPE_INT:
                                Integer intg = rs.getInt(i);
                                record.put(column, intg);
                                break;
                            case DB_TYPE_DOUBLE:
                                String dblStr = rs.getString(i);
                                Double dbl = 0.0;
                                if(dblStr != null){
                                    dbl = Double.parseDouble(dblStr);
                                }
                                record.put(column, dbl);
                                break;
                            }
                        }
                        records.add(record);
                    }
                }
            }
        } catch(SQLException e) {
        	logger.log(Level.SEVERE,e.getMessage(),e);
        	e.printStackTrace();
        	throw new Xception("internal error");
        } catch(Exception e){
            logger.log(Level.SEVERE,e.getMessage(),e);
            e.printStackTrace();
            throw new Xception("internal error");
        }
        return records;
    }

    public void execute(String query) throws Xception {
        try (Connection con = DriverManager.getConnection(url,user,password);
             Statement state = con.createStatement()) {
            if(db.equals("pg")){
                String schema = PropLoader.getProp("schema");
                state.execute("SET search_path to "+schema+";");
            }
            con.setAutoCommit(false);
            for(String qrr : query.split(";")) {
                state.executeUpdate(qrr);
            }
            con.commit();
        } catch (SQLException e) {
        	e.printStackTrace();
        	batch=new ArrayList<>();
        	if(e.getSQLState().startsWith("23")) {
        		throw new Xception("duplicate entry");
        	}
        	logger.log(Level.SEVERE,e.getMessage(),e);
        	throw new Xception("mysql error");
        }
    }

    public void executeBatch() throws Xception {
        StringBuilder qBuild = new StringBuilder();
        for(String query : batch) {
            qBuild.append(query);
        }
        execute(qBuild.toString());
        batch = new ArrayList<>();
    }

    public void addToBatch(String query) throws Xception {
        if(batch==null) {
            batch=new ArrayList<>();
        }
        batch.add(query);
    }
}
