package app.models;
	public class Branch {
	    private Integer branchId;
	    private String ifsc;
	    private String address;
	    private String branchName;
	    private Long accountCount;
	    private Long customerCount;
	    private Double branchWorth;
	    private Long pincode;

	    // Getter methods
	    public Integer getBranchId() {
	        return branchId;
	    }

	    public String getIfsc() {
	        return ifsc;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public String getBranchName() {
	        return branchName;
	    }

	    public Long getAccountCount() {
	        return accountCount;
	    }

	    public Long getCustomerCount() {
	        return customerCount;
	    }

	    public Double getBranchWorth() {
	        return branchWorth;
	    }

	    // Setter methods
	    public void setBranchId(Integer branchId) {
	        this.branchId = branchId;
	    }

	    public void setIfsc(String ifsc) {
	        this.ifsc = ifsc;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public void setBranchName(String branchName) {
	        this.branchName = branchName;
	    }

	    public void setAccountCount(Long accountCount) {
	        this.accountCount = accountCount;
	    }

	    public void setCustomerCount(Long customerCount) {
	        this.customerCount = customerCount;
	    }

	    public void setBranchWorth(Double branchWorth) {
	        this.branchWorth = branchWorth;
	    }

	    public void setPincode(Long pincode) {
	    	this.pincode=pincode;
	    }
		public Long getPincode() {
			return this.pincode;
		}
		@Override
		public String toString() {
			return "Branch{" +
					"branchId=" + branchId +
					", ifsc='" + ifsc + '\'' +
					", address='" + address + '\'' +
					", branchName='" + branchName + '\'' +
					", accountCount=" + accountCount +
					", customerCount=" + customerCount +
					", branchWorth=" + branchWorth +
					", pincode=" + pincode +
					'}';
		}
}
