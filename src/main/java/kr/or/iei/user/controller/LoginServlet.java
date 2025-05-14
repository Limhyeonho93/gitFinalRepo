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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String userPw = request.getParameter("userPw");
		
		// 3. 로직 - 입력한 정보와 DB에 있는 정보 비교
		UserService service = new UserService();
		User loginUser = service.loginUser(userId, userPw);
		
		// 4. 결과처리
		RequestDispatcher view = null;
		
		if(loginUser == null) { // 로그인 실패
			// 로그인 실패 시 이동할 페이지는 여기로
			view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			// 화면에 보여줄거
			request.setAttribute("title", "???");
			request.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
			request.setAttribute("icon", "error.png");
			request.setAttribute("loc", "/user/loginFrm");
		}else { // Login successful
            request.getSession().setAttribute("user", loginUser);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
		}
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
