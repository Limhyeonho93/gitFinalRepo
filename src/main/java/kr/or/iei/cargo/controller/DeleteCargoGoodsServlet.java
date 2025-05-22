package kr.or.iei.cargo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cargo.model.service.CargoService;
import kr.or.iei.cargo.model.vo.CargoGoods;

/**
 * Servlet implementation class DeleteCargoGoodsServlet
 */
@WebServlet("/cargo/delCargoGoodsServlet")
public class DeleteCargoGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCargoGoodsServlet() {
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
		
		CargoGoods goods = new CargoGoods();
        goods.setTrackingNo(trackingNo);
        goods.setSeq(seq);
        goods.setCompCd(compCd);
		
        CargoService cargoService=new CargoService();
        int result=cargoService.deleteCargoGoods(goods);
        
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
