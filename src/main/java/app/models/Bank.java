package app.models;

public class Bank {
    private Double bankWorth;
    private Long customerCount;
    private Long accountCount;
    private Long employeeCount;
    private Long branchCount;

    // Getter methods
    public Double getBankWorth() {
        return bankWorth;
    }

    public Long getCustomerCount() {
        return customerCount;
    }

    public Long getAccountCount() {
        return accountCount;
    }

    public Long getEmployeeCount() {
        return employeeCount;
    }
    public Long getBranchCount() {
        return branchCount;
    }

    // Setter methods
    public void setBankWorth(Double bankWorth) {
        this.bankWorth = bankWorth;
    }

    public void setCustomerCount(Long customerCount) {
        this.customerCount = customerCount;
    }

    public void setAccountCount(Long accountCount) {
        this.accountCount = accountCount;
    }

    public void setEmployeeCount(Long employeeCount) {
        this.employeeCount = employeeCount;
    }
    public void setBranchCount(Long branchCount) {
        this.branchCount = branchCount;
    }
    @Override
	public String toString() {
        return "Bank{" +
                "bankWorth=" + bankWorth +
                ", customerCount=" + customerCount +
                ", accountCount=" + accountCount +
                ", employeeCount=" + employeeCount +
                ", branchCount=" + branchCount +
                '}';
    }
}
