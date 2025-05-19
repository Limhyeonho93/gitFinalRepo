package kr.or.iei.cargo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cargo.model.service.CargoService;
import kr.or.iei.cargo.model.vo.CargoMain;

/**
 * Servlet implementation class UpdateCargoDetailsServlet
 */
@WebServlet("/cargo/updateCargoDetails")
public class UpdateCargoDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCargoDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     // 한글 처리
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        // 파라미터 추출
        String trackingNo = request.getParameter("trackingNo");
        String goodsName = request.getParameter("goodsName");
        String no = request.getParameter("no");
        String unitPrice = request.getParameter("unitPrice");
        String unitWeight = request.getParameter("unitWeight");
        String receiverAdd = request.getParameter("receiverAdd");
        String userId = request.getParameter("userId");
        String compCd = request.getParameter("compCd");

        // CargoMain 객체 생성
        CargoMain cargo = new CargoMain();
        cargo.setTrackingNo(trackingNo);
        cargo.setReceiverAdd(receiverAdd);
        cargo.setNo(Integer.parseInt(no));
        cargo.setReceiverName(goodsName);
        cargo.setUserId(userId);
        cargo.setCompCd(compCd);
        // 여기서 필요에 따라 다른 필드를 채울 수 있습니다.

        // CargoService 호출
        CargoService cargoService = new CargoService();
        boolean success = cargoService.updateCargoDetails(cargo);

        // 결과 반환
        PrintWriter out = response.getWriter();
        out.print("{\"success\": " + success + "}");
        out.flush();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
