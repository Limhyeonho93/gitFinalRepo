package kr.or.iei.cargo.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import kr.or.iei.cargo.model.service.CargoService;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class CargoSearchServlet
 */
@WebServlet("/srchCargo")
public class CargoSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargoSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] searchValue = request.getParameterValues("searchValue");
		String searchOption=request.getParameter("searchOption");
		
		HttpSession session = request.getSession(false); // false: 기존 세션만 가져옴
		User loginUser = (User) session.getAttribute("user");

		CargoService service=new CargoService();
		ArrayList<CargoMain> list=service.searchCargo(searchValue,searchOption,loginUser);
		
		//응답 데이터 JSON 변환. 백->프론트로 넘길때 객체로 넘길 수 없기 때문에 변환 필수
		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		response.getWriter().print(gson.toJson(list));  // 클라이언트에 JSON 응답
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
