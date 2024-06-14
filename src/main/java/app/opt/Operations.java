package app.opt;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.exceptions.UIException;
import app.exceptions.Xception;
import app.ifaces.AccountDaoIface;
import app.ifaces.BankDaoIface;
import app.ifaces.BranchDaoIface;
import app.ifaces.CustomerDaoIface;
import app.ifaces.EmployeeDaoIface;
import app.ifaces.TransactionDaoIface;
import app.ifaces.UserDaoIface;
import app.models.Account;
import app.models.Bank;
import app.models.Branch;
import app.models.Customer;
import app.models.Employee;
import app.models.Transaction;
import app.models.User;
import app.persist.AccountDao;
import app.persist.BankDao;
import app.persist.BranchDao;
import app.persist.CustomerDao;
import app.persist.EmployeeDao;
import app.persist.TransactionDao;
import app.persist.UserDao;
import app.utils.LoggerProvider;
import app.utils.Tools;

public class Operations {
	private Logger logger = LoggerProvider.getLogger();
	public AccountDaoIface adao;
	public UserDaoIface udao;
	public CustomerDaoIface cdao;
	public EmployeeDaoIface edao;
	public TransactionDaoIface tdao;
	public BranchDaoIface brdao;
	public BankDaoIface bdao;
	public boolean state = true;

