package app.models;
public class Customer extends User {
	public Customer(User user) {
		this.setUserId(user.getUserId());
		this.setPassHash(user.getPassHash());
		this.setUserName(user.getUserName());
		this.setEmail(user.getEmail());
		this.setStatus(user.getStatus());
		this.setType(user.getType());
	}
	public Customer() {

	}
	public void setUser(User user) {
		this.setUserId(user.getUserId());
		this.setPassHash(user.getPassHash());
		this.setUserName(user.getUserName());
		this.setEmail(user.getEmail());
		this.setStatus(user.getStatus());
		this.setType(user.getType());
	}
    private Long aadhar;
    private String pan;
    private Long primaryAccountNumber =0l;
    public Long getPrimaryAccountNumber() {
    	return this.primaryAccountNumber;
    }

    public void setPrimaryAccountNumber(Long accountNumber) {
    	this.primaryAccountNumber = accountNumber;
    }
    public Long getAadhar() {
        return aadhar;
    }

    public void setAadhar(Long aadhar) {
        this.aadhar = aadhar;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Customer {");
        sb.append(", aadhar='").append(aadhar).append('\'');
        sb.append(", pan='").append(pan).append('\'');
        sb.append(", primary='").append(primaryAccountNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
