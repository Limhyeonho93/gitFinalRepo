
package kr.or.iei.cargo.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import kr.or.iei.cargo.model.service.CargoService;
import kr.or.iei.cargo.model.service.ManageNoService;
import kr.or.iei.cargo.model.vo.CargoGoods;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.user.model.vo.User;

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

        String trackingNo = request.getParameter("trackingNo");
        String receiverName = request.getParameter("receiverName");
        String receiverAdd = request.getParameter("receiverAdd");
        String receiverZip = request.getParameter("receiverZip");
        String receiverTel = request.getParameter("receiverTel");
        String sellerName = request.getParameter("sellerName");
        String sellerAdd = request.getParameter("sellerAdd");
        String sellerTel = request.getParameter("sellerTel");
        
        String goodsName = request.getParameter("goodsName");
        int qty = Integer.parseInt(request.getParameter("qty"));
        int unitPrice = Integer.parseInt(request.getParameter("unitPrice"));

        // gw 값 처리: null 또는 빈 문자열이면 기본값 0 // 이거 jsp에서 검증하고 넘어오면 필요없지 않나? 
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

        
        //창고이동ID생성
        ManageNoService mns = new ManageNoService();
        String wareCdId = mns.findZipWareCd(receiverZip);
        
        //세션에서 회사코드와 사내 관리번호 가져옴 

		HttpSession session = request.getSession();
		//String compCd =((User) session.getAttribute("user")).getCompCd();
	    String userId= ((User) session.getAttribute("user")).getUserId();



        // CargoMain 객체 생성
        CargoMain cargo = new CargoMain();
        cargo.setCompCd(compCd);
        cargo.setWarehouseMoveid(wareCdId);
        cargo.setTrackingNo(trackingNo);
        cargo.setReceiverName(receiverName);
        cargo.setReceiverAdd(receiverAdd);
        cargo.setReceiverZip(receiverZip);
        cargo.setReceiverTel(receiverTel);
        cargo.setSellerName(sellerName);
        cargo.setSellerAdd(sellerAdd);
        cargo.setSellerTel(sellerTel);
        cargo.setGw(gw);
        cargo.setGwt(gwt);
        cargo.setNo(1); // 화물 단 건 등록이니 총 화물 개수는 1
        cargo.setDeliveryStop("N");
        cargo.setUserId(userId);
        
        // CargoGoods 객체 생성
        CargoGoods goods=new CargoGoods();
        goods.setCompCd(compCd); 
        goods.setWarehouseMoveid(wareCdId);
        goods.setTrackingNo(trackingNo);
        goods.setSeq(1); // 화물 단 건 등록이니 시퀀스 1
        goods.setGoodsName(goodsName); 
        goods.setUnitPrice(unitPrice);
        goods.setQty(qty); //상품개수
        goods.setUnitWeight(gw); // 중량
        goods.setDeliveryStop("N");
        goods.setUserId(userId); 

        // 서비스 호출
        CargoService service = new CargoService();
        int result = service.insertCargo(cargo,goods);
        
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
	    if(result>0) {
	    	request.setAttribute("title", "성공");
	    	request.setAttribute("msg", "화물 등록 완료");
	    	request.setAttribute("icon", "success");
	    	request.setAttribute("loc", "/cargo/search");
	    }else {
	    	request.setAttribute("title", "실패");
	    	request.setAttribute("msg", "화물 등록 중 오류가 발생되었습니다.");
	    	request.setAttribute("icon", "error");
	    	request.setAttribute("loc", "/cargo/cargoRegister");
	    }
	      
	    view.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}