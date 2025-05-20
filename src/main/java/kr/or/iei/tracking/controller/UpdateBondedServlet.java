package kr.or.iei.tracking.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.tracking.model.service.TrackingService;

/**
 * Servlet implementation class UpdateBondedServlets
 */
@WebServlet("/bonded/updateBonded")
public class UpdateBondedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBondedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchDate = request.getParameter("searchDate");
		String wareCd = request.getParameter("wareCd");
		String updateColumn = request.getParameter("updateColumn");
		
		TrackingService service = new TrackingService();
		int res = service.updateBondedData(searchDate, wareCd, updateColumn);
		
		if(res > 0) {
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
