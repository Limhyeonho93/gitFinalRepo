package kr.or.iei.cargo.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cargo.model.service.CargoService;

/**
 * Servlet implementation class DeleteMultiCargoServlet
 */
@WebServlet("/cargo/DeleteMultiCargo")
public class DeleteMultiCargoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMultiCargoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");

		 // 프론트에서 배열로 보낸 데이터 받기
		 String[] trackingNos = request.getParameterValues("trackingNos");

		 boolean result = false;

		 if (trackingNos != null && trackingNos.length > 0) {
		     CargoService service = new CargoService(); // 또는 필드로 빼도 됨
		     result = service.deleteMultipleTrackingNos(Arrays.asList(trackingNos));
		 }

		 // 응답 JSON 반환
		 response.setContentType("application/json; charset=UTF-8");
		 response.getWriter().write("{\"success\": " + result + "}");
		
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
