package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import app.enums.Access;
import app.enums.Org;
import app.enums.Validity;
import app.exceptions.APIException;
import app.opt.RestOperations;
import app.utils.LoggerProvider;

public class ApiOne extends HttpServlet {
	/* g8pl234ft5wsdf43e6yr34q1 */
	private static final long serialVersionUID = 1L;
	public JSONObject resJSON = new JSONObject();
	public JSONArray jsonArr = new JSONArray();
	private RestOperations ropt;
	private Logger logger;

	public ApiOne() {
		super();
	}

	@Override
	public void init() {
		ropt = new RestOperations();
		logger = LoggerProvider.getLogger();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Map<String, Object> reqMap = requestResolver(request, response);
			if (reqMap == null) {
				return;
			}
			String resource = reqMap.get("resource").toString();
			switch (resource) {
				case "customer":
					resJSON.put("response", ropt.getCustomer(reqMap));
					break;
				case "employee":
					break;
				case "account":
					break;
				case "transaction":
					break;
				case "branch":
					break;
			}
		} catch (APIException e) {
			resJSON.put("error", e.getMessage());
		} catch (Exception e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
			resJSON.put("error", "internal error");
		} finally {
			resWriter(resJSON, response);
			resJSON = new JSONObject();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Map<String, Object> reqMap = requestResolver(request, response);
			if (reqMap == null) {
				throw new APIException("internal error");
			}
			if (reqMap.get("json") == null) {
				throw new APIException("no data");
			}
			String resource = reqMap.get("resource").toString();
			switch (resource) {
				case "customer":
					Long customerId = ropt.addCustomer(reqMap);
					if (customerId == null) {
						throw new APIException("add failed");
					}
					resJSON.put("customerId", customerId);
					break;
				case "employee":
					break;
				case "account":
					break;
				case "transaction":
					break;
				case "branch":
					break;
				case "generateKey":
					if (!((Access) reqMap.get("access")).equals(Access.ADMIN)) {
						throw new APIException("unauthorised request");
					}
					String key = ropt.generateKey((JSONObject) reqMap.get("json"));
					if (key == null) {
						throw new APIException("key generation failed");
					}
					resJSON.put("APIKEY", key);
					break;
			}
		} catch (APIException e) {
			resJSON.put("error", e.getMessage());
		} catch (Exception e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
			resJSON.put("error", "internal error");
		} finally {
			resWriter(resJSON, response);
			resJSON = new JSONObject();
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Map<String, Object> reqMap = requestResolver(request, response);
			if (reqMap == null) {
				return;
			}
			String resource = reqMap.get("resource").toString();
			switch (resource) {
				case "customer":
					break;
				case "employee":
					break;
				case "account":
					break;
				case "transaction":
					break;
				case "branch":
					break;
			}
		} catch (APIException e) {
			resJSON.put("error", e.getMessage());
		} catch (Exception e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
			resJSON.put("error", "internal error");
		} finally {
			resWriter(resJSON, response);
			resJSON = new JSONObject();
		}
	}

	@Override
	@SuppressWarnings("unused")
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Map<String, Object> reqMap = requestResolver(request, response);
			if (reqMap == null) {
				return;
			}
			String resource = reqMap.get("resource").toString();
			String id = reqMap.get("id").toString();
			int limit = (Integer) reqMap.get("limit");
			int offset = (Integer) reqMap.get("offset");
			switch (resource) {
				case "customer":
					break;
				case "employee":
					break;
				case "account":
					break;
				case "transaction":
					break;
				case "branch":
					break;
			}
		} catch (APIException e) {
			resJSON.put("error", e.getMessage());
		} catch (Exception e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
			resJSON.put("error", "internal error");
		} finally {
			resWriter(resJSON, response);
			resJSON = new JSONObject();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> requestResolver(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		Map resMap = new HashMap<>();
		String route = request.getPathInfo();
		String apiKey = request.getHeader("APIKEY");
		String orgSegment = apiKey.substring(0, 8);
		String accessSegment = apiKey.substring(8, 16);
		String validitySegment = apiKey.substring(16, 24);
		Org org = ropt.getOrg(orgSegment);
		if (org == null) {
			throw new APIException("invalid api key");
		}
		resMap.put("org", org);
		Validity validity = ropt.getValidity(validitySegment);
		if (validity == null) {
			throw new APIException("invalid api key");
		}
		switch (validity) {
			case EXPIRED:
				throw new APIException("api key expired");
			case BLOCKED:
				throw new APIException("api key blocked");
			case VALID:
				break;
			default:
				throw new APIException("!internal error");

		}
		Access access = ropt.getAccess(accessSegment);
		if (access == null) {
			throw new APIException("invalid api key");
		}
		resMap.put("access", access);
		String[] arr = route.split("/");
		resMap.put("resource", arr[1]);
		String id = arr.length > 2 ? arr[2] : "";
		resMap.put("id", id);
		String content = request.getContentType();
		if (content == null || !content.startsWith("application/json")) {
			String limitParam = request.getParameter("limit");
			int limit = (limitParam == null || Integer.parseInt(limitParam) > 20) ? 20 : Integer.parseInt(limitParam);
			resMap.put("limit", limit);
			String offsetParam = request.getParameter("offset");
			int offset = offsetParam != null ? Integer.parseInt(offsetParam) : 0;
			resMap.put("offset", offset);
		} else {
			JSONObject json = bodyParser(request);
			resMap.put("json", json);
		}
		return resMap;
	}

	private void resWriter(JSONObject json, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.write(json.toString());
		writer.flush();
	}

	private JSONObject bodyParser(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			body.append(line);
		}
		reader.close();
		JSONObject content = new JSONObject(body.toString());
		return content;
	}
}
