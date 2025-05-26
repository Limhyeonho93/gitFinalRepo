//package kr.or.iei.invoice.controller;
//
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import kr.or.iei.invoice.model.service.InvoiceService;
//import kr.or.iei.invoice.model.service.ShoppingService;
//
///**
// * Servlet implementation class UpdateInvoiceServlet
// */
//@WebServlet("/invoice/updateShopping")
//public class UpdateInvoiceServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public UpdateInvoiceServlet() {
//		super();
//	}
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		String totalParam = request.getParameter("total");
//		int total = 0;
//
//		try {
//			if (totalParam != null && !totalParam.isEmpty()) {
//				total = Integer.parseInt(totalParam);
//			} else {
//				throw new IllegalArgumentException("Total 값이 전달되지 않았습니다.");
//			}
//
//			// 2. 서비스 호출
//			ShoppingService service = new ShoppingService();
//			int result = service.updateInvoice(total);
//
//			// 3. 결과 처리
//			if (result > 0) {
//				response.sendRedirect("/"); // 성공
//			} else {
//				response.sendRedirect("/msg.jsp"); // 실패
//			}
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Total 값이 올바른 숫자가 아닙니다.");
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
//		}
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		doGet(request, response);
//	}
//
//}