	public Operations() {
		try {
			brdao = new BranchDao();
			adao = new AccountDao();
			udao = new UserDao();
			cdao = new CustomerDao();
			edao = new EmployeeDao();
			tdao = new TransactionDao();
			bdao = new BankDao();
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public User getUser(Long userId) throws UIException {
		User user = new User();
		user.setUserId(userId);
		try {
			user = udao.getUser(user);
			if (user.getUserName() == null) {
				throw new UIException("user not found");
			}
			return user;
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public boolean checkUserStatus(Long userId) throws UIException {
		User user = new User();
		user.setUserId(userId);
		user = getUser(userId);
		if (user.getStatus().equals("active")) {
			return true;
		} else {
			return false;
		}
	}

	public User login(Long userId, String password) throws UIException {
		User user = new User();
		user.setUserId(userId);
		try {
			String passHash = Tools.hasher(password);
			user = udao.getUser(user);
			if (user.getUserName() == null) {
				throw new UIException("user not found");
			}
			if (user.getPassHash().equals(passHash)) {
				if (!user.getStatus().equals("active")) {
					throw new UIException("user inactive");
				} else {
					return user;
				}
			} else {
				throw new UIException("invalid password");
			}
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public Long getPrimaryAccountNumber(Long customerId) throws UIException {
		Customer customer = new Customer();
		customer.setUserId(customerId);
		try {
			customer = cdao.getCustomer(customer);
			if (customer.getPan() == null) {
				throw new UIException("customer not found");
			} else {
				return customer.getPrimaryAccountNumber();
			}
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public Account getAccount(Long accountNumber) throws UIException {
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		try {
			List<Account> accounts = adao.getAccount(account);
			account = accounts.isEmpty() ? null : accounts.get(0);
			if (account == null) {
				throw new UIException("account not found");
			} else {
				return account;
			}
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public List<Account> getAccounts(Long customerId) throws UIException {
		Account account = new Account();
		account.setCustomerId(customerId);
		List<Account> accounts;
		try {
			accounts = adao.getAccount(account);
			if (accounts.isEmpty()) {
				throw new UIException("account not found");
			} else {
				return accounts;
			}
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public List<Long> getAccountNumbers(Long customerId) throws UIException {
		List<Account> accounts = getAccounts(customerId);
		List<Long> nums = new ArrayList<>();
		for (Account account : accounts) {
			nums.add(account.getAccountNumber());
		}
		return nums;

	}

	public Long getCustomerId(Long accountNumber) throws UIException {
		Account account = getAccount(accountNumber);
		return account.getCustomerId();
	}

	public boolean setPrimary(Long customerId, Long accountNumber) throws UIException {
		try {
			System.out.println(customerId);
			Customer customer = new Customer();
			customer.setUserId(customerId);
			customer.setPrimaryAccountNumber(accountNumber);
			cdao.setCustomer(customer);
			cdao.execute();
			return true;
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public void makeTransaction(Transaction transaction) throws UIException {
		synchronized(transaction.getSourceAccountNumber()){
			Account sourceAccount = new Account();
			Long sourceAccountNumber = transaction.getSourceAccountNumber();
			Long transactionAccountNumber = transaction.getTransactionAccountNumber();
			if (sourceAccountNumber == transactionAccountNumber) {
				throw new UIException("cant transfer to same account");
			}
			sourceAccount = getAccount(sourceAccountNumber);
			switch (transaction.getTransactionType()) {
				case "deposit":
					sourceAccount = credit(sourceAccount, transaction.getCredit());
					transaction.setTransactionAccountNumber(0L);
					transaction.setBalance(sourceAccount.getBalance());
					try {
						adao.setAccount(sourceAccount);
						tdao.setTransaction(transaction);
						tdao.execute();
					} catch (Xception e) {
						logger.log(Level.SEVERE, e.getMessage(), e);
						throw new UIException("internal error");
					}
					break;
				case "withdraw":
					sourceAccount = debit(sourceAccount, transaction.getDebit());
					transaction.setBalance(sourceAccount.getBalance());
					transaction.setTransactionAccountNumber(0L);
					try {
						adao.setAccount(sourceAccount);
						tdao.setTransaction(transaction);
						tdao.execute();
					} catch (Xception e) {
						logger.log(Level.SEVERE, e.getMessage(), e);
						throw new UIException("internal error");
					}
					break;
				case "transfer":
					sourceAccount = getAccount(sourceAccountNumber);
					sourceAccount = debit(sourceAccount, transaction.getDebit());
					transaction.setBalance(sourceAccount.getBalance());
					try {
						adao.setAccount(sourceAccount);
						tdao.setTransaction(transaction);
					} catch (Xception e) {
						logger.log(Level.SEVERE, e.getMessage(), e);
						throw new UIException("sender side error");
					}
					Account transactionAccount = new Account();
					Transaction nextTrans = new Transaction();
					if (transaction.getScope().equals("internal")) {
						if (sourceAccountNumber.equals(transactionAccountNumber)) {
							throw new UIException("cant transfer to same account");
						}
						transactionAccount = getAccount(transactionAccountNumber);
						nextTrans.setSourceAccountNumber(transactionAccountNumber);
						nextTrans.setCustomerId(transactionAccount.getCustomerId());
						nextTrans.setTransactionId(transaction.getTransactionId());
						nextTrans.setCredit(transaction.getDebit());
						nextTrans.setTimeStamp(transaction.getTimeStamp());
						nextTrans.setTransactionAccountNumber(transaction.getSourceAccountNumber());
						nextTrans.setTransactionType("transfer");
						try {
							transactionAccount = credit(transactionAccount, nextTrans.getCredit());
						} catch (UIException e) {
							if (e.getMessage().equals("inactive account")) {
								throw new UIException("recipient account inactive");
							}
						}
						nextTrans.setBalance(transactionAccount.getBalance());
						try {
							adao.setAccount(transactionAccount);
							tdao.setTransaction(nextTrans);
						} catch (Xception e) {
							logger.log(Level.SEVERE, e.getMessage(), e);
							throw new UIException("reciever side error");
						}
					}
					try {
						tdao.execute();
					} catch (Xception e) {
						logger.log(Level.SEVERE, e.getMessage(), e);
						throw new UIException("internal error");
					}
					break;
			}
		}
	}

	private Account debit(Account account, Long amount) throws UIException {
		if (account.getStatus().equals("inactive")) {
			throw new UIException("inactive account");
		} else if (account.getBalance() >= amount) {
			account.setBalance(account.getBalance() - amount);
			return account;
		}
		else {
			throw new UIException("insufficient balance");
		}
	}

	private Account credit(Account account, Long amount) throws UIException {
		if (account.getStatus().equals("inactive")) {
			throw new UIException("inactive account");
		}
		account.setBalance(account.getBalance() + amount);
		return account;
	}

	public List<Transaction> getTransactions(Long number, int page, int limit, Long minMillis, Long maxMillis)
			throws UIException {
		List<Transaction> trans;
		try {
			trans = tdao.getTransaction(number, page, limit, minMillis, maxMillis);
			return trans;
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public List<Transaction> getTransaction(String transactionId) throws UIException {
		try {
			List<Transaction> transactions = tdao.getTransaction(transactionId);
			if (transactions.isEmpty()) {
				throw new UIException("no transaction found");
			} else {
				return transactions;
			}
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public Long getTransactionPages(Long number, Long minMillis, Long maxMillis) throws UIException {
		try {
			Long x = tdao.getPages(number, minMillis, maxMillis);
			return x;
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public Customer getCustomer(Long customerId) throws UIException {
		User user = new User();
		user.setUserId(customerId);
		try {
			user = udao.getUser(user);
			if (user.getUserName() == null) {
				throw new UIException("user not found");
			}
			Customer customer = new Customer(user);
			customer = cdao.getCustomer(customer);
			if (customer.getAadhar() == null) {
				throw new UIException("customer not found!");
			}
			return customer;
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public List<Customer> getCustomers(int limit, int offset) throws Xception {
		return cdao.getCustomer(limit, offset);
	}

	public void changePassword(Long userId, String password) throws UIException {
		User user = new User();
		user.setUserId(userId);
		user.setPassHash(Tools.hasher(password));
		try {
			udao.setUser(user);
			udao.execute();
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public Employee getEmployee(Long userId) throws UIException {
		User user = getUser(userId);
		Employee employee = new Employee(user);
		try {
			employee = edao.getEmployee(employee);
			if (employee.getRole() == null) {
				throw new UIException("employee not found");
			}
			return employee;
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public void updateUserEmail(Long userId, String email) throws UIException {
		boolean isValidEmail = email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
		if (!isValidEmail) {
			throw new UIException("invalid email");
		}
		User user = new User();
		user.setUserId(userId);
		user.setEmail(email);
		try {
			udao.setUser(user);
			udao.execute();
		} catch (Xception e) {
			throw new UIException(e.getMessage());
		}
	}

	public Employee addEmployee(Employee employee) throws UIException {
		Employee emp;
		try {
			emp = edao.getEmployee(employee);
			if (!emp.getUserId().equals(0L)) {
				throw new UIException("employee exists");
			}
			employee = edao.addEmployee(employee);
			return employee;
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException(e.getMessage());
		}
	}

	public Employee updateEmployee(Employee employee) throws UIException {
		try {
			edao.setEmployee(employee);
			edao.execute();
			employee = getEmployee(employee.getUserId());
			return employee;
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public Customer addCustomer(Customer customer) throws UIException {
		Customer cust;
		String error = new String();
		try {
			cust = cdao.getCustomer(customer);
			if (cust != null) {
				if (cust.getAadhar().equals(customer.getAadhar())) {
					error += "aadhar exists";
				}
				if (cust.getPan().equals(customer.getPan())) {
					error += " pan exists";
				}
			}
			if (!error.isEmpty()) {
				throw new UIException(error);
			}
			customer = cdao.addCustomer(customer);
			return customer;
		} catch (Xception e) {
			if (e.getMessage().equals("mysql error")) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new UIException("internal error");
			}
			throw new UIException(e.getMessage());
		}
	}

	public Account addAccount(Account account) throws UIException {
		try {
			Branch branch = new Branch();
			branch.setBranchId(Integer.parseInt(account.getBranchId().toString()));
			branch = brdao.getBranch(branch).get(0);
			account.setIfsc(branch.getIfsc());
			account = adao.addAccount(account);
			return account;
		} catch (Xception e) {
			throw new UIException(e.getMessage());
		}
	}

	public Branch addBranch(Branch branch) throws UIException {
		try {
			branch = brdao.addBranch(branch);
			return branch;
		} catch (Xception e) {
			if (e.getMessage().equals("duplicate entry")) {
				throw new UIException("branch exists");
			} else {
				logger.log(Level.SEVERE,e.getMessage(),e);
				throw new UIException("internal error");
			}
		}
	}

	public Branch getBranch(Branch branch) throws UIException {
		try {
			List<Branch> branches = brdao.getBranch(branch);
			if (branches.isEmpty()) {
				throw new UIException("branch not found");
			}
			return branches.get(0);
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}

	public void updateAccount(Account account) throws UIException {
		try {
			adao.setAccount(account);
			adao.execute();
		} catch (Xception e) {
			if (e.getMessage().equals("duplicate entry")) {
				throw new UIException("duplicate entry");
			} else {
				logger.log(Level.SEVERE,e.getMessage(),e);
			}
				throw new UIException("internal error");
		}
	}

	public void updateCustomer(Customer customer) throws UIException {
		try {
			cdao.setCustomer(customer);
			cdao.execute();
		} catch (Xception e) {
			if (e.getMessage().equals("duplicate entry")) {
				throw new UIException("duplicate entry");
			} else {
				logger.log(Level.SEVERE,e.getMessage(),e);
				throw new UIException("internal error");
			}
		}
	}

	public void updateBranch(Branch branch) throws UIException {
		try {
			brdao.setBranch(branch);
			brdao.execute();
		} catch (Xception e) {
			if (e.getMessage().equals("duplicate entry")) {
				throw new UIException(e.getMessage());
			} else {
				logger.log(Level.SEVERE,e.getMessage(),e);
				throw new UIException("internal error");
			}
		}
	}

	public Bank getBank() throws UIException {
		try {
			return bdao.getBank();
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new UIException("internal error");
		}
	}
}
