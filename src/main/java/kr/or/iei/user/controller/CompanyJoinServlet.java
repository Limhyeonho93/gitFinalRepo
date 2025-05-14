package kr.or.iei.user.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.user.model.service.UserService;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class JoinServlet
 */
@WebServlet("/user/join")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 인코딩 - 필터 
		// 2. 값 추출
		String userId = request.getParameter("userId");
		String compCd = request.getParameter("compCd");
		String userPw = request.getParameter("userPw");
		String userName = request.getParameter("userName");
		String deptName = request.getParameter("deptName");
		String telNo = request.getParameter("telNo");
		
		// 3. 로직
		User user = new User();
		user.setUserId(userId);
		user.setCompCd(compCd);
		user.setUserPw(userPw);
		user.setUserName(userName);
		user.setDeptName(deptName);
		user.setTelNo(telNo);
		
		UserService service = new UserService();
		int result = service.insertUser(user);
		
		// 4. 결과 처리
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		
		if(result > 0) {
			request.setAttribute("title", "회원가입 성공");
			request.setAttribute("msg", "회원가입이 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/");
		}else {
			request.setAttribute("title", "회원가입 실패");
			request.setAttribute("msg", "회원가입에 실패하였습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/");
		}
		//4.3 페이지 이동
		view.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
