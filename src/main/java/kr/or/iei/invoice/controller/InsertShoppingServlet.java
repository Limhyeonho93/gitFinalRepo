package kr.or.iei.invoice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

		String action = request.getParameter("action");
		ShoppingService service = new ShoppingService();
		
		String disGrade = request.getParameter("disGrade");
		String weightParam = request.getParameter("weight");

		if ("retrieve".equals(action)) {
			System.out.println("retrieve");

			// 조회 처리
			int weight = 0; // 기본값 설정

			if (weightParam != null && !weightParam.trim().isEmpty()) {
				try {
					weight = Integer.parseInt(weightParam.trim());
				} catch (NumberFormatException e) {
					weight = 0; // 숫자로 변환 실패 시 기본값 유지
				}
			}

			String regionName = service.getRegionName(disGrade);
			int price = service.calculateCost(disGrade, weight);
			
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(String.format("{\"data\": {\"regionName\": \"%s\", \"price\": %d}}", regionName, price));
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");

			System.out.println("update");
			// 업데이트 처리
			int total = Integer.parseInt(request.getParameter("total"));
			boolean success = service.updateShoppingData(total,disGrade,weightParam);

			request.setAttribute("title", "성공");
	    	request.setAttribute("msg", "요금 수정 완료");
	    	request.setAttribute("icon", "success");
	    	request.setAttribute("loc", "/invoice/insertShoppingFrm");

			view.forward(request, response);
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