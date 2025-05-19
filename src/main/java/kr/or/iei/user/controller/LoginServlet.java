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
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // 1. 인코딩 - 필터
      // 2. 값 추출
      String userId = request.getParameter("userId");
      String userPw = request.getParameter("userPw");
      
       // 3. 로직 - Null 체크 추가 (아이디와 비밀번호가 null인지 확인)
       if (userId == null || userPw == null) {
           RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
           request.setAttribute("title", "로그인 실패");
           request.setAttribute("msg", "아이디와 비밀번호를 입력해주세요.");
           request.setAttribute("icon", "error");
           request.setAttribute("loc", "/user/loginFrm");
           view.forward(request, response);
           return;
       }

      // 3.1 로직 - 입력한 정보와 DB에 있는 정보 비교
      UserService service = new UserService();
      User loginUser = null; // 로그인시 관리자인지 아닌지 구별을 위해 null로 초기화

      // 3.2 관리자 로그인 처리
      if ("admin".equals(userId)) {
         if ("12".equals(userPw)) { // 관리자 비밀번호 확인
            loginUser = new User();
            loginUser.setUserId("admin");
            loginUser.setUserName("관리자");
         }
      } else {
         // 일반 사용자 이메일 형식 검증
         if (userId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            loginUser = service.loginUser(userId, userPw);
         }
      }

      // 4. 일반회원 로그인 처리
      if (loginUser == null) { // 로그인 실패
         // 일반 회원 로그인 실패 시 이동할 페이지는 여기로
         RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp"); // 보여질 공통 메세지 코드 한번에 작성

         request.setAttribute("title", "???");
         request.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
         request.setAttribute("icon", "error.png");
         request.setAttribute("loc", "/user/loginFrm");
         view.forward(request, response);
      } else { // 일반회원 로그인 성공
         request.getSession().setAttribute("user", loginUser);
         response.sendRedirect(request.getContextPath() + "/index.jsp");
      }

   }


   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      doGet(request, response);
   }

}
