package kr.or.iei.invoice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.invoice.model.service.ShoppingService;

/**
 * Servlet implementation class UpdateShoppingCostServlet
 */
@WebServlet("/invoice/saveShoppingCost")
public class SaveShoppingCostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveShoppingCostServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String disGrade = request.getParameter("disGrade");
		int weight = Integer.parseInt(request.getParameter("weight"));
		int adPrice = Integer.parseInt(request.getParameter("adPrice"));

		ShoppingService service = new ShoppingService();
		int basePrice = service.calculateCost(disGrade, weight);
		int totalPrice = basePrice + adPrice;

		boolean result = service.saveShoppingCost(disGrade, weight, totalPrice);

		if (result) {
			request.setAttribute("regionName", disGrade);
			request.setAttribute("weight", weight);
			request.setAttribute("basePrice", basePrice);
			request.setAttribute("adPrice", adPrice);
			request.setAttribute("totalPrice", totalPrice);
			request.getRequestDispatcher("/WEB-INF/views/invoice/success.jsp").forward(request, response);
		} else {
			response.sendRedirect("/invoice/failure");
		}
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
