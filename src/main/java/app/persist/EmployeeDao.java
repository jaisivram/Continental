package app.persist;


import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.exceptions.Xception;
import app.ifaces.EmployeeDaoIface;
import app.ifaces.UserDaoIface;
import app.models.Employee;
import app.models.User;
import app.utils.LoggerProvider;
import app.utils.Tools;
import server.Local;

public class EmployeeDao implements EmployeeDaoIface {

    private SQLClient client;
    private UserDaoIface udao; // UserDao instance
	private Logger logger = LoggerProvider.getLogger();
    public EmployeeDao() throws Xception {
        try {
            client = SQLClient.getInstance();
            udao = new UserDao(); // Initialize UserDao instance
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
	public Employee getEmployee(Employee employee) throws Xception {
		try {
            List<Map<String,Object>> records = null;
            String query;
            if(!employee.getAadhar().equals(0L)) {
                query = "select * from employeedata where aadhar = '"+employee.getAadhar()+"';";
                records = client.executeQuery(query);
            }
            else if(employee.getUserId()!=0) {
                query = "SELECT * FROM employeedata where employeeid="+employee.getUserId();
                records = client.executeQuery(query);
            }
            else if(employee.getEmail()!=null) {
                employee = new Employee(udao.getUser(employee));
                return getEmployee(employee);
            }
            if(records.isEmpty()) {
                return employee;
            }
            return (Employee) Tools.mapToPojo(records.get(0), employee);
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
	}
	@Override
	public void setEmployee(Employee employee) throws Xception {
		try {
            udao.setUser(employee);
            StringBuilder query = new StringBuilder();
            if(employee.getUserId()>0) {
                query.append("update employeedata set lastmodifiedby="+Local.getUser()+", lastmodifiedtime="+System.currentTimeMillis()+", ");
                Map<String,Object> empMap = Tools.pojoToMap(employee,"");
                empMap.forEach((key,val)->{
                    if(val!=null) {
                        query.append(key+"='"+val+"',");
                    }
                });
                query.deleteCharAt(query.lastIndexOf(",")).append(" where employeeid="+employee.getUserId()).append(";");
                query.append(("insert into audit (operation,userid,timestamp) values('employee modification EUID:"+employee.getUserId()+"',"+Local.getUser()+","+System.currentTimeMillis()+");"));
            }
            client.addToBatch(query.toString());
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
	}
	@Override
	public Employee addEmployee(Employee employee) throws Xception {
		try {
            User user = employee;
            user = udao.addUser(employee);
            employee.setUserId(user.getUserId());
            StringBuilder query = new StringBuilder();
            query.append("insert into employeedata (employeeid,role,branchid,aadhar,createdby,createdtime) values (");
            query.append("\'").append(employee.getUserId()).append("\',");
            query.append("\'").append(employee.getRole()).append("\',");
            query.append("\'").append(employee.getBranchId()).append("\',");
            query.append("\'").append(employee.getAadhar()).append("\',");
            query.append("\'").append(Local.getUser()).append("\',");
            query.append("\'").append(System.currentTimeMillis()).append("\')");
            client.addToBatch(query.toString());
            execute();
            client.execute("insert into audit (operation,userid,timestamp) values('employee addition EUID:"+employee.getUserId()+"',"+Local.getUser()+","+System.currentTimeMillis()+");");
            return employee;
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
	}
}
