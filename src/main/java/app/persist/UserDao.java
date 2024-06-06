package app.persist;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.exceptions.Xception;
import app.ifaces.UserDaoIface;
import app.models.Customer;
import app.models.Employee;
import app.models.User;
import app.utils.LoggerProvider;
import app.utils.Tools;
import server.Local;

public class UserDao implements UserDaoIface {
    private Logger logger = LoggerProvider.getLogger();
    private SQLClient client;

    public UserDao() throws Xception {
        try {
            client = SQLClient.getInstance();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error initializing MySQLClient", e);
            throw new Xception("Error initializing MySQLClient", e);
        }
    }

    @Override
	public void execute() throws Xception {
        try {
            client.executeBatch();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error executing batch", e);
            throw new Xception("Error executing batch", e);
        }
    }

    @Override
    public User getUser(User user) throws Xception {
        List<Map<String, Object>> records;
        String query;
        try {
            if (user.getUserId() != 0) {
                query = "SELECT * FROM userdata where userid=" + user.getUserId();
                records = client.executeQuery(query);
            } else if (user.getEmail() != null) {
                query = "SELECT * FROM userdata where email='" + user.getEmail() + "'";
                records = client.executeQuery(query);
            } else {
                return user;
            }
            if (records.isEmpty()) {
                return user;
            }
            return (User) Tools.mapToPojo(records.get(0), new User());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting user", e);
            throw new Xception("Error getting user", e);
        }
    }

    @Override
    public void setUser(User user) throws Xception {
        try {
            StringBuilder query = new StringBuilder();
            String depth = "";
            if (user instanceof Employee || user instanceof Customer) {
                depth = "parent";
            }
            query.append("update userdata set lastmodifiedby=").append(Local.getUser()).append(", lastmodifiedtime=")
                    .append(System.currentTimeMillis()).append(", ");
            Map<String, Object> userMap = Tools.pojoToMap(user, depth);
            userMap.forEach((key, val) -> {
                if (val != null) {
                    query.append(key).append("='").append(val).append("' ,");
                }
            });
            query.deleteCharAt(query.lastIndexOf(",")).append("where userid=").append(user.getUserId()).append(";");
            client.addToBatch(query.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error setting user", e);
            throw new Xception("Error setting user", e);
        }
    }

    @Override
	public User addUser(User user) throws Xception {
        try {
            StringBuilder query = new StringBuilder();
            query.append("insert into userdata (username,passhash,status,type,email,createdby,createdtime) values (");
            query.append("'").append(user.getUserName()).append("',");
            query.append("'").append(user.getPassHash()).append("',");
            query.append("'").append(user.getStatus()).append("',");
            query.append("'").append(user.getType()).append("',");
            query.append("'").append(user.getEmail()).append("',");
            query.append("'").append(Local.getUser()).append("',");
            query.append("'").append(System.currentTimeMillis()).append("');");
            client.execute(query.toString());
            user = getUser(user);
            return user;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding user", e);
            throw new Xception("Error adding user", e);
        }
    }
}
