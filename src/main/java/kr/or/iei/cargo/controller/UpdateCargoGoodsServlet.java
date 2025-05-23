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
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class UpdateCargoGoodsServlet
 */
@WebServlet("/cargo/updCargoGoodsDetails")
public class UpdateCargoGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCargoGoodsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String trackingNo = request.getParameter("trackingNo");
		int seq = Integer.parseInt(request.getParameter("seq"));
		String compCd=request.getParameter("compCd");
		String goodsName = request.getParameter("goodsName");
		int unitPrice = Integer.parseInt(request.getParameter("unitPrice"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		float unitWeight = Float.parseFloat(request.getParameter("unitWeight"));
		String deliveryStop = request.getParameter("deliveryStop");
		
		
		CargoGoods goods = new CargoGoods();
        goods.setTrackingNo(trackingNo);
        goods.setSeq(seq);
        goods.setCompCd(compCd);
        goods.setGoodsName(goodsName);
        goods.setUnitPrice(unitPrice);
        goods.setQty(qty);
        goods.setUnitWeight(unitWeight);
        goods.setDeliveryStop(deliveryStop);
        HttpSession session = request.getSession();
        goods.setUserId(((User) session.getAttribute("user")).getUserId()); // 세션에 저장되어있던 아이디
        
        CargoService cargoService=new CargoService();
        int result=cargoService.updCargoGoodsDetail(goods);
        
        PrintWriter out = response.getWriter();
        out.print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
