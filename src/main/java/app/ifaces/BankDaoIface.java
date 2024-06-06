package app.ifaces;
import app.exceptions.Xception;
import app.models.Bank;
public interface BankDaoIface {
    public Bank getBank() throws Xception;
}
