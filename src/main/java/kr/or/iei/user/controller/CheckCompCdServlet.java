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
 * Servlet implementation class CheckCompCdServlet
 */
@WebServlet("/user/checkCompCd")
public class CheckCompCdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckCompCdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String compCd = request.getParameter("compCd");
		
		CustomerInfoService service = new CustomerInfoService();
		
		Company comp = service.checkCompcd(compCd);
		
		if (comp != null) {
			// 이미 존재하는 회사코드
			response.getWriter().write("duplicate");
		} else {
			// 사용 가능한 회사코드
			response.getWriter().write("available");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
