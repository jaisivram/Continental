package app.models;


public class User {
    private Long userId = 0L;
    private String passHash;
    private String userName;
    private String type;
    private String status;
    private String email;

    public User() {
        // Default constructor
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
    	this.userId = userId;
    }
    public String getPassHash() {
        return this.passHash;
    }
    public void setPassHash(String hash) {
    	this.passHash = hash;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User {")
          .append("userId=").append(userId)
          .append(", passHash=").append(passHash)
          .append(", userName='").append(userName).append('\'')
          .append(", type='").append(type).append('\'')
          .append(", status='").append(status).append('\'')
          .append(", email='").append(email).append('\'')
          .append('}');
        return sb.toString();
    }
}
