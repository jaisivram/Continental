package app.ifaces;

import java.util.List;

import app.exceptions.Xception;
import app.models.Branch;

public interface BranchDaoIface {
	public List<Branch> getBranch(Branch branch) throws Xception;
	public Branch addBranch(Branch branch) throws Xception;
	public void setBranch(Branch branch) throws Xception;
	public void deleteBranch(Branch branch) throws Xception;
	public void execute() throws Xception;
}
