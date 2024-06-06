package app.models;


public class Transaction {
    private String transactionType;
    private String transactionId;
    private Long transactionNumber;
    private Long timeStamp;
    private Long customerId;
    private Long sourceAccountNumber;
    private Long transactionAccountNumber;
    private Long credit = 0L;
    private Long debit = 0L;
    private Long balance = 0L;
    private String scope = "internal";

    public Transaction() {
        setTimeStamp(System.currentTimeMillis());
    }
    public void setScope(String scope) {
    	this.scope = scope;
    }
    public String getScope() {
    	return this.scope;
    }
    public void setTransactionNumber(Long transactionNumber) {
    	this.transactionNumber = transactionNumber;
    }
    public Long getTransactionNumber() {
    	return this.transactionNumber;
    }
    public void setTransactionId(String id) {
    	this.transactionId = id;
    }
    public String getTransactionId() {
    	return this.transactionId;
    }
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(Long sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public Long getTransactionAccountNumber() {
        return transactionAccountNumber;
    }

    public void setTransactionAccountNumber(Long transactionAccountNumber) {
        this.transactionAccountNumber = transactionAccountNumber;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public Long getDebit() {
        return debit;
    }

    public void setDebit(Long debit) {
        this.debit = debit;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Override
	public String toString() {
        return "Transaction{" +
                "transactionType='" + transactionType + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", transactionNumber=" + transactionNumber +
                ", timeStamp=" + timeStamp +
                ", customerId=" + customerId +
                ", sourceAccountNumber='" + sourceAccountNumber + '\'' +
                ", transactionAccountNumber='" + transactionAccountNumber + '\'' +
                ", credit=" + credit +
                ", debit=" + debit +
                ", balance=" + balance +
                '}';
    }
}
