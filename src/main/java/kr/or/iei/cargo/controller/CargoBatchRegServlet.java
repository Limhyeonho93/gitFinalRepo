package kr.or.iei.cargo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.or.iei.common.RenamePolicy;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class CargoBatchRegServlet
 */
@WebServlet("/cargo/cargoBatchReg")
@MultipartConfig
public class CargoBatchRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargoBatchRegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 InputStream is = request.getPart("cargoFile").getInputStream();  // "cargoFile" 파라미터 이름으로 파일을 받음
		 
		 //세션에서 회사코드와 사내 관리번호 가져옴 
		 HttpSession session = request.getSession();
		 String compCd =((User) session.getAttribute("user")).getCompCd();
	     String userId= ((User) session.getAttribute("user")).getUserId();
	     
	     System.out.println(compCd);
	     System.out.println(userId);

		 // CargoMainImport 클래스 객체 생성
         CargoMainImport cargoImport = new CargoMainImport();

         
         // 엑셀 파일을 처리하는 메서드 호출
         // InputStream을 직접 전달
         String resultMessage=cargoImport.readExcel(is, compCd, userId);
         
         //처리 완료 후 응답 메시지 반환
         RequestDispatcher view=request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
         
         request.setAttribute("title", "알림");
         request.setAttribute("msg", resultMessage);
		 request.setAttribute("icon", "error");
		 if(resultMessage.equals("엑셀 파일이 업로드 되었습니다.")) {
			 request.setAttribute("icon", "success");
		 }
		 request.setAttribute("loc", "/cargo/cargoBatchRegFrm");
		 
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
