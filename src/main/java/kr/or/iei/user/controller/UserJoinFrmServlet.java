package kr.or.iei.user.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.invoice.model.service.InvoiceService;
import kr.or.iei.invoice.model.vo.CompInfo;

/**
 * Servlet implementation class UserJoinFrmServlet
 */
@WebServlet("/user/userJoinFrm")
public class UserJoinFrmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserJoinFrmServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 인코딩 - 필터
		request.setCharacterEncoding("UTF-8");
		
		// 2. 뷰 페이지 포워딩
		InvoiceService service = new InvoiceService();
		
		ArrayList<CompInfo> compInfoArr = service.getAllComp();
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/user/userJoin.jsp");

		request.setAttribute("compInfoArr", compInfoArr);
		view.forward(request, response);

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
