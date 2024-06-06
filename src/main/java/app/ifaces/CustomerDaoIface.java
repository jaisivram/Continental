package app.ifaces;

import java.util.List;

import app.exceptions.Xception;
import app.models.Customer;

public interface CustomerDaoIface {
	public Customer getCustomer(Customer customer) throws Xception;
	public void setCustomer(Customer customer) throws Xception;
	public Customer addCustomer(Customer customer) throws Xception;
	public int getMpin(Long customerId) throws Xception;
	public void setMpin(Long customerId,int mpin) throws Xception;
	public void execute() throws Xception;
	List<Customer> getCustomer(int limit, int offset) throws Xception;
}
