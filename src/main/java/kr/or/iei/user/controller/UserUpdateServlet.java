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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/user/update")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("userName");
		String telNo = request.getParameter("telNo");
		String userId = request.getParameter("userId");
		
		User updUser = new User();
		updUser.setUserName(userName);
		updUser.setTelNo(telNo);
		updUser.setUserId(userId);
		
		UserService service = new UserService();
		int result = service.updateUser(updUser);
		
		
		//이동할 페이지
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		
		if(result > 0) {
			
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "수정되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/user/userUpdateFrm");
			
			HttpSession session = request.getSession(false); //세션 있으면 존재하는 세션. 없으면 null 반환
			if(session != null) {
				User loginUser = (User) session.getAttribute("user"); //로그인 했을 때, 등록한 회원 정보
				
				//세션 정보 업데이트
				loginUser.setUserName(userName);
				loginUser.setTelNo(telNo);
				loginUser.setUserId(userId);
				
				
			}
		}else {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "회원 정보 수정 중, 오류가 발생하였습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/user/myPage");
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
