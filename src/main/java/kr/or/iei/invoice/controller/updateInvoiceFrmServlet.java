package kr.or.iei.invoice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class updateInvoiceFrmServlet
 */
@WebServlet("/invoice/updateInvoiceFrm")
public class updateInvoiceFrmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateInvoiceFrmServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		// 2. 요청시 전달값 꺼내기
		String disGrade = request.getParameter("disGrade");
		int weight = Integer.parseInt(request.getParameter("weight"));
		
		// 3. JSP로 포워딩
		request.setAttribute("disGrade", disGrade);
		request.setAttribute("weight", weight);
		
		request.getRequestDispatcher("/WEB-INF/views/invoice/insertShopping.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
