package kr.or.iei.cargo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CargoRegisterMultiServlet
 */
@WebServlet("/cargo/CargoRegisterMulti")
public class CargoRegisterMultiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargoRegisterMultiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
