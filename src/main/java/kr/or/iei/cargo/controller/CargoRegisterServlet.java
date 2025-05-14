package kr.or.iei.cargo.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.or.iei.cargo.model.service.CargoService;
import kr.or.iei.cargo.model.vo.CargoMain;

/**
 * Servlet implementation class cargoRegister
 */
@WebServlet("/cargo/cargoRegister")
public class CargoRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CargoRegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 파라미터 받아오기
        String compCd = request.getParameter("compCd");
        String warehouseMoveid = request.getParameter("warehouseMoveid");
        String trackingNo = request.getParameter("trackingNo");
        String manageNo = request.getParameter("manageNo");
        String receiverName = request.getParameter("receiverName");
        String receiverAdd = request.getParameter("receiverAdd");
        String receiverZip = request.getParameter("receiverZip");
        String receiverTel = request.getParameter("receiverTel");
        String sellerName = request.getParameter("sellerName");
        String sellerAdd = request.getParameter("sellerAdd");
        String sellerTel = request.getParameter("sellerTel");

        // gw 값 처리: null 또는 빈 문자열이면 기본값 0
        String gwParam = request.getParameter("gw");
        int gw = 0; // 기본값 0 설정
        if (gwParam != null && !gwParam.isEmpty()) {
            try {
                gw = Integer.parseInt(gwParam);
            } catch (NumberFormatException e) {
                // 오류 발생 시 기본값을 0으로 설정
                gw = 0;
            }
        }

        String gwt = request.getParameter("gwt");

        // no 값 처리: null 또는 빈 문자열이면 기본값 0
        String noParam = request.getParameter("no");
        int no = 0; // 기본값 0 설정
        if (noParam != null && !noParam.isEmpty()) {
            try {
                no = Integer.parseInt(noParam);
            } catch (NumberFormatException e) {
                // 오류 발생 시 기본값을 0으로 설정
                no = 0;
            }
        }

        String deliveryStop = request.getParameter("deliveryStop");
        String UserId = request.getParameter("UserId");
        String regDate = request.getParameter("regDate");
        String updDate = request.getParameter("updDate");

        // CargoMain 객체 생성
        CargoMain cargo = new CargoMain();
        cargo.setCompCd(compCd);
        cargo.setWarehouseMoveid(warehouseMoveid);
        cargo.setTrackingNo(trackingNo);
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
        cargo.setUserId(UserId);
        cargo.setRegDate(regDate);
        cargo.setUpdDate(updDate);

        // 서비스 호출
        CargoService service = new CargoService();
        int result = service.insertCargo(cargo);
        
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/cargo/cargoRegister.jsp");
	      if(result >0 ) {
	    	  request.setAttribute("title", "성공");
	    	  request.setAttribute("msg", "등록 성공하였습니다.");
	    	  request.setAttribute("icon", "success");
	    	  request.setAttribute("loc", "/cargo/cargoList");
	      }else {
	    	  request.setAttribute("title", "실패");
	    	  request.setAttribute("msg", "등록 중, 오류가 발생하였습니다.");
	    	  request.setAttribute("icon", "error");
	    	  request.setAttribute("loc", "/cargo/cargoRegister");
	      }
	      request.setAttribute("loc", "/cargo/view?trackingNo=" + trackingNo);
	      
	      view.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}