package app.models;

import java.io.Serializable;

public class Account implements Serializable{
    private static final long serialVersionUID = 1L;
	private Long accountNumber;
    private Long balance;
    private String ifsc;
    private Long branchId;
    private Long customerId;
    private String status;
    private Long creationTime;

    public Account() {
    }
    public Long getCreationTime() {
    	return this.creationTime;
    }
    public void setCreationTime(Long time) {
    	this.creationTime = time;
    }
    public Long getBranchId() {
        return this.branchId;
    }

    public void setBranchId(Long id) {
        this.branchId = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", ifsc='" + ifsc + '\'' +
                ", branchId=" + branchId +
                ", customerId=" + customerId +
                ", creation time = "+ creationTime +
                ", status='" + status + '\'' +
                '}';
    }
}
