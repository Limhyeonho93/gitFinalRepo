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
@WebFilter("/noWay")
public class LoginFilter extends HttpFilter implements Filter {

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public LoginFilter() {
		super();
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 사용자가 로그인을 하지않고 url로 접근할 때 로그인 세션 정보를 확인하고 없을 경우 로그인 페이지로 빼주기 위한 필터
		// 로그인, 로그아웃 페이지가 없어서 오류뜰듯
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 로그인 검증이 필요없는 페이지(로그인페이지, 로그아웃페이지)일 때는 예외처리
		String uri = req.getRequestURI();


		// 사용자가 /user/loginForm에 접근할 때 필터가 요청을 가로채서 세션이 없다고 판단하고 다시 /user/loginForm으로
		// 리다이렉트 (무한루프) ->예외처리
		// 정적리소스(/resources/)도 예외처리를 해주지 않으면 동작 불가 (이미지가 꺠지거나 스크립트 오류가 발생할 수 있음)
		
		boolean isExcluded = uri.equals("/") 
			    || uri.equals(req.getContextPath() + "/user/login") 
			    || uri.equals(req.getContextPath() + "/user/logout")
			    || uri.equals(req.getContextPath() + "/user/loginFrm") 
			    || uri.equals(req.getContextPath() + "/user/companyJoin") 
			    || uri.equals(req.getContextPath() + "/user/companyJoinFrm") 
			    || uri.equals(req.getContextPath() + "/user/userJoin") 
	            || uri.equals(req.getContextPath() + "/user/userJoinFrm") 
			    || uri.startsWith(req.getContextPath() + "/resources/");

		
		if (uri.startsWith("/resources/")) {
		    chain.doFilter(request, response); // 필터 통과
		    return;
		}

		if (isExcluded) {
			chain.doFilter(request, response);
			return;
		}

		// 로그인 안하고 접근했을때 이동 및 안내 메세지
		HttpSession session = req.getSession(false);
		
		if (session == null || session.getAttribute("user") == null) {
			// 로그인 정보 없으면 로그인 페이지로 이동
            req.getSession().setAttribute("errorMessage", "로그인이 필요한 섹션입니다. 로그인 화면으로 이동합니다.");
			resp.sendRedirect(req.getContextPath() + "/user/login");
			return;
		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
}