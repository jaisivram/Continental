package app.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.exceptions.Xception;
import app.ifaces.BranchDaoIface;
import app.models.Branch;
import app.utils.LoggerProvider;
import app.utils.Tools;
import server.Local;

public class BranchDao implements BranchDaoIface {
    private SQLClient client;
    private Logger logger = LoggerProvider.getLogger();

    public BranchDao() throws Xception {
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
    public List<Branch> getBranch(Branch branch) throws Xception {
        try {
            List<Branch> branches = new ArrayList<>();
            String query = new String();
            StringBuilder queryBuilder = new StringBuilder();
            if (branch.getBranchId() != null) {
                queryBuilder.append("SELECT * FROM branchdata WHERE branchid = '").append(branch.getBranchId())
                        .append("';");
                query = queryBuilder.toString();
            } else if (branch.getBranchName() != null) {
                queryBuilder.append("SELECT * FROM branchdata WHERE branchname = '").append(branch.getBranchName())
                        .append("';");
                query = queryBuilder.toString();
            }
            List<Map<String, Object>> records = client.executeQuery(query);

            // Populate Branch objects with fetched data
            for (Map<String, Object> record : records) {
                Branch fetchedBranch = new Branch();
                fetchedBranch.setBranchId((Integer) record.get("branchid"));
                fetchedBranch.setIfsc((String) record.get("ifsc"));
                fetchedBranch.setAddress((String) record.get("address"));
                fetchedBranch.setBranchName((String) record.get("branchname"));
                fetchedBranch.setPincode((Long) record.get("pincode"));
                // Fill other fields based on aggregate data
                fillAdditionalBranchData(fetchedBranch);

                branches.add(fetchedBranch);
            }

            return branches;
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    private void fillAdditionalBranchData(Branch branch) throws Xception {
        try {
            String query;
            StringBuilder queryBuilder = new StringBuilder();

            // Count number of accounts for this branch
            queryBuilder.append("SELECT COUNT(*) AS account_count FROM accountdata WHERE branchid = ")
                    .append(branch.getBranchId()).append(";");
            query = queryBuilder.toString();
            List<Map<String, Object>> accountCountRecords = client.executeQuery(query);
            long accountCount = (long) accountCountRecords.get(0).get("account_count");
            branch.setAccountCount(accountCount);

            // Count number of customers for this branch
            queryBuilder.setLength(0);
            queryBuilder
                    .append("SELECT COUNT(DISTINCT customerid) AS customer_count FROM accountdata WHERE branchid = ")
                    .append(branch.getBranchId()).append(";");
            query = queryBuilder.toString();
            List<Map<String, Object>> customerCountRecords = client.executeQuery(query);
            long customerCount = (long) customerCountRecords.get(0).get("customer_count");
            branch.setCustomerCount(customerCount);

            // Calculate branch worth by summing up balances of all accounts for this branch
            queryBuilder.setLength(0);
            queryBuilder.append("SELECT SUM(balance) as total_balance FROM accountdata WHERE branchid = ")
                    .append(branch.getBranchId()).append(";");
            query = queryBuilder.toString();
            List<Map<String, Object>> totalBalanceRecords = client.executeQuery(query);
            Double totalBalance = 0.0; // Default value

            if (!totalBalanceRecords.isEmpty()) {
                Object totalBalanceObj = totalBalanceRecords.get(0).get("total_balance");
                if (totalBalanceObj != null) {
                    totalBalance = (Double) totalBalanceObj;
                }
            }
            branch.setBranchWorth(totalBalance);
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public Branch addBranch(Branch branch) throws Xception {
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(
                    "INSERT INTO branchdata (ifsc, branchname, address, pincode,createdby,createdtime) VALUES (");
            queryBuilder.append("'").append(branch.getIfsc()).append("', ");
            queryBuilder.append("'").append(branch.getBranchName()).append("', ");
            queryBuilder.append("'").append(branch.getAddress()).append("', ");
            queryBuilder.append("'").append(branch.getPincode()).append("', ");
            queryBuilder.append("'").append(Local.getUser()).append("', ");
            queryBuilder.append("'").append(System.currentTimeMillis()).append("');");
            String query = queryBuilder.toString();
            client.addToBatch(query);
            execute();
            client.execute("insert into audit (operation,userid,timestamp) values('branch addition BRID:"
                    + branch.getBranchName() + "'," + Local.getUser() + "," + System.currentTimeMillis() + ");");
            branch = getBranch(branch).get(0);
            return branch;
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void setBranch(Branch branch) throws Xception {
        try {
            StringBuilder query = new StringBuilder();
            query.append("update branchdata set lastmodifiedby=" + Local.getUser() + ", lastmodifiedtime="
                    + System.currentTimeMillis() + ", ");
            Map<String, Object> branchMap = Tools.pojoToMap(branch, "");
            branchMap.forEach((key, val) -> {
                if (val != null && !val.toString().equals("0") && !val.toString().isEmpty()) {
                    query.append(key + "='" + val + "' ,");
                }
            });
            query.deleteCharAt(query.lastIndexOf(",")).append("WHERE branchid = ").append(branch.getBranchId())
                    .append(";");
            query.append("insert into audit (operation,userid,timestamp) values('branch modification BRID:"
                    + branch.getBranchId() + "'," + Local.getUser() + "," + System.currentTimeMillis() + ");");
            client.addToBatch(query.toString());
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void deleteBranch(Branch branch) throws Xception {
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("DELETE FROM branchdata WHERE branchid = ").append(branch.getBranchId()).append(";");
            queryBuilder.append("insert into audit (operation,userid) values('branch deletion BRID:"
                    + branch.getBranchId() + "'," + Local.getUser() + ");");
            String query = queryBuilder.toString();
            client.addToBatch(query);
            execute();
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal error", e);
            throw new Xception("Internal error");
        }
    }

    // Other methods for adding, updating, deleting branches go here
}
