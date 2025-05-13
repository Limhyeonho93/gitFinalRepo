package kr.or.iei.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public LoginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//사용자가 로그인을 하지않고 url로 접근할 때 로그인 세션 정보를 확인하고 없을 경우 로그인 페이지로 빼주기 위한 필터
		//로그인, 로그아웃 페이지가 없어서 오류뜰듯 
		 HttpServletRequest req = (HttpServletRequest) request;
		 HttpServletResponse resp = (HttpServletResponse) response;
		 
		 //로그인 검증이 필요없는 페이지(로그인페이지, 로그아웃페이지)일 때는 예외처리
		 String uri = req.getRequestURI();
		 
		 if(uri.equals("/user/login") || uri.equals("/user/logout")) { 
			 chain.doFilter(request, response);
			 
			 return;
		 }
		 
		 HttpSession session = req.getSession(false);
		 if(session == null || session.getAttribute("user") == null) {
			 // 로그인 정보 없으면 로그인 페이지로 이동
			 resp.sendRedirect(req.getContextPath() + "/user/login");
			 return;
		 }
		 
		 chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
