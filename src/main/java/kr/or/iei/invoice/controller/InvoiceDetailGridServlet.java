package kr.or.iei.invoice.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.or.iei.invoice.model.service.InvoiceService;
import kr.or.iei.invoice.model.vo.CargoUnitInvoice;
import kr.or.iei.invoice.model.vo.Invoice;

/**
 * Servlet implementation class InvoiceDetailGridServlet
 */
@WebServlet("/invoice/dataDetailGrid")
public class InvoiceDetailGridServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InvoiceDetailGridServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String compCd = request.getParameter("compCd");
		String from = request.getParameter("from");
		String to = request.getParameter("to");

		InvoiceService service = new InvoiceService();

		ArrayList<CargoUnitInvoice> arr = service.trackingNoInvoice(from, to, compCd);
		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		response.getWriter().print(gson.toJson(arr)); // 클라이언트에 JSON 응답
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
