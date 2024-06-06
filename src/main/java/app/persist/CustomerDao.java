package app.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.exceptions.Xception;
import app.ifaces.CustomerDaoIface;
import app.ifaces.UserDaoIface;
import app.models.Customer;
import app.models.User;
import app.utils.LoggerProvider;
import app.utils.Tools;
import server.Local;

public class CustomerDao implements CustomerDaoIface {
    private SQLClient client;
    private UserDaoIface udao;
    private Logger logger = LoggerProvider.getLogger();

    public CustomerDao() throws Xception {
        try {
            client = SQLClient.getInstance();
            udao = new UserDao();
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
	public void execute() throws Xception {
        try {
            client.executeBatch();
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public List<Customer> getCustomer(int limit, int offset) throws Xception {
        try {
            List<Customer> customers = new ArrayList<>();
            List<Map<String, Object>> custs;
            String query = "select * from customerdata limit " + limit + " offset " + offset + ";";
            custs = client.executeQuery(query);
            for (Map<String, Object> cust : custs) {
                User user = new User();
                user.setUserId((Long) cust.get("customerid"));
                user = udao.getUser(user);
                Customer customer = new Customer(user);
                Tools.mapToPojo(cust, customer);
                customers.add(customer);
            }
            return customers;
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public Customer getCustomer(Customer customer) throws Xception {
        try {
            List<Map<String, Object>> records;
            String query;
            if (customer.getAadhar() != null && customer.getPan() != null) {
                query = "select * from customerdata where pan = '" + customer.getPan() + "' or aadhar = '" + customer.getAadhar() + "'";
                records = client.executeQuery(query);
            }
            customer = new Customer(udao.getUser(customer));
            if (customer.getUserId() != 0) {
                query = "SELECT * FROM customerdata where customerid=" + customer.getUserId();
                records = client.executeQuery(query);
            } else {
                return null;
            }
            if (records.isEmpty()) {
                return customer;
            }
            return (Customer) Tools.mapToPojo(records.get(0), customer);
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void setCustomer(Customer customer) throws Xception {
        try {
            udao.setUser(customer);
            StringBuilder query = new StringBuilder();
            query.append("update customerdata set lastmodifiedby=" + Local.getUser() + ", lastmodifiedtime=" + System.currentTimeMillis() + ", ");
            Map<String, Object> custMap = Tools.pojoToMap(customer, "");
            custMap.forEach((key, val) -> {
                if (val != null && !val.toString().equals("0")) {
                    query.append(key + "='" + val + "' ,");
                }
            });
            query.deleteCharAt(query.lastIndexOf(",")).append("where customerid=" + customer.getUserId()).append(";");
            query.append("insert into audit (operation,userid,timestamp) values('customer moddifcation CUID:" + customer.getUserId() + "'," + Local.getUser() + "," + System.currentTimeMillis() + ");");
            client.addToBatch(query.toString());
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
	public Customer addCustomer(Customer customer) throws Xception {
        try {
            User user = customer;
            user = udao.addUser(customer);
            customer.setUserId(user.getUserId());
            StringBuilder query = new StringBuilder();
            query.append("insert into customerdata (customerid,primaryaccountnumber,pan,aadhar,createdby,createdtime) values (");
            query.append("\'").append(customer.getUserId()).append("\',");
            query.append("\'").append(customer.getPrimaryAccountNumber()).append("\',");
            query.append("\'").append(customer.getPan()).append("\',");
            query.append("\'").append(customer.getAadhar()).append("\',");
            query.append("\'").append(Local.getUser()).append("\',");
            query.append("\'").append(System.currentTimeMillis()).append("\')");
            client.execute(query.toString());
            client.execute("insert into audit (operation,userid,timestamp) values('customer addition CUID:" + customer.getUserId() + "'," + Local.getUser() + "," + System.currentTimeMillis() + ");");
            return customer;
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public int getMpin(Long customerId) throws Xception {
        try {
            String query = "select mpin from mpindata where customerid=" + customerId;
            return (Integer) client.executeQuery(query).get(0).get("mpin");
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void setMpin(Long customerId, int mpin) throws Xception {
        try {
            String query = "update mpindata set mpin=" + mpin + " where customerid=" + customerId;
            client.execute(query);
            execute();
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }
}
