package app.ifaces;

import java.util.List;

import app.exceptions.Xception;
import app.models.Transaction;

public interface TransactionDaoIface {
	public List<Transaction> getTransaction(Long accountNumber,int page,int limit,Long minmilllis,Long maxmillis) throws Xception;
	public List<Transaction> getTransaction(String transactionId) throws Xception;
	public void setTransaction(Transaction transaction) throws Xception;
	public void execute() throws Xception;
	public Long getPages(Long number,Long minmillis,Long maxmillis) throws Xception;
}
