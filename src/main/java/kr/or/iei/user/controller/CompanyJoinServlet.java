package kr.or.iei.user.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.user.model.service.UserService;
import kr.or.iei.user.model.vo.Company;

/**
 * Servlet implementation class JoinServlet
 */
@WebServlet("/user/companyJoin")
public class CompanyJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompanyJoinServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		 String compCd = request.getParameter("compCd");
		    String password = request.getParameter("password");
		    String compName = request.getParameter("compName");
		    String email = request.getParameter("email");
		    String compAddr = request.getParameter("compAddr");
		    String compTel = request.getParameter("compTel");

		    Company company = new Company();
		    company.setComp_cd(compCd);
		    company.setComp_name(compName);
		    company.setEmail(email);
		    company.setComp_addr(compAddr);
		    company.setComp_tel(compTel);

		    UserService service = new UserService();
		    int result = service.registerCompany(company, password);

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
		        request.setAttribute("loc", "/user/companyJoinFrm");
		    }
		    view.forward(request, response);

		
		
		
		
		
//		// 1. 인코딩 - 필터
//		// 2. 값 추출
//		String compCd = request.getParameter("compCd");
//	    String password = request.getParameter("password");
//		String compName = request.getParameter("compName");
//		String grade = request.getParameter("grade");
//	    String email     = request.getParameter("email");
//		String compAddr = request.getParameter("compAddr");
//		String postalCode = request.getParameter("postalCode");
//		String compTel = request.getParameter("compTel");
//		String transactionStatus = request.getParameter("transactionStatus");
//		String regDate = new java.sql.Date(System.currentTimeMillis()).toString(); // 현재 날짜
//		String updDate = null; // 초기값 null로 설정 == 관리자가 나중에 수정할수 있도록 할거임
//
//		
//		// 2. 입력값 검증
//		// 회사 코드 검증
//		if (compCd == null || !compCd.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{1,10}$")) {
//			request.setAttribute("title", "회원가입 실패");
//			request.setAttribute("msg", "회사 코드는 글자와 숫자가 섞인 최대 10글자여야 합니다.");
//			request.setAttribute("icon", "error");
//			request.setAttribute("loc", "/user/CompanyJoinFrm");
//			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
//			view.forward(request, response);
//			return;
//		}
//
//		// 3. 비즈니스 로직 처리
//        Company company = new Company();
//        
//        company.setComp_cd(compCd);
//        company.setComp_name(compName);
//        company.setEmail(email);
//        company.setComp_addr(compAddr);
//        company.setComp_zip(postalCode);
//        company.setComp_tel(compTel);
//        company.setDeal_flg(transactionStatus.charAt(0));
//        company.setGrade(grade.charAt(0));
//        company.setReg_date(java.sql.Date.valueOf(regDate));
//        company.setUpd_date(null); // 초기값 null로 설정
//
//        UserService service = new UserService();
//        int result = service.insertCompany(company, password);
//
//        // 4. 결과 처리
//        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
//        if (result > 0) {
//            request.setAttribute("title", "회원가입 성공");
//            request.setAttribute("msg", "회원가입이 완료되었습니다.");
//            request.setAttribute("icon", "success");
//            request.setAttribute("loc", "/");
//        } else {
//            request.setAttribute("title", "회원가입 실패");
//            request.setAttribute("msg", "회원가입에 실패하였습니다.");
//            request.setAttribute("icon", "error");
//            request.setAttribute("loc", "/user/companyJoinFrm");
//        }
//        view.forward(request, response);

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
