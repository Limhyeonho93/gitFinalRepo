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

		 String action = request.getParameter("action");

	        ShoppingService service = new ShoppingService();

	        if ("retrieve".equals(action)) {
	            // 조회 처리
	            String disGrade = request.getParameter("disGrade");

	            // 무게가 빈값일때 조회시 에러 방지
	            String weightParam = request.getParameter("weight");
	            int weight = 0; // 기본값 빈값(널 방지)

	            if (weightParam != null && !weightParam.trim().isEmpty()) {
	                try {
	                    weight = Integer.parseInt(weightParam.trim());
	                } catch (NumberFormatException e) {
	                    weight = 0;
	                }
	            }

	            // grade값을 지역명으로 변환
	            String regionName = " ";

	            switch (disGrade) {
	                case "A":
	                    regionName = "수도권";
	                    break;
	                case "B":
	                    regionName = "충청/강원";
	                    break;
	                case "C":
	                    regionName = "경상/전라";
	                    break;
	                case "D":
	                    regionName = "제주/도서산간";
	                    break;
	            }

	            // 기본 요금 계산
	            int price = service.calculateCost(disGrade, weight);

	            // JSON 응답 설정
	            response.setContentType("application/json");
	            response.getWriter().write(String.format("{\"regionName\": \"%s\", \"price\": %d}", regionName, price));

	        } else if ("update".equals(action)) {
	            // 업데이트 처리
	            int total = Integer.parseInt(request.getParameter("total"));

	            int result = service.updateInvoice(total);

	            // JSON 응답 설정
	            response.setContentType("application/json");
	            response.getWriter().write(String.format("{\"success\": %b}", result > 0));
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
