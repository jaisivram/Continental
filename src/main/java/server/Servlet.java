package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import app.exceptions.UIException;
import app.models.Account;
import app.models.Bank;
import app.models.Branch;
import app.models.Customer;
import app.models.Employee;
import app.models.Transaction;
import app.models.User;
import app.opt.Operations;
import app.persist.SQLClient;
import app.utils.LoggerProvider;
import app.utils.Tools;

public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger;

	public Servlet() {
		logger = LoggerProvider.getLogger();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Operations opt = new Operations();
			Object userId = request.getSession().getAttribute("userId");
			Object employeeRole = request.getSession().getAttribute("employeeRole");
			String route = request.getPathInfo();
			if (route != "/login") {
				Local.setUser((Long) userId);
			}
			if (!route.equals("/home") && !route.equals("/login")) {
				request.getSession().setAttribute("currentPage", route.substring(1));
			}
			request.setAttribute("parent", route.substring(1));
			switch (route) {
				case "/add":
				request.getRequestDispatcher("/jsp/file.jsp").forward(request , response);
				return;
				case "/home":
					loadHome(request ,response, opt);
					break;
				case "/login":
					request.getRequestDispatcher("/jsp/login.jsp").forward(request , response);
					break;
				case "/dashboard":
					loadDashboard(request ,response, opt);
					break;
				case "/profile":
					loadProfile(request ,response, opt);
					break;
				case "/logout":
					request.getSession().invalidate();
					response.sendRedirect("/Continental/app/login");
					break;
				case "/admin/add-branch":
					request.getRequestDispatcher("/jsp/add/add-branch.jsp").forward(request , response);
					break;
				case "/admin/view-branch":
					request.getRequestDispatcher("/jsp/view/view-branch.jsp").forward(request , response);
					break;
				case "/admin/add-employee":
					request.getRequestDispatcher("/jsp/add/add-employee.jsp").forward(request , response);
					break;
				case "/admin/view-employee":
					request.getRequestDispatcher("/jsp/view/view-employee.jsp").forward(request , response);
					break;
				case "/employee/add-customer":
					request.getRequestDispatcher("/jsp/add/add-customer.jsp").forward(request , response);
					break;
				case "/employee/view-customer":
					request.getRequestDispatcher("/jsp/view/view-customer.jsp").forward(request , response);
					break;
				case "/employee/add-account":
					request.getRequestDispatcher("/jsp/employee/employee-add-account.jsp").forward(request , response);
					break;
				case "/admin/add-account":
					request.getRequestDispatcher("/jsp/admin/admin-add-account.jsp").forward(request , response);
					break;
				case "/employee/view-account":
					if (employeeRole != null && employeeRole.equals("admin")) {
						request.setAttribute("isAdmin", true);
					}
					request.getRequestDispatcher("/jsp/view/view-account.jsp").forward(request , response);
					break;
				case "/customer/accounts":
					customerLoadAccounts(request ,response, opt);
					break;
				case "/customer/deposit":
					customerDeposit(request ,response, opt);
					break;
				case "/employee/deposit":
					request.getRequestDispatcher("/jsp/employee/employee-deposit.jsp").forward(request , response);
					break;
				case "/customer/withdraw":
					customerWithdraw(request ,response, opt);
					break;
				case "/employee/withdraw":
					request.getRequestDispatcher("/jsp/employee/employee-withdraw.jsp").forward(request , response);
					break;
				case "/customer/transfer":
					customerTransfer(request ,response, opt);
					break;
				case "/employee/transfer":
					request.getRequestDispatcher("/jsp/employee/employee-transfer.jsp").forward(request , response);
					break;
				case "/employee/view-statement":
					request.getRequestDispatcher("/jsp/view/view-statement.jsp").forward(request , response);
					break;
				case "/customer/statement":
					loadCustomerStatement(request ,response, opt);
					break;
				default:
					response.getWriter().append("Served at: ").append(request.getContextPath());
					break;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			response.sendError(500);
		} finally {
			SQLClient.destroyClient();	
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Operations opt = new Operations();
			Object userId = request.getSession().getAttribute("userId");
			String route = request.getPathInfo();
			if (route != "/login") {
				Local.setUser((Long) userId);
			}
			request.setAttribute("parent", route.substring(1));
			switch (route) {
				case "/login":
					login(request ,response, opt);
					break;
				case "/admin/add-branch":
					addBranch(request ,response, opt);
					break;
				case "/admin/update-branch":
					updateBranch(request ,response, opt);
					break;
				case "/admin/view-branch":
					viewBranch(request ,response, opt);
					break;
				case "/admin/add-employee":
					addEmployee(request ,response, opt);
					break;
				case "/admin/update-employee":
					updateEmployee(request ,response, opt);
					break;
				case "/admin/view-employee":
					viewEmployee(request ,response, opt);
					break;
				case "/employee/add-customer":
					addCustomer(request ,response, opt);
					break;
				case "/employee/update-self":
					updateProfile(request ,response, opt);
					break;
				case "/employee/update-customer":
					updateCustomer(request ,response, opt);
					break;
				case "/employee/view-customer":
					viewCustomer(request ,response, opt);
					break;
				case "/customer/update-self":
					updateProfile(request ,response, opt);
					break;
				case "/customer/change-password":
					changePassword(request ,response, opt);
					break;
				case "/employee/change-password":
					changePassword(request ,response, opt);
					break;
				case "/employee/add-account":
					addAccount(request ,response, opt);
					break;
				case "/admin/add-account":
					addAccount(request ,response, opt);
					break;
				case "/employee/update-account":
					updateAccount(request ,response, opt);
					break;
				case "/employee/view-account":
					viewAccount(request ,response, opt);
					break;
				case "/customer/set-primary":
					setPrimary(request ,response, opt);
					break;
				case "/admin/update-account":
					updateAccount(request ,response, opt);
					break;
				case "/customer/deposit":
					deposit(request ,response, opt);
					break;
				case "/employee/deposit":
					deposit(request ,response, opt);
					break;
				case "/customer/withdraw":
					withdraw(request ,response, opt);
					break;
				case "/employee/withdraw":
					withdraw(request ,response, opt);
					break;
				case "/customer/transfer":
					transfer(request ,response, opt);
					break;
				case "/employee/transfer":
					transfer(request ,response, opt);
					break;
				case "/employee/view-statement":
					viewStatement(request ,response, opt);
					break;
				case "/customer/statement":
					statementPost(request ,response, opt);
					break;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			response.sendError(500);
		} finally {
			SQLClient.destroyClient();	
		}
	}

	public void checkUser(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			if (!opt.checkUserStatus((Long) request.getSession().getAttribute("userId"))) {
				response.sendRedirect("/Continental/app/login");
			}
		} catch (UIException e) {
			response.sendRedirect("/Continental/app/login");
			return;
		} catch (NumberFormatException e){
			request.setAttribute("login-error", "invalid input");
			request.getRequestDispatcher("/jsp/login.jsp").forward(request , response);
			return;
		}
	}

	private void customerLoadAccounts(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object userId = request.getSession().getAttribute("userId");
		try {
			List<Account> accounts = opt.getAccounts((Long) userId);
			Long primaryAccountNumber = opt.getPrimaryAccountNumber((Long) userId);
			request.setAttribute("accounts", accounts);
			request.setAttribute("primaryAccountNumber", primaryAccountNumber);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher("/jsp/customer/customer-accounts.jsp").forward(request , response);
	}

	private void customerDeposit(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object userId = request.getSession().getAttribute("userId");
		try {
			List<Account> accounts = opt.getAccounts((Long) userId);
			Long primaryAccountNumber = opt.getPrimaryAccountNumber((Long) userId);
			request.setAttribute("accounts", accounts);
			request.setAttribute("primaryAccountNumber", primaryAccountNumber);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher("/jsp/customer/customer-deposit.jsp").forward(request , response);
	}

	private void customerWithdraw(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object userId = request.getSession().getAttribute("userId");
		try {
			List<Account> accounts = opt.getAccounts((Long) userId);
			Long primaryAccountNumber = opt.getPrimaryAccountNumber((Long) userId);
			request.setAttribute("accounts", accounts);
			request.setAttribute("primaryAccountNumber", primaryAccountNumber);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher("/jsp/customer/customer-withdraw.jsp").forward(request , response);
	}

	private void customerTransfer(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object userId = request.getSession().getAttribute("userId");
		try {
			List<Account> accounts = opt.getAccounts((Long) userId);
			Long primaryAccountNumber = opt.getPrimaryAccountNumber((Long) userId);
			request.setAttribute("accounts", accounts);
			request.setAttribute("primaryAccountNumber", primaryAccountNumber);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher("/jsp/customer/customer-transfer.jsp").forward(request , response);
	}

	private void loadHome(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object userId = request.getSession().getAttribute("userId");
		Object userType = request.getSession().getAttribute("userType");
		Object employeeRole = request.getSession().getAttribute("employeeRole");
		switch (userType.toString()) {
			case "customer":
				try {
					Long primaryAccountNumber = opt.getPrimaryAccountNumber((Long) userId);
					Account account = opt.getAccount(primaryAccountNumber);
					request.setAttribute("account", account);
					request.setAttribute("primaryAccountNumber", primaryAccountNumber);
				} catch (UIException e) {
					request.setAttribute("error", e.getMessage());
				}
				request.getRequestDispatcher("/jsp/customer/customer.jsp").forward(request , response);
				break;
			case "employee":
				if (employeeRole.toString().equals("admin")) {
					try {
						Bank bank = opt.getBank();
						request.setAttribute("bank", bank);
					} catch (UIException e) {
						request.setAttribute("error", e);
					}
					request.getRequestDispatcher("/jsp/admin/admin.jsp").forward(request , response);
					break;
				}
				Branch branch = new Branch();
				branch.setBranchId(Integer.parseInt(request.getSession().getAttribute("employeeBranch").toString()));
				try {
					branch = opt.getBranch(branch);
					request.setAttribute("branch", branch);
				} catch (UIException e) {
					request.setAttribute("error", e.getMessage());
				}
				request.getRequestDispatcher("/jsp/employee/employee.jsp").forward(request , response);
				break;
		}
	}

	private void loadCustomerStatement(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object userId = request.getSession().getAttribute("userId");
		List<Object> timeFrames = new ArrayList<>();
		timeFrames.add("selected");
		timeFrames.add("Last 7 days");
		timeFrames.add((7L * 24 * 60 * 60 * 1000));
		timeFrames.add("Last 30 days");
		timeFrames.add((30L * 24 * 60 * 60 * 1000));
		timeFrames.add("Last 6 months");
		timeFrames.add((6L * 30 * 24 * 60 * 60 * 1000));
		request.setAttribute("timeframes", timeFrames);
		Long minMillis = System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000);
		Long maxMillis = System.currentTimeMillis();
		try {
			Long primary = opt.getPrimaryAccountNumber((Long) userId);
			List<Long> nums = opt.getAccountNumbers((Long) userId);
			List<Transaction> transactions = opt.getTransactions(primary, 1, 10, minMillis, maxMillis);
			Long records = opt.getTransactionPages(primary, minMillis, maxMillis);
			Long pages = (long) Math.ceil(records / 10.000);
			request.setAttribute("currentAccountNumber", primary);
			request.setAttribute("accounts", nums);
			request.setAttribute("pages", pages);
			request.setAttribute("transactions", transactions);
			if (pages > 0) {
				request.setAttribute("currentPage", 1);
			} else {
				request.setAttribute("currentPage", 0);
			}
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			System.out.println("here");
		}
		request.getRequestDispatcher("/jsp/customer/customer-statement.jsp").forward(request , response);
	}

	private void loadProfile(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object userId = request.getSession().getAttribute("userId");
		Object userType = request.getSession().getAttribute("userType");
		switch (userType.toString()) {
			case "customer":
				try {
					Customer customer = opt.getCustomer((Long) userId);
					request.setAttribute("customer", customer);
				} catch (UIException e) {
					request.setAttribute("error", e.getMessage());
				}
				request.getSession().setAttribute("currentContent", "customer-profile.jsp");
				request.getRequestDispatcher("/jsp/customer/customer-profile.jsp").forward(request , response);
				break;
			case "employee":
				try {
					Employee employee = opt.getEmployee((Long) userId);
					request.setAttribute("employee", employee);
				} catch (UIException e) {
					request.setAttribute("error", e.getMessage());
				}
				request.getRequestDispatcher("/jsp/employee/employee-profile.jsp").forward(request , response);
		}
	}

	private void loadDashboard(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object userId = request.getSession().getAttribute("userId");
		Object userType = request.getSession().getAttribute("userType");
		Object employeeRole = request.getSession().getAttribute("employeeRole");
		switch (userType.toString()) {
			case "customer":
				try {
					Long primaryAccountNumber = opt.getPrimaryAccountNumber((Long) userId);
					Account account = opt.getAccount(primaryAccountNumber);
					request.setAttribute("account", account);
					request.setAttribute("primaryAccountNumber", primaryAccountNumber);
				} catch (UIException e) {
					request.setAttribute("error", e.getMessage());
				}
				request.getRequestDispatcher("/jsp/customer/customer-dash.jsp").forward(request , response);
				break;
			case "employee":
				switch (employeeRole.toString()) {
					case "admin":
						try {
							Bank bank = opt.getBank();
							request.setAttribute("bank", bank);
						} catch (UIException e) {
							request.setAttribute("error", e.getMessage());
						}
						request.getRequestDispatcher("/jsp/admin/admin-dash.jsp").forward(request , response);
						break;
					default:
						Branch branch = new Branch();
						branch.setBranchId(Integer.parseInt(request.getSession().getAttribute("employeeBranch").toString()));
						try {
							branch = opt.getBranch(branch);
							request.setAttribute("branch", branch);
						} catch (UIException e) {
							request.setAttribute("error", e.getMessage());
						}
						request.getRequestDispatcher("/jsp/employee/employee-dash.jsp").forward(request , response);
						break;
				}
		}
	}

	private void statementPost(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		JSONObject json = null;
		try {
			json = bodyParser(request);
		} catch (Exception e) {

		}
		Long userId = (Long) request.getSession().getAttribute("userId");
		String userType = request.getSession().getAttribute("userType").toString();
		if (userType.toString().equals("customer")) {
			userId = (Long) request.getSession().getAttribute("userId");
			Long minMillis = Long.parseLong(json.getString("timeFrame"));
			List<Object> timeFrames = new ArrayList<>();
			Object[] tmfArr = new Object[] { "Last 7 days", ((7L * 24 * 60 * 60 * 1000)), "Last 30 days",
					((30L * 24 * 60 * 60 * 1000)), "Last 6 months", ((6L * 30 * 24 * 60 * 60 * 1000)) };
			for (int i = 0; i < tmfArr.length; i += 2) {
				if (((Long) tmfArr[i + 1]).equals(minMillis)) {
					timeFrames.add("selected");
					timeFrames.add(tmfArr[i].toString());
					timeFrames.add(tmfArr[i + 1]);
				} else {
					timeFrames.add(tmfArr[i].toString());
					timeFrames.add(tmfArr[i + 1]);
				}
			}
			System.out.println(minMillis);
			System.out.println(timeFrames);
			request.setAttribute("timeframes", timeFrames);
			Long actualMinMillis = System.currentTimeMillis() - minMillis;
			Long maxMillis = System.currentTimeMillis();
			int pageNumber;
			try {
				pageNumber = Integer.parseInt(json.getString("pageNumber"));
			} catch (JSONException e) {
				pageNumber = 1;
			}
			try {
				List<Transaction> transactions;
				String accNum = json.getString("sourceAccountNumber");
				Long records;
				if (accNum.equals("all")) {
					records = opt.getTransactionPages(userId, actualMinMillis, maxMillis);
					transactions = opt.getTransactions(userId, pageNumber, 10, actualMinMillis, maxMillis);
				} else {
					System.out.println(Long.parseLong(accNum));
					records = opt.getTransactionPages(Long.parseLong(accNum), actualMinMillis, maxMillis);
					transactions = opt.getTransactions(Long.parseLong(accNum), pageNumber, 10, actualMinMillis,
							maxMillis);
				}
				List<Long> nums = opt.getAccountNumbers(userId);
				Long pages = (long) Math.ceil(records / 10.000);
				request.setAttribute("currentAccountNumber", Long.parseLong(accNum));
				request.setAttribute("accounts", nums);
				request.setAttribute("currentPage", pageNumber);
				request.setAttribute("pages", pages);
				request.setAttribute("transactions", transactions);
			} catch (UIException e) {
				request.setAttribute("error", e.getMessage());
				e.printStackTrace();
			}
			request.getRequestDispatcher("/jsp/customer/customer-statement.jsp").forward(request , response);
		}
	}

	private void changePassword(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Long userId = (Long) request.getSession().getAttribute("userId");
		String userType = request.getSession().getAttribute("userType").toString();
		if (userType.equals("customer")) {
			try {
				Customer customer = opt.getCustomer(userId);
				JSONObject body = bodyParser(request);
				request.setAttribute("customer", customer);
				String oldPassword = body.getString("oldPassword");
				String newPassword = body.getString("newPassword");
				String confirmPassword = body.getString("confirmPassword");
				if (!customer.getPassHash().equals(Tools.hasher(oldPassword))) {
					request.setAttribute("passwordChangeError", "invalid old password");
				} else if (!newPassword.equals(confirmPassword)) {
					request.setAttribute("passwordChangeError", "new password and confirm password are not same");
				} else {
					opt.changePassword(userId, confirmPassword);
					request.setAttribute("passwordChangeSuccess", "password changed");
				}
			} catch (UIException e) {
				request.setAttribute("passwordChangeError", e.getMessage());
			}
			request.getRequestDispatcher("/jsp/customer/customer-profile.jsp").forward(request , response);
		} else {
			try {
				Employee employee = opt.getEmployee(userId);
				JSONObject body = bodyParser(request);
				request.setAttribute("employee", employee);
				String oldPassword = body.getString("oldPassword");
				String newPassword = body.getString("newPassword");
				String confirmPassword = body.getString("confirmPassword");
				if (!employee.getPassHash().equals(Tools.hasher(oldPassword))) {
					request.setAttribute("passwordChangeError", "invalid old password");
				} else if (!newPassword.equals(confirmPassword)) {
					request.setAttribute("passwordChangeError", "new password and confirm password are not same");
				} else {
					opt.changePassword(userId, confirmPassword);
					request.setAttribute("passwordChangeSuccess", "password changed");
				}
			} catch (UIException e) {
				request.setAttribute("passwordChangeError", e.getMessage());
			}
			request.getRequestDispatcher("/jsp/employee/employee-profile.jsp").forward(request , response);
		}
	}

	private void setPrimary(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Long CustomerId = (Long) request.getSession().getAttribute("userId");
		JSONObject json = bodyParser(request);
		Long accountNumber = Long.parseLong(json.getString("accountNumber"));
		try {
			opt.setPrimary(CustomerId, accountNumber);
			request.setAttribute("primaryAccountNumber", accountNumber);
			List<Account> accounts = opt.getAccounts(CustomerId);
			request.setAttribute("accounts", accounts);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher("/jsp/customer/customer-accounts.jsp").forward(request , response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response, Operations opt) throws IOException, ServletException {
		Long userId = Long.parseLong(request.getParameter("userId"));
		if (request.getSession().getAttribute("userId") != null) {
			if (((Long) request.getSession().getAttribute("userId")).equals(userId)) {
				response.sendRedirect("/Continental/app/home");
				return;
			}
		}
		String password = request.getParameter("password");
		try {
			User user = opt.login(userId, password);
			request.getSession().setAttribute("userId", userId);
			request.getSession().setAttribute("userType", user.getType());
			if (user.getType().equals("employee")) {
				Employee employee = opt.getEmployee(userId);
				request.getSession().setAttribute("employeeRole", employee.getRole());
				if (!employee.getRole().equals("admin")) {
					request.getSession().setAttribute("employeeBranch", employee.getBranchId());
				}
			}
			response.sendRedirect("/Continental/app/home");
			return;
		} catch (UIException e) {
			request.setAttribute("login-error", e.getMessage());
			request.getRequestDispatcher("/jsp/login.jsp").forward(request , response);
			return;
		} catch (NumberFormatException e){
			request.setAttribute("login-error", e.getMessage());
			request.getRequestDispatcher("/jsp/login.jsp").forward(request , response);
			return;
		}

	}

	private void deposit(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Transaction transaction = new Transaction();
		JSONObject json = bodyParser(request);
		Long userId = (Long) request.getSession().getAttribute("userId");
		String userType = request.getSession().getAttribute("userType").toString();
		Long sourceAccountNumber = Long.parseLong(json.getString("sourceAccountNumber"));
		Long amount = Long.parseLong(json.getString("amount"));
		Long time = System.currentTimeMillis();
		String id = "DP/" + userId + "/" + userType + "/" + time;
		Long customerId = 0L;
		if (userType.equals("customer")) {
			try {
				customerId = userId;
				Long accountOwner = opt.getCustomerId(sourceAccountNumber);
				if (!accountOwner.equals(customerId)) {
					throw new UIException("not your account lol!");
				}
			} catch (UIException e) {
				loadAfterTranscation(request,opt);
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/jsp/customer/customer-deposit.jsp").forward(request , response);
				return;
			}
		} else {
			try {
				customerId = opt.getCustomerId(sourceAccountNumber);
			} catch (UIException e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/jsp/employee/employee-deposit.jsp").forward(request , response);
				return;
			}
		}
		transaction.setCredit(amount);
		transaction.setTransactionId(id);
		transaction.setTimeStamp(time);
		transaction.setSourceAccountNumber(sourceAccountNumber);
		transaction.setTransactionType("deposit");
		transaction.setCustomerId(customerId);
		try {
			opt.makeTransaction(transaction);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			if (userType.equals("customer")) {
				loadAfterTranscation(request,opt);
			}
			request.getRequestDispatcher("/jsp/" + userType + "-deposit.jsp").forward(request , response);
			return;
		}
		request.setAttribute("tid", id);
		if (userType.toString().equals("customer")) {
			loadAfterTranscation(request,opt);
			request.getRequestDispatcher("/jsp/customer/customer-deposit.jsp").forward(request , response);
		} else {
			request.getRequestDispatcher("/jsp/employee/employee-deposit.jsp").forward(request , response);
		}
		return;
	}

	private void withdraw(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Transaction transaction = new Transaction();
		JSONObject json = bodyParser(request);
		Long userId = (Long) request.getSession().getAttribute("userId");
		String userType = request.getSession().getAttribute("userType").toString();
		Long sourceAccountNumber = Long.parseLong(json.getString("sourceAccountNumber"));
		Long amount = Long.parseLong(json.getString("amount"));
		Long time = System.currentTimeMillis();
		String id = "WD/" + userId + "/" + userType + "/" + time;
		Long customerId = 0L;
		if (userType.equals("customer")) {
			try {
				customerId = userId;
				Long accountOwner = opt.getCustomerId(sourceAccountNumber);
				if (!accountOwner.equals(customerId)) {
					throw new UIException("not your account lol!");
				}
			} catch (UIException e) {
				loadAfterTranscation(request,opt);
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/jsp/customer/customer-withdraw.jsp").forward(request , response);
				return;
			}
		} else {
			try {
				customerId = opt.getCustomerId(sourceAccountNumber);
			} catch (UIException e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/jsp/employee/employee-withdraw.jsp").forward(request , response);
				return;
			}
		}
		transaction.setDebit(amount);
		transaction.setTransactionId(id);
		transaction.setTimeStamp(time);
		transaction.setSourceAccountNumber(sourceAccountNumber);
		transaction.setTransactionType("withdraw");
		transaction.setCustomerId(customerId);
		try {
			opt.makeTransaction(transaction);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			if (userType.equals("customer")) {
				loadAfterTranscation(request,opt);
			}
			request.getRequestDispatcher("/jsp/" + userType + "-withdraw.jsp").forward(request , response);
			return;
		}
		request.setAttribute("tid", id);
		if (userType.toString().equals("customer")) {
			loadAfterTranscation(request,opt);
			request.getRequestDispatcher("/jsp/customer/customer-withdraw.jsp").forward(request , response);
		} else {
			request.getRequestDispatcher("/jsp/employee/employee-withdraw.jsp").forward(request , response);
		}
		return;
	}

	private void transfer(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		String userType = request.getSession().getAttribute("userType").toString();
		JSONObject json = bodyParser(request);
		Transaction transaction = new Transaction();
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long sourceAccountNumber = Long.parseLong(json.getString("sourceAccountNumber"));
		Long transcationAccountNumber = Long.parseLong(json.getString("transactionAccountNumber"));
		Object scope = json.getString("scope");
		Long amount = Long.parseLong(json.getString("amount"));
		Long time = System.currentTimeMillis();
		String id = "TF/" + userId + "/" + userType + "/" + time;
		Long customerId = 0L;
		if (userType.equals("customer")) {
			try {
				customerId = userId;
				Long accountOwner = opt.getCustomerId(sourceAccountNumber);
				if (!accountOwner.equals(customerId)) {
					throw new UIException("not your account lol!");
				}
			} catch (UIException e) {
				loadAfterTranscation(request,opt);
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/jsp/customer/customer-transfer.jsp").forward(request , response);
				return;
			}
		} else {
			try {
				customerId = opt.getCustomerId(sourceAccountNumber);
			} catch (UIException e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/jsp/employee/employee-transfer.jsp").forward(request , response);
				return;
			}
		}
		transaction.setDebit(amount);
		transaction.setTransactionId(id);
		transaction.setTimeStamp(time);
		transaction.setSourceAccountNumber(sourceAccountNumber);
		transaction.setTransactionType("transfer");
		transaction.setCustomerId(customerId);
		transaction.setTransactionAccountNumber(transcationAccountNumber);
		if (scope != null && !scope.toString().trim().isEmpty()) {
			transaction.setScope(scope.toString());
		}
		try {
			opt.makeTransaction(transaction);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			if (userType.toString().equals("customer")) {
				loadAfterTranscation(request,opt);
				request.getRequestDispatcher("/jsp/customer/customer-transfer.jsp").forward(request , response);
			} else {
				request.getRequestDispatcher("/jsp/employee/employee-transfer.jsp").forward(request , response);
			}
			return;
		}
		request.setAttribute("tid", id);
		if (userType.toString().equals("customer")) {
			loadAfterTranscation(request,opt);
			request.getRequestDispatcher("/jsp/customer/customer-transfer.jsp").forward(request , response);
		} else {
			request.getRequestDispatcher("/jsp/employee/employee-transfer.jsp").forward(request , response);
		}
		return;
	}

	private void updateProfile(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Long userId = (Long) request.getSession().getAttribute("userId");
		try {
			JSONObject json = bodyParser(request);
			String email = json.getString("email");
			opt.updateUserEmail(userId, email);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/customer/customer-profile.jsp").forward(request , response);
			return;
		}
		String userType = request.getSession().getAttribute("userType").toString();
		try {
			switch (userType) {
				case "customer":
					Customer customer = opt.getCustomer(userId);
					request.setAttribute("customer", customer);
					request.getRequestDispatcher("/jsp/customer/customer-profile.jsp").forward(request , response);
					break;
				case "employee":
					Employee employee = opt.getEmployee(userId);
					request.setAttribute("employee", employee);
					request.getRequestDispatcher("/jsp/employee/employee-profile.jsp").forward(request , response);
					break;
			}
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			if (userType.equals("customer")) {
				request.getRequestDispatcher("/jsp/customer/customer-profile.jsp").forward(request , response);
				return;
			}
			request.getRequestDispatcher("/jsp/employee/employee-profile.jsp").forward(request , response);
			return;
		}

	}

	private void addEmployee(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			JSONObject json = bodyParser(request);
			Employee employee = new Employee();
			employee.setType("employee");
			employee.setUserName(json.getString("userName"));
			employee.setEmail(json.getString("email"));
			employee.setPassHash(Tools.hasher(json.getString("password")));
			employee.setBranchId(Long.parseLong(json.getString("branchId")));
			employee.setRole(json.getString("role"));
			employee.setStatus("active");
			employee.setAadhar(Long.parseLong(json.getString("aadhar")));
			employee = opt.addEmployee(employee);
			request.setAttribute("employee", employee);
			request.getRequestDispatcher("/jsp/card/employee.jsp").forward(request , response);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/add/add-employee.jsp").forward(request , response);
			return;
		}
	}

	private void viewEmployee(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			JSONObject json = bodyParser(request);
			Employee employee = new Employee();
			Long id = (Long.parseLong(json.getString("employeeId")));
			employee = opt.getEmployee(id);
			request.setAttribute("employee", employee);
			request.getRequestDispatcher("/jsp/card/employee.jsp").forward(request , response);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/view/view-employee.jsp").forward(request , response);
		}
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			JSONObject json = bodyParser(request);
			Employee employee = new Employee();
			employee.setUserId(Long.parseLong(json.getString("employeeId")));
			employee.setEmail(json.getString("email"));
			employee.setRole(json.getString("role"));
			employee.setStatus(json.getString("status"));
			employee.setAadhar(Long.parseLong(json.getString("aadhar")));
			employee.setBranchId(Long.parseLong(json.getString("branchId")));
			employee = opt.updateEmployee(employee);
			request.setAttribute("employee", employee);
			request.getRequestDispatcher("/jsp/card/employee.jsp").forward(request , response);
			return;
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/card/employee.jsp").forward(request , response);
		}
	}

	private void addBranch(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			JSONObject json = bodyParser(request);
			Branch branch = new Branch();
			branch.setBranchName(json.getString("branchName"));
			branch.setAddress(json.getString("branchAddress"));
			branch.setIfsc(json.getString("ifsc"));
			branch.setPincode(Long.parseLong(json.getString("pincode")));
			branch = opt.addBranch(branch);
			branch = opt.getBranch(branch);
			request.setAttribute("branch", branch);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/add/add-branch.jsp").forward(request , response);
			return;
		}
		request.getRequestDispatcher("/jsp/card/branch.jsp").forward(request , response);
	}

	private void updateBranch(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Branch br = null;
		try {
			JSONObject json = bodyParser(request);
			String address = json.getString("address");
			Integer branchId = Integer.parseInt(json.getString("branchId"));
			Branch branch = new Branch();
			branch.setBranchId(branchId);
			br = opt.getBranch(branch);
			branch.setAddress(address);
			opt.updateBranch(branch);
			branch = opt.getBranch(branch);
			request.setAttribute("branch", branch);
			request.getRequestDispatcher("/jsp/card/branch.jsp").forward(request , response);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.setAttribute("branch", br);
			request.getRequestDispatcher("/jsp/card/branch.jsp").forward(request , response);
		}
	}

	private void viewBranch(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			JSONObject json = bodyParser(request);
			Integer branchId = Integer.parseInt(json.getString("branchId"));
			Branch branch = new Branch();
			branch.setBranchId(branchId);
			branch = opt.getBranch(branch);
			request.setAttribute("branch", branch);
			request.getRequestDispatcher("/jsp/card/branch.jsp").forward(request , response);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/view/view-branch.jsp").forward(request , response);
		} catch (Exception e) {

			request.setAttribute("error", "internal error");
			request.getRequestDispatcher("/jsp/view/view-branch.jsp").forward(request , response);
		}
	}

	private void addCustomer(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			JSONObject json = bodyParser(request);
			Customer customer = new Customer();
			customer.setUserName(json.getString("userName"));
			customer.setEmail(json.getString("email"));
			customer.setPassHash(Tools.hasher(json.getString("password")));
			customer.setStatus("active");
			customer.setType("customer");
			customer.setAadhar(Long.parseLong(json.getString("aadhar")));
			customer.setPan(json.getString("pan"));
			customer = opt.addCustomer(customer);
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/jsp/card/customer.jsp").forward(request , response);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/add/add-customer.jsp").forward(request , response);
		}
	}

	private void updateCustomer(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			JSONObject json = bodyParser(request);
			Customer customer = new Customer();
			customer.setUserId(Long.parseLong(json.getString("customerId")));
			customer.setAadhar(Long.parseLong(json.getString("aadhar")));
			customer.setEmail(json.getString("email"));
			customer.setPan(json.getString("pan"));
			customer.setStatus(json.getString("status"));
			customer.setPrimaryAccountNumber(Long.parseLong(json.getString("primaryAccountNumber")));
			opt.updateCustomer(customer);
			customer = opt.getCustomer(customer.getUserId());
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/jsp/card/customer.jsp").forward(request , response);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/card/cusomer.jsp").forward(request , response);
		}
	}

	private void viewCustomer(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			JSONObject json = bodyParser(request);
			Customer customer = new Customer();
			customer.setUserId(Long.parseLong(json.getString("customerId")));
			customer = opt.getCustomer(customer.getUserId());
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/jsp/card/customer.jsp").forward(request , response);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/view/view-customer.jsp").forward(request , response);
		}
	}

	private void addAccount(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object employeeRole = request.getSession().getAttribute("employeeRole");
		try {
			JSONObject json = bodyParser(request);
			Long customerId = Long.parseLong(json.getString("customerId"));
			Account account = new Account();
			Long time = System.currentTimeMillis();
			account.setStatus("active");
			account.setBalance(0l);
			account.setCreationTime(time);
			account.setCustomerId(customerId);
			if (employeeRole != null) {
				if (employeeRole.equals("admin")) {
					Long branchId = Long.parseLong(json.getString("branchId"));
					account.setBranchId(branchId);
					request.setAttribute("isAdmin", true);
				}
			}
			account = opt.addAccount(account);
			request.setAttribute("account", account);
			request.getRequestDispatcher("/jsp/card/account.jsp").forward(request , response);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			if (employeeRole != null && employeeRole.equals("admin")) {
				request.getRequestDispatcher("/jsp/admin/admin-add-account.jsp").forward(request , response);
			} else {
				request.getRequestDispatcher("/jsp/employee/employee-add-account.jsp").forward(request , response);
				return;
			}
		}
	}

	private void updateAccount(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		String employeeRole = request.getSession().getAttribute("employeeRole").toString();
		try {
			JSONObject json = bodyParser(request);
			Account account = new Account();
			account.setAccountNumber(Long.parseLong(json.getString("accountNumber")));
			account.setStatus(json.getString("status"));
			Long branchId = opt.getEmployee((Long) request.getSession().getAttribute("userId")).getBranchId();
			if (employeeRole.equals("admin")) {
				account.setBranchId(Long.parseLong(json.getString("branchId")));
				account.setIfsc(json.getString("ifsc"));
				request.setAttribute("isAdmin", true);
			} else {
				if (!json.getString("branchId").equals(branchId.toString())) {
					request.setAttribute("error", "not your branch");
					request.getRequestDispatcher("/jsp/view/view-account.jsp").forward(request , response);
					return;
				}
			}
			opt.updateAccount(account);
			account = opt.getAccount(account.getAccountNumber());
			request.setAttribute("account", account);
			request.getRequestDispatcher("/jsp/card/account.jsp").forward(request , response);
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/card/account.jsp").forward(request , response);
		}

	}

	private void viewAccount(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		Object employeeRole = request.getSession().getAttribute("employeeRole");
		if (employeeRole != null && employeeRole.equals("admin")) {
			request.setAttribute("isAdmin", true);
		}
		try {
			JSONObject json = bodyParser(request);
			String accountNumber = (json.getString("accountNumber"));
			if (accountNumber.equals("")) {
				Long customerId = Long.parseLong(json.getString("customerId"));
				List<Account> accounts = opt.getAccounts(customerId);
				if(!employeeRole.equals("admin")){
					for(Account account : accounts){
						if(account.getBranchId().equals(request.getAttribute("employeeBranch"))){
							throw new UIException("not your branch");
						}
					}
				}
				request.setAttribute("accounts", accounts);
				request.getRequestDispatcher("/jsp/card-list/account.jsp").forward(request , response);
				return;
			} else {
				Account account = opt.getAccount(Long.parseLong(accountNumber));
				request.setAttribute("account", account);
				request.getRequestDispatcher("/jsp/card/account.jsp").forward(request , response);
				return;
			}
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			if (employeeRole != null && employeeRole.equals("admin")) {
				request.setAttribute("isAdmin", true);
			}
			request.getRequestDispatcher("/jsp/view-account.jsp").forward(request , response);
			return;
		}
	}

	private void viewStatement(HttpServletRequest request, HttpServletResponse response, Operations opt)
			throws IOException, ServletException {
		try {
			JSONObject json = bodyParser(request);
			String transactionId = json.getString("transactionId");
			String customerId = json.getString("customerId");
			String sourceAccountNumber = json.getString("sourceAccountNumber");
			List<Transaction> transactions = null;
			Long pageNumber = json.getString("pageNumber").equals("") ? 1L
					: Long.parseLong(json.getString("pageNumber"));
			request.setAttribute("pageNumber", pageNumber);
			Long pages = 1L;
			String fromDateStr = "";
			String toDateStr = "";
			if (!transactionId.equals("")) {
				transactions = opt.getTransaction(transactionId);
				request.setAttribute("transactionId", transactionId);
				request.setAttribute("sourceAccountNumber", sourceAccountNumber);
				request.setAttribute("customerId", customerId);
				request.setAttribute("transactions", transactions);
				request.setAttribute("pages", (long) Math.ceil(pages / 10.0));
				request.setAttribute("pageNumber", pageNumber);
				request.setAttribute("fromDate", fromDateStr);
				request.setAttribute("toDate", toDateStr);
				request.getRequestDispatcher("/jsp/card-list/statements.jsp").forward(request , response);
				return;
			}
			fromDateStr = json.getString("fromDate");
			toDateStr = json.getString("toDate");
			Long fromDate = java.time.LocalDate
					.parse(fromDateStr, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay()
					.toInstant(java.time.ZoneOffset.UTC).toEpochMilli();

			Long toDate = java.time.LocalDate
					.parse(toDateStr, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay()
					.toInstant(java.time.ZoneOffset.UTC).toEpochMilli();
			request.setAttribute("fromDate", fromDateStr);
			request.setAttribute("toDate", toDateStr);
			if (!customerId.equals("")) {
				transactions = opt.getTransactions(Long.parseLong(customerId), Integer.parseInt(pageNumber.toString()),
						10, fromDate, toDate);
				if (transactions.isEmpty()) {
					throw new UIException("no transaction found");
				}
				pages = opt.getTransactionPages(Long.parseLong(customerId), fromDate, toDate);
				request.setAttribute("transactionId", transactionId);
				request.setAttribute("customerId", customerId);
				request.setAttribute("sourceAccountNumber", sourceAccountNumber);
				request.setAttribute("pages", (long) Math.ceil(pages / 10.0));
				request.setAttribute("transactions", transactions);
				request.getRequestDispatcher("/jsp/card-list/statements.jsp").forward(request , response);
				return;
			} else if (!sourceAccountNumber.equals("")) {
				transactions = opt.getTransactions(Long.parseLong(sourceAccountNumber),
						Integer.parseInt(pageNumber.toString()), 10, fromDate, toDate);
				if (transactions.isEmpty()) {
					throw new UIException("no transaction found");
				}
				pages = opt.getTransactionPages(Long.parseLong(sourceAccountNumber), fromDate, toDate);
				request.setAttribute("sourceAccountNumber", sourceAccountNumber);
				request.setAttribute("customerId", customerId);
				request.setAttribute("transactionId", transactionId);
				request.setAttribute("pages", (long) Math.ceil(pages / 10.0));
				request.setAttribute("transactions", transactions);
				request.getRequestDispatcher("/jsp/card-list/statements.jsp").forward(request , response);
				return;
			}
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsp/view/view-statement.jsp").forward(request , response);
			return;
		}
	}

	private JSONObject bodyParser(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			body.append(line);
		}
		JSONObject content = new JSONObject(body.toString());
		return content;
	}

	private void loadAfterTranscation(HttpServletRequest request,Operations opt) {
		try {
			if (request.getSession().getAttribute("userType").toString().equals("customer")) {
				Long customerId = (Long) request.getSession().getAttribute("userId");
				List<Account> accounts = opt.getAccounts(customerId);
				Long primaryAccNo = opt.getPrimaryAccountNumber(customerId);
				request.setAttribute("soruceAccountNumber", 0L);
				request.setAttribute("primaryAccountNumber", primaryAccNo);
				request.setAttribute("accounts", accounts);
				request.setAttribute("", primaryAccNo);
			}
		} catch (UIException e) {
			request.setAttribute("error", e.getMessage());
		}
	}

}
