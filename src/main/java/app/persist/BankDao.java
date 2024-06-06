package app.persist;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.exceptions.Xception;
import app.ifaces.BankDaoIface;
import app.models.Bank;
import app.utils.LoggerProvider;

public class BankDao implements BankDaoIface {
    private SQLClient client;
    private Logger logger = LoggerProvider.getLogger();
    public BankDao() throws Xception {
        client = SQLClient.getInstance();
    }

    @Override
    public Bank getBank() throws Xception {
        try {
            Bank bank = new Bank();
            String query = "select count(accountnumber) as count from accountdata";
            Map<String,Object> record = client.executeQuery(query).get(0);
            bank.setAccountCount((Long)record.get("count"));
            query = "select count(customerid) as count from customerdata";
            record = client.executeQuery(query).get(0);
            bank.setCustomerCount((Long)record.get("count"));
            query = "select count(employeeid) as count from employeedata";
            record = client.executeQuery(query).get(0);
            bank.setEmployeeCount((Long)record.get("count"));
            query = "select count(branchid) as count from branchdata";
            record = client.executeQuery(query).get(0);
            bank.setBranchCount((Long)record.get("count"));
            query = "select sum(balance) as sum from accountdata";
            record = client.executeQuery(query).get(0);
            bank.setBankWorth((Double)record.get("sum"));
            return bank;
        }
        catch (Xception e){
            throw e;
        }
        catch (Exception e){
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("internal error");
        }
    }

}
