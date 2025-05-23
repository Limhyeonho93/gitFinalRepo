package kr.or.iei.cargo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.cargo.model.service.CargoService;
import kr.or.iei.cargo.model.vo.CargoGoods;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.user.model.vo.User;

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
		
		
		// 파라미터 추출
        String trackingNo = request.getParameter("trackingNo");
        String compCd = request.getParameter("compCd");
        String warehouseMoveid=request.getParameter("warehouseMoveid");
        String manageNo = request.getParameter("manageNo");
        String receiverName = request.getParameter("receiverName");
        String receiverAdd = request.getParameter("receiverAdd");
        String receiverZip = request.getParameter("receiverZip");
        String receiverTel = request.getParameter("receiverTel");
        String sellerName = request.getParameter("sellerName");
        String sellerAdd = request.getParameter("sellerAdd");
        String sellerTel = request.getParameter("sellerTel");
        Float gw = Float.parseFloat(request.getParameter("gw"));
        String gwt = request.getParameter("gwt");
        int no = Integer.parseInt(request.getParameter("no"));
        String deliveryStop = request.getParameter("deliveryStop");
        
        
        // CargoMain 객체 생성
        CargoMain cargo = new CargoMain();
        cargo.setTrackingNo(trackingNo);
        cargo.setCompCd(compCd);
        cargo.setWarehouseMoveid(warehouseMoveid);
        cargo.setManageNo(manageNo);
        cargo.setReceiverName(receiverName);
        cargo.setReceiverAdd(receiverAdd);
        cargo.setReceiverZip(receiverZip);
        cargo.setReceiverTel(receiverTel);
        cargo.setSellerName(sellerName);
        cargo.setSellerAdd(sellerAdd);
        cargo.setSellerTel(sellerTel);
        cargo.setGw(gw);
        cargo.setGwt(gwt);
        cargo.setNo(no);
        cargo.setDeliveryStop(deliveryStop);
        HttpSession session = request.getSession();
        cargo.setUserId(((User) session.getAttribute("user")).getUserId()); // 세션에 저장되어있던 아이디
        
        //deliveryStop가 Y로 바뀔 시 CargoGoods에도 반영 위해 // 이거 구현 여부 생각중
        CargoGoods goods = new CargoGoods();
        goods.setCompCd(compCd);
        goods.setTrackingNo(trackingNo);
        goods.setDeliveryStop(deliveryStop);
        goods.setUserId((String) session.getAttribute("userId"));
        
        // CargoService 호출
        CargoService cargoService = new CargoService();
        int result = cargoService.updateCargoDetails(cargo,goods);
        
        PrintWriter out = response.getWriter();
        out.print(result);
        
        // 결과 반환
        /*
        PrintWriter out = response.getWriter();
        out.print("{\"success\": " + success + "}");
        out.flush();
        */
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
