package kr.or.iei.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.user.model.service.CustomerInfoService;
import kr.or.iei.user.model.vo.Company;

/**
 * Servlet implementation class CustomerSaveServlet
 */
@WebServlet("/user/saveCompany")
public class CustomerSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerSaveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 파라미터 꺼내기
				String mode = request.getParameter("mode");

				Company c = new Company();
				c.setComp_cd(request.getParameter("compCd"));
				c.setComp_name(request.getParameter("compName"));
				c.setComp_addr(request.getParameter("compAddr"));
				c.setComp_zip(request.getParameter("compZip"));
				c.setComp_tel(request.getParameter("compTel"));
				if("KHAC000000".equals(request.getParameter("compCd"))) {
					c.setComp_div("L");
					c.setGrade('1'); // 관리자
				}else {
					c.setComp_div("S");
					c.setGrade('2'); // 기본등급
				}
				c.setUserId(request.getParameter("userId"));
				c.setDeal_flg('1'); // 기본값 설정
				
				CustomerInfoService service = new CustomerInfoService();
				int result = 0;
				if ("insert".equals(mode)) {
					result = service.insertCustomerInfo(c);
				} else if ("update".equals(mode)) {
					result = service.updateCustomerInfo(c);
				}
				
				response.setContentType("text/plain; charset=UTF-8");
				response.getWriter().write(result > 0 ? "success" : "fail");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
