package app.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.exceptions.Xception;
import app.ifaces.TransactionDaoIface;
import app.models.Transaction;
import app.utils.LoggerProvider;
import app.utils.Tools;
import server.Local;

public class TransactionDao implements TransactionDaoIface {
    private SQLClient client;
    private Logger logger = LoggerProvider.getLogger();

    public TransactionDao() throws Xception {
        try {
            client = SQLClient.getInstance();
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
    public Long getPages(Long number, Long minmillis, Long maxmillis) throws Xception {
        try {
            String mode = null;
            if (number - 917600000000000L > 0) {
                mode = "sourceaccountnumber";
            } else {
                mode = "customerid";
            }
            List<Map<String, Object>> records;
            String query;
            query = "SELECT count(*) FROM transactiondata where " + mode + "=" + number + " and timestamp < "
                    + maxmillis + " and timestamp > " + minmillis;
            records = client.executeQuery(query);
            return (Long) records.get(0).get("count(*)");
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public List<Transaction> getTransaction(Long number, int page, int limit, Long minmilli, Long maxmilli)
            throws Xception {
        try {
            if (page == 0) {
                page = 1;
            }
            String mode = null;
            if (number - 917600000000000L > 0) {
                mode = "sourceaccountnumber";
            } else {
                mode = "customerid";
            }
            List<Transaction> transactions = new ArrayList<>();
            List<Map<String, Object>> records;
            String query;
            query = "SELECT * FROM transactiondata where " + mode + "=" + number + " and timestamp < " + maxmilli
                    + " and timestamp > " + minmilli + " order by serialnumber desc " + " limit " + limit + " offset "
                    + (page - 1) * limit;
            records = client.executeQuery(query);
            for (Map<String, Object> record : records) {
                transactions.add((Transaction) Tools.mapToPojo(record, new Transaction()));
            }
            return transactions;
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
	public List<Transaction> getTransaction(String transactionId) throws Xception {
        try {
            List<Transaction> transactions = new ArrayList<>();
            List<Map<String, Object>> records;
            String query;
            query = "SELECT * FROM transactiondata where transactionid = '" + transactionId + "';";
            System.out.println(query);
            records = client.executeQuery(query);
            for (Map<String, Object> record : records) {
                transactions.add((Transaction) Tools.mapToPojo(record, new Transaction()));
            }
            return transactions;
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void setTransaction(Transaction transaction) throws Xception {
        try {
            StringBuilder query = new StringBuilder();
            query.append(
                    "insert into transactiondata (transactionid,transactiontype,timestamp,customerid,sourceaccountnumber,transactionaccountnumber,scope,credit,debit,balance,createdby,createdtime) values (");
            query.append("\'").append(transaction.getTransactionId()).append("\',");
            query.append("\'").append(transaction.getTransactionType()).append("\',");
            query.append("\'").append(transaction.getTimeStamp()).append("\',");
            query.append("\'").append(transaction.getCustomerId()).append("\',");
            query.append("\'").append(transaction.getSourceAccountNumber()).append("\',");
            query.append("\'").append(transaction.getTransactionAccountNumber()).append("\',");
            query.append("\'").append(transaction.getScope()).append("\',");
            query.append("\'").append(transaction.getCredit()).append("\',");
            query.append("\'").append(transaction.getDebit()).append("\',");
            query.append("\'").append(transaction.getBalance()).append("\',");
            query.append("\'").append(Local.getUser()).append("\',");
            query.append("\'").append(System.currentTimeMillis()).append("\',");
            query.deleteCharAt(query.lastIndexOf(",")).append(");");
            query.append(("insert into audit (operation,userid,timestamp) values('made transaction TID:"
                    + transaction.getTransactionId() + "'," + Local.getUser() + "," + System.currentTimeMillis()
                    + ");"));
            client.addToBatch(query.toString());
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }
}
