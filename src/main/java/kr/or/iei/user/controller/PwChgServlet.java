package kr.or.iei.user.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.user.model.service.UserService;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class pwChgServlet
 */
@WebServlet("/user/pwChg")
public class PwChgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PwChgServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userPw = request.getParameter("userPw");
		String newUserPw = request.getParameter("newUserPw");
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		
		User loginUser = (User)session.getAttribute("user");
		
		//사용자가 입력한 현재 비밀번호와 세션에 등록된 비밀번호가 다른경우
		if(!loginUser.getUserPw().equals(userPw)) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "현재 비밀번호가 일치하지 않습니다.");
			request.setAttribute("icon", "warning");
			request.setAttribute("callback", "self.close();");
			
			view.forward(request, response);
			return;
		}
			
			UserService service = new UserService();
			int result = service.updateUserPw(loginUser.getUserId(), newUserPw);
			
			if(result > 0) {
				request.setAttribute("title", "성공");
				request.setAttribute("msg", "비밀번호가 변경되었습니다. 변경된 비밀번호로 다시 로그인하세요.");
				request.setAttribute("icon", "success");
				request.setAttribute("loc", "/user/loginFrm");
				
				session.invalidate();
				
				
			}else {
				request.setAttribute("title", "실패");
				request.setAttribute("msg", "비밀번호 변경 처리 중, 오류가 발생하였습니다.");
				request.setAttribute("icon", "error");
				request.setAttribute("callback", "self.close();");
			}
			
				view.forward(request, response);
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
