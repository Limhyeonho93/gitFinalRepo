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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 // 1. 파라미터 추출
        String userId = request.getParameter("userId");
        String compCd = request.getParameter("compCd");
        String userPw = request.getParameter("userPw");
        String userName = request.getParameter("userName");
        String deptName = request.getParameter("deptName");
        String telNo = request.getParameter("telNo");
        String userLevel = request.getParameter("userLevel");

        // 2. User 객체 생성 및 값 설정
        User user = new User();
        user.setUserId(userId);
        user.setCompCd(compCd);
        user.setUserPw(userPw);
        user.setUserName(userName);
        user.setDeptName(deptName);
        user.setTelNo(telNo);
        user.setUserLevel(userLevel); // 기본값: 일반 사용자
        

        // 3. 서비스 호출하여 회원가입 처리
        UserService service = new UserService();
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}