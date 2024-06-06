package server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletFilter implements Filter {

    public ServletFilter() {

    }

	@Override
	public void destroy() {

	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Object userId = req.getSession().getAttribute("userId");
		Object userType = req.getSession().getAttribute("userType");
		Object employeeRole = req.getSession().getAttribute("employeeRole");
		String route = req.getPathInfo();
		switch(route) {
		case "/login":
			if(userId!=null){
				res.sendRedirect("/Continental/app/home");
				return;
			}
			break;
		default:
			if(userId==null) {
				res.sendRedirect("/Continental/app/login");
				return;
			}
			if(route.startsWith("/employee")){
				if(!userType.toString().equals("employee")){
					res.sendError(404);
					return;
				}
			}
			if(route.startsWith("/admin")){
				if(!employeeRole.toString().equals("admin")){
					res.sendError(404);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}
	@Override
	public void init(FilterConfig fConfig) throws ServletException {


	}
}
