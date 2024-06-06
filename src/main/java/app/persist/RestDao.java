package app.persist;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.enums.Access;
import app.enums.Org;
import app.enums.Validity;
import app.exceptions.Xception;
import app.ifaces.RestDaoIface;
import app.utils.LoggerProvider;
import app.utils.Tools;

public class RestDao implements RestDaoIface {
    private SQLClient client;
    private Logger logger = LoggerProvider.getLogger();

    public RestDao() throws Xception {
        try {
            client = SQLClient.getInstance();
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String getSegment(Enum enm) throws Xception {
        try {
            String query = "";
            if (enm instanceof Org) {
                query = "select segment from orgdata where org='" + ((Org) enm).getValue() + "';";
            } else if (enm instanceof Access) {
                query = "select segment from accessdata where access='" + ((Access) enm).getValue() + "';";
            }
            List<Map<String, Object>> resMapList = client.executeQuery(query);
            if (resMapList.isEmpty()) {
                return null;
            }
            return resMapList.get(0).get("segment").toString();
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public Org getOrg(String segment) throws Xception {
        try {
            Tools.nullCheck(segment);
            String query = "select org from orgdata where segment = '" + segment + "';";
            List<Map<String, Object>> resMapList = client.executeQuery(query);
            if (resMapList.isEmpty()) {
                return null;
            }
            Object org = resMapList.get(0).get("org");
            return Org.getEnum(org.toString());
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void addOrg(String segment, Org org) throws Xception {
        try {
            Tools.nullCheck(org);
            Tools.nullCheck(segment);
            String query = "insert into orgdata values('" + segment + "','" + org.getValue() + "');";
            client.execute(query);
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void setOrg(String segment, Org org) throws Xception {
        try {
            Tools.nullCheck(org);
            Tools.nullCheck(segment);
            String query = "update orgdata set org = '" + org.getValue() + "' where segment = '" + segment + "';";
            client.execute(query);
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public Access getAccess(String segment) throws Xception {
        try {
            Tools.nullCheck(segment);
            String query = "select access from accessdata where segment = '" + segment + "';";
            List<Map<String, Object>> resMapList = client.executeQuery(query);
            if (resMapList.isEmpty()) {
                return null;
            }
            Object access = resMapList.get(0).get("access");
            if (access == null) {
                return null;
            }
            return Access.getEnum(access.toString());
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void addAccess(String segment, Access access) throws Xception {
        try {
            Tools.nullCheck(access);
            Tools.nullCheck(segment);
            String query = "insert into accessdata values('" + segment + "','" + access.getValue() + "');";
            client.execute(query);
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void setAccess(String segment, Access access) throws Xception {
        try {
            Tools.nullCheck(access);
            Tools.nullCheck(segment);
            String query = "update accessdata set access = '" + access.getValue() + "' where segment = '" + segment
                    + "';";
            client.execute(query);
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public Validity getValidity(String segment) throws Xception {
        try {
            Tools.nullCheck(segment);
            String query = "select * from keydata where segment = '" + segment + "';";
            List<Map<String, Object>> resMapList = client.executeQuery(query);
            if (resMapList.isEmpty()) {
                return null;
            }
            Object validity = resMapList.get(0).get("validity");
            if (validity == null) {
                return null;
            }
            Object expiry = resMapList.get(0).get("expiry");
            if (expiry != null) {
                if ((Long) expiry < System.currentTimeMillis()) {
                    setValidity(segment, Validity.EXPIRED, (Long) expiry);
                    return Validity.EXPIRED;
                }
            }
            return Validity.getEnum(validity.toString());
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void addValidity(String segment, Validity validity, Long expiry) throws Xception {
        try {
            Tools.nullCheck(segment);
            String query = "insert into keydata (segment,validity,expiry) values('" + segment + "','"
                    + validity.getValue() + "','" + expiry + "')";
            client.execute(query);
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }

    @Override
    public void setValidity(String segment, Validity validity, Long expiry) throws Xception {
        try {
            Tools.nullCheck(segment);
            Tools.nullCheck(validity);
            Tools.nullCheck(segment);
            String query = "update keydata set validity = '" + validity.getValue() + "', expiry = '" + expiry
                    + "' where segment = '" + segment + "';";
            client.execute(query);
        } catch (Xception e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new Xception("Internal error");
        }
    }
}
