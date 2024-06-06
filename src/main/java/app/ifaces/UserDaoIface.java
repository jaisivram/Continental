package app.ifaces;
import app.exceptions.Xception;
import app.models.User;

public interface UserDaoIface {
	public User getUser(User user) throws Xception;
	public void setUser(User user) throws Xception;
	public User addUser(User user) throws Xception;
	public void execute() throws Xception;
}
