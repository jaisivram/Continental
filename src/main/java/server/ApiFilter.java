package server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.utils.LoggerProvider;
public class ApiFilter implements Filter {
	private Logger logger = LoggerProvider.getLogger();
    public ApiFilter() {

    }

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		logger.info("IP :"+req.getRemoteHost()+"  URL:"+req.getRequestURL());
		HttpServletResponse res = (HttpServletResponse) response;
		String route = req.getPathInfo();
		if(route==null) {
			res.sendError(404);
			return;
		}
		String apiKey = req.getHeader("APIKEY");
		if(apiKey==null) {
			res.sendError(400);
			return;
		}
		Local.setApi(apiKey);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
