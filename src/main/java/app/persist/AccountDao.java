package app.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.exceptions.Xception;
import app.ifaces.AccountDaoIface;
import app.models.Account;
import app.utils.LoggerProvider;
import app.utils.Tools;
import server.Cache;
import server.Local;

public class AccountDao implements AccountDaoIface {
	private Cache cache = Cache.getInstance();
    private SQLClient client;
    private Logger logger = LoggerProvider.getLogger();

    public AccountDao() throws Xception {
        try {
            client = SQLClient.getInstance();
        } catch (Xception e) {
            throw e; 
        } catch (Exception e) {
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
            throw new Xception("Internal error"); 
        }
    }

    @Override
    public List<Account> getAccount(Account account) throws Xception {
        List<Account> accounts = new ArrayList<>();
        Account acc = null;
        List<Map<String, Object>> records;
        String query;
        try {
            if (account.getAccountNumber() != null) {
                acc = cache.getAccount(account.getAccountNumber());
                if(acc!=null){
                    accounts.add(acc);
                    return accounts;
                }
                query = "SELECT * FROM accountdata where accountnumber=" + account.getAccountNumber();
                records = client.executeQuery(query);
                if (records.isEmpty()) {
                    return accounts;
                }
                for (Map<String, Object> record : records) {
                    account = (Account) Tools.mapToPojo(record, new Account());
                    accounts.add(account);
                    cache.putAccount(account);
                }
                return accounts;
            } else if (account.getCustomerId() != null && (account.getCreationTime() == null)) {
                query = "SELECT accountnumber FROM accountdata where customerId=" + account.getCustomerId();
                records = client.executeQuery(query);
                if (records.isEmpty()) {
                    return accounts;
                }
                for (Map<String, Object> record : records) {
                    Long accountNumber = (Long) record.get("accountnumber");
                    acc = new Account();
                    acc.setAccountNumber(accountNumber);
                    acc = getAccount(acc).get(0);
                    accounts.add(acc);
                }
                return accounts;
            } else if (account.getCustomerId() != null && account.getCreationTime() != null) {
                query = "SELECT accountnumber FROM accountdata where customerid=" + account.getCustomerId() + " and creationtime = '" + account.getCreationTime() + "';";
                records = client.executeQuery(query);
                if (records.isEmpty()) {
                    return null;
                }
                for (Map<String, Object> record : records) {
                    Long accountNumber = (Long) record.get("accountnumber");
                    acc = new Account();
                    acc.setAccountNumber(accountNumber);
                    acc = getAccount(acc).get(0);
                    accounts.add(acc);
                }
                return accounts;
            } else if (account.getIfsc() != null) {
                query = "SELECT accountnumber FROM accountdata where ifsc='" + account.getIfsc() + "'";
                records = client.executeQuery(query);
                if (records.isEmpty()) {
                    return null;
                }
                for (Map<String, Object> record : records) {
                    Long accountNumber = (Long) record.get("accountnumber");
                    acc = new Account();
                    acc.setAccountNumber(accountNumber);
                    acc = getAccount(acc).get(0);
                    accounts.add(acc);
                }
                return accounts;
            } else if (account.getBranchId() != null) {
                query = "SELECT accountnumber FROM accountdata where branchid='" + account.getBranchId() + "'";
                records = client.executeQuery(query);
                if (records.isEmpty()) {
                    return null;
                }
                for (Map<String, Object> record : records) {
                    Long accountNumber = (Long) record.get("accountnumber");
                    acc = new Account();
                    acc.setAccountNumber(accountNumber);
                    acc = getAccount(acc).get(0);
                    accounts.add(acc);
                }
                return accounts;
            } else {
                return null;
            }
        } catch (Xception e) {
            throw e; 
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error"); // Log the exception
            throw new Xception("internal error"); 
        }
    }

    @Override
    public void setAccount(Account account) throws Xception {
        try {
            cache.putAccount(account);
            StringBuilder query = new StringBuilder();
            if (account.getAccountNumber() != null) {
                query.append("update accountdata set lastmodifiedby=" + Local.getUser() + ", lastmodifiedtime=" + System.currentTimeMillis() + ",");
                Map<String, Object> accMap = Tools.pojoToMap(account, "");
                accMap.forEach((key, val) -> {
                    if (val != null && !key.equals("accountnumber")) {
                        query.append(key + "='" + val + "' ,");
                    }
                });
                query.deleteCharAt(query.lastIndexOf(",")).append("where accountnumber=" + account.getAccountNumber()).append(";");
                query.append("insert into audit (operation,userid,timestamp) values('account modification ACC:" + account.getAccountNumber() + "'," + Local.getUser() + "," + System.currentTimeMillis() + ");");
                client.addToBatch(query.toString());
            }
        } catch (Xception e) {
            throw e; 
        } catch (Exception e) {
            throw new Xception("Internal error"); 
        }
    }

    @Override
	public Account addAccount(Account account) throws Xception {
        try {
            StringBuilder query = new StringBuilder();
            query.append("insert into accountdata (ifsc,balance,branchid,customerid,status,creationtime,createdby) values (");
            query.append("\'").append(account.getIfsc()).append("\',");
            query.append(account.getBalance()).append(",");
            query.append(account.getBranchId()).append(",");
            query.append(account.getCustomerId()).append(",");
            query.append("\'").append(account.getStatus()).append("\',");
            query.append("\'").append(account.getCreationTime()).append("\',");
            query.append("\'").append(Local.getUser()).append("\');");
            client.addToBatch(query.toString());
            execute();
            account = getAccount(account).get(0);
            query = new StringBuilder();
            query.append("insert into audit (operation,userid,timestamp) values('account addition | ACC:" + account.getAccountNumber() + "'," + Local.getUser() + "," + account.getCreationTime() + ");");
            client.execute(query.toString());
            return account;
        } catch (Xception e) {
            throw e; 
        } catch (Exception e) {
            throw new Xception("Internal error"); 
        }
    }
}
