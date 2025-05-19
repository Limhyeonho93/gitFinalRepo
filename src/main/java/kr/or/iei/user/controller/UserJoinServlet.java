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
 * Servlet implementation class UserJoinServlet
 */
@WebServlet("/user/userJoin")
public class UserJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserJoinServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 값 받아오고
		String compCd = request.getParameter("compCd");
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String userName = request.getParameter("userName");
		String deptName = request.getParameter("deptName");
		String telNo = request.getParameter("telNo");

		// 2. 회사 코드가 DB에 있는 회사인지 확인하고
		UserService service = new UserService();
		boolean isValidCompCd = service.isValidCompanyCode(compCd);

		// 없으면 그냥 빠꾸 빠꾸 하아아악씨시
		if (!isValidCompCd) {
			request.setAttribute("title", "회원가입 실패");
			request.setAttribute("msg", "유효하지 않은 회사 코드입니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/user/userJoinFrm");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			view.forward(request, response);
			return;
		}

		// 회원가입 처리
		User user = new User(userId, compCd, userPw, userName, deptName, telNo, null, null,"2");
		int result = service.insertUser(user);

		// 4. 결과 처리
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		if (result > 0) {
			request.setAttribute("title", "회원가입 성공");
			request.setAttribute("msg", "회원가입이 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/user/loginFrm");
		} else {
			request.setAttribute("title", "회원가입 실패");
			request.setAttribute("msg", "회원가입 중 오류가 발생했습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/user/userJoinFrm");
		}
		view.forward(request, response);

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
