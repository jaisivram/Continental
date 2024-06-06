package app.opt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.enums.Access;
import app.enums.Org;
import app.enums.Validity;
import app.exceptions.APIException;
import app.exceptions.UIException;
import app.exceptions.Xception;
import app.ifaces.RestDaoIface;
import app.models.Customer;
import app.persist.RestDao;
import app.utils.LoggerProvider;
import app.utils.Tools;

public class RestOperations {
	private Logger logger = LoggerProvider.getLogger();
	private Operations opt;
	private RestDaoIface rdao;

	public RestOperations() {
		opt = new Operations();
		try {
			rdao = new RestDao();
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public Access getAccess(String segment) throws APIException {
		try {
			Tools.nullCheck(segment);
			return rdao.getAccess(segment);
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new APIException("internal error");
		}
	}

	public Validity getValidity(String segment) throws APIException {
		try {
			Tools.nullCheck(segment);
			return rdao.getValidity(segment);
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new APIException("internal error");
		}
	}

	public Org getOrg(String segment) throws APIException {
		try {
			Tools.nullCheck(segment);
			return rdao.getOrg(segment);
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new APIException("internal error");
		}
	}

	public String generateOrgKey(Map<String, Object> data) throws APIException {
		return null;
	}

	public String generateAccessKey(Map<String, Object> data) throws APIException {
		return null;
	}

	public String generateKey(JSONObject data) throws APIException {
		try {//org, access, appname, validity,expiry
			Org org = Org.getEnum(data.getString("org"));
			String orgSegment = rdao.getSegment(org);
			Access access = Access.getEnum(data.getString("access"));
			String accessSegment = rdao.getSegment(access);
			String appName = data.get("appname").toString();
			Long creationTime = System.currentTimeMillis();
			String random = Tools.genStr(10);
			String validitySegment = Tools.hasher(appName + creationTime + random).substring(0, 8);
			Validity validity = Validity.getEnum(data.getString("validity"));
			Long expiry = Long.parseLong(data.getString("expiry"));
			rdao.addValidity(validitySegment, validity, expiry);
			String apiKey = orgSegment + accessSegment + validitySegment;
			return apiKey;
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new APIException("internal error");
		}
	}

	public JSONArray getCustomer(Map<String, Object> reqMap) throws APIException {
		try {
			JSONArray jsonArr = new JSONArray();
			Access access = (Access) reqMap.get("access");
			if (!access.equals(Access.ADMIN) && !access.equals(Access.EMPLOYEE)) {
				throw new APIException("access denied");
			}
			Long userId = reqMap.get("id").toString().isEmpty() ? -1L : Integer.parseInt(reqMap.get("id").toString());
			int limit = Integer.parseInt(reqMap.get("limit").toString());
			int offset = Integer.parseInt(reqMap.get("offset").toString());
			List<Customer> customers = new ArrayList<>();

			if (userId > 0) {
				Customer customer = opt.getCustomer(userId);
				if (customer != null) {
					jsonArr.put(new JSONObject(Tools.pojoToMap(customer, "child")));
				}
			} else {
				customers = opt.getCustomers(limit, offset);
				for (Customer customer : customers) {
					jsonArr.put(new JSONObject(Tools.pojoToMap(customer, "child")));
				}
			}
			return jsonArr;
		} catch (UIException e) {
			throw new APIException(e.getMessage());
		} catch (Xception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new APIException("internal error");
		}
	}

	public Long addCustomer(Map<String, Object> reqMap) throws APIException {
		try {
			JSONObject json = (JSONObject) reqMap.get("json");

			Access access = (Access) reqMap.get("access");
			if (!access.equals(Access.ADMIN) && !access.equals(Access.EMPLOYEE)) {
				throw new APIException("access denied");
			}
			Customer customer = new Customer();
			customer.setUserName(json.getString("username"));
			customer.setEmail(json.getString("email"));
			customer.setPassHash(json.getString("passhash"));
			customer.setStatus(json.getString("status"));
			customer.setType("customer");
			customer.setAadhar(json.getLong("aadhar"));
			customer.setPan(json.getString("pan"));
			customer = opt.addCustomer(customer);
			return customer.getUserId();
		} catch (UIException e) {
			throw new APIException(e.getMessage());
		} catch (JSONException e) {
			throw new APIException("invalid data");
		}
	}
}
