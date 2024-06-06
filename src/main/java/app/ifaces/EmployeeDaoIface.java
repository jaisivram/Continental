package app.ifaces;

import app.exceptions.Xception;
import app.models.Employee;

public interface EmployeeDaoIface {
	public Employee getEmployee(Employee employee) throws Xception;
	public void setEmployee(Employee employee) throws Xception;
	public void execute() throws Xception;
	public Employee addEmployee(Employee employee) throws Xception;
}
