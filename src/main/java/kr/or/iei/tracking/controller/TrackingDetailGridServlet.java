package kr.or.iei.tracking.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.or.iei.cargo.model.service.CargoService;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.tracking.model.service.TrackingService;
import kr.or.iei.tracking.model.vo.TrackingJoin;

/**
 * Servlet implementation class TrackingDetailGridServlets
 */
@WebServlet("/tracking/trackingDetail")
public class TrackingDetailGridServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrackingDetailGridServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String manageNo = request.getParameter("manageNo");
		// 어레이로 받기위해서 getParameterValues
		TrackingService service = new TrackingService();

		ArrayList<TrackingJoin> arr = service.getTrackingDetail(manageNo);

		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		response.getWriter().print(gson.toJson(arr)); // 클라이언트에 JSON 응답
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
