package app.ifaces;

import app.enums.Access;
import app.enums.Org;
import app.enums.Validity;
import app.exceptions.Xception;

public interface RestDaoIface {
	public Access getAccess(String segment) throws Xception;
	public void addAccess(String segment,Access access) throws Xception;
	public void setAccess(String segment,Access access) throws Xception;
	public Validity getValidity(String segment) throws Xception;
	public void addValidity(String segment,Validity validity,Long expiry) throws Xception;
	public void setValidity(String segment,Validity validity,Long expiry) throws Xception;
	public Org getOrg(String segment) throws Xception;
	public void addOrg(String segment, Org org) throws Xception;
	void setOrg(String segment, Org org) throws Xception;
	String getSegment(@SuppressWarnings("rawtypes") Enum enm) throws Xception;
}
