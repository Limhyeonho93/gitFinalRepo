package kr.or.iei.user.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import kr.or.iei.tracking.model.vo.TrackingJoin;
import kr.or.iei.user.model.service.CustomerInfoService;
import kr.or.iei.user.model.vo.Company;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class CustomerInfoGrid
 */
@WebServlet("/user/getCustomerGrid")
public class CustomerInfoGrid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerInfoGrid() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerInfoService service = new CustomerInfoService();
		
		HttpSession session = request.getSession(false); // 기존 세션만 가져옴
		User loginUser = (User) session.getAttribute("user");
		
		ArrayList<Company> arr = new ArrayList<Company>();
		if(loginUser.getUserLevel().equals("1")) {
			arr = service.getCustomerInfo();
		}else {
			arr = service.getSelerCustomerInfo(loginUser);
		}

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
