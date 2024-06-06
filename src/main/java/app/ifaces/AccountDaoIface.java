package app.ifaces;


import java.util.List;

import app.exceptions.Xception;
import app.models.Account;

public interface AccountDaoIface {
	public List<Account> getAccount(Account account) throws Xception;
	public void setAccount(Account account) throws Xception;
	public void execute() throws Xception;
	public Account addAccount(Account account) throws Xception;
}
