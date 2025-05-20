package kr.or.iei.tracking.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.or.iei.tracking.model.service.TrackingService;
import kr.or.iei.tracking.model.vo.DailyWarehouseSummary;

/**
 * Servlet implementation class InsertBondedServlets
 */
@WebServlet("/bonded/insertBond")
public class InsertBondedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertBondedServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchDate = request.getParameter("searchDate");
		String wareCd = request.getParameter("wareCd");

		TrackingService service = new TrackingService();
		ArrayList<DailyWarehouseSummary> manageArr = service.getManageNoWithBonded(searchDate, wareCd);

		int res = 0;
		for (DailyWarehouseSummary ma : manageArr) {
			res = service.insertBondedData(ma.getManageNo(), ma.getWarehouseMoveid());

		}

		if (res > 0) {
			response.getWriter().print("success");
		} else {
			response.getWriter().print("fail");
		}

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
