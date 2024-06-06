package app.models;

public class Employee extends User {
    private String role;
    private Long branchId=0L;
    private Long aadhar=0L;
	public Employee(User user) {
		this.setUserId(user.getUserId());
		this.setPassHash(user.getPassHash());
		this.setUserName(user.getUserName());
		this.setEmail(user.getEmail());
		this.setStatus(user.getStatus());
		this.setType(user.getType());
	}
    public Employee() {
        // Default constructor
    }
    public String getRole() {
        return role;
    }
    public Long getBranchId() {
    	return this.branchId;
    }
    public void setBranchId(Long branchId) {
    	this.branchId = branchId;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Long getAadhar() {
    	return this.aadhar;
    }
    public void setAadhar(Long aadhar) {
    	this.aadhar = aadhar;
    }
    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()+"\n")
          .append("role=").append(role).append('\'')
          .append('}');
        return sb.toString();
    }
}
