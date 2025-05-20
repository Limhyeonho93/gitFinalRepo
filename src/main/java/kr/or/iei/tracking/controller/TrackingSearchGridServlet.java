package kr.or.iei.tracking.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import kr.or.iei.invoice.model.vo.Invoice;
import kr.or.iei.tracking.model.service.TrackingService;
import kr.or.iei.tracking.model.vo.DailyWarehouseSummary;
import kr.or.iei.tracking.model.vo.TrackingJoin;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class TrackingSearchGridServletss
 */
@WebServlet("/tracking/trackingGrid")
public class TrackingSearchGridServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrackingSearchGridServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchOption = request.getParameter("searchOption");
		// 어레이로 받기위해서 getParameterValues
		String[] searchValue = request.getParameterValues("searchValue");
		
		TrackingService service = new TrackingService();
		
		HttpSession session = request.getSession(false); // 기존 세션만 가져옴
		User loginUser = (User) session.getAttribute("user");
		
		ArrayList<TrackingJoin> arr = new ArrayList<TrackingJoin>();
		if(loginUser.getUserLevel().equals("1")) {
			arr = service.getTrackingGroup(searchOption, searchValue);
		}else {
			arr = service.getSelerTrackingGroup(searchOption, searchValue, loginUser);
		}
		

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
