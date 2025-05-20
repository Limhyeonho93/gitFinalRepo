package kr.or.iei.invoice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.invoice.model.service.ShoppingService;

/**
 * Servlet implementation class InsertInvoice
 */
@WebServlet("/invoice/insertShopping")
public class InsertShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertShoppingServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String disGrade = request.getParameter("disGrade");
		int weight = Integer.parseInt(request.getParameter("weight"));

		// grade값을 지역명으로 변환
		String regionName = "";
		String dbDisGrade = "";

		switch (disGrade) {
		case "A":
			dbDisGrade = "A";
			regionName = "수도권";
			break;
		case "B":
			dbDisGrade = "B";
			regionName = "충청/강원";
			break;
		case "C":
			dbDisGrade = "C";
			regionName = "경상/전라";
			break;
		case "D":
			dbDisGrade = "D";
			regionName = "제주/도서산간";
			break;
		}

		// 서비스 호출
		ShoppingService service = new ShoppingService();
		int total = service.calculateCost(dbDisGrade, weight);

		// 결과를 request에 담아 JSP로 전달
		request.setAttribute("regionName", disGrade);
		request.setAttribute("grade", disGrade);
		request.setAttribute("regionName", regionName);
		request.setAttribute("total", total);
		request.getRequestDispatcher("/WEB-INF/views/invoice/insertShopping.jsp").forward(request, response);

		request.setCharacterEncoding("UTF-8");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
